package com.andreizubkov.taco_cloud.data;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.andreizubkov.taco_cloud.tacos.TacoOrder;
import com.andreizubkov.taco_cloud.tacos.Ingredient;
import com.andreizubkov.taco_cloud.tacos.Taco;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder tacoOrder) {

        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
            "INSERT INTO Taco_Order (delivery_Name, delivery_Street, delivery_City, delivery_State, delivery_Zip, cc_number, "
            + "cc_expiration, cc_cvv, placed_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
        );
        pscf.setReturnGeneratedKeys(true);

        tacoOrder.setPlacedAt(new Date());
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
            tacoOrder.getDeliveryName(),
            tacoOrder.getDeliveryStreet(),
            tacoOrder.getDeliveryCity(),
            tacoOrder.getDeliveryState(),
            tacoOrder.getDeliveryZip(),
            tacoOrder.getCcNumber(),
            tacoOrder.getCcExpiration(),
            tacoOrder.getCcCVV(),
            tacoOrder.getPlacedAt()
        ));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long tacoOrderId = keyHolder.getKey().longValue();
        tacoOrder.setId(tacoOrderId);

        List<Taco> tacos = tacoOrder.getTacos();
        int k = 0;
        for (Taco taco : tacos) {
            saveTaco(tacoOrderId, k++, taco);
        }
        return tacoOrder;
    }

    private long saveTaco(Long orderId, int orderKey, Taco taco) {

        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
            "INSERT INTO Taco (name, taco_order, taco_order_key, created_at) VALUES (?, ?, ?, ?)",
            Types.VARCHAR, Types.BIGINT, Types.BIGINT, Types.TIMESTAMP
        );
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
            taco.getName(),
            orderId,
            orderKey,
            taco.getCreatedAt()
        ));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);
        saveIngredientRefs(tacoId, taco.getIngredients());

        return tacoId;
    }

    private void saveIngredientRefs(long tacoId, List<Ingredient> ingredients) {
        int key = 0;
        for (Ingredient ingredient : ingredients) {
            jdbcOperations.update(
                "INSERT INTO Ingredient_Ref (ingredient, taco, taco_key) VALUES (?, ?, ?)",
                ingredient.getId(), tacoId, key++
            );
        }
    }
}