package com.ecommerce.app.controller;

import com.ecommerce.app.model.OrderModel;
import com.ecommerce.app.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Pankaj Tirpude [tirpudepankaj@gmail.com]
 */

@RestController
@RequestMapping(value = "/ecommerce-app/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderModel> create(@RequestBody OrderModel orderModel) {
        return ResponseEntity.accepted().body(orderService.create(orderModel));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderModel> fetch(@PathVariable("orderId") String orderId) {
        return ResponseEntity.ok(orderService.fetch(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderModel>> fetchAll() {
        return ResponseEntity.ok(orderService.fetchAll());
    }

    /**
     *
     * @param orderId
     * @param patchModel
     * @return
     *
     * It should be configure at delivery service.
     *
     * For every status-event this hook should be call by delivery service.
     *
     */
    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderModel> statusProgress(@PathVariable("orderId") String orderId, @RequestBody String currentStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.patchOrder(orderId, currentStatus));
    }
}
