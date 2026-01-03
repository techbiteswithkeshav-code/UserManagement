package com.usermanagement.utility;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import java.util.List;
import okhttp3.MediaType;

public class Constants {

  public static final String GET_COMMON_ENDPOINT_PATH = "public/v2";
  public static final String GET_USERS_ENDPOINT = "users";
  public static final String AUTHORIZATION_KEYWORD = "Authorization";
  public static final String PAGE_KEY = "page";
  public static final String PAGE_SIZE_KEY = "per_page";
  public static final String BEARER_WITH_SPACE_KEYWORD = "Bearer ";
  public static final MediaType JSON_MEDIA_TYPE =
      MediaType.parse("application/json; charset=utf-8");
  public static final Gson GOOGLE_GSON = new Gson();
  public static final int PAGE_SIZE = 100;
  public static final String ACCESS_TOKEN =
      "89277f7db085a30456c52af97f75e30c2b9901764e6e42ae480177baba829496";
  public static final long INITIAL_BACKOFF_MILLIS = 5000;
  public static final int MAX_RETRIES = 3;
  public static final List<Integer> RETRY_CODES = ImmutableList.of(429);
  public static final String DATA_FETCH_FAILED_MESSAGE = "Failed to get the data";
}
