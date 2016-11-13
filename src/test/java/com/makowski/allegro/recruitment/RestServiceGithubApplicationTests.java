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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class RestServiceGithubApplicationTests {

	@Autowired
	ApiController apiController;

	private MockMvc mvc;

	@Before
	public void setUp(){

		mvc = MockMvcBuilders.standaloneSetup(apiController)
				.setControllerAdvice(new GithubServiceErrorsExceptionHandler())
				.build();
	}

	@Test
	public void shouldReciveCorrectDataFromGithubWithCorrectUserDetails() throws Exception {

		mvc.perform(get("/repositories/makowskimateusz/marks"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.fullName").value("makowskimateusz/marks"))
				.andExpect(jsonPath("$.description").isNotEmpty())
				.andExpect(jsonPath("$.cloneUrl").value("https://github.com/makowskimateusz/marks.git"))
				.andExpect(jsonPath("$.stars").value(0))
				.andExpect(jsonPath("$.createdAt").value("2016-03-19T16:24:37Z"));

	}

	@Test
	public void shouldReturnBadRequestStatusWhenThereIsNoUser() throws Exception {

		mvc.perform(get("/repositories/afdjshfajdhfajkshfdajkdhfadf/marks"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("There is no such user or repository"));

	}

	@Test
	public void shouldReturnBadRequestStatusWhenThereIsNoRepository() throws Exception {

		mvc.perform(get("/repositories/makowskimateusz/asfadsfasdfasdfadsf"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("There is no such user or repository"));

	}


}
