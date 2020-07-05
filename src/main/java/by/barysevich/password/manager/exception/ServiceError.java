package by.barysevich.password.manager.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import org.springframework.http.HttpStatus;

public enum ServiceError {
    NOT_FOUND("Resource not found", 40401, HttpStatus.NOT_FOUND),
    UNAUTHENTICATED("Not authenticated", 40301, FORBIDDEN),
    BAD_USER_CREDENTIALS("Username or password are incorrect", 40302, FORBIDDEN),
    ACCESS_DENIED("You are not authorised to access the resource", 40101, UNAUTHORIZED),
    USER_DUPLICATE_KEY("User with that %s already exists", 40002, HttpStatus.BAD_REQUEST),
    METHOD_NOT_SUPPORTED("Method '%s' not supported", 40501, METHOD_NOT_ALLOWED),
    MEDIA_TYPE_NOT_SUPPORTED("Media type '%s' not supported", 41501, UNSUPPORTED_MEDIA_TYPE),
    MEDIA_TYPE_NOT_ACCEPTABLE("Media type is not acceptable", 40601, NOT_ACCEPTABLE),
    INTERNAL_ERROR("Internal error", 50001, INTERNAL_SERVER_ERROR),
    BAD_REQUEST("BAD_REQUEST", 40001, HttpStatus.BAD_REQUEST),
    BAD_PARAMETER("%s has bad format: '%s'", 40003, HttpStatus.BAD_REQUEST);

    private String message;
    private int serviceCode;
    private HttpStatus status;

    ServiceError(String message, int serviceCode, HttpStatus status) {
        this.message = message;
        this.serviceCode = serviceCode;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
