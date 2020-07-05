package by.barysevich.password.manager.repository.impl;

import static by.barysevich.password.manager.repository.api.SqlConstants.CLIENT_ID;
import static by.barysevich.password.manager.repository.api.SqlConstants.DESCRIPTION;
import static by.barysevich.password.manager.repository.api.SqlConstants.ID;
import static by.barysevich.password.manager.repository.api.SqlConstants.PASSWORD;
import static by.barysevich.password.manager.repository.api.SqlConstants.PASSWORD_LAST_UPDATE_DATE;
import static by.barysevich.password.manager.repository.api.SqlConstants.TITLE;
import static by.barysevich.password.manager.repository.api.SqlConstants.USERNAME;

import by.barysevich.password.manager.model.Credentials;
import by.barysevich.password.manager.repository.api.Specification;
import by.barysevich.password.manager.repository.specification.DeleteSpecification;
import by.barysevich.password.manager.repository.specification.InsertSpecification;
import by.barysevich.password.manager.repository.specification.UpdateSpecification;
import java.time.LocalDate;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CredentialsRepository extends AbstractRepository<Credentials, String> {

    public CredentialsRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    void handleDuplicates(String field) {
        //Never happens
        //table doesn't contain unique keys
    }

    @Override
    protected Specification insertSpecification(Credentials entity) {
        return InsertSpecification.builder()
                .tableName(Credentials.TABLE_NAME)
                .fieldValueEntry(ID, UUID.randomUUID())
                .fieldValueEntry(TITLE, entity.getTitle())
                .fieldValueEntry(USERNAME, entity.getUsername())
                .fieldValueEntry(PASSWORD, entity.getPassword())
                .fieldValueEntry(DESCRIPTION, entity.getDescription())
                .fieldValueEntry(CLIENT_ID, entity.getClientId())
                .build();
    }

    @Override
    protected RowMapper<Credentials> mapper() {
        return (rs, rowNum) -> Credentials.builder()
                .id(rs.getString(ID))
                .title(rs.getString(TITLE))
                .username(rs.getString(USERNAME))
                .password(rs.getString(PASSWORD))
                .description(rs.getString(DESCRIPTION))
                .passwordLastUpdateDate(rs.getDate(PASSWORD_LAST_UPDATE_DATE).toLocalDate())
                .clientId(rs.getInt(CLIENT_ID))
                .build();
    }

    @Override
    protected Specification updateSpecification(String id, Credentials entity) {
        var builder = UpdateSpecification.builder()
                .tableName(Credentials.TABLE_NAME)
                .fieldValueEntry(ID, id);
        if (StringUtils.isNotBlank(entity.getUsername())) {
            builder.fieldValueEntry(USERNAME, entity.getUsername());
        }
        if (StringUtils.isNotBlank(entity.getPassword())) {
            builder.fieldValueEntry(PASSWORD, entity.getPassword());
            builder.fieldValueEntry(PASSWORD_LAST_UPDATE_DATE, LocalDate.now());
        }
        if (StringUtils.isNotBlank(entity.getDescription())) {
            builder.fieldValueEntry(DESCRIPTION, entity.getDescription());
        }

        if (StringUtils.isNotBlank(entity.getTitle())) {
            builder.fieldValueEntry(TITLE, entity.getTitle());
        }
        return builder.build();
    }

    @Override
    protected Specification deleteSpecification() {
        return new DeleteSpecification(Credentials.TABLE_NAME);
    }
}
