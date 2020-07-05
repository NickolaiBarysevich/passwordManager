package by.barysevich.password.manager.repository.specification;

import by.barysevich.password.manager.repository.api.Specification;

public class FindCredentialsByClientIdSpecification implements Specification {

    /*language=SQL*/
    private static final String SQL = "SELECT * FROM credentials WHERE client_id = ?";

    private final int clientId;

    public FindCredentialsByClientIdSpecification(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String sql() {
        return SQL;
    }

    @Override
    public Object[] parameters() {
        return new Object[]{clientId};
    }
}
