package com.example.app.controllers;

import com.example.app.Utilities.OrderStatus;
import com.example.app.interfaces.BookShelfApiRequestServiceInterface;
import com.example.app.interfaces.OrderItemRepositoryInterface;
import com.example.app.models.dtos.BookAndStock;
import com.example.app.models.dtos.OrderItem;
import com.example.app.models.dtos.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(name = "Orders", description = "Endpoints for managing user's orders")
@RequestMapping("/orderservice/orderstorage")
public class OrderItemController {

    @Autowired
    private BookShelfApiRequestServiceInterface bookShelfApiRequestService;

    @Autowired
    private OrderItemRepositoryInterface orderItemService;

    @Operation(summary = "Change order status", security = @SecurityRequirement(name = "TokenAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The order status was successfully changed", content = @Content),
            @ApiResponse(responseCode = "404", description = "The ordered item has not been found", content = @Content)
    })
    @RequestMapping(value = "/{clientId}/{orderId}", method = RequestMethod.PATCH)
    ResponseEntity<?> changeOrderStatus(@PathVariable int clientId, @PathVariable int orderId, @RequestBody Map<String, Integer> requestBody)
    {
        OrderStatus orderStatusEnum = OrderStatus.values()[requestBody.get("status")];

        if(orderItemService.updateOrderStatus(clientId, orderId, orderStatusEnum) == 1)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Make a new order", security = @SecurityRequirement(name = "TokenAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The order was successfully placed", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OrderItem.class))}),
            @ApiResponse(responseCode = "406", description = "Invalid books or not enough stock", content = @Content)
    })
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

}
