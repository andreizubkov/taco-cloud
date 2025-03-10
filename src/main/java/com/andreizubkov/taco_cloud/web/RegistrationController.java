package com.andreizubkov.taco_cloud.web;

import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.andreizubkov.taco_cloud.data.UserRepository;
import com.andreizubkov.taco_cloud.security.RegistrationForm;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    
    private userPasswordValidator userPassValidator;
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder, userPasswordValidator userPasswordValidator) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userPassValidator = userPasswordValidator;
    }

    @ModelAttribute(name = "registrationForm")
    public RegistrationForm registrationForm() {
        return new RegistrationForm();
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistraton(@Valid RegistrationForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(userPassValidator);
    }
}
