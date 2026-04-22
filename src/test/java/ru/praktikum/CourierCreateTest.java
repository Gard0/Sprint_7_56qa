package ru.praktikum;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import ru.praktikum.model.Courier;
import ru.praktikum.util.CourierGenerator;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest extends BaseApiTest {

    @Test
    void shouldCreateCourier() {
        Courier courier = CourierGenerator.createRandomCourier();

        Response createResponse = courierSteps.createCourier(courier);
        courierIdToDelete = courierSteps.getCourierId(courier);

        createResponse.then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    void shouldNotCreateDuplicateCourier() {
        Courier courier = CourierGenerator.createRandomCourier();
        courierSteps.createCourier(courier);
        courierIdToDelete = courierSteps.getCourierId(courier);

        Response duplicateResponse = courierSteps.createCourier(courier);

        duplicateResponse.then()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    void shouldNotCreateCourierWithoutLogin() {
        Courier courier = new Courier(null, "testPassword", "CourierName");

        Response createResponse = courierSteps.createCourier(courier);

        createResponse.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    void shouldNotCreateCourierWithoutPassword() {
        Courier courier = new Courier("courierWithoutPassword", null, "CourierName");

        Response createResponse = courierSteps.createCourier(courier);

        createResponse.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
