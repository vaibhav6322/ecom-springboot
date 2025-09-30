package com.ecom.app.repositories;

import com.ecom.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean getUsersById(Long id);
}
//type of entity and type of primary Key
