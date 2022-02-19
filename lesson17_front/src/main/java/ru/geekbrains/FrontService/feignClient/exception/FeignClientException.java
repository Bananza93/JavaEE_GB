package ru.geekbrains.FrontService.feignClient.exception;

public class FeignClientException extends RuntimeException {

    private final Integer status;
    private final String errorMessage;

    public FeignClientException(Integer status, String errorMessage) {
        super(String.format("%d %s", status, errorMessage));
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public Integer getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
