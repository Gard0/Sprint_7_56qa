package ru.praktikum.client;

import io.restassured.response.Response;
import ru.praktikum.config.Endpoints;
import ru.praktikum.model.Courier;
import ru.praktikum.model.CourierCredentials;

public class CourierClient extends BaseClient {

    public Response createCourier(Courier courier) {
        return post(Endpoints.COURIER, courier);
    }

    public Response loginCourier(CourierCredentials credentials) {
        return post(Endpoints.COURIER_LOGIN, credentials);
    }

    public Response deleteCourier(int courierId) {
        return delete(Endpoints.COURIER + "/" + courierId);
    }

    public Response deleteWithoutId() {
        return delete(Endpoints.COURIER + "/");
    }
}
