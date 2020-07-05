package by.barysevich.password.manager.repository.specification;

import by.barysevich.password.manager.repository.api.Specification;

public class FindCredentialsByIdAndClientIdSpecification implements Specification {

    /*language=SQL*/
    private static final String SQL = "SELECT * FROM credentials WHERE id = ? AND client_id = ?";

    private String id;
    private int clientId;

    public FindCredentialsByIdAndClientIdSpecification(String id, int clientId) {
        this.id = id;
        this.clientId = clientId;
    }

    @Override
    public String sql() {
        return SQL;
    }

    @Override
    public Object[] parameters() {
        return new Object[]{id, clientId};
    }
}
