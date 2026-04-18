package ru.praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.praktikum.model.Order;
import ru.praktikum.steps.OrderSteps;
import ru.praktikum.util.OrderGenerator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderTrackTest {

    private final OrderSteps orderSteps = new OrderSteps();
    private int track;

    @BeforeEach
    void setUpOrder() {
        Order order = OrderGenerator.createOrder(null);
        track = orderSteps.createOrder(order)
                .then()
                .statusCode(201)
                .extract()
                .path("track");
    }

    @Test
    void shouldReturnOrderByTrack() {
        orderSteps.getOrderByTrack(track).then()
                .statusCode(200)
                .body("order", notNullValue())
                .body("order.track", equalTo(track));
    }

    @Test
    void shouldNotReturnOrderWithoutTrack() {
        orderSteps.getOrderByTrackWithoutTrack().then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    void shouldNotReturnOrderForWrongTrack() {
        orderSteps.getOrderByTrack(999999999).then()
                .statusCode(404)
                .body("message", equalTo("Заказ не найден"));
    }
}
