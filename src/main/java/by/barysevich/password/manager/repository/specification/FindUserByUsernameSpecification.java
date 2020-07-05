package by.barysevich.password.manager.repository.specification;

import by.barysevich.password.manager.repository.api.Specification;

public class FindUserByUsernameSpecification implements Specification {

    /*language=SQL*/
    private static final String SQL = "SELECT * FROM service_user WHERE username = ?";

    private final String username;

    public FindUserByUsernameSpecification(String username) {
        this.username = username;
    }

    @Override
    public String sql() {
        return SQL;
    }

    @Override
    public Object[] parameters() {
        return new Object[]{username};
    }
}
