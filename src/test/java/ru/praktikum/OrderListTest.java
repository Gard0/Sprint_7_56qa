package ru.praktikum;

import org.junit.jupiter.api.Test;
import ru.praktikum.steps.OrderSteps;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {

    private final OrderSteps orderSteps = new OrderSteps();

    @Test
    void shouldReturnOrdersList() {
        orderSteps.getOrders().then()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}
