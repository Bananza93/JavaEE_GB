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
class CartPositionDtoTest {

    @Autowired
    private JacksonTester<CartPositionDto> cartPositionDtoJsonTester;

    @Test
    void toJsonTest() throws IOException {
        CartPositionDto dto = CartPositionDto.builder()
                .productId(1L)
                .title("product")
                .price(BigDecimal.valueOf(100.99))
                .qnt(2)
                .build();

        JsonContent<CartPositionDto> positionJson = cartPositionDtoJsonTester.write(dto);

        assertThat(positionJson)
                .extractingJsonPathNumberValue("@.productId")
                .isEqualTo(1);
        assertThat(positionJson)
                .extractingJsonPathStringValue("@.title")
                .isEqualTo("product");
        assertThat(positionJson)
                .extractingJsonPathNumberValue("@.price")
                .isEqualTo(100.99);
        assertThat(positionJson)
                .extractingJsonPathNumberValue("@.qnt")
                .isEqualTo(2);
    }

    @Test
    void fromJsonTest() throws IOException {
        String jsonString = "{\"productId\":1,\"title\":\"product\",\"price\":100.99,\"qnt\":2}";

        CartPositionDto dto = cartPositionDtoJsonTester.parseObject(jsonString);

        assertThat(dto.getProductId()).isEqualTo(1);
        assertThat(dto.getTitle()).isEqualTo("product");
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(100.99));
        assertThat(dto.getQnt()).isEqualTo(2);
    }
}