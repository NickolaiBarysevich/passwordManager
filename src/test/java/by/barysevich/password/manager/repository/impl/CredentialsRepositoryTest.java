package by.barysevich.password.manager.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.barysevich.password.manager.configuration.TestConfig;
import by.barysevich.password.manager.model.Credentials;
import by.barysevich.password.manager.repository.specification.FindCredentialsByClientIdSpecification;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(classes = TestConfig.class)
public class CredentialsRepositoryTest {

    private CredentialsRepository repository;

    @Autowired
    public CredentialsRepositoryTest(JdbcTemplate jdbcTemplate) {
        repository = new CredentialsRepository(jdbcTemplate);
    }

    @Test
    public void shouldInsertCredentialsAndReturnId() {
        final var testCredentials = Credentials.builder()
                .title("test title")
                .clientId(1L)
                .build();

        var id = repository.insert(testCredentials);

        assertTrue(StringUtils.isNotBlank(id));
    }

    @Test
    public void shouldUpdateCredentialsAndReturnAffectedRows() {
        final var expectedAffectedRows = 1;
        final var testId = "f7ff04ea-ba74-4c78-af95-4d7fd55c84ad";
        final var testCredentials = Credentials.builder()
                .description("new description")
                .build();

        var actual = repository.update(testId, testCredentials);

        assertEquals(expectedAffectedRows, actual);
    }

    @Test
    public void shouldDeleteCredentialsAndReturnAffectedRows() {
        final var expectedAffectedRows = 1;
        final var testId = "cb7dba31-53d9-49cc-8df7-a669cfc6e209";

        var actual = repository.delete(testId);

        assertEquals(expectedAffectedRows, actual);
    }

    @Test
    public void shouldQueryCredentialsByClientId() {
        final var testClientId = 2;
        final var expectedSize = 2;

        var actual = repository.query(new FindCredentialsByClientIdSpecification(testClientId));

        assertEquals(expectedSize, actual.size());
        actual.forEach(c -> assertEquals(testClientId, c.getClientId()));
    }

}