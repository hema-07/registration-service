package com.gamesys.registration.util;

public class Constants {

    public static final String existingUser = "USER_SERVICE_ERROR_1001";
    public static final String existingUserDescription = "User is already present, Please enter new user details to register";

    public static final String blackListed = "USER_SERVICE_ERROR_1002";
    public static final String blackListedDescription = "User is BlackListed";

    public static final String missingFirstName = "USER_SERVICE_ERROR_1003";
    public static final String missingFirstNameDetails = "Missing User first name";

    public static final String missingLastName = "USER_SERVICE_ERROR_1004";
    public static final String missingLastNameDetails = "Missing User last name";

    public static final String missingDateOfBirth = "USER_SERVICE_ERROR_1005";
    public static final String missingDateOfBirthDetails = "Missing User DOB";

    public static final String missingEmail = "USER_SERVICE_ERROR_1006";
    public static final String missingEmailDetails = "Missing Email address";

    public static final String  invalidRequestParam = "USER_SERVICE_ERROR_1007";
    public static final String invalidRequestParamDetails = "Bad Request, Invalid request parameter :";

    public static final String  jdbcException = "USER_SERVICE_ERROR_1008";
    public static final String jdbcExceptionDetails = "JDBC connection exception occured :";

    public static final String  servletBindingError = "USER_SERVICE_ERROR_1009";
    public static final String servletBindingErrorDetails = "Invalid Body details :";

    public static final String mismatchLengthFirstName = "USER_SERVICE_ERROR_1010";
    public static final String mismatchLengthFirstNameDetails = "Mismatch length in column first name, should be less than 15";

    public static final String mismatchLengthLastName = "USER_SERVICE_ERROR_1011";
    public static final String mismatchLengthLastNameDetails = "Mismatch length in column last name, should be less than 15";

    public static final String mismatchLengthDateOfBirth = "USER_SERVICE_ERROR_1012";
    public static final String mismatchLengthDateOfBirthDetails = "Mismatch length in column DOB, should be less than 10";

    public static final String savedNewUser = "New User has been saved to DB";
    public static final String existingUserFound = "This is a Existing User";
    public static final String blackListedUserFound = "This is a BlackListed User";

    public static final String problemFetchingExclusionErrorCode = "USER_SERVICE_ERROR_1013";
    public static final String problemFetchingExclusionService = "There is a problem Fetching ExclusionService";

}
