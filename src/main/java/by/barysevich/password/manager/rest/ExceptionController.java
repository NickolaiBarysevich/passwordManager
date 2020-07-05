package by.barysevich.password.manager.rest;

import static by.barysevich.password.manager.exception.ServiceError.BAD_REQUEST;
import static by.barysevich.password.manager.exception.ServiceError.BAD_USER_CREDENTIALS;
import static by.barysevich.password.manager.exception.ServiceError.INTERNAL_ERROR;
import static by.barysevich.password.manager.exception.ServiceError.MEDIA_TYPE_NOT_ACCEPTABLE;
import static by.barysevich.password.manager.exception.ServiceError.MEDIA_TYPE_NOT_SUPPORTED;
import static by.barysevich.password.manager.exception.ServiceError.METHOD_NOT_SUPPORTED;
import static by.barysevich.password.manager.exception.ServiceError.NOT_FOUND;

import by.barysevich.password.manager.exception.ServiceError;
import by.barysevich.password.manager.exception.ServiceException;
import by.barysevich.password.manager.rest.dto.ExceptionResponse;

import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@RestController
public class ExceptionController extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceError(ServiceException e) {
        var serviceError = e.getError();
        return buildResponse(serviceError, e.getParameters());
    }

    private ResponseEntity<Object> buildResponse(ServiceError serviceError, Object... parameters) {
        var message = Objects.isNull(parameters) || parameters.length == 0
                ? serviceError.getMessage()
                : String.format(serviceError.getMessage(), parameters);
        var response = ExceptionResponse.builder()
                .message(message)
                .serviceCode(serviceError.getServiceCode())
                .build();
        return ResponseEntity.status(serviceError.getStatus()).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e) {
        return buildResponse(BAD_USER_CREDENTIALS);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleInternalError(Throwable t) {
        return buildResponse(INTERNAL_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        return buildResponse(METHOD_NOT_SUPPORTED, ex.getMethod());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request) {
        return buildResponse(MEDIA_TYPE_NOT_SUPPORTED, ex.getContentType());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status,
                                                                      WebRequest request) {
        return buildResponse(MEDIA_TYPE_NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        return buildResponse(NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        return buildResponse(INTERNAL_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return buildResponse(BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        return buildResponse(BAD_REQUEST);
    }
}
