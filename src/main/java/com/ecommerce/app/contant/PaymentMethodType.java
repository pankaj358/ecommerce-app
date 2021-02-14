package com.ecommerce.app.contant;

public enum PaymentMethodType {


    CREDIT_CARD("credit-card"),
    DEBIT_CART("debit-card"),
    CASH("cash"),
    CHECK("check");


    private String text;

    PaymentMethodType(String text) {
        this.text = text;
    }

    public String getText()
    {
        return this.text;
    }
}
