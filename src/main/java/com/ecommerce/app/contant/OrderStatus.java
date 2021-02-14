package com.ecommerce.app.contant;

public enum OrderStatus {

    ACCEPTED("accepted"),

    DISPATCHED("dispatched");

    private String text;

    OrderStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
