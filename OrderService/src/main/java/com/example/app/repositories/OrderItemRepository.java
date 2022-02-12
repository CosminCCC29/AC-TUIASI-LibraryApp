package com.example.app.repositories;

import com.example.app.Utilities.OrderStatus;
import com.example.app.interfaces.OrderItemRepositoryInterface;
import com.example.app.models.dbentities.OrderItemEntity;
import com.example.app.models.dtos.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

@Repository
public class OrderItemRepository implements OrderItemRepositoryInterface {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public OrderItem putOrderItem(OrderItem orderItem) {

        // Converting OrderItem to OrderItemEntity
        OrderItemEntity orderItemEntityToSave = new OrderItemEntity(orderItem);

        int orderId = Integer.parseInt(mongoTemplate.aggregate(Aggregation.newAggregation(group().max("_id").as("_id")), "client_" + orderItem.getClientId(), String.class).getMappedResults().get(0).split(":")[1].split("}")[0].strip()) + 1;
        orderItemEntityToSave.set_id(orderId);

        // Storing the book
        OrderItemEntity savedEntity = mongoTemplate.save(orderItemEntityToSave, "client_" + orderItem.getClientId());

        // Converting OrderItemEntity to OrderItem
        OrderItem savedOrderItem = new OrderItem(savedEntity);
        savedOrderItem.setClientId(orderItem.getClientId());

        return savedOrderItem;
    }

    @Override
    public long updateOrderStatus(int clientId, int orderId, OrderStatus orderStatus)
    {
        Query query = Query.query(Criteria.where("_id").is(orderId));
        Update update = new Update();
        update.set("status", orderStatus);

        return mongoTemplate.updateFirst(query, update, "client_"+clientId).getMatchedCount();
    }

    @Override
    public void deleteOrderItemById(int clientId, int orderId) {
        mongoTemplate.remove("{ _id : "+orderId+" }", "client_"+clientId);
    }
}
