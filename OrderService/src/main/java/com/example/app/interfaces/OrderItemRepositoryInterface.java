package com.example.app.interfaces;

import com.example.app.models.dtos.OrderItem;

public interface OrderItemRepositoryInterface {
    OrderItem putOrderItem(OrderItem orderItem);
    void deleteOrderItemById(int clientId, int orderId);
}
