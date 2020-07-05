package by.barysevich.password.manager.repository.specification;

import by.barysevich.password.manager.repository.api.Specification;
import java.util.HashMap;
import java.util.Map;

public class InsertSpecification implements Specification {

    private String tableName;
    private Map<String, Object> fieldValueMap;
    private Object[] parameters;

    private InsertSpecification(String tableName, Map<String, Object> fieldValueMap) {
        this.tableName = tableName;
        this.fieldValueMap = fieldValueMap;
        placeParameters();
    }

    private void placeParameters() {
        parameters = new Object[fieldValueMap.size()];
        var index = 0;
        for (Object value : fieldValueMap.values()) {
            parameters[index++] = value;
        }
    }

    @Override
    public String sql() {
        var builder = new StringBuilder();
        builder.append("INSERT INTO ")
                .append(tableName)
                .append(" (");
        fieldValueMap.keySet().forEach(k -> builder.append(k).append(","));
        builder.deleteCharAt(builder.length() - 1)
                .append(") ")
                .append("VALUES (");
        fieldValueMap.keySet().forEach(k -> builder.append("?").append(","));
        builder.deleteCharAt(builder.length() - 1)
                .append(")");
        return builder.toString();
    }

    @Override
    public Object[] parameters() {
        return parameters;
    }

    public static Builder builder() {
        return new Builder();
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

        public InsertSpecification build() {
            return new InsertSpecification(innerTableName, innerFieldValueMap);
        }
    }
}
