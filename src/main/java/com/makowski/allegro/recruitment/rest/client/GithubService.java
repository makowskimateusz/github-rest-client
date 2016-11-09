package com.makowski.allegro.recruitment.rest.client;

import com.makowski.allegro.recruitment.exception.NotFoundRepositoryOrUserException;
import com.makowski.allegro.recruitment.model.GithubData;
import lombok.Getter;
import org.springframework.stereotype.Service;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by Mateusz Makowski on 09.11.2016.
 */
@Service
public class GithubService {

    private Retrofit retrofit;

    @Getter
    private GithubApiService service;

    public GithubService() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GithubApiService.class);
    }

    public GithubData getData(String owner, String repositoryName) throws IOException{

        return Optional.ofNullable(service.getInformationsRequest(owner, repositoryName)
                .execute().body()).orElseThrow(() -> new NotFoundRepositoryOrUserException("There is no such user or repository"));
    }
}
