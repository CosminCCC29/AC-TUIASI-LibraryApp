package com.example.app.models.dbentities;

import com.example.app.Utilities.OrderStatus;
import com.example.app.models.dtos.BookItem;
import com.example.app.models.dtos.OrderItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity {
    private int _id;
    private Date date;
    private List<BookItem> bookItems;
    private OrderStatus status;

    public OrderItemEntity(OrderItem orderItem)
    {
        date = orderItem.getDate();
        bookItems = orderItem.getBookItems();
        status = orderItem.getStatus();
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
