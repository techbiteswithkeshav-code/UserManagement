package com.usermanagement.utility;

import lombok.Getter;

@Getter
public class Config {

  private String scheme;
  private String host;
  private Integer port;
  private Long retryDelay;

}
