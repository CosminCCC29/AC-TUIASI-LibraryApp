package com.example.app.controllers;

import com.example.app.Utilities.OrderStatus;
import com.example.app.interfaces.BookShelfApiRequestServiceInterface;
import com.example.app.interfaces.OrderItemRepositoryInterface;
import com.example.app.models.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orderservice/orderstorage")
public class OrderItemController {

    @Autowired
    BookShelfApiRequestServiceInterface bookShelfApiRequestService;

    @Autowired
    OrderItemRepositoryInterface orderItemService;

    @PostMapping("/{clientId}")
    public ResponseEntity<?> postOrderItem(@RequestHeader("Authorization") String authorizationHeaderField, @PathVariable int clientId, @RequestBody OrderItem orderItem)
    {
        if(authorizationHeaderField == null || !authorizationHeaderField.startsWith("Bearer "))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Token token = new Token(authorizationHeaderField.substring("Bearer ".length()));

        orderItem.setClientId(clientId);

        // ----------

        List<BookAndStock> bookAndStockList = new LinkedList<>(orderItem.getBookItems()).stream().map(bookItem -> new BookAndStock(bookItem.getISBN(), bookItem.getQuantity())).collect(Collectors.toList());

        try {
            ResponseEntity<List<BookAndStock>> responseEntity = bookShelfApiRequestService.validateOrderStockRequest(token, bookAndStockList);
        }
        catch (HttpStatusCodeException httpStatusCodeException)
        {
            return ResponseEntity.status(httpStatusCodeException.getStatusCode()).headers(httpStatusCodeException.getResponseHeaders()).body(httpStatusCodeException.getResponseBodyAsString());
        }

        // -----------

        OrderItem savedOrderItem = orderItemService.putOrderItem(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderItem);
    }

    @RequestMapping(value = "/{clientId}/{orderId}", method = RequestMethod.PATCH)
    ResponseEntity<?> changeOrderStatus(@PathVariable int clientId, @PathVariable int orderId, @RequestBody Map<String, Integer> requestBody)
    {
        OrderStatus orderStatusEnum = OrderStatus.values()[requestBody.get("status")];

        if(orderItemService.updateOrderStatus(clientId, orderId, orderStatusEnum) == 1)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
