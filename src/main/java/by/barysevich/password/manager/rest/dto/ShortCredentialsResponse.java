package by.barysevich.password.manager.rest.dto;

import by.barysevich.password.manager.util.AssertHelper;

public class ShortCredentialsResponse {

    private String id;
    private String title;

    private ShortCredentialsResponse() {

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private ShortCredentialsResponse inner;

        public Builder() {
            inner = new ShortCredentialsResponse();
        }

        public Builder id(String id) {
            inner.id = id;
            return this;
        }

        public Builder title(String title) {
            inner.title = title;
            return this;
        }

        /**
         * Builds a short credentials response.
         * Also checks required fields without which
         * the response will be inconsistent.
         *
         * @return credentials response
         */
        public ShortCredentialsResponse build() {
            AssertHelper.assertNotBlank(inner.id, "id");
            AssertHelper.assertNotBlank(inner.title, "title");
            return inner;
        }
    }
}
