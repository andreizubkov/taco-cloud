package com.andreizubkov.taco_cloud.data;

import org.springframework.data.repository.CrudRepository;
import com.andreizubkov.taco_cloud.tacos.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}