package by.barysevich.password.manager.repository.impl;

import static by.barysevich.password.manager.repository.api.SqlConstants.ID;
import static by.barysevich.password.manager.repository.api.SqlConstants.PASSWORD;
import static by.barysevich.password.manager.repository.api.SqlConstants.PASSWORD_LAST_UPDATE_DATE;
import static by.barysevich.password.manager.repository.api.SqlConstants.EMAIL;
import static by.barysevich.password.manager.repository.api.SqlConstants.USERNAME;

import by.barysevich.password.manager.exception.ServiceError;
import by.barysevich.password.manager.exception.ServiceException;
import by.barysevich.password.manager.model.User;
import by.barysevich.password.manager.repository.api.Specification;
import by.barysevich.password.manager.repository.specification.DeleteSpecification;
import by.barysevich.password.manager.repository.specification.InsertSpecification;
import by.barysevich.password.manager.repository.specification.UpdateSpecification;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRepository extends AbstractRepository<User, Integer> {

    public UserRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    void handleDuplicates(String field) {
        throw new ServiceException(ServiceError.USER_DUPLICATE_KEY, field);
    }

    @Override
    protected Specification insertSpecification(User entity) {
        return InsertSpecification.builder()
                .tableName(User.TABLE_NAME)
                .fieldValueEntry(USERNAME, entity.getUsername())
                .fieldValueEntry(PASSWORD, entity.getPassword())
                .fieldValueEntry(EMAIL, entity.getEmail())
                .build();
    }

    @Override
    protected RowMapper<User> mapper() {
        return (rs, rowNum) -> User.builder()
                .id(rs.getInt(ID))
                .username(rs.getString(USERNAME))
                .password(rs.getString(PASSWORD))
                .email(rs.getString(EMAIL))
                .passwordLastUpdateDate(rs.getDate(PASSWORD_LAST_UPDATE_DATE).toLocalDate())
                .build();
    }

    @Override
    protected Specification updateSpecification(Integer id, User entity) {
        var builder = UpdateSpecification.builder()
                .tableName(User.TABLE_NAME)
                .fieldValueEntry(ID, id);
        if (StringUtils.isNotBlank(entity.getUsername())) {
            builder.fieldValueEntry(USERNAME, entity.getUsername());
        }
        if (StringUtils.isNotBlank(entity.getPassword())) {
            builder.fieldValueEntry(PASSWORD, entity.getPassword());
            builder.fieldValueEntry(PASSWORD_LAST_UPDATE_DATE, LocalDate.now());
        }
        if (StringUtils.isNotBlank(entity.getEmail())) {
            builder.fieldValueEntry(EMAIL, entity.getEmail());
        }
        return builder.build();
    }

    @Override
    protected Specification deleteSpecification() {
        return new DeleteSpecification(User.TABLE_NAME);
    }

}
