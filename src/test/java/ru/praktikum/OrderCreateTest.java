package ru.praktikum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.praktikum.model.Order;
import ru.praktikum.steps.OrderSteps;
import ru.praktikum.util.OrderGenerator;

import java.util.List;
import java.util.stream.Stream;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class OrderCreateTest {

    private final OrderSteps orderSteps = new OrderSteps();

    @ParameterizedTest(name = "Создание заказа с цветами: {0}")
    @MethodSource("orderColors")
    void shouldCreateOrderWithDifferentColors(List<String> colors) {
        Order order = OrderGenerator.createOrder(colors);

        orderSteps.createOrder(order).then()
                .statusCode(SC_CREATED)
                .body("track", notNullValue())
                .body("track", greaterThan(0));
    }

    private static Stream<Arguments> orderColors() {
        return Stream.of(
                Arguments.of(List.of("BLACK")),
                Arguments.of(List.of("GREY")),
                Arguments.of(List.of("BLACK", "GREY")),
                Arguments.of((Object) null)
        );
    }
}
