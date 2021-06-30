package com.gamesys.registration.validator;

import com.gamesys.registration.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserParameterValidationTest {

    @Autowired
    UserParameterValidation userParameterValidation;

    @Test
    public void validate_success_scenario() {
        User user = User.builder()
                .userFirstName("Hema")
                .userLastName("Tamil")
                .userDateOfBirth("30/6/95")
                .userCountry("UK")
                .userEmail("hema@gmail.com")
                .build();
        ValidationResult validate = userParameterValidation.validate(user);
        Assert.assertEquals(true, validate.isValid());

    }

    @Test
    public void validate_first_name_missing_scenario() {
        User user = User.builder()
                .userLastName("Tamil")
                .userDateOfBirth("30/6/95")
                .userCountry("UK")
                .userEmail("hema@gmail.com")
                .build();
        ValidationResult validate = userParameterValidation.validate(user);
        Assert.assertEquals(false, validate.isValid());

    }

    @Test
    public void validate_date_of_birth_missing_scenario() {
        User user = User.builder()
                .userFirstName("Hema")
                .userLastName("Tamil")
                .userCountry("UK")
                .userEmail("hema@gmail.com")
                .build();
        ValidationResult validate = userParameterValidation.validate(user);
        Assert.assertEquals(false, validate.isValid());

    }

    @Test
    public void validate_user_email_missing_scenario() {
        User user = User.builder()
                .userFirstName("Hema")
                .userLastName("Tamil")
                .userDateOfBirth("30/6/95")
                .userCountry("UK")
                .build();
        ValidationResult validate = userParameterValidation.validate(user);
        Assert.assertEquals(false, validate.isValid());

    }

    @Test
    public void validate_user_dateOfBirth_failure_scenario_maxlength() {
        User user = User.builder()
                .userFirstName("Hema")
                .userLastName("Tamil")
                .userDateOfBirth("30/6/95gbhytf")
                .userCountry("UK")
                .userEmail("hema@gmail.com")
                .build();
        ValidationResult validate = userParameterValidation.validate(user);
        Assert.assertEquals(false, validate.isValid());

    }

    @Test
    public void validate_user_first_name_failure_scenario_maxlength() {
        User user = User.builder()
                .userFirstName("Hemafhcgknughindrkuhgkhnuhrdgnucnhndjfckng")
                .userLastName("Tamil")
                .userDateOfBirth("30/6/95")
                .userCountry("UK")
                .userEmail("hema@gmail.com")
                .build();
        ValidationResult validate = userParameterValidation.validate(user);
        Assert.assertEquals(false, validate.isValid());

    }

    @Test
    public void validate_user_last_name_failure_scenario_maxlength() {
        User user = User.builder()
                .userFirstName("Hema")
                .userLastName("Tamildunchegrngekdrhcrnvichkrckcrghcnre")
                .userDateOfBirth("30/6/95")
                .userCountry("UK")
                .userEmail("hema@gmail.com")
                .build();
        ValidationResult validate = userParameterValidation.validate(user);
        Assert.assertEquals(false, validate.isValid());

    }

}