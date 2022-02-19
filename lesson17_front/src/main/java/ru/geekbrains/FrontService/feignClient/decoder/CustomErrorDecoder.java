package ru.geekbrains.FrontService.feignClient.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;
import ru.geekbrains.FrontService.feignClient.exception.FeignClientException;

import java.io.IOException;

public class CustomErrorDecoder implements ErrorDecoder {

    private final StringDecoder stringDecoder = new StringDecoder();

    @Override
    public FeignClientException decode(final String methodKey, Response response) {
        String message = "Null Response Body.";
        if (response.body() != null) {
            try {
                message = stringDecoder.decode(response, String.class).toString();
            } catch (IOException e) {
                System.out.println(methodKey + " Error Deserializing response body from failed feign request response.");
            }
        }
        return new FeignClientException(response.status(), message);
    }
}
