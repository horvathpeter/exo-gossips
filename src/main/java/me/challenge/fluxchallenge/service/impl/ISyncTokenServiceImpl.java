package me.challenge.fluxchallenge.service.impl;

import me.challenge.fluxchallenge.bo.Credentials;
import me.challenge.fluxchallenge.bo.User;
import me.challenge.fluxchallenge.service.ISyncTokenService;
import me.challenge.fluxchallenge.token.UserToken;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class ISyncTokenServiceImpl implements ISyncTokenService {

    @Override
    public User authenticate(Credentials credentials) {
        validateCredentials(credentials);

        randomDelay();

        return new User(credentials.getUsername());
    }

    @Override
    public UserToken requestToken(User user) {
        validateUser(user);

        randomDelay();

        return new UserToken("blablah"); // TODO token in right format
    }

    private void validateUser(User user) {
        if (user.getUserId().charAt(0) == 'A') {
            throw new IllegalArgumentException(user.toString());
        }
    }

    private void validateCredentials(Credentials credentials) {
        if (!Objects.equals(credentials.getPassword(), credentials.getUsername().toUpperCase())) {
            throw new IllegalArgumentException(credentials.toString());
        }
    }

    private void randomDelay() {
        int seconds = new Random().nextInt(5 - 1) + 1;

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
