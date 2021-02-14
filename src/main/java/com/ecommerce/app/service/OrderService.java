package com.ecommerce.app.service;


import com.ecommerce.app.domain.Order;
import com.ecommerce.app.model.OrderModel;

import java.util.List;
import java.util.Optional;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


public interface OrderService {

    public OrderModel create(OrderModel orderModel);

    public OrderModel fetch(String orderId);

    public List<OrderModel> fetchAll();

    public Optional<OrderModel> fetchOrderByCart(String cartId);

    public OrderModel patchOrder(String orderId, String currentStatus);

}
