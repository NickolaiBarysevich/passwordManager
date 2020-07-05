package by.barysevich.password.manager.util;

import org.apache.commons.lang3.StringUtils;

public final class AssertHelper {

    private AssertHelper() {
    }

    /**
     * Assert that sting is not blank.
     *
     * @param str string to be asserted
     * @param fieldName name of the field
     */
    public static void assertNotBlank(String str, String fieldName) {
        if (StringUtils.isBlank(str)) {
            throw new IllegalArgumentException(String.format("%s must not be blank!", fieldName));
        }
    }

    /**
     * Assert int number that it is positive.
     *
     * @param number to be asserted
     * @param fieldName name of the field
     */
    public static void assertPositive(int number, String fieldName) {
        if (number <= 0) {
            throw new IllegalArgumentException(String.format("%s must be positive", fieldName));
        }
    }

    /**
     * Assert long number that it is positive.
     *
     * @param number to be asserted
     * @param fieldName name of the field
     */
    public static void assertPositive(long number, String fieldName) {
        if (number <= 0) {
            throw new IllegalArgumentException(String.format("%s must be positive", fieldName));
        }
    }

    /**
     * Assert string that it is positive number.
     *
     * @param numeric string to be asserted
     * @param fieldName name of the field
     */
    public static void assertPositiveNumber(String numeric, String fieldName) {
        if (!StringUtils.isNumeric(numeric) && numeric.startsWith("-")) {
            throw new IllegalArgumentException(String.format("%s must be positive number", fieldName));
        }
    }
}
