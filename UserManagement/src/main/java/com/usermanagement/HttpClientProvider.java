package com.usermanagement;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

public class HttpClientProvider {

  public static final OkHttpClient CLIENT = new OkHttpClient.Builder()
      .connectTimeout(10, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .addInterceptor(new RetryInterceptor()).build();

}