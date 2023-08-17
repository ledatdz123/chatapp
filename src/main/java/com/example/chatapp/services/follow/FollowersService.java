package com.example.chatapp.services.follow;

import com.example.chatapp.model.Followers;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.repository.FollowersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowersService {

    private final FollowersRepository followersRepository;

    @Autowired
    public FollowersService(FollowersRepository followersRepository) {
        this.followersRepository = followersRepository;
    }

    public Followers createFollower(UserApp from, UserApp to) {
        Followers follower = new Followers(from, to);
        return followersRepository.save(follower);
    }

    public List<Followers> getAllFollowers() {
        return followersRepository.findAll();
    }

    // Add more service methods as needed for followers-related operations
    public void unfollowUser(UserApp from, UserApp to) {
        // Find and delete the existing follower relationship
        Followers follower = followersRepository.findByFromAndTo(from, to);
        if (follower != null) {
            followersRepository.delete(follower);
        }
    }
}
