package com.makowski.allegro.recruitment.rest.client;

import com.makowski.allegro.recruitment.model.RepoDetails;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Mateusz Makowski on 09.11.2016.
 */
public interface GithubApiService {

    @GET("repos/{user}/{repositoryName}")
    Call<RepoDetails> getRepoDetails(@Path("user") String user, @Path("repositoryName") String repositoryName);

}
