package ru.praktikum.util;

import ru.praktikum.model.Courier;
import ru.praktikum.model.CourierCredentials;

import java.util.UUID;

public final class CourierGenerator {

    private CourierGenerator() {
    }

    public static Courier createRandomCourier() {
        String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        return new Courier(
                "courier_" + suffix,
                "password_" + suffix,
                "Name" + suffix
        );
    }

    public static CourierCredentials credentialsFromCourier(Courier courier) {
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }
}
