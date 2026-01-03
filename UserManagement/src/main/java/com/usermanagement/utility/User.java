package com.usermanagement.utility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

  //when to use @SerializedName
  private Integer id;
  private String name;
  private String email;
  private String gender;
  private String status;

}
