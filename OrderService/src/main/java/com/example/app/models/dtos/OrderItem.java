package com.example.app.models.dtos;

import com.example.app.Utilities.OrderStatus;
import com.example.app.models.dbentities.OrderItemEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private int clientId;
    private Date date;
    private List<BookItem> bookItems;
    private OrderStatus status;

    public OrderItem(OrderItemEntity orderItemEntity)
    {
        date = orderItemEntity.getDate();
        bookItems = orderItemEntity.getBookItems();
        status = orderItemEntity.getStatus();
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
