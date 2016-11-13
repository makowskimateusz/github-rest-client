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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Mateusz Makowski on 13.11.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test-error.properties")
public class RestServiceGithubCriticalTests {

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
    public void shouldGetInternalServerErrorResponse() throws Exception {

        mvc.perform(get("/repositories/someUser/someRepo"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Internal github server error"));

    }

    @Test
    public void shouldGetServiceUnavaiableErrorResponse() throws Exception {

        mvc.perform(get("/repositories/otherUser/otherRepo"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Github service unavailable"));

    }

}
