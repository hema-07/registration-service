package com.gamesys.registration.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.gamesys.registration.controller.UserRegistrationController;
import com.gamesys.registration.entity.User;
import com.gamesys.registration.modal.ExclusionServiceResponse;
import com.gamesys.registration.repository.UserRepository;
import com.gamesys.registration.service.ExclusionService;
import com.gamesys.registration.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserRegistrationIntegrationTests {

    @Mock
    UserRegistrationController userRegistrationController;

    @Mock
    UserService userService;

    @Mock
    ExclusionService exclusionService;

    @Autowired
    MockMvc mockMvc;

    User user = User.builder()
            .userFirstName("Hema")
            .userLastName("Tamil")
            .userDateOfBirth("30/6/95")
            .userCountry("UK")
            .userEmail("hema@gmail.com")
            .build();

    User blackListUser = User.builder()
            .userFirstName("Hemavathi")
            .userLastName("Tamilmaran")
            .userDateOfBirth("25/6/95")
            .userCountry("UK")
            .userEmail("hema2@gmail.com")
            .build();


   //@Test
    public void test_adduser_endpoint_success() throws Exception {
        List<User> list = new ArrayList<>();
        list.add(user);
        ExclusionServiceResponse exclusionServiceResponse = ExclusionServiceResponse.builder()
                .users(list)
                .build();
        Mockito.when(exclusionService.callExclusionService()).thenReturn(new ResponseEntity<>(exclusionServiceResponse, HttpStatus.OK));
        ResultActions resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .header("Content-Type","application/json")
                        .header("Authorization","Basic YWRtaW46YWRtaW4=")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    //@Test
    public void test_add_user_for_existing_user() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .header("Content-Type","application/json")
                        .header("Authorization","Basic YWRtaW46YWRtaW4=")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        ResultActions resultActions1 = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .header("Content-Type","application/json")
                        .header("Authorization","Basic YWRtaW46YWRtaW4=")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions1.andExpect(MockMvcResultMatchers.status().isInternalServerError());

    }


    @Test
    public void test_get_all_endpoint_for_success_scenario() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/user")
                        .header("Authorization","Basic YWRtaW46YWRtaW4=")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    public static String asJsonString(final Object object) {

        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

}
