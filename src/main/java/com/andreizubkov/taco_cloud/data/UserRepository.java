package com.andreizubkov.taco_cloud.data;

import org.springframework.data.repository.CrudRepository;
import com.andreizubkov.taco_cloud.security.Users;

public interface UserRepository extends CrudRepository<Users, Long> {

    Users findByUsername(String username);
}
