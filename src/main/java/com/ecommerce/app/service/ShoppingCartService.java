package com.ecommerce.app.service;

import com.ecommerce.app.model.ShoppingCartModel;

import java.util.List;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


public interface ShoppingCartService {

    public ShoppingCartModel create(ShoppingCartModel shoppingCartModel);

    public ShoppingCartModel fetch(String shoppingCartId);

    public List<ShoppingCartModel> fetchAll();

}
