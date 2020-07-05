package by.barysevich.password.manager.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AssertHelperTest {

    private static final String TEST_FIELD_NAME = "name";

    @Test
    public void shouldThrowOnNotPositiveInt() {
        final var negativeInt = -1;

        try {
            AssertHelper.assertPositive(negativeInt, TEST_FIELD_NAME);
        } catch (Exception e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            assertEquals(e.getMessage(), TEST_FIELD_NAME + " must be positive");
        }
    }

    @Test
    public void shouldNotThrowOnPositiveInt() {
        final var negativeInt = 1;
        AssertHelper.assertPositive(negativeInt, TEST_FIELD_NAME);
    }

    @Test
    public void shouldThrowOnNotPositiveLong() {
        final var negativeInt = -1L;

        try {
            AssertHelper.assertPositive(negativeInt, TEST_FIELD_NAME);
        } catch (Exception e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            assertEquals(e.getMessage(), TEST_FIELD_NAME + " must be positive");
        }
    }

    @Test
    public void shouldNotThrowOnPositiveLong() {
        final var negativeInt = 1L;
        AssertHelper.assertPositive(negativeInt, TEST_FIELD_NAME);
    }

    @Test
    public void shouldThrowOnBlankString() {
        final var blankString = "";

        try {
            AssertHelper.assertNotBlank(blankString, TEST_FIELD_NAME);
        } catch (Exception e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            assertEquals(e.getMessage(), TEST_FIELD_NAME + " must not be blank!");
        }
    }

    @Test
    public void shouldNotThrowOnNotBlankString() {
        final var notBlankString = "notBlank";

        AssertHelper.assertNotBlank(notBlankString, TEST_FIELD_NAME);
    }
}