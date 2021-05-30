package me.challenge.fluxchallenge.service.impl;

import me.challenge.fluxchallenge.bo.Credentials;
import me.challenge.fluxchallenge.bo.User;
import me.challenge.fluxchallenge.service.IAsyncTokenService;
import me.challenge.fluxchallenge.token.UserToken;
import reactor.core.publisher.Mono;

public class IAsyncTokenServiceImpl implements IAsyncTokenService {

    @Override
    public Mono<User> authenticate(Credentials credentials) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<UserToken> issueToken(Credentials credentials) {
        throw new UnsupportedOperationException();
    }
}
