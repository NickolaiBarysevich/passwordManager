package by.barysevich.password.manager.rest.dto;

import by.barysevich.password.manager.util.AssertHelper;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class CredentialsResponse {

    private String id;
    private String title;
    private String username;
    private String password;
    private String description;
    private LocalDate passwordLastUpdateDate;

    private CredentialsResponse() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDescription() {
        return description;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalDate getPasswordLastUpdateDate() {
        return passwordLastUpdateDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private CredentialsResponse inner;

        public Builder() {
            inner = new CredentialsResponse();
        }

        public Builder id(String id) {
            inner.id = id;
            return this;
        }

        public Builder username(String username) {
            inner.username = username;
            return this;
        }

        public Builder title(String title) {
            inner.title = title;
            return this;
        }

        public Builder encodedPassword(String encodedPassword) {
            inner.password = encodedPassword;
            return this;
        }

        public Builder description(String description) {
            inner.description = description;
            return this;
        }

        public Builder passwordLastUpdateDate(LocalDate passwordLastUpdateDate) {
            inner.passwordLastUpdateDate = passwordLastUpdateDate;
            return this;
        }

        /**
         * Builds a credentials response.
         * Also checks required fields without which
         * the response will be inconsistent.
         *
         * @return credentials response
         */
        public CredentialsResponse build() {
            AssertHelper.assertNotBlank(inner.id, "id");
            AssertHelper.assertNotBlank(inner.title, "title");
            return inner;
        }
    }
}
