package com.ecommerce.app.service;

import com.ecommerce.app.model.ProductModel;

import java.util.List;


/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */

public interface ProductService {

    public ProductModel create(ProductModel productModel);

    public ProductModel fetch(String productId);

    public List<ProductModel> fetchAll();

}
