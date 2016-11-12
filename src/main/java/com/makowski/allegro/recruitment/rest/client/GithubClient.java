package com.makowski.allegro.recruitment.rest.client;

import com.makowski.allegro.recruitment.exception.GithubApiTimeoutException;
import com.makowski.allegro.recruitment.exception.RepositoryOrUserNotFoundException;
import com.makowski.allegro.recruitment.model.RepoDetails;
import com.squareup.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mateusz Makowski on 09.11.2016.
 */
@Service
public class GithubClient {

    @Value("${connectTime}")
    private int connectTime;
    @Value("${readTime}")
    private int readTime;
    @Value("${baseUrl}")
    private String baseUrl;

    private GithubApiService service;

    private Retrofit retrofit;

    private OkHttpClient okHttpClient;

    @Autowired
    public GithubClient(@Value("${connectTime}") int connectTime,
                        @Value("${readTime}") int readTime,
                        @Value("${baseUrl}") String baseUrl) {

        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(connectTime, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(readTime, TimeUnit.MILLISECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        service = retrofit.create(GithubApiService.class);
    }

    public RepoDetails getData(String owner, String repositoryName) throws IOException{
        try{
            return Optional.ofNullable(service.getRepoDetails(owner, repositoryName)
                    .execute().body()).orElseThrow(() -> new RepositoryOrUserNotFoundException("There is no such user or repository"));
        } catch (SocketTimeoutException e) {
            throw new GithubApiTimeoutException(e.getMessage());
        }
    }
}
