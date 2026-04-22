package ru.praktikum;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import ru.praktikum.model.Courier;
import ru.praktikum.util.CourierGenerator;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class CourierDeleteTest extends BaseApiTest {

    @Test
    void shouldDeleteCourier() {
        Courier courier = CourierGenerator.createRandomCourier();
        courierSteps.createCourier(courier);
        int courierId = courierSteps.getCourierId(courier);

        Response deleteResponse = courierSteps.deleteCourier(courierId);

        deleteResponse.then()
                .statusCode(SC_OK)
                .body("ok", equalTo(true));
    }

    @Test
    void shouldNotDeleteCourierWithoutId() {
        courierSteps.deleteCourierWithoutId().then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Not Found."));
    }

    @Test
    void shouldNotDeleteNonexistentCourier() {
        courierSteps.deleteCourier(999999999).then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Курьера с таким id нет."));
    }
}
