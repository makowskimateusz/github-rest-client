package com.makowski.allegro.recruitment.rest.api;

import com.makowski.allegro.recruitment.rest.client.GithubClient;
import com.makowski.allegro.recruitment.model.GithubData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by Mateusz Makowski on 09.11.2016.
 */
@RestController
@RequestMapping("repositories/")
public class ApiController {

    @Autowired
    public void setGithub(GithubClient github) {
        this.github = github;
    }

    @Autowired
    private GithubClient github;



    @GetMapping(value = "/{owner}/{repositoryName}")
    public GithubData getRepositoryDetails(@PathVariable("owner") String owner,
                                           @PathVariable("repositoryName") String repositoryName) throws IOException {

        return github.getData(owner, repositoryName);

    }
}
