package com.makowski.allegro.recruitment.rest.api;

import com.makowski.allegro.recruitment.rest.client.GithubClient;
import com.makowski.allegro.recruitment.model.RepoDetails;
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
public class ApiController {

    @Autowired
    private GithubClient github;

    @GetMapping(value = "/{owner}/{repositoryName}")
    public RepoDetails getRepositoryDetails(@PathVariable("owner") String owner,
                                            @PathVariable("repositoryName") String repositoryName) throws IOException {

        return github.getData(owner, repositoryName);

    }
}
