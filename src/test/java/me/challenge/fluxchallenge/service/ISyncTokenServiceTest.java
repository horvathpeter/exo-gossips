package me.challenge.fluxchallenge.service;

import me.challenge.SpringRestReactiveFunctionalApplication;
import me.challenge.fluxchallenge.bo.Credentials;
import me.challenge.fluxchallenge.bo.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRestReactiveFunctionalApplication.class)
public class ISyncTokenServiceTest {

    @Autowired
    private ISyncTokenService service;

    @Test
    public void authenticateUserTest() {
        final Credentials credentials = new Credentials("peter", "PETER");
        final User user = service.authenticate(credentials);
        Assertions.assertThat(user.getUserId()).isEqualTo("peter");
    }

}