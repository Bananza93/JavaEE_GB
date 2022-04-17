package ru.geekbrains.lesson7.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CartDtoTest {

    @Autowired
    JacksonTester<CartDto> cartDtoJson;

    @Test
    void toJsonTest() throws IOException {
        CartDto dto = CartDto.builder()
                .productCount(6)
                .sumPrice(BigDecimal.valueOf(400))
                .currentCart(List.of(
                        CartPositionDto.builder()
                                .productId(1L)
                                .title("product1")
                                .price(BigDecimal.valueOf(100))
                                .qnt(2)
                                .build(),
                        CartPositionDto.builder()
                                .productId(2L)
                                .title("product2")
                                .price(BigDecimal.valueOf(50))
                                .qnt(4)
                                .build()
                ))
                .build();

        JsonContent<CartDto> json = cartDtoJson.write(dto);

        assertThat(json)
                .extractingJsonPathNumberValue("@.productCount")
                .isEqualTo(6);
        assertThat(json)
                .extractingJsonPathNumberValue("@.sumPrice")
                .isEqualTo(400);
        assertThat(json)
                .extractingJsonPathArrayValue("@.currentCart")
                .hasSize(2);
    }

    @Test
    void fromJsonTest() throws IOException {
        String jsonString = "{\"productCount\":6,"
                + "\"currentCart\":[{\"productId\":1,\"title\":\"product1\",\"price\":100,\"qnt\":2},{\"productId\":2,\"title\":\"product2\",\"price\":50,\"qnt\":4}],"
                + "\"sumPrice\":400}";

        CartDto dto = cartDtoJson.parseObject(jsonString);

        assertThat(dto.getProductCount()).isEqualTo(6);
        assertThat(dto.getSumPrice()).isEqualTo(BigDecimal.valueOf(400));
        assertThat(dto.getCurrentCart()).hasSize(2);
    }

}