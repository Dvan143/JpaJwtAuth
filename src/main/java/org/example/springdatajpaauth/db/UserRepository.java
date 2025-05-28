package org.example.springdatajpaauth.db;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserClass,Long>{
    boolean existsByUsername(String username);
    UserClass getUserByUsername(String username);
}
