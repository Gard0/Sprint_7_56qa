package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.client.OrderClient;
import ru.praktikum.model.Order;

public class OrderSteps {

    private final OrderClient orderClient = new OrderClient();

    @Step("Создать заказ")
    public Response createOrder(Order order) {
        return orderClient.createOrder(order);
    }

    @Step("Получить список заказов")
    public Response getOrders() {
        return orderClient.getOrders();
    }

    @Step("Получить заказ по треку {track}")
    public Response getOrderByTrack(int track) {
        return orderClient.getOrderByTrack(track);
    }

    @Step("Получить заказ без трека")
    public Response getOrderByTrackWithoutTrack() {
        return orderClient.getOrderByTrackWithoutTrack();
    }

    @Step("Принять заказ {orderId} курьером {courierId}")
    public Response acceptOrder(int orderId, int courierId) {
        return orderClient.acceptOrder(orderId, courierId);
    }

    @Step("Принять заказ {orderId} без courierId")
    public Response acceptOrderWithoutCourierId(int orderId) {
        return orderClient.acceptOrderWithoutCourierId(orderId);
    }

    @Step("Принять заказ без orderId")
    public Response acceptOrderWithoutOrderId(int courierId) {
        return orderClient.acceptOrderWithoutOrderId(courierId);
    }
}
