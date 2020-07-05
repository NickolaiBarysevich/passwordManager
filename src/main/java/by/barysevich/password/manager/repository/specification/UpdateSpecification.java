package by.barysevich.password.manager.repository.specification;

import by.barysevich.password.manager.repository.api.Specification;
import by.barysevich.password.manager.repository.api.SqlConstants;
import by.barysevich.password.manager.util.AssertHelper;
import java.util.HashMap;
import java.util.Map;

public class UpdateSpecification implements Specification {

    private String tableName;
    private Map<String, Object> fieldValueMap;
    private Object[] parameters;

    private UpdateSpecification(String tableName, Map<String, Object> fieldValueMap) {
        this.tableName = tableName;
        this.fieldValueMap = fieldValueMap;
        placeParameters();
    }

    private void placeParameters() {
        var id = fieldValueMap.get(SqlConstants.ID);
        AssertHelper.assertPositiveNumber(id.toString(), "id");
        parameters = new Object[fieldValueMap.size()];
        var index = 0;
        for (Object value : fieldValueMap.values()) {
            if (!value.equals(id)) {
                parameters[index++] = value;
            }
        }
        parameters[index] = id;
    }

    @Override
    public String sql() {
        var builder = new StringBuilder();
        builder.append("UPDATE ")
                .append(tableName)
                .append(" SET ");
        fieldValueMap.keySet().forEach(k -> {
            if (!k.equals(SqlConstants.ID)) {
                builder.append(k).append("=?,");
            }
        });
        builder.deleteCharAt(builder.length() - 1)
                .append(" WHERE id=?");
        return builder.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Object[] parameters() {
        return parameters;
    }

    public static final class Builder {
        private String innerTableName;
        private Map<String, Object> innerFieldValueMap;

        private Builder() {
            innerFieldValueMap = new HashMap<>();
        }

        public Builder tableName(String tableName) {
            innerTableName = tableName;
            return this;
        }

        public Builder fieldValueEntry(String field, Object value) {
            innerFieldValueMap.put(field, value);
            return this;
        }

        public Builder filedValueMap(Map<String, Object> fieldValeMap) {
            innerFieldValueMap = new HashMap<>(fieldValeMap);
            return this;
        }

        public UpdateSpecification build() {
            return new UpdateSpecification(innerTableName, innerFieldValueMap);
        }
    }
}
