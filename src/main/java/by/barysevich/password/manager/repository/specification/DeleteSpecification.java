package by.barysevich.password.manager.repository.specification;

import by.barysevich.password.manager.repository.api.Specification;

public class DeleteSpecification implements Specification {

    /*language=SQL*/
    private static final String DELETE_QUERY = "DELETE FROM %s WHERE id = ?";

    private final String tableName;

    public DeleteSpecification(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String sql() {
        return String.format(DELETE_QUERY, tableName);
    }

    @Override
    public Object[] parameters() {
        return new Object[0];
    }
}
