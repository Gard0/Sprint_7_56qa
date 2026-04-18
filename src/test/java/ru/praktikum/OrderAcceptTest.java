package ru.praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.praktikum.model.Courier;
import ru.praktikum.model.Order;
import ru.praktikum.steps.OrderSteps;
import ru.praktikum.util.CourierGenerator;
import ru.praktikum.util.OrderGenerator;

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
                .statusCode(201)
                .extract()
                .path("track");

        orderId = orderSteps.getOrderByTrack(track)
                .then()
                .statusCode(200)
                .extract()
                .path("order.id");
    }

    @Test
    void shouldAcceptOrder() {
        orderSteps.acceptOrder(orderId, courierIdToDelete).then()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    @Test
    void shouldNotAcceptOrderWithoutCourierId() {
        orderSteps.acceptOrderWithoutCourierId(orderId).then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    void shouldNotAcceptOrderWithWrongCourierId() {
        orderSteps.acceptOrder(orderId, 999999999).then()
                .statusCode(404)
                .body("message", equalTo("Курьера с таким id не существует"));
    }

    @Test
    void shouldNotAcceptOrderWithoutOrderId() {
        orderSteps.acceptOrderWithoutOrderId(courierIdToDelete).then()
                .statusCode(404);
    }

    @Test
    void shouldNotAcceptOrderWithWrongOrderId() {
        orderSteps.acceptOrder(999999999, courierIdToDelete).then()
                .statusCode(404)
                .body("message", equalTo("Заказа с таким id не существует"));
    }
}