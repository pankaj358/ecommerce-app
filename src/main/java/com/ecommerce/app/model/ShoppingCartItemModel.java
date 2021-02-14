package com.ecommerce.app.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;



/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class ShoppingCartItemModel implements Serializable {

    private UUID shoppingCarItemId;

    private UUID productId;

    private Integer quantity;

    private UUID shoppingCartId;


}
