package ru.praktikum;

import org.junit.jupiter.api.AfterEach;
import ru.praktikum.steps.CourierSteps;

public abstract class BaseApiTest {

    protected final CourierSteps courierSteps = new CourierSteps();
    protected Integer courierIdToDelete;

    @AfterEach
    void cleanupCourier() {
        if (courierIdToDelete != null) {
            courierSteps.deleteCourier(courierIdToDelete);
            courierIdToDelete = null;
        }
    }
}
