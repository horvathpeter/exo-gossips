package me.challenge.fluxchallenge.service;

import me.challenge.fluxchallenge.bo.Credentials;
import me.challenge.fluxchallenge.bo.User;
import me.challenge.fluxchallenge.token.UserToken;

public interface ISyncTokenService {

    User authenticate(Credentials credentials);

    UserToken requestToken(User user);

    default UserToken issueToken(Credentials credentials) {
        final User user = authenticate(credentials);
        return requestToken(user);
    }

}
