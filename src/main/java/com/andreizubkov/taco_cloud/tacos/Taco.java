package com.andreizubkov.taco_cloud.tacos;

import lombok.Data;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
public class Taco {

    private long id;

    private Date createdAt = new Date();

    @NotBlank(message="The field must not be empty")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotEmpty(message="You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;
}
