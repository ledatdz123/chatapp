package com.example.chatapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

//@Entity
//@Table(name="roles")
//public class Role implements GrantedAuthority {
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    @Column(name="role_id")
//    private Integer roleId;
//
//    private String authority;
//
//    public Role(){
//        super();
//    }
//
//    public Role(String authority){
//        this.authority = authority;
//    }
//
//    public Role(Integer roleId, String authority){
//        this.roleId = roleId;
//        this.authority = authority;
//    }
//
//    @Override
//    public String getAuthority() {
//        // TODO Auto-generated method stub
//        return this.authority;
//    }
//
//    public void setAuthority(String authority){
//        this.authority = authority;
//    }
//
//    public Integer getRoleId(){
//        return this.roleId;
//    }
//
//    public void setRoleId(Integer roleId){
//        this.roleId = roleId;
//    }
//}
@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    private String name;
}
