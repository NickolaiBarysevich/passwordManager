package by.barysevich.password.manager.model;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    public static final String TABLE_NAME = "service_user";

    private int id;
    private String username;
    private String password;
    private String email;
    private LocalDate passwordLastUpdateDate;

    private User() {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getPasswordLastUpdateDate() {
        return passwordLastUpdateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && Objects.equals(username, user.username)
                && Objects.equals(password, user.password)
                && Objects.equals(email, user.email)
                && Objects.equals(passwordLastUpdateDate, user.passwordLastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, passwordLastUpdateDate);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final User inner;

        private Builder() {
            inner = new User();
        }

        public Builder id(int id) {
            inner.id = id;
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

        public Builder email(String email) {
            inner.email = email;
            return this;
        }

        public User build() {
            return inner;
        }
    }
}
