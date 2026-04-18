package ru.praktikum;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import ru.praktikum.model.Courier;
import ru.praktikum.util.CourierGenerator;

import static org.hamcrest.Matchers.equalTo;

public class CourierDeleteTest extends BaseApiTest {

    @Test
    void shouldDeleteCourier() {
        Courier courier = CourierGenerator.createRandomCourier();
        courierSteps.createCourier(courier);
        int courierId = courierSteps.getCourierId(courier);

        Response deleteResponse = courierSteps.deleteCourier(courierId);

        deleteResponse.then()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    @Test
    void shouldNotDeleteCourierWithoutId() {
        courierSteps.deleteCourierWithoutId().then()
                .statusCode(404);
    }

    @Test
    void shouldNotDeleteNonexistentCourier() {
        courierSteps.deleteCourier(999999999).then()
                .statusCode(404)
                .body("message", equalTo("Курьера с таким id нет."));
    }
}