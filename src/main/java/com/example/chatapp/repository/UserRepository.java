package com.example.chatapp.repository;

import com.example.chatapp.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApp, Integer> {
    Optional<UserApp> findByUsername(String email);
    //@Query("select u from UserApp u where u.fullName like %:query% or u.email like %:query%")
    @Query("select u from UserApp u where u.username like %:query%")
    public List<UserApp> searchUser(@Param("query") String query);
}
