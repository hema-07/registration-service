package com.gamesys.registration.validator;

import com.gamesys.registration.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResult {

    private boolean isValid;
    private String message;
    private String errorCode;
    private User user;

    public ValidationResult(boolean isValid, User user) {
        this.isValid = isValid;
        this.user = user;
    }

    public ValidationResult(boolean isValid, String message, String errorCode) {
        this.isValid = isValid;
        this.message = message;
        this.errorCode = errorCode;
    }
}
