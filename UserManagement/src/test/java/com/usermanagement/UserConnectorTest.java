package com.usermanagement;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.usermanagement.utility.Constants.GOOGLE_GSON;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.usermanagement.utility.Config;
import com.usermanagement.utility.User;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserConnectorTest {

  UserConnector userConnector;
  private WireMockServer wireMockServer;
  Config config = GOOGLE_GSON.fromJson(getResponseFromFile("config.json"), Config.class);

  @BeforeEach
  void setUp() {
    userConnector = new UserConnector();
    wireMockServer = new WireMockServer(
        WireMockConfiguration.options().notifier(new ConsoleNotifier(true))
            .port(config.getPort()));
    wireMockServer.start();
  }

  @AfterEach
  void stopWireMock() {
    if (wireMockServer != null) {
      wireMockServer.stop();
    }
  }

  @Test
  @SneakyThrows
  void getUsers() {
    wireMockServer.stubFor(
        get(urlEqualTo("/public/v2/users?page=1&per_page=100")).willReturn(
            aResponse().withBody(getResponseFromFile("users.json"))));

    wireMockServer.stubFor(
        get(urlEqualTo(
            "/public/v2/users?page=2&per_page=100")).willReturn(
            aResponse().withBody(getResponseFromFile("another_users.json"))));

    wireMockServer.stubFor(
        get(urlEqualTo(
            "/public/v2/users?page=3&per_page=100")).willReturn(
            aResponse().withBody(getResponseFromFile("empty_users.json"))));

    List<User> users = userConnector.getUsers();

    assertEquals(2, users.size());
  }

  @Test
  @SneakyThrows
  void getUser() {
    wireMockServer.stubFor(
        get(urlEqualTo("/public/v2/users/8316172")).willReturn(
            aResponse().withBody(getResponseFromFile("user.json"))));

    User user = userConnector.getUser("8316172");

    assertEquals("amit2", user.getName());
  }

  @Test
  @SneakyThrows
  void deleteUser() {
    wireMockServer.stubFor(
        delete(urlEqualTo("/public/v2/users/8316172")).willReturn(
            aResponse().withStatus(204)));

    assertDoesNotThrow(() -> userConnector.deleteUser("8316172"));
  }

  @Test
  @SneakyThrows
  void createUser() {
    wireMockServer.stubFor(
        post(urlEqualTo("/public/v2/users")).willReturn(
            aResponse().withStatus(201).withBody(getResponseFromFile("user.json"))));

    User payload = GOOGLE_GSON.fromJson("{\n" +
        "  \"name\": \"amit2\",\n" +
        "  \"email\": \"amit111@test.com\",\n" +
        "  \"gender\": \"male\",\n" +
        "  \"status\": \"active\"\n" +
        "}", User.class);

    assertDoesNotThrow(() -> userConnector.createUser(payload));
  }

  @Test
  @SneakyThrows
  void updateUser() {
    wireMockServer.stubFor(
        put(urlEqualTo("/public/v2/users/8316172")).willReturn(
            aResponse().withStatus(200).withBody(getResponseFromFile("user.json"))));

    User payload = GOOGLE_GSON.fromJson("{\n" +
        "  \"id\": 8316172,\n" +
        "  \"name\": \"amit2\",\n" +
        "  \"email\": \"amit111@test.com\",\n" +
        "  \"gender\": \"male\",\n" +
        "  \"status\": \"active\"\n" +
        "}", User.class);

    assertDoesNotThrow(() -> userConnector.updateUser(payload));
  }


  public static String getResponseFromFile(final String file) {
    try {
      return IOUtils.toString(Objects.requireNonNull(
          Thread.currentThread().getContextClassLoader().getResourceAsStream(file)));
    } catch (IOException e) {
      return StringUtils.EMPTY;
    }
  }
}
