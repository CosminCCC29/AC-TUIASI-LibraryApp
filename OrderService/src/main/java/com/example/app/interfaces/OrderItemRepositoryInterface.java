package com.example.app.interfaces;

import com.example.app.Utilities.OrderStatus;
import com.example.app.models.dtos.OrderItem;

public interface OrderItemRepositoryInterface {
    OrderItem putOrderItem(OrderItem orderItem);
    long updateOrderStatus(int clientId, int orderId, OrderStatus orderStatus);
    void deleteOrderItemById(int clientId, int orderId);
}
