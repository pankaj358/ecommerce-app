package com.ecommerce.app.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Builder
public class ShoppingCartModel implements Serializable {


   private UUID userId;

   private UUID shoppingCartId;

   private BigDecimal cartPrice;

   private List<ShoppingCartItemModel> itemList;


}
