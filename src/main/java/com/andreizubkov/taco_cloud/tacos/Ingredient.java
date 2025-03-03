package com.andreizubkov.taco_cloud.tacos;

import lombok.Data;

@Data
public class Ingredient {
    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

    private final String id;
    private final String name;
    private final Type type;
}
