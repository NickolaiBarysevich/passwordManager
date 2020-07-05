package by.barysevich.password.manager.exception;

import static by.barysevich.password.manager.exception.ServiceError.BAD_PARAMETER;
import static by.barysevich.password.manager.exception.ServiceError.NOT_FOUND;

public class ServiceException extends RuntimeException {

    private ServiceError error;
    private Object[] parameters;

    public ServiceException(Throwable originalException, ServiceError error) {
        super(originalException);
        this.error = error;
    }

    public ServiceException(Throwable originalException, ServiceError error, Object... parameters) {
        super(originalException);
        this.error = error;
        this.parameters = parameters;
    }

    public ServiceException(ServiceError error, Object... parameters) {
        this.error = error;
        this.parameters = parameters;
    }

    public ServiceError getError() {
        return error;
    }

    public static ServiceException notFound() {
        return new ServiceException(NOT_FOUND);
    }

    public static ServiceException badParameter(String parameter, String message) {
        return new ServiceException(BAD_PARAMETER, parameter, message);
    }

    @Override
    public String getMessage() {
        return error.getMessage();
    }

    public Object[] getParameters() {
        return parameters;
    }
}
