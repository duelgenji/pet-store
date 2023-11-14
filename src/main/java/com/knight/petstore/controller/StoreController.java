package com.knight.petstore.controller;

import com.knight.petstore.data.OrderData;
import com.knight.petstore.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author knight
 * @since 2023/11/8
 **/
@Tag(name="store", description = "Access to Petstore orders")
@RequestMapping("store")
@RestController
public class StoreController {

    private static OrderData orderData = new OrderData();

    @Operation(summary="订购宠物", description = "订购一个宠物")
    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {

        if (order == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No Order provided. Try again?");
        }

        orderData.addOrder(order);
        return ResponseEntity.ok("order created");
    }

    @Operation(summary="删除一个订单By username", description = "删除指定orderId的订单")
    @DeleteMapping("{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {

        if (orderId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No orderId provided. Try again?");
        }

        orderData.deleteOrderById(orderId);
        Order order = orderData.getOrderById(orderId);

        if (null == order) {
            return ResponseEntity.ok("order deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Order couldn't be deleted.");
        }
    }


    @Operation(summary="获取订单信息By orderID", description = "获取指定orderId的订单信息")
    @GetMapping("{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {

        if (orderId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No orderId provided. Try again?");
        }
        Order order = orderData.getOrderById(orderId);

        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(order);
    }

    @Operation(summary="获取各状态宠物的订单数量", description = "获取各状态宠物的订单数量")
    @GetMapping("inventory")
    public ResponseEntity<?> getInventory() {
        Map<String, Integer> map = orderData.getCountByStatus();
        return ResponseEntity.ok(map);
    }
}
