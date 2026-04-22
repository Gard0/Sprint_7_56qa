package ru.praktikum;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import ru.praktikum.model.Courier;
import ru.praktikum.model.CourierCredentials;
import ru.praktikum.util.CourierGenerator;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
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
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .body("id", greaterThan(0));
    }

    @Test
    void shouldNotLoginWithoutLogin() {
        Response loginResponse = courierSteps.loginCourier(new CourierCredentials(null, "password"));

        loginResponse.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    void shouldNotLoginWithoutPassword() {
        Response loginResponse = courierSteps.loginCourier(new CourierCredentials("login", ""));

        loginResponse.then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    void shouldNotLoginWithWrongPassword() {
        Courier courier = CourierGenerator.createRandomCourier();
        courierSteps.createCourier(courier);
        courierIdToDelete = courierSteps.getCourierId(courier);

        Response loginResponse = courierSteps.loginCourier(new CourierCredentials(courier.getLogin(), "wrongPassword"));

        loginResponse.then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    void shouldNotLoginNonexistentCourier() {
        Courier courier = CourierGenerator.createRandomCourier();

        Response loginResponse = courierSteps.loginCourier(CourierGenerator.credentialsFromCourier(courier));

        loginResponse.then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
