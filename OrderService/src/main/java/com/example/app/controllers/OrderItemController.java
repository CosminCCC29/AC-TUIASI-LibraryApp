package com.example.app.controllers;

import com.example.app.interfaces.BookShelfApiRequestServiceInterface;
import com.example.app.interfaces.OrderItemRepositoryInterface;
import com.example.app.models.dtos.Book;
import com.example.app.models.dtos.BookAndStock;
import com.example.app.models.dtos.BookItem;
import com.example.app.models.dtos.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orderservice/orderstorage")
public class OrderItemController {

    @Autowired
    BookShelfApiRequestServiceInterface bookShelfApiRequestService;

    @Autowired
    OrderItemRepositoryInterface orderItemService;

    @PostMapping("/{clientId}")
    public ResponseEntity<?> postOrderItem(@PathVariable int clientId, @RequestBody OrderItem orderItem)
    {
        orderItem.setClientId(clientId);

        // ----------

        List<BookAndStock> bookAndStockList = new LinkedList<>(orderItem.getBookItems()).stream().map(bookItem -> new BookAndStock(bookItem.getISBN(), bookItem.getQuantity())).collect(Collectors.toList());

        try {
            ResponseEntity<List<BookAndStock>> responseEntity = bookShelfApiRequestService.validateOrderStockRequest(bookAndStockList);
        }
        catch (HttpStatusCodeException httpStatusCodeException)
        {
            return ResponseEntity.status(httpStatusCodeException.getStatusCode()).headers(httpStatusCodeException.getResponseHeaders()).body(httpStatusCodeException.getResponseBodyAsString());
        }

        // -----------

        // todo Gestionat daca comanda exista deja
        OrderItem savedOrderItem = orderItemService.putOrderItem(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderItem);
    }

    @DeleteMapping("/{clientId}/{orderId}")
    void deleteOrderItem(@PathVariable int clientId, @PathVariable int orderId)
    {
        // orderItemService.deleteOrderItemById(clientId, orderId);
    }

}
