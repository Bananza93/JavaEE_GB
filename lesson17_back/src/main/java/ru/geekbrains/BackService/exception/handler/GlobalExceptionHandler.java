package ru.geekbrains.BackService.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.geekbrains.BackService.exception.CategoryNotFoundException;
import ru.geekbrains.BackService.exception.ProductAlreadyExistsException;
import ru.geekbrains.ResponseDto;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseBody ResponseDto categoryNotFoundExceptionHandler(HttpServletRequest request, Exception e) {
        return new ResponseDto("URL: " + request.getRequestURI() + " | error: " + e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductAlreadyExistsException.class)
    @ResponseBody ResponseDto productAlreadyExistsExceptionHandler(HttpServletRequest request, Exception e) {
        return new ResponseDto("URL: " + request.getRequestURI() + " | error: " + e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody ResponseDto exceptionHandler(HttpServletRequest request) {
        return new ResponseDto("URL: " + request.getRequestURI() + " | error: Internal server error. Try again later.");
    }
}
