package com.gamesys.registration.service;

import com.gamesys.registration.modal.ExclusionServiceResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;

@Component
public class ExclusionService {

    private static final Logger logger = LogManager.getLogger(ExclusionService.class);

    @Value("${registration.exclusionUrl}")
    private String exclusionEndPoint;

    RestTemplate restTemplate = new RestTemplate();

    /**
     * Rest Call to Exclusion service
     * @return list of blacklisted User objects
     */
    public ResponseEntity<ExclusionServiceResponse> callExclusionService() {

        HttpEntity<String> httpEntity = new HttpEntity<>(createHeaders());
        ResponseEntity<ExclusionServiceResponse> responseEntity = null;

        try {
            logger.info("Calling Exclusion Service..");
            responseEntity = restTemplate.exchange(exclusionEndPoint, HttpMethod.GET, httpEntity, ExclusionServiceResponse.class);
        } catch (Exception e) {
            logException(e, exclusionEndPoint);
        }

        return responseEntity;
    }


    private void logException(Exception ex, String exclusionEndPoint) {
        if (ex instanceof HttpStatusCodeException) {
            HttpStatusCodeException e = (HttpStatusCodeException) ex;
            String responseEntity = e.getResponseBodyAsString();
            logger.error( "HttpStatusCodeException :: " + e.getStatusCode() +
                    " Error Calling URL :: Exclusion endpoint - " + exclusionEndPoint +
                    " Response Body :: "+ responseEntity +
                    " Error Log :: " + ex.getMessage());
        } else {

            logger.error("Exception :: Error calling url :: Exclusion endpoint" + exclusionEndPoint + " Error Log :: "+ ex.getMessage());
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(ACCEPT, APPLICATION_JSON_VALUE);
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        return headers;
    }
}
