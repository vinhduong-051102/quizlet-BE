package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("from User u where u.userName=:name and u.password=:password")
    User findByUserNameAndPassword(@Param("name") String name, @Param("password") String password);
}
