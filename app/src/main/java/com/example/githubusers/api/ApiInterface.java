package com.example.githubusers.api;

import com.example.githubusers.model.UsersData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by tt on 25/10/17.
 */

public interface ApiInterface {
    @GET("/search/users?sort=followers&order=desc")
    Observable<UsersData> getUser(@Query("q") String name);
}
