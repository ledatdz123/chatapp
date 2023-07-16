package com.example.chatapp.repository;

import com.example.chatapp.model.MessageReact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageReactRepository extends JpaRepository<MessageReact, Integer> {
    List<MessageReact> findByMessage_Id(Integer id);
}
