package by.barysevich.password.manager.repository.specification;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DeleteSpecificationTest {

    private static final String TEST_TABLE_NAME = "table";
    private static final String EXPECTED_SQL = "DELETE FROM table WHERE id = ?";

    @Test
    public void shouldReturnQueryWithTableName() {
        var specification = new DeleteSpecification(TEST_TABLE_NAME);

        final var actual = specification.sql();

        assertEquals(EXPECTED_SQL, actual);
    }
}