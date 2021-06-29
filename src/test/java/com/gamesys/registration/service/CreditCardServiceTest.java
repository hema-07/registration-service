package com.gamesys.registration.service;

import com.gamesys.registration.entity.User;
import com.gamesys.registration.repository.UserRepository;
import com.gamesys.registration.validator.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static com.gamesys.registration.util.Constants.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class CreditCardServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void addNewCard_success_scenario() {
        User user = User.builder()
                .userFirstName("Hema")
                .userLastName("Tamil")
                .userDateOfBirth("30/6/95")
                .userCountry("UK")
                .userEmail("hema@gmail.com")
                .build();
        String addUser = userService.addUser(user);

        assertEquals(savedNewUser, addUser);
    }



    @Test
    public void getAllUsersList_failure() {
        userRepository.deleteAll();
        List<User> allCreditCardList = userService.getAllUsersFromDB();
        assertEquals(0, allCreditCardList.size());

    }

    @Test
    public void validate() {
        User user = User.builder()
                .userFirstName("Hema")
                .userLastName("Tamil")
                .userDateOfBirth("30/6/95")
                .userCountry("UK")
                .userEmail("hema@gmail.com")
                .build();
        ValidationResult validate = userService.validate(user);
        assertEquals(true, validate.isValid());
    }
}