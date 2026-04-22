package ru.praktikum.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.praktikum.config.TestConfig;

public abstract class BaseClient {

    protected RequestSpecification getBaseSpec() {
        RestAssured.config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 20000)
                        .setParam("http.socket.timeout", 20000)
                        .setParam("http.connection-manager.timeout", 20000L));

        return RestAssured.given()
                .baseUri(TestConfig.BASE_URL)
                .basePath(TestConfig.API_PATH)
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssured());
    }

    protected Response get(String path) {
        return getBaseSpec()
                .when()
                .get(path);
    }

    protected Response getWithQuery(String path, String name, Object value) {
        return getBaseSpec()
                .queryParam(name, value)
                .when()
                .get(path);
    }

    protected Response post(String path, Object body) {
        return getBaseSpec()
                .body(body)
                .when()
                .post(path);
    }

    protected Response put(String path) {
        return getBaseSpec()
                .when()
                .put(path);
    }

    protected Response putWithQuery(String path, String name, Object value) {
        return getBaseSpec()
                .queryParam(name, value)
                .when()
                .put(path);
    }

    protected Response delete(String path) {
        return getBaseSpec()
                .when()
                .delete(path);
    }
}
