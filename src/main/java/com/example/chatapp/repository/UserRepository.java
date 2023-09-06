package com.example.chatapp.repository;

import com.example.chatapp.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApp, Integer> {
    UserApp findByUsername(String email);
    //@Query("select u from UserApp u where u.fullName like %:query% or u.email like %:query%")
    @Query("select u from UserApp u where u.username like %:query%")
    public List<UserApp> searchUser(@Param("query") String query);


    @Query(value = "select u.user_id, u.username, u.user_image, COUNT(f.to_user_fk) AS follow_count from users u left join followers f \n" +
            "ON u.user_id = f.to_user_fk\n" +
            "GROUP BY u.user_id, u.username\n" +
            "ORDER by follow_count DESC\n" +
            "LIMIT 6;", nativeQuery = true)
    public List<Object[]> getTopFiveUser();
}
