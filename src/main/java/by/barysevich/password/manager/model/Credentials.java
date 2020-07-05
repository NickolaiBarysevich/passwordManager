package by.barysevich.password.manager.model;

import java.time.LocalDate;
import java.util.Objects;

public class Credentials {

    public static final String TABLE_NAME = "credentials";

    private String id;
    private String title;
    private String username;
    private String password;
    private String description;
    private LocalDate passwordLastUpdateDate;
    private long clientId;

    private Credentials() {
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

    public LocalDate getPasswordLastUpdateDate() {
        return passwordLastUpdateDate;
    }

    public long getClientId() {
        return clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Credentials that = (Credentials) o;
        return id == that.id
                && clientId == that.clientId
                && Objects.equals(title, that.title)
                && Objects.equals(username, that.username)
                && Objects.equals(password, that.password)
                && Objects.equals(description, that.description)
                && Objects.equals(passwordLastUpdateDate, that.passwordLastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, username, password, description, passwordLastUpdateDate, clientId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Credentials inner;

        private Builder() {
            inner = new Credentials();
        }

        public Builder id(String id) {
            inner.id = id;
            return this;
        }

        public Builder title(String title) {
            inner.title = title;
            return this;
        }

        public Builder username(String username) {
            inner.username = username;
            return this;
        }

        public Builder password(String password) {
            inner.password = password;
            return this;
        }

        public Builder passwordLastUpdateDate(LocalDate passwordLastUpdateDate) {
            inner.passwordLastUpdateDate = passwordLastUpdateDate;
            return this;
        }

        public Builder description(String description) {
            inner.description = description;
            return this;
        }

        public Builder clientId(long clientId) {
            inner.clientId = clientId;
            return this;
        }

        public Credentials build() {
            return inner;
        }
    }
}
