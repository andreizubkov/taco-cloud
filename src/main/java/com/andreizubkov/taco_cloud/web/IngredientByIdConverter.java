package com.andreizubkov.taco_cloud.web;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import com.andreizubkov.taco_cloud.data.IngredientRepository;
import com.andreizubkov.taco_cloud.tacos.Ingredient;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    
    private IngredientRepository ingredientRepo;

    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findById(id).orElse(null);
    }
}