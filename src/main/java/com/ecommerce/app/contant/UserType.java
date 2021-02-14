package com.ecommerce.app.contant;

public enum UserType {

    SELLER("seller"),
    BUYER("buyer");


    private String text;

    UserType(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return  this.text;
    }
}
