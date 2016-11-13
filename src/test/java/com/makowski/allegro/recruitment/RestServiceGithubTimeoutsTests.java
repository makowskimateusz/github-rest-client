package com.makowski.allegro.recruitment;

import com.makowski.allegro.recruitment.exception.GithubServiceErrorsExceptionHandler;
import com.makowski.allegro.recruitment.rest.api.ApiController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import utils.MockedServer;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Mateusz Makowski on 10.11.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test-timeout.properties")
public class RestServiceGithubTimeoutsTests {

    @Autowired
    ApiController apiController;

    private MockedServer mockGithubServer;

    private MockMvc mvc;

    @Before
    public void setUp() throws IOException {

        mockGithubServer = new MockedServer();
        mockGithubServer.runMockServer();

        mvc = MockMvcBuilders.standaloneSetup(apiController)
                .setControllerAdvice(new GithubServiceErrorsExceptionHandler())
                .build();

    }

    @Test
    public void shouldGetTimeoutInformation() throws Exception {

        mvc.perform(get("/repositories/makowskimateusz/marks"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Read timed out"));

    }

}
