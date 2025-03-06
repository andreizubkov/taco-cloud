package com.andreizubkov.taco_cloud.data;

import org.springframework.data.repository.CrudRepository;
import com.andreizubkov.taco_cloud.tacos.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
}