package com.andreizubkov.taco_cloud.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import com.andreizubkov.taco_cloud.tacos.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {

    Page<Taco> findAll(Pageable page);
}