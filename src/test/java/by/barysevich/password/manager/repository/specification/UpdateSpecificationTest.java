package by.barysevich.password.manager.repository.specification;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UpdateSpecificationTest {

    private static final String TEST_TABLE_NAME = "table";
    private static final Map<String, Object> TEST_FIELDS_VALUES = new HashMap<>();

    public static final String EXPECTED_SQL = "UPDATE table SET description=?,title=? WHERE id=?";
    public static final Object[] EXPECTED_PARAMETERS = {"descr", "test", 10};

    /**
     * Sets up the test.
     */
    @BeforeAll
    public static void setUp() {
        TEST_FIELDS_VALUES.put("id", 10);
        TEST_FIELDS_VALUES.put("title", "test");
        TEST_FIELDS_VALUES.put("description", "descr");
    }

    @Test
    public void shouldCreateUpdateQuery() {
        var specification = UpdateSpecification.builder()
                .tableName(TEST_TABLE_NAME)
                .filedValueMap(TEST_FIELDS_VALUES)
                .build();

        final var actual = specification.sql();

        assertEquals(EXPECTED_SQL, actual);
    }

    @Test
    public void shouldCorrectlyPlaceParameters() {
        var specification = UpdateSpecification.builder()
                .tableName(TEST_TABLE_NAME)
                .filedValueMap(TEST_FIELDS_VALUES)
                .build();

        final var actual = specification.parameters();

        assertArrayEquals(EXPECTED_PARAMETERS, actual);
    }

}