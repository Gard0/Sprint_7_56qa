package ru.praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.praktikum.model.Courier;
import ru.praktikum.model.Order;
import ru.praktikum.steps.OrderSteps;
import ru.praktikum.util.CourierGenerator;
import ru.praktikum.util.OrderGenerator;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class OrderAcceptTest extends BaseApiTest {

    private final OrderSteps orderSteps = new OrderSteps();
    private int orderId;

    @BeforeEach
    void setUpOrderAndCourier() {
        Courier courier = CourierGenerator.createRandomCourier();
        courierSteps.createCourier(courier);
        courierIdToDelete = courierSteps.getCourierId(courier);

        Order order = OrderGenerator.createOrder(null);
        int track = orderSteps.createOrder(order)
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .path("track");

        orderId = orderSteps.getOrderByTrack(track)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("order.id");
    }

    @Test
    void shouldAcceptOrder() {
        orderSteps.acceptOrder(orderId, courierIdToDelete).then()
                .statusCode(SC_OK)
                .body("ok", equalTo(true));
    }

    @Test
    void shouldNotAcceptAcceptedOrder() {
        orderSteps.acceptOrder(orderId, courierIdToDelete).then()
                .statusCode(SC_OK)
                .body("ok", equalTo(true));

        orderSteps.acceptOrder(orderId, courierIdToDelete).then()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот заказ уже в работе"));
    }

    @Test
    void shouldNotAcceptOrderWithoutCourierId() {
        orderSteps.acceptOrderWithoutCourierId(orderId).then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    void shouldNotAcceptOrderWithWrongCourierId() {
        orderSteps.acceptOrder(orderId, 999999999).then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Курьера с таким id не существует"));
    }

    @Test
    void shouldNotAcceptOrderWithoutOrderId() {
        orderSteps.acceptOrderWithoutOrderId(courierIdToDelete).then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Not Found."));
    }

    @Test
    void shouldNotAcceptOrderWithWrongOrderId() {
        orderSteps.acceptOrder(999999999, courierIdToDelete).then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Заказа с таким id не существует"));
    }
}
