package com.ecommerce.app.contant;

public enum ErrorMessages {

    RESOURCE_NOT_FOUND("'%s', Resource not found!"),

    PRODUCT_DETAILS_EMPTY("Product details are empty!"),

    NOT_SELLER("Only seller can add product!"),

    USER_TYPE_VALIDATION("User type fields is mandatory"),

    USER_EMPTY("User details are empty!"),

    ORDER_VALIDATION_FAILED_FOR_UNKNOWN_REASON("Something went wrong with order!, Can't placed the order at the moment."),

    PAYMENT_FAILURE_MESSAGE("Payment Failed, Either method type is null, Or We do not supporting check payment method at the moment.");


    private String text;

    ErrorMessages(String text)
    {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
