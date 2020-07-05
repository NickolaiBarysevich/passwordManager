package by.barysevich.password.manager.service.validator;

import by.barysevich.password.manager.exception.ServiceException;
import by.barysevich.password.manager.util.AssertHelper;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public final class StringValidator {

    private String target;
    private boolean validateBlank;
    private int minSize;
    private int maxSize;
    private String pattern;
    private String exceptionMessage;
    private String parameterName;

    private StringValidator(String target) {
        this.target = target;
        maxSize = Integer.MAX_VALUE;
    }

    public static StringValidator getInstance(String target) {
        return new StringValidator(target);
    }

    public StringValidator notBlank() {
        validateBlank = true;
        return this;
    }

    public StringValidator min(int min) {
        minSize = min;
        return this;
    }

    public StringValidator max(int max) {
        maxSize = max;
        return this;
    }

    public StringValidator pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public StringValidator exceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        return this;
    }

    public StringValidator parameterName(String parameterName) {
        this.parameterName = parameterName;
        return this;
    }

    /**
     * Validates the target string as were specified.
     *
     */
    public void validate() {
        AssertHelper.assertNotBlank(parameterName, "parameterName");
        AssertHelper.assertNotBlank(exceptionMessage, "exceptionMessage");
        if (validateBlank && StringUtils.isBlank(target)) {
            throw ServiceException.badParameter(parameterName, exceptionMessage);
        }

        if (validateBlank && Objects.nonNull(pattern) && !target.matches(pattern)) {
            throw ServiceException.badParameter(parameterName, exceptionMessage);
        }

        if (validateBlank && (target.length() > maxSize || target.length() < minSize)) {
            throw ServiceException.badParameter(parameterName, exceptionMessage);
        }
    }
}
