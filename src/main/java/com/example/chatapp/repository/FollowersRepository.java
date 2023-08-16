package com.example.chatapp.repository;

import com.example.chatapp.model.Followers;
import com.example.chatapp.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowersRepository extends JpaRepository<Followers, Integer> {
    // Custom query methods for Followers entity (if needed)
    List<Followers> findByTo(UserApp to);
    List<Followers> findByFrom(UserApp from);
    Followers findByFromAndTo(UserApp from, UserApp to);
}
