package com.testShoopingCard.shopping_card.Service;

import com.testShoopingCard.shopping_card.ApplicationConstants.Applicationconstants;
import com.testShoopingCard.shopping_card.Dto.OrderDto;
import com.testShoopingCard.shopping_card.Entity.Cart;
import com.testShoopingCard.shopping_card.Entity.Order;
import com.testShoopingCard.shopping_card.Entity.OrderItem;
import com.testShoopingCard.shopping_card.Entity.Product;
import com.testShoopingCard.shopping_card.Enums.OrderStatus;
import com.testShoopingCard.shopping_card.Exception.ServiceException;
import com.testShoopingCard.shopping_card.Repository.OrderRepository;
import com.testShoopingCard.shopping_card.Repository.ProductRepository;
import com.testShoopingCard.shopping_card.Service.Interfaces.OrderInterface;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderInterface {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Integer userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItem(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);
        // after order is finished, need to clear relevant cards
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderItem> createOrderItem(Order order, Cart cart) {

        return cart.getItems().stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    if(product.getInventory() < cartItem.getQuantity()){
                        throw new ServiceException("Sorry you can't order this product, as all were sold",Applicationconstants.NOT_FOUND,HttpStatus.BAD_REQUEST);
                    }
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(order, product, cartItem.getUnitPrice(), cartItem.getQuantity());
                }).toList();
    }


    @Override
    public OrderDto getOrder(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ServiceException("Order id not found", Applicationconstants.NOT_FOUND, HttpStatus.BAD_REQUEST));
    }

    @Override
    public List<OrderDto> getUserOrders(Integer userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public OrderDto convertToDto(Order order){
        return modelMapper.map(order,OrderDto.class);
    }
}
