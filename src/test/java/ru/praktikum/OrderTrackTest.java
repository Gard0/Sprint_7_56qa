package ru.praktikum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.praktikum.model.Order;
import ru.praktikum.steps.OrderSteps;
import ru.praktikum.util.OrderGenerator;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
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
                .statusCode(SC_CREATED)
                .extract()
                .path("track");
    }

    @Test
    void shouldReturnOrderByTrack() {
        orderSteps.getOrderByTrack(track).then()
                .statusCode(SC_OK)
                .body("order", notNullValue())
                .body("order.track", equalTo(track));
    }

    @Test
    void shouldNotReturnOrderWithoutTrack() {
        orderSteps.getOrderByTrackWithoutTrack().then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    void shouldNotReturnOrderForWrongTrack() {
        orderSteps.getOrderByTrack(999999999).then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Заказ не найден"));
    }
}
