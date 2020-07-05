package by.barysevich.password.manager.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.barysevich.password.manager.configuration.TestConfig;
import by.barysevich.password.manager.exception.ServiceError;
import by.barysevich.password.manager.exception.ServiceException;
import by.barysevich.password.manager.model.User;
import by.barysevich.password.manager.repository.specification.FindUserByUsernameSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(classes = TestConfig.class)
public class UserRepositoryTest {

    private UserRepository repository;

    @Autowired
    public UserRepositoryTest(JdbcTemplate jdbcTemplate) {
        repository = new UserRepository(jdbcTemplate);
    }

    @Test
    public void shouldFindByUsername() {
        final var expectedUsername = "user1";

        var actual = repository.queryForOne(new FindUserByUsernameSpecification(expectedUsername)).orElse(null);

        assertNotNull(actual);
        assertEquals(expectedUsername, actual.getUsername());
    }

    @Test
    public void shouldInsertUserAndReturnId() {
        final var testUser = User.builder()
                .username("user5")
                .password("password5")
                .email("phone5")
                .build();

        var actualId = repository.insert(testUser);

        assertTrue(actualId > 4);
    }

    @Test
    public void shouldThrowServiceExceptionOnDuplicate() {
        final var testUser = User.builder()
                .username("user1")
                .password("password1")
                .email("phone1")
                .build();

        try {
            repository.insert(testUser);
        } catch (Exception e) {
            assertEquals(ServiceException.class, e.getClass());
            var serviceException = (ServiceException) e;
            var actualError = serviceException.getError();
            assertEquals(ServiceError.USER_DUPLICATE_KEY, actualError);
        }
    }

    @Test
    public void shouldUpdateUserAndReturnAffectedRows() {
        final var expectedRowsAffected = 1;
        final var testId = 2;
        final var testUser = User.builder()
                .username("user2_2")
                .password("password2_2")
                .email("phone2_2")
                .build();

        var actual = repository.update(testId, testUser);

        assertEquals(expectedRowsAffected, actual);
    }

    @Test
    public void shouldNotUpdateUserOnNullFieldsAndReturnAffectedRows() {
        final var expectedRowsAffected = 0;
        final var testId = 2;
        final var testUser = User.builder()
                .build();

        var actual = repository.update(testId, testUser);

        assertEquals(expectedRowsAffected, actual);
    }

    @Test
    public void shouldThrowServiceExceptionOnUpdateDuplicate() {
        final var testId = 2;
        final var testUser = User.builder()
                .username("user1")
                .password("password2")
                .email("phone2")
                .build();

        try {
            repository.update(testId, testUser);
        } catch (Exception e) {
            assertEquals(ServiceException.class, e.getClass());
            var serviceException = (ServiceException) e;
            var actualError = serviceException.getError();
            assertEquals(ServiceError.USER_DUPLICATE_KEY, actualError);
        }
    }

    @Test
    public void shouldDeleteUserAndReturnAffectedRows() {
        final var expectedAffectedRows = 1;
        final var testId = 3;

        final var actual = repository.delete(testId);

        assertEquals(expectedAffectedRows, actual);
    }

    @Test
    public void shouldNotDeleteUserOnNotExistingIdAndReturnAffectedRows() {
        final var expectedAffectedRows = 0;
        final var testId = 0;

        final var actual = repository.delete(testId);

        assertEquals(expectedAffectedRows, actual);
    }
}
