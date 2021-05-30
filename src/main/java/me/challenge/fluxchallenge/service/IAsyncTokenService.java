package me.challenge.fluxchallenge.service;

import me.challenge.fluxchallenge.bo.Credentials;
import me.challenge.fluxchallenge.bo.User;
import me.challenge.fluxchallenge.token.UserToken;
import reactor.core.publisher.Mono;

public interface IAsyncTokenService {

    Mono<User> authenticate(Credentials credentials);

    Mono<UserToken> issueToken(Credentials credentials);

    // TODO remove
    default UserToken requestToken(User user) {
        throw new UnsupportedOperationException();
    }
}
