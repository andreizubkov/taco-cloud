package com.andreizubkov.taco_cloud.data;

import com.andreizubkov.taco_cloud.tacos.Ingredient;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Optional;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("SELECT id, name, type FROM Ingredient", this::mapRowToIngredient);
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        List<Ingredient> result = jdbcTemplate.query(
            "SELECT id, name, type FROM Ingredient WHERE id=?",
            this::mapRowToIngredient,
            id
        );
        return Optional.ofNullable(result.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update(
            "INSERT INTO Ingredient (id, name, type) values (?, ?, ?)",
            ingredient.getId(),
            ingredient.getName(),
            ingredient.getType().toString()
        );
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet row, int rolNum) throws SQLException {
        return new Ingredient(
            row.getString("id"), 
            row.getString("name"), 
            Ingredient.Type.valueOf(row.getString("type"))
        );
    }
}