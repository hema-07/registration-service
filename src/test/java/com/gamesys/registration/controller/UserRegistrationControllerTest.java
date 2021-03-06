package com.gamesys.registration.controller;

import com.gamesys.registration.entity.User;
import com.gamesys.registration.modal.ErrorResponse;
import com.gamesys.registration.service.UserService;
import com.gamesys.registration.util.Constants;
import com.gamesys.registration.validator.ValidationResult;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.gamesys.registration.util.Constants.blackListed;
import static com.gamesys.registration.util.Constants.blackListedDescription;

@SpringBootTest
public class UserRegistrationControllerTest {

    @InjectMocks
    UserRegistrationController userRegistrationController;

    @Mock
    UserService userService;

    @Test
    public void addUser_success_scenario() {
        User user = User.builder()
                .userId("2")
                .userFirstName("Hemavathi")
                .userLastName("Tamil")
                .userDateOfBirth("30/6/95")
                .userCountry("UK")
                .userEmail("hema34@gmail.com")
                .build();
        ValidationResult validationResult = new ValidationResult();
        validationResult.setValid(true);
        Mockito.when(userService.validate(user)).thenReturn(validationResult);
        Mockito.when(userService.addUser(user)).thenReturn(Constants.savedNewUser);
        ResponseEntity<?> responseEntity = userRegistrationController.addUser(user);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void addUser_for_existing_user_Failure_scenario() {
        User user = User.builder()
                .userId("5")
                .userFirstName("Hema")
                .userLastName("Tamil")
                .userDateOfBirth("30/6/95")
                .userCountry("UK")
                .userEmail("hema@gmail.com")
                .build();
        ValidationResult validationResult = new ValidationResult();
        validationResult.setValid(true);
        Mockito.when(userService.validate(user)).thenReturn(validationResult);
        Mockito.when(userService.addUser(user)).thenReturn(Constants.existingUserFound);
        ResponseEntity<?> responseEntity = userRegistrationController.addUser(user);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

    }

    @Test
    public void addUser_for_blacklist_user_Failure_scenario() {
        User user = User.builder()
                .userFirstName("Hema")
                .userLastName("Tamil")
                .userDateOfBirth("30/6/95")
                .userCountry("UK")
                .userEmail("hema@gmail.com")
                .build();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(blackListed)
                .errorDescription(blackListedDescription)
                .build();
        ValidationResult validationResult = new ValidationResult();
        validationResult.setValid(true);
        Mockito.when(userService.validate(user)).thenReturn(validationResult);
        Mockito.when(userService.addUser(user)).thenReturn(Constants.blackListedUserFound);
        ResponseEntity<?> responseEntity = userRegistrationController.addUser(user);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());


    }

    @Test
    public void getAllUsers_success_scenario() {
        ResponseEntity<?> responseEntity = userRegistrationController.getUsers();
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}