package com.andreizubkov.taco_cloud.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.andreizubkov.taco_cloud.security.RegistrationForm;

@Component
public class userPasswordValidator implements Validator {
    private final SpringValidatorAdapter springValidatorAdapter;

    public userPasswordValidator(SpringValidatorAdapter springValidatorAdapter) {
        super();
        this.springValidatorAdapter = springValidatorAdapter;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        springValidatorAdapter.validate(obj, errors);
        RegistrationForm form = (RegistrationForm) obj;
        if (!form.getPassword().equals(form.getConfirm())) {
            errors.rejectValue("confirm", "user.differentPassword", "Confirm password must match the password");
        }
    }
}
