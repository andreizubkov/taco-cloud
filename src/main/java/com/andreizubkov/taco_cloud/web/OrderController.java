package com.andreizubkov.taco_cloud.web;

import jakarta.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.andreizubkov.taco_cloud.tacos.TacoOrder;
import com.andreizubkov.taco_cloud.data.OrderRepository;
import com.andreizubkov.taco_cloud.security.Users;

@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }
    
    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal Users user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUsers(user);
        orderRepo.save(order);
        sessionStatus.setComplete();
        log.info("Order submitted: {}", order);
        
        return "redirect:/";
    }

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OrderController.class);
}
