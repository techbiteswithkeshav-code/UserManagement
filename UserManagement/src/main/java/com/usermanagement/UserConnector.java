package com.usermanagement;

import com.usermanagement.exception.UserException;
import com.usermanagement.utility.User;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserConnector {

  private final UserClient userClient;

  public UserConnector() {
    RequestBuilder requestBuilder = new RequestBuilder();
    this.userClient = new UserClient(requestBuilder);
  }

  public List<User> getUsers() throws UserException, IOException {
    //as per scenario we can return exception to caller or failure
    return userClient.getUsers();
  }

  public User getUser(String id) throws UserException, IOException {
    return userClient.getUser(id);
  }

  public void deleteUser(String id) throws UserException, IOException {
    userClient.deleteUser(id);
  }

  public void createUser(User user) throws UserException, IOException {
    userClient.createUser(user);
  }

  public void updateUser(User user) throws UserException, IOException {
    userClient.updateUser(user);
  }

//  public static void main(String[] args) {
//    RequestBuilder requestBuilder = new RequestBuilder();
//    UserClient userManagementClient = new UserClient(requestBuilder);
//    try {
//      // userManagementClient.getUsers();
//
//     // userManagementClient.getUser("8316077");
//
//      //userManagementClient.deleteUser("8115306");
//
//      User user = new User();
////      user.setEmail("amit223@test.com");
////      user.setName("amit223");
////      user.setGender("male");
////      user.setStatus("active");
////
////      userManagementClient.createUser(user);
//
//
////      user = new User();
//
//      user.setEmail("amit22@test.com");
//      user.setName("amit22");
//      user.setGender("male");
//      user.setStatus("active");
//      user.setId(8316182);
//
//     userManagementClient.updateUser(user);
//
//
//    } catch (Exception e) {
//      log.error("Exception is: ", e);
//      e.printStackTrace();
//    }
//  }

}
