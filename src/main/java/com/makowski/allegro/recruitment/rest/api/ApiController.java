package com.makowski.allegro.recruitment.rest.api;

import com.makowski.allegro.recruitment.rest.client.GithubService;
import com.makowski.allegro.recruitment.model.GithubData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by Mateusz Makowski on 09.11.2016.
 */
@RestController
@RequestMapping("repositories/")
@Slf4j
public class ApiController {

    @Autowired
    private GithubService github;

    @GetMapping(value = "/{owner}/{repositoryName}")
    public GithubData getRepositoryDetails(@PathVariable("owner") String owner,
                                           @PathVariable("repositoryName") String repositoryName) throws IOException {

        return github.getData(owner, repositoryName);

    }
}
