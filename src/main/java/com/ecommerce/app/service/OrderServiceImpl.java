package com.ecommerce.app.service;

import com.ecommerce.app.contant.ErrorMessages;
import com.ecommerce.app.contant.OrderStatus;
import com.ecommerce.app.contant.TransactionStatus;
import com.ecommerce.app.domain.Order;
import com.ecommerce.app.domain.ShoppingCart;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.exception.ValidationException;
import com.ecommerce.app.model.OrderModel;
import com.ecommerce.app.model.PaymentDetailsModel;
import com.ecommerce.app.repository.OrderRepository;
import com.ecommerce.app.repository.ShoppingCartRepository;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.util.EcommerceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * @author Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@Service
public class OrderServiceImpl implements OrderService {



    @Resource
    private OrderRepository orderRepository;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private ValidationService validationService;
    /**
     *  FIXME
     *  we shouldn't be using shopping-repo, user-repo
     *  in microservice-architecture this service should talks with
     *  other service not with the repo
     */
    @Resource
    private UserRepository userRepository;
    @Resource
    private ShoppingCartRepository shoppingCartRepository;

    @Resource
    private EcommerceUtil ecommerceUtil;

    @Resource
    private PaymentService paymentService;

    /**
     * @param orderModel
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderModel create(OrderModel orderModel) {

        /**
         *  FIXME
         *  Do we need validate order on below things?
         *  1. Shopping Cart Id
         *  2. User Id
         *
         */

        validationService.isValidOrder(orderModel);
        Order order = objectMapper.convertValue(orderModel, Order.class);
        order.setOrderDate(Instant.now().truncatedTo(ChronoUnit.HOURS));
        order.setUser(userRepository.findById(orderModel.getUserId()).get());
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findById(orderModel.getShoppingCartId());
        if (optionalCart.isPresent() == false)
            throw new ValidationException(ErrorMessages.ORDER_VALIDATION_FAILED_FOR_UNKNOWN_REASON.getText());
        order.setShoppingCart(optionalCart.get());

        /**
         *  FIXME
         *  Delivery date can be fetch from delivery-service
         *  and It should be update as per delivery service criteria
         *
         */
        order.setDeliveryDate(Instant.now().plus(5, ChronoUnit.DAYS).truncatedTo(ChronoUnit.HOURS));

        /**
         *  Transaction status could be decline in that case what we need to do?
         *  Reply with payment failure and ask user to try again sometimes;
         */
        PaymentDetailsModel paymentDetails = PaymentDetailsModel
                .builder()
                .transactionStatus(TransactionStatus.ACCEPTED.getText())
                .methodType(orderModel.getPaymentDetails().getMethodType())
                .paymentDate(Instant.now())
                .amount(optionalCart.get().getCartPrice())
                .build();
        validationService.isValidPaymentDetails(paymentDetails);
        /**
         * FIXME
         * The initial order status would be 'accepted' and it should be change
         * as time passed 'picked-to-deliver -> on-the-way -> delivered ' etc
         * this status should be managed or hook using delivery-service.
         */
        order.setPaymentDetails(ecommerceUtil.toPaymentDetails(paymentDetails));
        order.setOrderStatus(OrderStatus.ACCEPTED.getText());
        order = orderRepository.save(order);

        return ecommerceUtil.toOrderModel(order);
    }

    @Override
    public OrderModel fetch(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(UUID.fromString(orderId));
        if (optionalOrder.isPresent()) {
            return ecommerceUtil.toOrderModel(optionalOrder.get());
        }
        throw new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND.getText(), orderId));
    }

    @Override
    public List<OrderModel> fetchAll() {
        List<Order> orderList = (List<Order>) orderRepository.findAll();
        List<OrderModel> modelList = orderList
                .stream().map(order -> ecommerceUtil.toOrderModel(order))
                .collect(Collectors.toList());
        return modelList;
    }

    @Override
    public Optional<OrderModel> fetchOrderByCart(String cartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(UUID.fromString(cartId)).get();
        Optional<Order> optionalOrder = orderRepository.findByShoppingCart(shoppingCart);
        if (optionalOrder.isPresent() == false)
            return Optional.empty();
        return Optional.of(ecommerceUtil.toOrderModel(optionalOrder.get()));
    }


    /**
     *
     * @param orderId
     * @param currentStatus
     * @return
     */

    @Override
    public OrderModel patchOrder(String orderId, String currentStatus) {
        Optional<Order> exitingOrder = orderRepository.findById(UUID.fromString(orderId));
        if (exitingOrder.isPresent()) {
            Order order = exitingOrder.get();
            order.setOrderStatus(currentStatus);
            order = orderRepository.save(order);
            return objectMapper.convertValue(order, OrderModel.class);
        }

        throw new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND.getText(), orderId));

    }
}
