package com.gamesys.registration.validator;

import com.gamesys.registration.entity.User;
import org.springframework.stereotype.Component;
import static com.gamesys.registration.util.Constants.*;

@Component
public class UserParameterValidation implements Validator{

    /**
     * This method validates the Request body
     * @param user unvalidated request object from controller
     * @return when the invalid request enters, it returns boolean value false with error code.
     * When the valid request enters, it returns boolean true with validated User object.
     */
    @Override
    public ValidationResult validate(User user) {

        if (user.getUserFirstName()==null || user.getUserFirstName().equals("") || user.getUserFirstName().isBlank()) {
            return new ValidationResult(false, missingFirstName, missingFirstNameDetails);
        }
        if (user.getUserFirstName().length() > 15) {
            return new ValidationResult(false, mismatchLengthFirstName, mismatchLengthFirstNameDetails);
        }
        if (user.getUserLastName() == null || user.getUserLastName().equals("")) {
            return new ValidationResult(false, missingLastName, missingLastNameDetails);
        }
        if (user.getUserLastName().length() > 15) {
            return new ValidationResult(false, mismatchLengthLastName, mismatchLengthLastNameDetails);
        }
        if (user.getUserDateOfBirth() == null || user.getUserDateOfBirth().equals("")) {
            return new ValidationResult(false, missingDateOfBirth, missingDateOfBirthDetails);
        }
        if (user.getUserDateOfBirth().length() > 10) {
            return new ValidationResult(false, mismatchLengthDateOfBirth, mismatchLengthDateOfBirthDetails);
        }
        if (user.getUserEmail() == null || user.getUserEmail().equals("")) {
            return new ValidationResult(false, missingEmail, missingEmailDetails);
        }

        return new ValidationResult(true, user);
    }
}
