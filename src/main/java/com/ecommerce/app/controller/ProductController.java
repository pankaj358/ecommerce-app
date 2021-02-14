package com.ecommerce.app.controller;

import com.ecommerce.app.model.ProductModel;
import com.ecommerce.app.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@RestController
@RequestMapping("/ecommerce-app/product")
public class ProductController {


    @Resource
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductModel> create(@RequestBody ProductModel productModel)
    {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.create(productModel));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductModel> fetch(@PathVariable("productId") String productId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(productService.fetch(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> fetchAll()
    {
        return ResponseEntity.status(HttpStatus.OK).body(productService.fetchAll());
    }
}
