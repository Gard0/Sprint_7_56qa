package ru.praktikum.util;

import ru.praktikum.model.Order;

import java.time.LocalDate;
import java.util.List;

public final class OrderGenerator {

    private OrderGenerator() {
    }

    public static Order createOrder(List<String> colors) {
        return new Order(
                "Иван",
                "Иванов",
                "Москва, Красная площадь, 1",
                4,
                "+79990001122",
                3,
                LocalDate.now().plusDays(1).toString(),
                "Тестовый заказ",
                colors
        );
    }
}
