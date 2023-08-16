package com.example.chatapp.services;

import com.example.chatapp.dto.UserDTO;
import com.example.chatapp.dto.request.UpdateUserRequest;
import com.example.chatapp.exception.UserException;
import com.example.chatapp.model.Followers;
import com.example.chatapp.model.Post;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.repository.FollowersRepository;
import com.example.chatapp.repository.PostRepository;
import com.example.chatapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private FollowersRepository followersRepository;
    @Autowired
    private PostRepository postRepository;
    public UserApp getProfile(Authentication authentication){
        UserApp userApp=userRepo.findByEmail(authentication.getName());
        if (userApp!=null){
            return userApp;
        }
        throw new UsernameNotFoundException("not found");
    }
    @Override
    public UserApp finUserById(Integer id) throws UserException {
        Optional<UserApp> opt=userRepo.findById(id);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new UserException("User not found with id"+id);
    }

    @Override
    public UserApp finUserProfile(String jwt) throws UserException {
        return null;
    }

    @Override
    public UserApp updateUser(Integer id, UpdateUserRequest red) throws UserException {
        return null;
    }

    @Override
    public List<UserApp> searchUser(String query) {
        List<UserApp> userApps=userRepo.searchUser(query);
        return userApps;
    }

    @Override
    public UserApp updateUser(UserApp updateUser, UserApp existsUser) throws UserException {
        if (updateUser.getSavedPosts()!=null){
            existsUser.setSavedPosts(updateUser.getSavedPosts());
        }
        if(updateUser.getUsername()!=null){
            existsUser.setUsername(updateUser.getUsername());
        }
        if (updateUser.getUserImage()!=null){
            existsUser.setUserImage(updateUser.getUserImage());
        }
        if(updateUser.getUserId().equals(existsUser.getUserId())){
            return userRepo.save(existsUser);
        }
        throw new UserException("you cant update this user");
    }

    @Override
    public List<UserApp> findUserByIds(List<Integer> userIds) throws UserException {
        return null;
    }

    @Override
    public List<UserApp> searchUserByQuery(String query) {
        return null;
    }

    @Override
    public void savedPost(Integer userId, Integer postId) {
        UserApp currentUser = userRepo.findById(userId).orElse(null);
        Post postToSave = postRepository.findById(postId).orElse(null);

        if (currentUser == null || postToSave == null) {
            return;
        }
        if (currentUser.getSavedPosts().contains(postToSave)) {
            return;
        }
        currentUser.getSavedPosts().add(postToSave);
        userRepo.save(currentUser);
    }

    @Override
    public void deleteSavedPost(Integer userId, Integer postId) {
        UserApp currentUser = userRepo.findById(userId).orElse(null);

        if (currentUser == null) {
            // Handle error: User not found with the given ID.
            return;
        }

        // Check if the post is saved by the user.
        Post postToDelete = null;
        for (Post savedPost : currentUser.getSavedPosts()) {
            if (savedPost.getId().equals(postId)) {
                postToDelete = savedPost;
                break;
            }
        }

        if (postToDelete == null) {
            // Handle error: Post not found in the user's saved posts.
            return;
        }

        currentUser.getSavedPosts().remove(postToDelete);
        userRepo.save(currentUser);
    }


    public UserApp getUserById(Integer userId) {
        UserApp user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            List<Followers> followers = followersRepository.findByTo(user);
            List<Followers> following = followersRepository.findByFrom(user);
            user.setFollowers(followers);
            user.setFollowing(following);
        }
        return user;
    }
    public UserDTO getUserDTOById(Integer userId) {
        UserApp user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUsername(user.getUsername());

            // Convert followers and following entities to UserDTO recursively
            userDTO.setFollowers(convertToUserDTOList(user.getFollowers()));
            userDTO.setFollowing(convertToUserDTOList(user.getFollowing()));
            return userDTO;
        }
        return null;
    }
    private List<UserDTO> convertToUserDTOList(List<Followers> followers) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (Followers follower : followers) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(follower.getFrom().getUserId());
            userDTO.setUsername(follower.getFrom().getUsername());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
}
