package ru.geekbrains.lesson7.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ProductDtoTest {

    @Autowired
    private JacksonTester<ProductDto> productDtoJacksonTester;

    @Test
    void toJsonTest() throws IOException {
        ProductDto dto = new ProductDto();
        dto.setId(1L);
        dto.setName("product");
        dto.setDescription("product description");
        dto.setImageURL("https://imghosting.co/product_image.jpg");
        dto.setCategory("category");
        dto.setPrice(BigDecimal.valueOf(100.99));
        dto.setQuantity(2);

        JsonContent<ProductDto> json = productDtoJacksonTester.write(dto);

        assertThat(json)
                .extractingJsonPathNumberValue("@.id")
                .isEqualTo(1);
        assertThat(json)
                .extractingJsonPathStringValue("@.name")
                .isEqualTo("product");
        assertThat(json)
                .extractingJsonPathStringValue("@.description")
                .isEqualTo("product description");
        assertThat(json)
                .extractingJsonPathStringValue("@.imageURL")
                .isEqualTo("https://imghosting.co/product_image.jpg");
        assertThat(json)
                .extractingJsonPathStringValue("@.category")
                .isEqualTo("category");
        assertThat(json)
                .extractingJsonPathNumberValue("@.price")
                .isEqualTo(100.99);
        assertThat(json)
                .extractingJsonPathNumberValue("@.quantity")
                .isEqualTo(2);
    }

    @Test
    void fromJsonTest() throws IOException {
        String jsonString = "{\"id\":1,\"name\":\"product\",\"description\":\"product description\","
                + "\"imageURL\":\"https://imghosting.co/product_image.jpg\",\"category\":\"category\","
                + "\"quantity\":2,\"price\":100.99}";

        ProductDto dto = productDtoJacksonTester.parseObject(jsonString);

        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getName()).isEqualTo("product");
        assertThat(dto.getDescription()).isEqualTo("product description");
        assertThat(dto.getImageURL()).isEqualTo("https://imghosting.co/product_image.jpg");
        assertThat(dto.getCategory()).isEqualTo("category");
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(100.99));
        assertThat(dto.getQuantity()).isEqualTo(2);
    }
}