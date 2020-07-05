package by.barysevich.password.manager.repository.impl;

import by.barysevich.password.manager.repository.api.Repository;
import by.barysevich.password.manager.repository.api.Specification;
import by.barysevich.password.manager.repository.api.SqlConstants;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

public abstract class AbstractRepository<E, I> implements Repository<E, I> {

    private JdbcTemplate jdbcTemplate;

    public AbstractRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public I insert(E entity) {
        var keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(con -> {
                var specification = insertSpecification(entity);
                PreparedStatement preparedStatement = con.prepareStatement(specification.sql(),
                        Statement.RETURN_GENERATED_KEYS);
                prepareParameters(preparedStatement, specification.parameters());
                return preparedStatement;
            }, keyHolder);
        } catch (DuplicateKeyException e) {
            handleDuplicates(extractFieldFromExceptionMessage(e.getMessage()));
        }
        return (I) keyHolder.getKeys().get(SqlConstants.ID);
    }

    abstract void handleDuplicates(String field);

    private String extractFieldFromExceptionMessage(String message) {
        var indexOfOpenBracket = message.indexOf('(') + 1;
        var indexOfCloseBracket = message.indexOf(')');
        return message.substring(indexOfOpenBracket, indexOfCloseBracket);
    }

    protected abstract Specification insertSpecification(E entity);

    private void prepareParameters(PreparedStatement ps, Object[] parameters) throws SQLException {
        for (int i = 0; i < parameters.length; ) {
            var param = parameters[i];
            ++i;
            if (param != null) {
                ps.setObject(i, param);
            } else {
                ps.setNull(i, Types.NULL);
            }
        }
    }

    protected abstract RowMapper<E> mapper();

    @Override
    public int update(I id, E entity) {
        var specification = updateSpecification(id, entity);
        var parameters = specification.parameters();
        try {
            return parameters.length > 1
                    ? jdbcTemplate.update(specification.sql(), parameters)
                    : 0;
        } catch (DuplicateKeyException e) {
            handleDuplicates(extractFieldFromExceptionMessage(e.getMessage()));
            return 0;
        }
    }

    protected abstract Specification updateSpecification(I id, E entity);

    @Override
    public int delete(I id) {
        var specification = deleteSpecification();
        return jdbcTemplate.update(specification.sql(), id);
    }

    protected abstract Specification deleteSpecification();


    @Override
    public List<E> query(Specification specification) {
        return jdbcTemplate.query(specification.sql(), specification.parameters(), mapper());
    }

    @Override
    public Optional<E> queryForOne(Specification specification) {
        var result = query(specification);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
