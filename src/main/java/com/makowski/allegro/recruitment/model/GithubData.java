package com.makowski.allegro.recruitment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Mateusz Makowski on 09.11.2016.
 */
@Data
public class GithubData {
    @JsonProperty(value = "fullName")
    private String full_name;
    private String description;
    @JsonProperty(value = "cloneUrl")
    private String clone_url;
    @JsonProperty(value = "stars")
    private int stargazers_count;
    @JsonProperty(value = "createdAt")
    private String created_at;
}
