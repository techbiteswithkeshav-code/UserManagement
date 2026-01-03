package com.usermanagement;

import static com.usermanagement.utility.Constants.ACCESS_TOKEN;
import static com.usermanagement.utility.Constants.AUTHORIZATION_KEYWORD;
import static com.usermanagement.utility.Constants.BEARER_WITH_SPACE_KEYWORD;
import static com.usermanagement.utility.Constants.GET_COMMON_ENDPOINT_PATH;
import static com.usermanagement.utility.Constants.GET_USERS_ENDPOINT;
import static com.usermanagement.utility.Constants.GOOGLE_GSON;
import static com.usermanagement.utility.Constants.JSON_MEDIA_TYPE;
import static com.usermanagement.utility.Constants.PAGE_KEY;
import static com.usermanagement.utility.Constants.PAGE_SIZE;
import static com.usermanagement.utility.Constants.PAGE_SIZE_KEY;

import com.usermanagement.utility.Config;
import com.usermanagement.utility.User;
import com.usermanagement.utility.Util;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

@Slf4j
public class RequestBuilder {

  private HttpUrl.Builder getBaseUrl() {
    Config config = GOOGLE_GSON.fromJson(Util.getResponseFromFile("config.json"), Config.class);
    HttpUrl.Builder baseUrl =
        new HttpUrl.Builder().scheme(config.getScheme()).host(config.getHost());
    if (config.getPort() != null) {
      baseUrl.port(config.getPort());
    }
    return baseUrl;
  }

  private HttpUrl getUsersUrl(int page) {
    HttpUrl usersUrl =
        getBaseUrl().addPathSegments(GET_COMMON_ENDPOINT_PATH)
            .addPathSegment(GET_USERS_ENDPOINT)
            .addQueryParameter(PAGE_KEY, String.valueOf(page))
            .addQueryParameter(PAGE_SIZE_KEY, String.valueOf(PAGE_SIZE))
            .build();
    log.debug("get users url is:{}", usersUrl.url());
    System.out.println("get users url is:" + usersUrl.url());
    return usersUrl;
  }

  private HttpUrl getUserUrl(String id) {
    HttpUrl userUrl =
        getBaseUrl().addPathSegments(GET_COMMON_ENDPOINT_PATH)
            .addPathSegment(GET_USERS_ENDPOINT)
            .addPathSegment(id)
            .build();
    log.debug("get user url is:{}", userUrl.url());
    System.out.println("get user url is:" + userUrl.url());//for testing used sout
    return userUrl;
  }

  public Request getUsersRequest(int page) {
    return new Request.Builder().url(getUsersUrl(page)).get()
        .addHeader(AUTHORIZATION_KEYWORD, BEARER_WITH_SPACE_KEYWORD + ACCESS_TOKEN).build();
  }

  public Request getUserRequest(String id) {
    return new Request.Builder().url(getUserUrl(id)).get()
        .addHeader(AUTHORIZATION_KEYWORD, BEARER_WITH_SPACE_KEYWORD + ACCESS_TOKEN).build();
  }

  private HttpUrl deleteUserUrl(String id) {
    HttpUrl userUrl =
        getBaseUrl().addPathSegments(GET_COMMON_ENDPOINT_PATH)
            .addPathSegment(GET_USERS_ENDPOINT)
            .addPathSegment(id)
            .build();
    log.debug("delete user url is:{}", userUrl.url());
    System.out.println("delete user url is:" + userUrl.url());
    return userUrl;
  }

  private HttpUrl createUserUrl() {
    HttpUrl userUrl =
        getBaseUrl().addPathSegments(GET_COMMON_ENDPOINT_PATH)
            .addPathSegment(GET_USERS_ENDPOINT)
            .build();
    log.debug("create user url is:{}", userUrl.url());
    System.out.println("create user url is:" + userUrl.url());
    return userUrl;
  }

  public Request deleteUserRequest(String id) {
    return new Request.Builder().url(deleteUserUrl(id)).delete()
        .addHeader(AUTHORIZATION_KEYWORD, BEARER_WITH_SPACE_KEYWORD + ACCESS_TOKEN).build();
  }

  public RequestBody getCreateUserPayload(final User user) {
    String jsonString = GOOGLE_GSON.toJson(user);
    log.debug("create user payload is:{}", jsonString);
    System.out.println("create user payload is:" + jsonString);

    return RequestBody.create(jsonString, JSON_MEDIA_TYPE);
  }

  public RequestBody getUpdateUserPayload(final User user) {
    String jsonString = GOOGLE_GSON.toJson(user);
    log.debug("update user payload is:{}", jsonString);
    System.out.println("update user payload is:" + jsonString);

    return RequestBody.create(jsonString, JSON_MEDIA_TYPE);
  }

  public Request createUserRequest(User user) {
    return new Request.Builder().url(createUserUrl()).post(getCreateUserPayload(user))
        .addHeader(AUTHORIZATION_KEYWORD, BEARER_WITH_SPACE_KEYWORD + ACCESS_TOKEN).build();
  }

  private HttpUrl updateUserUrl(int id) {
    HttpUrl userUrl =
        getBaseUrl().addPathSegments(GET_COMMON_ENDPOINT_PATH)
            .addPathSegment(GET_USERS_ENDPOINT)
            .addPathSegment(String.valueOf(id))
            .build();
    log.debug("update user url is:{}", userUrl.url());
    System.out.println("update user url is:" + userUrl.url());
    return userUrl;
  }

  public Request updateUserRequest(User user) {
    return new Request.Builder().url(updateUserUrl(user.getId())).put(getUpdateUserPayload(user))
        .addHeader(AUTHORIZATION_KEYWORD, BEARER_WITH_SPACE_KEYWORD + ACCESS_TOKEN).build();
  }
}
