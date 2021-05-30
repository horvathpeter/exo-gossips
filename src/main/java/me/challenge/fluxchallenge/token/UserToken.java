package me.challenge.fluxchallenge.token;

public class UserToken {
    private final String token;

    public UserToken(String token) {
        this.token = token;
    }

    public String getToken(){
        return token;
    }
}
