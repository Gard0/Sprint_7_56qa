package ru.praktikum.client;

import io.restassured.response.Response;
import ru.praktikum.model.Order;

public class OrderClient extends BaseClient {

    private static final String ORDERS_PATH = "/orders";

    public Response createOrder(Order order) {
        return post(ORDERS_PATH, order);
    }

    public Response getOrders() {
        return get(ORDERS_PATH);
    }

    public Response acceptOrder(int orderId, int courierId) {
        return putWithQuery(ORDERS_PATH + "/accept/" + orderId, "courierId", courierId);
    }

    public Response acceptOrderWithoutCourierId(int orderId) {
        return put(ORDERS_PATH + "/accept/" + orderId);
    }

    public Response acceptOrderWithoutOrderId(int courierId) {
        return putWithQuery(ORDERS_PATH + "/accept/", "courierId", courierId);
    }

    public Response getOrderByTrack(int track) {
        return getWithQuery(ORDERS_PATH + "/track", "t", track);
    }

    public Response getOrderByTrackWithoutTrack() {
        return get(ORDERS_PATH + "/track");
    }
}
