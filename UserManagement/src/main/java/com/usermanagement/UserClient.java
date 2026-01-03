package com.usermanagement;

import com.usermanagement.exception.UserException;
import com.usermanagement.utility.Constants;
import com.usermanagement.utility.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Slf4j
public class UserClient {

  private final RequestBuilder requestBuilder;

  public UserClient(RequestBuilder requestBuilder) {
    this.requestBuilder = requestBuilder;
  }

  /**
   * This method will return paginated users
   *
   * @return List of users fetched
   * @throws IOException   In case of connectivity error
   * @throws UserException In case api response is not ok
   */
  public List<User> getUsers() throws IOException, UserException {
    int page = 1;
    User[] users = new User[] {};
    List<User> allUsers = new ArrayList<>();
    do {
      try (Response response = HttpClientProvider.CLIENT.newCall(
              requestBuilder.getUsersRequest(page))
          .execute();
           ResponseBody body = response.body()) {
        int responseCode = response.code();
        if (log.isDebugEnabled()) {
          log.debug("get users api response code is:{}", responseCode);
        }
        if (body != null) {
          String apiResponse = body.string();
          if (responseCode == 200) {
            users = Constants.GOOGLE_GSON.fromJson(apiResponse, User[].class);

            //below sout for testing -- not for production
            System.out.println("users fetched: " + users.length);

            allUsers.addAll(Arrays.asList(users));
          } else if (responseCode == 401) {
            //token expired--regenerate
          } else {
            throw new UserException("get user api response is:" + apiResponse);
          }
        } else {
          throw new UserException("get users response is null");
        }
      }
      page++;
    }
    while (users.length > 0);
    System.out.println("total users fetched:" + allUsers.size());
    return allUsers;
  }

  /**
   * This method will return a single user
   *
   * @param id User id of user to be returned
   * @return Fetched User
   * @throws IOException   In case of connectivity error
   * @throws UserException In case api response is not ok
   */
  public User getUser(String id) throws IOException, UserException {
    try (Response response = HttpClientProvider.CLIENT.newCall(
        requestBuilder.getUserRequest(id)).execute();
         ResponseBody body = response.body()) {
      int responseCode = response.code();
      if (log.isDebugEnabled()) {
        log.debug("get user api response code is:{}", responseCode);
      }
      if (body != null) {
        String apiResponse = body.string();
        if (log.isDebugEnabled()) {
          log.debug("get user api response code is:{}", responseCode);
        }
        if (responseCode == 200) {
          User user = Constants.GOOGLE_GSON.fromJson(apiResponse, User.class);
          System.out.println("fetched user name: " + user.getName());
          return user;
        }
        throw new UserException("get user api response is:" + apiResponse);
      }
      throw new UserException("get user response is null");
    }
  }

  /**
   * This method will delete a user
   *
   * @param id Id if user to be deleted
   * @throws IOException   In case of connectivity error
   * @throws UserException In case api response is not ok
   */
  public void deleteUser(String id) throws IOException, UserException {
    try (Response response = HttpClientProvider.CLIENT.newCall(
            requestBuilder.deleteUserRequest(id))
        .execute();
         ResponseBody body = response.body()) {
      int responseCode = response.code();
      if (log.isDebugEnabled()) {
        log.debug("delete user api response code is:{}", responseCode);
      }
      if (body != null) {
        String apiResponse = body.string();
        if (responseCode == 204) {
          // response code may differ for different delete apis
          System.out.println("user deleted with id: " + id);
          return;
        }
        throw new UserException("delete user api response is:" + apiResponse);
      }
      throw new UserException("delete user response is null");
    }
  }

  /**
   * This method will create a user
   *
   * @param user User object to be created
   * @throws IOException   In case of connectivity error
   * @throws UserException In case api response is not ok
   */
  public void createUser(User user) throws IOException, UserException {
    try (Response response = HttpClientProvider.CLIENT.newCall(
            requestBuilder.createUserRequest(user))
        .execute();
         ResponseBody body = response.body()) {
      int responseCode = response.code();
      if (log.isDebugEnabled()) {
        log.debug("create user api response code is:{}", responseCode);
      }
      if (body != null) {
        String apiResponse = body.string();
        if (responseCode == 201) {
          // response code may differ for different create apis
          User createdUser = Constants.GOOGLE_GSON.fromJson(apiResponse, User.class);
          System.out.println("user created with id: " + createdUser.getId());
          return;
        }
        throw new UserException("create user api response is:" + apiResponse);
      }
      throw new UserException("create user response is null");
    }
  }

  /**
   * This method will update a user
   *
   * @param user User object to be updated
   * @throws IOException   In case of connectivity error
   * @throws UserException In case api response is not ok
   */
  public void updateUser(User user) throws IOException, UserException {
    try (Response response = HttpClientProvider.CLIENT.newCall(
            requestBuilder.updateUserRequest(user))
        .execute();
         ResponseBody body = response.body()) {
      int responseCode = response.code();
      if (log.isDebugEnabled()) {
        log.debug("update user api response code is:{}", responseCode);
      }
      if (body != null) {
        String apiResponse = body.string();
        if (responseCode == 200) {
          // response code may differ for different update apis
          System.out.println("user updated with id:" + user.getId());
          return;
        }
        throw new UserException("update user api response is:" + apiResponse);
      }
      throw new UserException("update user response is null");
    }
  }
}
