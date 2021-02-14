package com.ecommerce.app.contant;

public enum TransactionStatus {

    ACCEPTED("accepted"),
    PENDING("pending"),
    DECLINE("decline");

    private String text;


    TransactionStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
