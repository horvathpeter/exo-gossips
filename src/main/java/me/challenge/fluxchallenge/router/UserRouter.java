package me.challenge.fluxchallenge.router;

import me.challenge.fluxchallenge.handler.UserHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class UserRouter {

    public RouterFunction<ServerResponse> usersRoute(UserHandler userHandler) {
        return RouterFunctions
                .route(GET("/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUser);
    }

}
