package com.usermanagement.exception;

public class UserException extends Exception {
  private static final long serialVersionUID = 1L;
  public UserException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public UserException(final String message) {
    super(message);
  }
}
