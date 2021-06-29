package com.gamesys.registration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gamesys.registration.entity.User;
import com.gamesys.registration.modal.ErrorResponse;
import com.gamesys.registration.service.UserService;
import com.gamesys.registration.validator.ValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gamesys.registration.util.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;


/**
 *
 */
@RestController
public class UserRegistrationController {


    @Autowired
    private UserService userService;

    private static final Logger logger = LogManager.getLogger(UserRegistrationController.class);

    /**
     *This method validates the request and find it from db. if it is new valid user detail, it will store it in DB.
     * @param user it has 6 params : user id, first / last name, date of birth, email, country
     * @return validated request will save into db, invalid request will throw an error response.
     * when entered user is already present in db or blacklisted, it will throw an error response with errorCode.
     */
    @RequestMapping(value = "/user", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> addUser(@RequestBody User user) {

        ValidationResult validationResult = userService.validate(user);

        if (!validationResult.isValid()) {
            return new ResponseEntity<>(validationResult, HttpStatus.BAD_REQUEST);
        }

        String userStatement = userService.addUser(user);

        if (userStatement.equalsIgnoreCase(savedNewUser)) {

            return new ResponseEntity<>( user, HttpStatus.CREATED);

        } else if (userStatement.equalsIgnoreCase(blackListedUserFound)) {

            logger.error("User is Blacklisted");
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .errorCode(blackListed)
                    .errorDescription(blackListedDescription)
                    .build();
            return new ResponseEntity<>( errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        } else if (userStatement.equalsIgnoreCase(existingUserFound)) {

            logger.error("User is already present in DB");
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .errorCode(existingUser)
                    .errorDescription(existingUserDescription)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

            return new ResponseEntity<>( user, HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = GET, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> getUsers() {

        List<User> allUsersFromDB = userService.getAllUsersFromDB();
        return new ResponseEntity<>( allUsersFromDB, HttpStatus.OK);
    }

}
