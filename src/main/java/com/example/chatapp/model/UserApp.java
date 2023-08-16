package com.example.chatapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Entity
//@Table(name="users")
//public class UserApp implements UserDetails{
//
//    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
//    private Integer userId;
//    @Column(unique=true)
//    private String username;
//    private String password;
//
//    @ManyToMany(fetch=FetchType.EAGER)
//    @JoinTable(
//            name="user_role_junction",
//            joinColumns = {@JoinColumn(name="user_id")},
//            inverseJoinColumns = {@JoinColumn(name="role_id")}
//    )
//    private Set<Role> authorities;
//
//    public UserApp() {
//        super();
//        authorities = new HashSet<>();
//    }
//
//
//    public UserApp(Integer userId, String username, String password, Set<Role> authorities) {
//        super();
//        this.userId = userId;
//        this.username = username;
//        this.password = password;
//        this.authorities = authorities;
//    }
//
//    public Integer getUserId() {
//        return this.userId;
//    }
//
//    public void setId(Integer userId) {
//        this.userId = userId;
//    }
//
//    public void setAuthorities(Set<Role> authorities) {
//        this.authorities = authorities;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // TODO Auto-generated method stub
//        return this.authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        // TODO Auto-generated method stub
//        return this.password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @Override
//    public String getUsername() {
//        // TODO Auto-generated method stub
//        return this.username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    /* If you want account locking capabilities create variables and ways to set them for the methods below */
//    @Override
//    public boolean isAccountNonExpired() {
//        // TODO Auto-generated method stub
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        // TODO Auto-generated method stub
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        // TODO Auto-generated method stub
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        // TODO Auto-generated method stub
//        return true;
//    }
//
//}
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserApp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
    private String userImage;
    private String bio;
    private String phone;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy="to")
    @JsonIgnore // Ignore the followers list when serializing to JSON
    private List<Followers> followers;

    @OneToMany(mappedBy="from")
    @JsonIgnore // Ignore the followers list when serializing to JSON
    private List<Followers> following;

    @ManyToMany
    @JoinTable(name = "user_saved_posts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private Set<Post> savedPosts = new HashSet<>();
}

