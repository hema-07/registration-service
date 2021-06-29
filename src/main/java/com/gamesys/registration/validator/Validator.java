package com.gamesys.registration.validator;

import com.gamesys.registration.entity.User;

public interface Validator {

    ValidationResult validate(User user);
}
