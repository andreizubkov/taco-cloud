package com.andreizubkov.taco_cloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        return "home";
    }

    @PostMapping
    public String processDesign() {
        log.info("Processing design");
        return "redirect:/design";
    }

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HomeController.class);
}