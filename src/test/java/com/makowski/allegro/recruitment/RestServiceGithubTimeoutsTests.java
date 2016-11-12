package com.makowski.allegro.recruitment;

import com.makowski.allegro.recruitment.exception.RepositoryOrUserNotFoundExceptionHandler;
import com.makowski.allegro.recruitment.model.GithubData;
import com.makowski.allegro.recruitment.rest.api.ApiController;
import com.makowski.allegro.recruitment.rest.client.GithubApiService;
import com.makowski.allegro.recruitment.rest.client.GithubClient;
import com.squareup.okhttp.OkHttpClient;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Mateusz Makowski on 10.11.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestServiceGithubTimeoutsTests.GithubMockConfiguration.class)
public class RestServiceGithubTimeoutsTests {

    @Autowired
    ApiController apiController;

    private MockMvc mvc;

    private MockGithubServer mockGithubServer;

    @Before
    public void setUp() throws IOException {

        mockGithubServer = new MockGithubServer();
        mockGithubServer.runMockServer();

        mvc = MockMvcBuilders.standaloneSetup(apiController)
                .setControllerAdvice(new RepositoryOrUserNotFoundExceptionHandler())
                .build();

    }

    @Test
    public void shouldGetTimeout() throws Exception {

        mvc.perform(get("/repositories/makowskimateusz/marks"))
                .andExpect(status().isBadRequest());
    }

    @ContextConfiguration
    static class GithubMockConfiguration {

        @Bean
        GithubClient githubClient() {

            GithubClient githubClient = new GithubClient();

            githubClient.setConnectTime(2);
            githubClient.setReadTime(3);
            githubClient.setBaseUrl("localhost:8081");

            return new GithubClient();
        }

        @Bean
        ApiController apiController() {
            ApiController apiController = new ApiController();
            apiController.setGithub(githubClient());
            return apiController;
        }

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
                    .end(Json.encodePrettily(new GithubData()));
        }

        @Override
        public void start() {

            Router router = Router.router(vertx);

            router.route().handler(BodyHandler.create());

            vertx.setTimer(10, id -> {
                router.post("/repositories/makowskimateusz/marks").handler(this::response);
            });

            vertx.createHttpServer()
                    .requestHandler(router::accept)
                    .listen(8081);
        }

    }

}
