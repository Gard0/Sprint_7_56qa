package ru.praktikum;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import ru.praktikum.model.Courier;
import ru.praktikum.model.CourierCredentials;
import ru.praktikum.util.CourierGenerator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest extends BaseApiTest {

    @Test
    void shouldLoginCourier() {
        Courier courier = CourierGenerator.createRandomCourier();
        courierSteps.createCourier(courier);
        courierIdToDelete = courierSteps.getCourierId(courier);

        Response loginResponse = courierSteps.loginCourier(CourierGenerator.credentialsFromCourier(courier));

        loginResponse.then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("id", greaterThan(0));
    }

    @Test
    void shouldNotLoginWithoutLogin() {
        Response loginResponse = courierSteps.loginCourier(new CourierCredentials(null, "password"));

        loginResponse.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    void shouldNotLoginWithWrongPassword() {
        Courier courier = CourierGenerator.createRandomCourier();
        courierSteps.createCourier(courier);
        courierIdToDelete = courierSteps.getCourierId(courier);

        Response loginResponse = courierSteps.loginCourier(new CourierCredentials(courier.getLogin(), "wrongPassword"));

        loginResponse.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    void shouldNotLoginNonexistentCourier() {
        Courier courier = CourierGenerator.createRandomCourier();

        Response loginResponse = courierSteps.loginCourier(CourierGenerator.credentialsFromCourier(courier));

        loginResponse.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}