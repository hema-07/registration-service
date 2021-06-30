package com.gamesys.registration.service;

import com.gamesys.registration.entity.User;
import com.gamesys.registration.modal.ExclusionServiceResponse;
import com.gamesys.registration.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExclusionServiceTest {

    @InjectMocks
    ExclusionService exclusionService;

    @Mock
    RestTemplate restTemplate = new RestTemplate();

    private String exclusionUrl = "http://localhost:9092/exclusion/v1.0/user";
    private String mockExclusionUrl = "/exclusion/v1.0/user";

    @Test
    void callExclusionService_success_scenario() {
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

        Mockito.when(restTemplate.getForEntity(exclusionUrl, ExclusionServiceResponse.class))
                .thenReturn(new ResponseEntity<>(exclusionServiceResponse, HttpStatus.OK));

        ResponseEntity<ExclusionServiceResponse> result = restTemplate.getForEntity(exclusionUrl, ExclusionServiceResponse.class);
        Assert.assertEquals(200, result.getStatusCodeValue());

    }


    @Test
    void callExclusionService_failure_scenario() {
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

        Mockito.when(restTemplate.getForEntity(exclusionUrl, ExclusionServiceResponse.class))
                .thenReturn(new ResponseEntity<>(exclusionServiceResponse, HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseEntity<ExclusionServiceResponse> result = restTemplate.getForEntity(exclusionUrl, ExclusionServiceResponse.class);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

    }
}