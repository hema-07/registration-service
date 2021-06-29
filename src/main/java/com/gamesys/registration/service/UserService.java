package com.gamesys.registration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesys.registration.entity.User;
import com.gamesys.registration.exception.RegistrationException;
import com.gamesys.registration.modal.ErrorResponse;
import com.gamesys.registration.modal.ExclusionServiceResponse;
import com.gamesys.registration.repository.UserRepository;
import com.gamesys.registration.util.Constants;
import com.gamesys.registration.validator.UserParameterValidation;
import com.gamesys.registration.validator.ValidationResult;
import com.gamesys.registration.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import static com.gamesys.registration.util.Constants.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator[] validators;

    @Autowired
    private ExclusionService exclusionService;

    private static final Logger logger = LogManager.getLogger(ExclusionService.class);

    /**
     * AddUser method is accepting User object and calls exclusion service. Exclusion response returns list of blacklisted Users, that helps
     * to validate the user request (User object). if it is new, store it in DB else will be the existing one if we find it from DB.
     * @param user User Object from controller.
     * @return Most importantly, it finds an User against blacklisted users, it will throw an message saying blacklisted user.
     */
    public String addUser(User user) {

        try {
            if (!checkUser(user)) {

                User userDetails = userRepository.findByuserEmail( user.getUserEmail(), user.getUserDateOfBirth() );

                if (userDetails == null) {

                    user.setUserId(getRandomNumberGenerator());
                    user.setUserStatus("N");
                    userRepository.save(user);

                    return savedNewUser;

                } else {

                    return existingUserFound;
                }
            } else {

                return blackListedUserFound;
            }

        } catch (Exception e) {

            logger.debug("Error while validating user");
            logException(e);
        }

        return null;
    }

    private void logException(Exception ex) {
        if (ex instanceof JDBCException) {
            JDBCException e = (JDBCException) ex;
            logger.error( "JDBCException :: " + e.getSQLState()+
                    " Error Log :: " + ex.getMessage());
        } else {

            logger.error("Exception :: Error saving user data : "+ ex.getMessage());
        }

    }

    /**
     * Calls Exclusion service.
     * Iterate Exclusion service response against user object from request.
     * @param user User object request
     * @return checks against blacklisted list and returns true if it is blacklisted.
     */
    public Boolean checkUser(User user) {

        ExclusionServiceResponse response = exclusionService.callExclusionService().getBody();
        List<User> blackedListUsers = response.getUsers();

        if (blackedListUsers != null) {
            for (User value : blackedListUsers) {
                User user1 = value;
                if (user.getUserDateOfBirth().equalsIgnoreCase(user1.getUserDateOfBirth()) && user.getUserFirstName().equalsIgnoreCase(user1.getUserFirstName()) && user.getUserLastName().equalsIgnoreCase(user1.getUserLastName())) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     *This method will validate the User object.
     * @param user this param is a request body from endpoint
     * @return ValidationResult returns a boolean value (valid request: true; invalid request: false) with object if it is valid.
     * invalid result will return boolean value false with error code and description
     */
    public ValidationResult validate(User user) {
        User validatedUser = null;

        for (Validator validator: this.validators) {
            ValidationResult validationResult = validator.validate(user);

            if (validator instanceof UserParameterValidation) {
                validatedUser = validationResult.getUser();
            }
            if (! validationResult.isValid()) {
                return validationResult;
            }
        }
        return new ValidationResult(true, validatedUser);
    }

    /**
     * random UUID generator method
     * @return string value - UUID for UserID unique Id
     */
    public static String getRandomNumberGenerator() {

        String uuid = String.valueOf(UUID.randomUUID());

        return uuid;
    }

    public List<User> getAllUsersFromDB() {

        Iterable<User> users = userRepository.findAll();

        return (List<User>) users;
    }
}


