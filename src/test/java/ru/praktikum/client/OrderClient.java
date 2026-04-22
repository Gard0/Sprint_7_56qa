package ru.praktikum.client;

import io.restassured.response.Response;
import ru.praktikum.config.Endpoints;
import ru.praktikum.model.Order;

public class OrderClient extends BaseClient {

    public Response createOrder(Order order) {
        return post(Endpoints.ORDERS, order);
    }

    public Response getOrders() {
        return get(Endpoints.ORDERS);
    }

    public Response acceptOrder(int orderId, int courierId) {
        return putWithQuery(Endpoints.ORDERS_ACCEPT + "/" + orderId, "courierId", courierId);
    }

    public Response acceptOrderWithoutCourierId(int orderId) {
        return put(Endpoints.ORDERS_ACCEPT + "/" + orderId);
    }

    public Response acceptOrderWithoutOrderId(int courierId) {
        return putWithQuery(Endpoints.ORDERS_ACCEPT + "/", "courierId", courierId);
    }

    public Response getOrderByTrack(int track) {
        return getWithQuery(Endpoints.ORDERS_TRACK, "t", track);
    }

    public Response getOrderByTrackWithoutTrack() {
        return get(Endpoints.ORDERS_TRACK);
    }
}
