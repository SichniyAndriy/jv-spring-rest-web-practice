package mate.academy.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.mapper.DtoResponseMapper;
import mate.academy.spring.model.Order;
import mate.academy.spring.model.ShoppingCart;
import mate.academy.spring.model.User;
import mate.academy.spring.model.dto.response.OrderResponseDto;
import mate.academy.spring.service.OrderService;
import mate.academy.spring.service.ShoppingCartService;
import mate.academy.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final DtoResponseMapper<OrderResponseDto, Order> dtoOrderResponseMapper;

    @Autowired
    public OrderController(OrderService orderService,
                           ShoppingCartService shoppingCartService,
                           UserService userService,
                           DtoResponseMapper<OrderResponseDto, Order> dtoOrderResponseMapper) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.dtoOrderResponseMapper = dtoOrderResponseMapper;
    }

    @PostMapping("/complete?{userId}")
    public OrderResponseDto complete(@PathVariable Long userId) {
        ShoppingCart shoppingCart = shoppingCartService.getByUser(userService.get(userId));
        return dtoOrderResponseMapper.toDto(orderService.completeOrder(shoppingCart));
    }

    @GetMapping("?{userId")
    public List<OrderResponseDto> getByUserId(@PathVariable Long userId) {
        User user = userService.get(userId);
        return orderService.getOrdersHistory(user)
                .stream()
                .map(dtoOrderResponseMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}