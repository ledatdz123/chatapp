package com.example.chatapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String chat_name;
    private String chat_image;

    @ManyToMany
    private Set<UserApp> admins=new HashSet<>();

    @Column(name = "is_group")
    private boolean isGroup;

    @JoinColumn(name = "created_by")
    @ManyToOne
    private UserApp createBy;

    @ManyToMany
    private Set<UserApp> users=new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChat_name() {
        return chat_name;
    }

    public void setChat_name(String chat_name) {
        this.chat_name = chat_name;
    }

    public String getChat_image() {
        return chat_image;
    }

    public void setChat_image(String chat_image) {
        this.chat_image = chat_image;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public UserApp getCreateBy() {
        return createBy;
    }

    public void setCreateBy(UserApp createBy) {
        this.createBy = createBy;
    }

    public Set<UserApp> getUsers() {
        return users;
    }

    public void setUsers(Set<UserApp> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Chat(Integer id, String chat_name, String chat_image, Set<UserApp> admins, boolean isGroup, UserApp createBy, Set<UserApp> users, List<Message> messages) {
        this.id = id;
        this.chat_name = chat_name;
        this.chat_image = chat_image;
        this.admins = admins;
        this.isGroup = isGroup;
        this.createBy = createBy;
        this.users = users;
        this.messages = messages;
    }

    public Set<UserApp> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<UserApp> admins) {
        this.admins = admins;
    }

    @OneToMany
    private List<Message> messages=new ArrayList<>();

    public Chat() {
    }
}
