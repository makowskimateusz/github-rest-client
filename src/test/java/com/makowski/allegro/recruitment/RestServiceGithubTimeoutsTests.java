package com.makowski.allegro.recruitment;

import com.jayway.restassured.RestAssured;
import com.makowski.allegro.recruitment.exception.RepositoryOrUserNotFoundExceptionHandler;
import com.makowski.allegro.recruitment.model.RepoDetails;
import com.makowski.allegro.recruitment.rest.api.ApiController;
import com.makowski.allegro.recruitment.rest.client.GithubClient;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Mateusz Makowski on 10.11.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class RestServiceGithubTimeoutsTests {

    @Autowired
    ApiController apiController;

    private MockGithubServer mockGithubServer;

    @Before
    public void setUp() throws IOException {

        mockGithubServer = new MockGithubServer();
        mockGithubServer.runMockServer();

        RestServiceGithubApplication.main(new String[]{});
        RestAssured.baseURI = "http://localhost:8081";

    }

    @Test
    public void shouldGetTimeout() throws Exception {
        when().get("/repositories/makowskimateusz/marks")
                .then().assertThat().statusCode(400);
    }

    class MockGithubServer extends AbstractVerticle {

        public void runMockServer() {
            Vertx vertx = Vertx.vertx();
            vertx.deployVerticle(new MockGithubServer());
        }

        public void response(RoutingContext routingContext) {

            routingContext
                    .response()
                    .setStatusCode(400)
                    .end(Json.encodePrettily(new RepoDetails()));
        }

        @Override
        public void start() {

            Router router = Router.router(vertx);

            router.route().handler(BodyHandler.create());

            vertx.setTimer(10, id -> {
                router.get("/repositories/makowskimateusz/marks").handler(this::response);
            });

            vertx.createHttpServer()
                    .requestHandler(router::accept)
                    .listen(8081);
        }

    }

}
