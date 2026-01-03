package com.usermanagement.utility;

import java.io.IOException;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class Util {
  public static String getResponseFromFile(final String file) {
    try {
      return IOUtils.toString(Objects.requireNonNull(
          Thread.currentThread().getContextClassLoader().getResourceAsStream(file)));
    } catch (IOException e) {
      return StringUtils.EMPTY;
    }
  }

}
