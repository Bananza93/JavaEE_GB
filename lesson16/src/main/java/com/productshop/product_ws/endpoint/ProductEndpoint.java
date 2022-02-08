package com.productshop.product_ws.endpoint;

import com.productshop.api.products.CategoryInfo;
import com.productshop.api.products.GetProductsRequest;
import com.productshop.api.products.GetProductsResponse;
import com.productshop.api.products.ProductInfo;
import com.productshop.product_ws.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class ProductEndpoint {

    private final ProductService productService;

    public ProductEndpoint(ProductService productService) {
        this.productService = productService;
    }

    @PayloadRoot(namespace = "http://productshop.com/api/products", localPart = "getProductsRequest")
    @ResponsePayload
    public GetProductsResponse getProducts(@RequestPayload GetProductsRequest request) {
        GetProductsResponse response = new GetProductsResponse();
        List<ProductInfo> productInfoList = response.getProduct();
        productService.fillListWithProducts(request.getMinPrice(), request.getMaxPrice())
                .stream()
                .map(e -> {
                    CategoryInfo categoryInfo = new CategoryInfo();
                    BeanUtils.copyProperties(e.getCategory(), categoryInfo);

                    ProductInfo productInfo = new ProductInfo();
                    BeanUtils.copyProperties(e, productInfo);
                    productInfo.setCategory(categoryInfo);

                    return productInfo;
                }).forEachOrdered(productInfoList::add);
        return response;
    }
}
