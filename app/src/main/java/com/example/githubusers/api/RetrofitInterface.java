package com.example.githubusers.api;

import android.util.Log;

import com.example.githubusers.BuildConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tt on 25/10/17.
 */

public class RetrofitInterface {
    public static final String HOST = "https://api.github.com";

    private static RetrofitInterface INSTANCE = null;

    private ApiInterface apiInterface;

    private RetrofitInterface (){

    }

    public static RetrofitInterface getInstance(){
        if (INSTANCE == null) {
            synchronized (RetrofitInterface.class){
                if (INSTANCE == null) {
                    return  new RetrofitInterface();
                }
            }
        }

        return INSTANCE;

    }

    public ApiInterface getApiInterface(){
        if (apiInterface == null) {
            return apiInterface = create();
        }

        return apiInterface;
    }

    private ApiInterface create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(buildClient())
                .build();
        return retrofit.create(ApiInterface.class);
    }


    public static OkHttpClient buildClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // Create OkHttpClient

        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(50, TimeUnit.SECONDS);
        builder.writeTimeout(40, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.interceptors().add(loggingInterceptor);

        }
        return builder.build();
    }
}
