package com.andreizubkov.taco_cloud.data;

import java.util.Optional;
import com.andreizubkov.taco_cloud.tacos.Ingredient;

public interface IngredientRepository {

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);

    Iterable<Ingredient> findAll();
}