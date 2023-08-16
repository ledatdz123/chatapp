package com.example.chatapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Followers {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="from_user_fk")
    @JsonManagedReference // Manage the serialization from this side
    private UserApp from;

    @ManyToOne
    @JoinColumn(name="to_user_fk")
    @JsonManagedReference // Manage the serialization from this side
    private UserApp to;

    public Followers() {};

    public Followers(UserApp from, UserApp to) {
        this.from = from;
        this.to = to;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserApp getFrom() {
        return from;
    }

    public void setFrom(UserApp from) {
        this.from = from;
    }

    public UserApp getTo() {
        return to;
    }

    public void setTo(UserApp to) {
        this.to = to;
    }
}

