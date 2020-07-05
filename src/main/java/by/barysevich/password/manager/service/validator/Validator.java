package by.barysevich.password.manager.service.validator;

public interface Validator<T> {

    void validate(T object) throws RuntimeException;
}
