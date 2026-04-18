package ru.praktikum.client;

import io.restassured.response.Response;
import ru.praktikum.model.Courier;
import ru.praktikum.model.CourierCredentials;

public class CourierClient extends BaseClient {

    private static final String COURIER_PATH = "/courier";
    private static final String LOGIN_PATH = "/courier/login";

    public Response createCourier(Courier courier) {
        return post(COURIER_PATH, courier);
    }

    public Response loginCourier(CourierCredentials credentials) {
        return post(LOGIN_PATH, credentials);
    }

    public Response deleteCourier(int courierId) {
        return delete(COURIER_PATH + "/" + courierId);
    }

    public Response deleteWithoutId() {
        return delete(COURIER_PATH + "/");
    }
}
