package com.ecommerce.app.service;

import com.ecommerce.app.contant.ErrorMessages;
import com.ecommerce.app.util.EcommerceUtil;
import com.ecommerce.app.domain.Product;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.model.ProductModel;
import com.ecommerce.app.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Pankaj Tirpude [tirpudepankaj@gmail.com]
 * @date : 20210210
 */

@Service
public class ProductServiceImpl implements ProductService {


    @Resource
    private ProductRepository productRepository;

    @Resource
    private ValidationService validationService;

    @Resource
    private EcommerceUtil ecommerceUtil;

    /**
     * @param productModel
     * @return
     */
    @Override
    public ProductModel create(ProductModel productModel) {

        /**
         *  FIXME
         *  Do we need to add product with available flag as false? if yes why?
         */

        validationService.isValidProduct(productModel);
        Product entity = ecommerceUtil.toProduct(productModel);
        entity = productRepository.save(entity);
        productModel = ecommerceUtil.toProductModel(entity);
        return productModel;
    }

    /**
     * @param productId
     * @return
     * @refactor-note while fetching the product details do we need to fetch
     * product which are not available?
     */
    @Override
    public ProductModel fetch(String productId) {

        Optional<Product> optionalProduct = productRepository.findById(UUID.fromString(productId));
        if (optionalProduct.isPresent()) {
            return ecommerceUtil.toProductModel(optionalProduct.get());
        }
        /**
         *  FIXME
         *  Please refactor me! EntryNotFoundException said once?
         */
        throw new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND.getText(), productId));
    }

    /**
     * @return
     * @refactor-note while fetching the product details do we need to fetch
     * product which are not available?
     */
    @Override
    public List<ProductModel> fetchAll() {

        List<Product> list = (List<Product>) productRepository.findAll();
        List<ProductModel> modelList =
                list.stream()
                        .map(product -> ecommerceUtil.toProductModel(product))
                        .collect(Collectors.toList());
        return modelList;
    }
}
