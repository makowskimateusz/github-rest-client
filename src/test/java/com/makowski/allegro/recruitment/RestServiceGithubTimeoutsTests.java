package com.makowski.allegro.recruitment;

import com.makowski.allegro.recruitment.exception.NotFoundRepositoryOrUserExceptionHandler;
import com.makowski.allegro.recruitment.model.GithubData;
import com.makowski.allegro.recruitment.rest.api.ApiController;
import com.makowski.allegro.recruitment.rest.client.GithubApiService;
import com.makowski.allegro.recruitment.rest.client.GithubClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.Buffer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Mateusz Makowski on 10.11.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestServiceGithubTimeoutsTests {

    @Autowired
    @InjectMocks
    ApiController apiController;

    @Mock
    GithubClient githubClient;

    private MockMvc mvc;

    @Before
    public void setUp() throws IOException {

        githubClient.setConnectTime(2);
        githubClient.setReadTime(3);

        mvc = MockMvcBuilders.standaloneSetup(apiController)
                .setControllerAdvice(new NotFoundRepositoryOrUserExceptionHandler())
                .build();


    }

    @Test
    public void shouldGetTimeout() throws Exception {

        mvc.perform(get("/repositories/makowskimateusz/marks"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
