package by.barysevich.password.manager.rest.dto;

import by.barysevich.password.manager.util.AssertHelper;

public class ExceptionResponse {

    private int serviceCode;
    private String message;
    private long timestamp;

    private ExceptionResponse() {
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private ExceptionResponse inner;

        private Builder() {
            inner = new ExceptionResponse();
        }

        public Builder serviceCode(int serviceCode) {
            inner.serviceCode = serviceCode;
            return this;
        }

        public Builder message(String message) {
            inner.message = message;
            return this;
        }


        /**
         * Builds an exception response.
         * Also checks required fields without which
         * the response will be inconsistent.
         *
         * @return credentials response
         */
        public ExceptionResponse build() {
            AssertHelper.assertNotBlank(inner.message, "message");
            AssertHelper.assertPositive(inner.serviceCode, "serviceCode");
            inner.timestamp = System.currentTimeMillis();
            return inner;
        }
    }
}
