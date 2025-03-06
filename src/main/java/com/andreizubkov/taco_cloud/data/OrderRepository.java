package com.andreizubkov.taco_cloud.data;

import com.andreizubkov.taco_cloud.tacos.TacoOrder;

public interface OrderRepository {
    
    TacoOrder save(TacoOrder tacoOrder);
}