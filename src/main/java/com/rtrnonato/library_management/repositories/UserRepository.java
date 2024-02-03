package com.rtrnonato.library_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rtrnonato.library_management.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
