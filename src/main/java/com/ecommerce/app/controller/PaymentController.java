package com.ecommerce.app.controller;

import com.ecommerce.app.model.PaymentDetailsModel;
import com.ecommerce.app.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("ecommerce-app/payment")
public class PaymentController {


    @Resource
    private PaymentService paymentService;

    @PostMapping("/charge")
    public ResponseEntity<PaymentDetailsModel> charge(@RequestBody PaymentDetailsModel paymentDetailsModel) {
        return ResponseEntity.accepted().body(paymentService.charge(paymentDetailsModel));
    }


    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDetailsModel> fetch(@PathVariable("paymentId") String paymentId) {
        return ResponseEntity.ok(paymentService.fetch(paymentId));
    }

    @GetMapping
    public ResponseEntity<List<PaymentDetailsModel>> fetchAll() {
        return ResponseEntity.ok(paymentService.fetchAll());
    }


}
