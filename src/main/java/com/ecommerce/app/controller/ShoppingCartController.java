package com.ecommerce.app.controller;

import com.ecommerce.app.model.ShoppingCartModel;
import com.ecommerce.app.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@RestController
@RequestMapping(value = "/ecommerce-app/shopping-cart")
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

    @PostMapping
    public ResponseEntity<ShoppingCartModel> create(@RequestBody ShoppingCartModel shoppingCartModel) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(shoppingCartService.create(shoppingCartModel));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ShoppingCartModel> fetch(@PathVariable("cartId") String cartId) {
        return ResponseEntity.ok(shoppingCartService.fetch(cartId));
    }

    @GetMapping
    public ResponseEntity<List<ShoppingCartModel>> fetchAll() {
        return ResponseEntity.ok(shoppingCartService.fetchAll());
    }


}
