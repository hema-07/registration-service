package com.gamesys.registration.service;

import com.gamesys.registration.entity.User;
import com.gamesys.registration.modal.ExclusionServiceResponse;
import com.gamesys.registration.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.gamesys.registration.util.Constants.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    ExclusionService exclusionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addNewUser_success_scenario() {
        User user = User.builder()
                .userFirstName("Hema")
                .userLastName("Tamil")
                .userDateOfBirth("30/6/95")
                .userCountry("UK")
                .userEmail("hema@gmail.com")
                .build();
        List<User> list = new ArrayList<>();
        list.add(user);
        ExclusionServiceResponse exclusionServiceResponse = ExclusionServiceResponse.builder()
                .users(list)
                .build();
        Mockito.when(userRepository.findByuserEmail(user.getUserEmail(), user.getUserDateOfBirth())).thenReturn(null);
        Mockito.when(exclusionService.callExclusionService()).thenReturn(new ResponseEntity<>(exclusionServiceResponse, HttpStatus.OK));

        User user1 = User.builder()
                .userFirstName("Hemavathi")
                .userLastName("Tamilmaran")
                .userDateOfBirth("23/6/95")
                .userCountry("UK")
                .userEmail("hema64@gmail.com")
                .build();

        String addUser = userService.addUser(user1);

        assertEquals(savedNewUser, addUser);
    }

    @Test
    public void addNewUser_existing_user_failure_scenario() {
        ExclusionServiceResponse exclusionServiceResponse = ExclusionServiceResponse.builder()
                .users(Collections.EMPTY_LIST)
                .build();
        Mockito.when(exclusionService.callExclusionService()).thenReturn(new ResponseEntity<>(exclusionServiceResponse, HttpStatus.OK));

        User user1 = User.builder()
                .userFirstName("Hemavathi")
                .userLastName("Tamilmaran")
                .userDateOfBirth("23/6/95")
                .userCountry("UK")
                .userEmail("hema64@gmail.com")
                .build();
        Mockito.when(userRepository.findByuserEmail(user1.getUserEmail(), user1.getUserDateOfBirth())).thenReturn(user1);

        String addUser = userService.addUser(user1);

        assertEquals(existingUserFound, addUser);
    }

    @Test
    public void addNewUser_method_failure_blacklisted_user_scenario() {
        User user = User.builder()
                .userFirstName("Hema")
                .userLastName("Tamil")
                .userDateOfBirth("30/6/95")
                .userCountry("UK")
                .userEmail("hema@gmail.com")
                .build();
        List<User> list = new ArrayList<>();
        list.add(user);
        ExclusionServiceResponse exclusionServiceResponse = ExclusionServiceResponse.builder()
                .users(list)
                .build();
        Mockito.when(exclusionService.callExclusionService()).thenReturn(new ResponseEntity<>(exclusionServiceResponse, HttpStatus.OK));

        String addUser = userService.addUser(user);

        assertEquals(blackListedUserFound, addUser);
    }


    @Test
    public void getAllUsersList_failure() {
        userRepository.deleteAll();
        List<User> allCreditCardList = userService.getAllUsersFromDB();
        assertEquals(0, allCreditCardList.size());

    }

}