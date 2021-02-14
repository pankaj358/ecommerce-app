package com.ecommerce.app.service;

import com.ecommerce.app.contant.ErrorMessages;
import com.ecommerce.app.contant.UserType;
import com.ecommerce.app.domain.ShoppingCart;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.exception.ValidationException;
import com.ecommerce.app.model.ProductModel;
import com.ecommerce.app.model.ShoppingCartItemModel;
import com.ecommerce.app.model.ShoppingCartModel;
import com.ecommerce.app.model.UserModel;
import com.ecommerce.app.repository.ShoppingCartRepository;
import com.ecommerce.app.util.EcommerceUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Resource
    private ShoppingCartRepository shoppingCartRepository;

    @Resource
    private ProductService productService;


    @Resource
    private ValidationService validationService;

    @Resource
    private EcommerceUtil ecommerceUtil;

    @Override
    public ShoppingCartModel create(ShoppingCartModel shoppingCartModel) {
        /**
         *  FIXME
         *  What if user provided same product twice for same cart ? Do we need to adjust it into the quantity?
         *  What if user is seller of product himself? Do we need to restrict him to buy
         *  What if the product is not available do we need to discard that item only or
         *  we need to discard whole cart?
         */

        validationService.isValidUser(UserModel.builder().userId(shoppingCartModel.getUserId()).userType(UserType.BUYER.getText()).build());
        List<ShoppingCartItemModel> cartItemList = shoppingCartModel.getItemList();
        cartItemList.forEach(this::isValidCartItem);
        shoppingCartModel.setCartPrice(calPrice(shoppingCartModel.getItemList()));
        ShoppingCart shoppingCart = ecommerceUtil.toShoppingCart(shoppingCartModel);
        shoppingCart.setCartPrice(calPrice(shoppingCartModel.getItemList()));
        shoppingCart = shoppingCartRepository.save(shoppingCart);
        return ecommerceUtil.toShoppingCartModel(shoppingCart);
    }

    @Override
    public ShoppingCartModel fetch(String shoppingCartId) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(UUID.fromString(shoppingCartId));
        if (optionalShoppingCart.isPresent() == false)
            throw new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND.getText(), shoppingCartId));
        return ecommerceUtil.toShoppingCartModel(optionalShoppingCart.get());
    }

    @Override
    public List<ShoppingCartModel> fetchAll() {
        List<ShoppingCart> list = (List<ShoppingCart>) shoppingCartRepository.findAll();
        return
                list.stream()
                        .map(shoppingCart -> ecommerceUtil.toShoppingCartModel(shoppingCart))
                        .collect(Collectors.toList());

    }

    private BigDecimal calPrice(List<ShoppingCartItemModel> list) {
        BigDecimal price = BigDecimal.ZERO;
        for (ShoppingCartItemModel item : list) {
            BigDecimal pricePerItem = productService.fetch(String.valueOf(item.getProductId())).getProductPrice();
            pricePerItem = pricePerItem.multiply(new BigDecimal(String.valueOf(item.getQuantity())));
            price = price.add(pricePerItem);
        }
        return price;
    }


    private boolean isValidCartItem(ShoppingCartItemModel cartItemModel) {
        ProductModel productModel = productService.fetch(cartItemModel.getProductId().toString());

        if (productModel == null || productModel.isAvailable() == false)
            throw new ValidationException(String.valueOf(cartItemModel.getProductId()));

        return true;
    }
}
