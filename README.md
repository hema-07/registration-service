## Registration Application

##### Language  :  Java (Spring boot) 2.5.2
##### Database  :  H2 
##### Test      :  Unit Test
##### CI/CD     :  Jenkins
##### security  : Spring security 2.3.4.RELEASE

### Functionality


This is a Spring boot application which demonstrates validating users.

I have used H2 database in this application. It accepts User modal as a request. 

    Spring security added to these endpoints. It checkes the encoded username and passcode that had already assinged in config file.
    
    When User request enters the endpoint, it passes to Validator method to validate the request.

    After validation, validated request enters into User service.

    Once it received list of blacklisted user from exclusion service, it takes that response and checks against user request.

    After checking, Valid User will be registered and saved to H2 DB, Blacklisted user and Invalid card will throw an error response.

I have written test cases for success and failure scenario.

I have installed my own jenkins and created a simple pipeline that pushes my code to pipeline and build it.

### Run configuration

    Run this User Registration application as spring boot application.
    Test file run configuration setup screenshot is in docs folder. 

### REST POST endpoint for adding new user

This following values are hitting the endpoint as a request from user.

    USER_ID - creating unique id for userid  details

    FIRST_NAME - User's first name

    LAST_NAME - User's last name

    DATE_OF_BIRTH - User's Date of Birth

    EMAIL - User's email id
    
    COUNTRY - User's country
    
    STATUS - User status ; New User- N; 


    Post call : http://localhost:9093/user

    {
        "userFirstName": "Hema",
        "userLastName": "Tamil",
        "userDateOfBirth": "384365",
        "userEmail": "gfdj@yahoo.com",
        "userCountry": "UK"
    }
    
    ---------------------------------------
    
##### Headers: 
    
    Content-Type:application/json
    
    Authorization:Basic YWRtaW46YWRtaW4=
    
    Accept:application/json
    
It return status code for success scenario.

For failure, i have implemented error code and error handling.


### Preparation 

Skeleton Project from  https://start.spring.io  

Jenkins: http://localhost:8080

Attached screenshot of my local Jenkins CI/CD page is in docs folder.