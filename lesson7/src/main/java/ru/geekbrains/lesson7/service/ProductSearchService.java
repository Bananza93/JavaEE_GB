package ru.geekbrains.lesson7.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.analysis.Analyzer;
import co.elastic.clients.elasticsearch._types.analysis.CustomAnalyzer;
import co.elastic.clients.elasticsearch._types.analysis.NGramTokenFilter;
import co.elastic.clients.elasticsearch._types.analysis.TokenFilter;
import co.elastic.clients.elasticsearch._types.analysis.TokenFilterDefinition;
import co.elastic.clients.elasticsearch._types.mapping.LongNumberProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TextProperty;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import co.elastic.clients.elasticsearch.indices.IndexSettingsAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.geekbrains.lesson7.dto.ProductSearchDoc;
import ru.geekbrains.lesson7.model.Product;
import ru.geekbrains.lesson7.repository.ProductRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ProductSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSearchService.class);
    private static final String INDEX_NAME = "products";
    private final ElasticsearchClient client;
    private final ProductRepository productRepository;

    public ProductSearchService(ElasticsearchClient client, ProductRepository productRepository) {
        this.client = client;
        this.productRepository = productRepository;
    }

    private boolean createIndex() {
        ExistsRequest existsRequest = new ExistsRequest.Builder().index(INDEX_NAME).build();
        try {
            if (client.indices().exists(existsRequest).value()) return true;
        } catch (IOException e) {
            LOGGER.error("Check if index exists failure: cause = " + e.getCause() + ", message = " + e.getMessage(), e);
        }
        CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder()
                .settings(new IndexSettings.Builder()
                        .analysis(new IndexSettingsAnalysis.Builder()
                                .filter("autocomplete_filter", new TokenFilter.Builder()
                                        .definition(new TokenFilterDefinition.Builder()
                                                .ngram(new NGramTokenFilter.Builder()
                                                        .minGram(1)
                                                        .maxGram(10)
                                                        .build())
                                                .build())
                                        .build())
                                .analyzer("autocomplete", new Analyzer.Builder()
                                        .custom(new CustomAnalyzer.Builder()
                                                .tokenizer("standard")
                                                .filter(List.of("lowercase", "autocomplete_filter"))
                                                .build())
                                        .build())
                                .build())
                        .maxNgramDiff(10)
                        .build())
                .mappings(new TypeMapping.Builder()
                        .properties(Map.of(
                                "id", new Property.Builder()
                                        .long_(new LongNumberProperty.Builder()
                                                .build())
                                        .build(),
                                "name", new Property.Builder()
                                        .text(new TextProperty.Builder()
                                                .analyzer("autocomplete")
                                                .searchAnalyzer("standard")
                                                .build())
                                        .build()))
                        .build())
                .build();
        try {
            CreateIndexResponse response = client.indices().create(createIndexRequest);
            if (Boolean.TRUE.equals(response.acknowledged())) {
                return true;
            }
        } catch (IOException e) {
            LOGGER.error("Create index failure: cause = " + e.getCause() + ", message = " + e.getMessage(), e);
        }
        return false;
    }

    public void indexProducts(List<Product> products) {
        if (createIndex()) {
            List<BulkOperation> operations = products.stream()
                    .map(p -> new ProductSearchDoc(p.getId(), p.getName()))
                    .map(doc -> new IndexOperation.Builder<ProductSearchDoc>()
                            .index(INDEX_NAME)
                            .document(doc)
                            .id(doc.getId().toString())
                            .build())
                    .map(op -> new BulkOperation.Builder().index(op).build())
                    .toList();
            BulkRequest bulkRequest = new BulkRequest.Builder().operations(operations).build();
            try {
                client.bulk(bulkRequest);
            } catch (IOException e) {
                LOGGER.error("Product indexing failure: cause = " + e.getCause() + ", message = " + e.getMessage(), e);
            }
        }
    }

    public void indexProduct(Product product) {
        if (createIndex()) {
            ProductSearchDoc doc = new ProductSearchDoc(product.getId(), product.getName());
            IndexRequest<ProductSearchDoc> request = new IndexRequest.Builder<ProductSearchDoc>()
                    .index(INDEX_NAME)
                    .document(doc)
                    .id(doc.getId().toString())
                    .build();
            try {
                client.index(request);
            } catch (IOException e) {
                LOGGER.error("Product indexing failure: cause = " + e.getCause() + ", message = " + e.getMessage(), e);
            }
        }
    }

    public void deleteProduct(Long id) {
        DeleteRequest request = new DeleteRequest.Builder()
                .index(INDEX_NAME)
                .id(id.toString())
                .build();
        try {
            client.delete(request);
        } catch (IOException e) {
            LOGGER.error("Product deleting failure: cause = " + e.getCause() + ", message = " + e.getMessage(), e);
        }
    }

    public List<Product> searchProducts(String searchString) {
        SearchRequest request = new SearchRequest.Builder()
                .index(INDEX_NAME)
                .query(new Query.Builder()
                        .match(new MatchQuery.Builder()
                                .query(searchString)
                                .field("name")
                                .build())
                        .build())
                .build();
        List<Product> products = Collections.emptyList();
        try {
            SearchResponse<ProductSearchDoc> docs = client.search(request, ProductSearchDoc.class);
            List<Long> ids = docs.hits().hits()
                    .stream()
                    .filter(hit -> hit.source() != null)
                    .map(hit -> hit.source().getId())
                    .toList();
            if (!ids.isEmpty()) {
                products = productRepository.getProductsById(ids);
            }
        } catch (IOException e) {
            LOGGER.error("Product searching failure: cause = " + e.getCause() + ", message = " + e.getMessage(), e);
        }
        return products;
    }
}
