package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.client.CourierClient;
import ru.praktikum.model.Courier;
import ru.praktikum.model.CourierCredentials;

public class CourierSteps {

    private final CourierClient courierClient = new CourierClient();

    @Step("Создать курьера")
    public Response createCourier(Courier courier) {
        return courierClient.createCourier(courier);
    }

    @Step("Авторизовать курьера")
    public Response loginCourier(CourierCredentials credentials) {
        return courierClient.loginCourier(credentials);
    }

    @Step("Удалить курьера с id {courierId}")
    public Response deleteCourier(int courierId) {
        return courierClient.deleteCourier(courierId);
    }

    @Step("Отправить запрос на удаление курьера без id")
    public Response deleteCourierWithoutId() {
        return courierClient.deleteWithoutId();
    }

    @Step("Получить id курьера")
    public int getCourierId(Courier courier) {
        return loginCourier(new CourierCredentials(courier.getLogin(), courier.getPassword()))
                .then()
                .extract()
                .path("id");
    }
}
