package com.ecommerce.app.util;

import com.ecommerce.app.domain.*;
import com.ecommerce.app.model.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public final class EcommerceUtil {


    public final Product toProduct(ProductModel productModel) {
        Product product = new Product();

        product.setProductName(productModel.getProductName());
        product.setProductPrice(productModel.getProductPrice());
        product.setAvailable(productModel.isAvailable());
        User user = new User();
        user.setUserId(productModel.getSellerId());
        product.setSeller(user);

        return product;
    }


    public final ProductModel toProductModel(Product product) {
        ProductModel productModel = new ProductModel();
        productModel.setProductId(product.getProductId());
        productModel.setProductPrice(product.getProductPrice());
        productModel.setProductName(product.getProductName());
        productModel.setAvailable(product.isAvailable());
        productModel.setSellerId(product.getSeller().getUserId());
        return productModel;
    }

    public final ShoppingCart toShoppingCart(ShoppingCartModel shoppingCartModel) {
        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(User.builder().userId(shoppingCartModel.getUserId()).build());
        shoppingCart.setShoppingCartItemList(shoppingCartModel
                .getItemList().stream().map(cartItemModel -> toShoppingCartItem(cartItemModel, shoppingCart)).collect(Collectors.toList()));
        return shoppingCart;
    }

    public final ShoppingCartItem toShoppingCartItem(ShoppingCartItemModel cartItemModel, final ShoppingCart shoppingCart) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setProductId(cartItemModel.getProductId());
        shoppingCartItem.setShoppingCart(shoppingCart);
        shoppingCartItem.setQuantity(cartItemModel.getQuantity());
        return shoppingCartItem;
    }

    public final ShoppingCartModel toShoppingCartModel(final ShoppingCart shoppingCart) {
        final ShoppingCartModel cartModel = new ShoppingCartModel();
        cartModel.setShoppingCartId(shoppingCart.getShoppingCartId());
        cartModel.setCartPrice(shoppingCart.getCartPrice());
        cartModel.setUserId(shoppingCart.getUser().getUserId());
        cartModel.setItemList(shoppingCart.getShoppingCartItemList().stream()
                .map(shoppingCartItem -> toShoppingCartItemModel(shoppingCartItem, shoppingCart.getShoppingCartId())).collect(Collectors.toList()));
        return cartModel;
    }

    public final ShoppingCartItemModel toShoppingCartItemModel(ShoppingCartItem shoppingCartItem, final UUID cartId) {
        ShoppingCartItemModel shoppingCartItemModel = new ShoppingCartItemModel();
        shoppingCartItemModel.setShoppingCarItemId(shoppingCartItem.getShoppingCarItemId());
        shoppingCartItemModel.setShoppingCartId(cartId);
        shoppingCartItemModel.setProductId(shoppingCartItem.getProductId());
        shoppingCartItemModel.setQuantity(shoppingCartItem.getQuantity());
        return shoppingCartItemModel;
    }

    public final UserModel toUserModel(User user) {
        return
                UserModel.builder()
                        .userId(user.getUserId())
                        .userType(user.getUserType())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .country(user.getCountry())
                        .phoneNumber(user.getPhoneNumber())
                        .build();
    }

    public final User toUser(UserModel userModel) {
        return
                User.builder()
                        .userId(userModel.getUserId())
                        .firstName(userModel.getFirstName())
                        .lastName(userModel.getLastName())
                        .country(userModel.getCountry())
                        .userType(userModel.getUserType())
                        .email(userModel.getEmail())
                        .phoneNumber(userModel.getPhoneNumber())
                        .build();
    }

    public final OrderModel toOrderModel(Order order) {
        return OrderModel.builder().orderId(order.getOrderId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .deliveryDate(order.getDeliveryDate())
                .paymentDetails(toPaymentDetailsModel(order.getPaymentDetails()))
                .shoppingCartId(order.getShoppingCart().getShoppingCartId())
                .userId(order.getUser().getUserId())
                .build();
    }


    public final PaymentDetails toPaymentDetails(PaymentDetailsModel paymentDetailsModel)
    {
        return PaymentDetails
                .builder()
                .amount(paymentDetailsModel.getAmount())
                .paymentDate(Instant.now())
                .methodType(paymentDetailsModel.getMethodType())
                .paymentStatus(paymentDetailsModel.getTransactionStatus())
                .build();
    }

    public final PaymentDetailsModel toPaymentDetailsModel(PaymentDetails paymentDetails)
    {
        return PaymentDetailsModel
                .builder()
                .paymentDate(paymentDetails.getPaymentDate())
                .paymentId(paymentDetails.getPaymentId())
                .amount(paymentDetails.getAmount())
                .methodType(paymentDetails.getMethodType())
                .transactionStatus(paymentDetails.getPaymentStatus())
                .build();
    }

}
