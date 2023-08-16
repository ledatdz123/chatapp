package com.example.chatapp.services.post;

import com.example.chatapp.dto.CommentDTO;
import com.example.chatapp.dto.LikeDTO;
import com.example.chatapp.dto.PostDTO;
import com.example.chatapp.dto.UserDTO;
import com.example.chatapp.model.*;
import com.example.chatapp.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikePostRepository likePostRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeCommentRepository likeCommentRepository;
    @Override
    public Post createPost(Post post, Integer userId) {
        UserApp userApp= userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        post.setUser(userApp);
        post.setCreateAt(LocalDateTime.now());
        Post createPost=postRepository.save(post);
        return createPost;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public PostDTO findPostById(Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        PostDTO postDTO=this.convertPostLikeToDTO(post);
        return postDTO;
    }

    @Override
    public List<PostDTO> findPostByUserId(Integer userId) {
        UserApp userApp=userRepository.findById(userId).orElse(null);
        if (userApp==null){
            return Collections.emptyList();
        }
        List<Post> postList=postRepository.findByUser(userApp);
        List<PostDTO> postDTOS=new ArrayList<>();
        for (Post element: postList
        ) {
            PostDTO postDTO=convertPostLikeToDTO(element);
            postDTOS.add(postDTO);
        }
        return postDTOS;
    }
    @Override
    public List<PostDTO> findPostByUserFollow(List<Integer> userIds) {
        List<UserApp> userApps = userRepository.findAllById(userIds);
        List<Post> postList=postRepository.findByUserIn(userApps);
        List<PostDTO> postDTOS=new ArrayList<>();
        for (Post element: postList
        ) {
            PostDTO postDTO=convertPostLikeToDTO(element);
            postDTOS.add(postDTO);
        }
        return postDTOS;
    }
    public PostDTO convertPostLikeToDTO(Post post){
        PostDTO postDTO=new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setCaption(post.getCaption());
        postDTO.setImage(post.getImage());
        postDTO.setLocation(post.getLocation());
        postDTO.setCreateAt(post.getCreateAt());
        UserDTO userDTO=new UserDTO();
        userDTO.setUserId(post.getUser().getUserId());
        userDTO.setUsername(post.getUser().getUsername());
        postDTO.setUser(userDTO);

        List<Comment> comment=commentRepository.findByPost(post);
        List<CommentDTO> commentDTOS=new ArrayList<>();
        if (comment==null){
            commentDTOS.isEmpty();
        }else {
            for (Comment el:comment
            ) {
                CommentDTO commentDTO=this.mapCommentToDTO(el);
                commentDTOS.add(commentDTO);
            }
        }
        postDTO.setComment(commentDTOS);
        List<LikePost> likes = likePostRepository.findByPost(post);
        List<LikeDTO> likeDTOs = new ArrayList<>();
        if (likes==null){
            likeDTOs.isEmpty();
        }
        else {
            for (LikePost like : likes) {
                LikeDTO likeDTO = convertLikeToDTO(like);
                likeDTOs.add(likeDTO);
            }
        }
        postDTO.setLikeByUsers(likeDTOs);
        return postDTO;
    }

    private LikeDTO convertLikeToDTO(LikePost like) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(like.getId());
        likeDTO.setUser(convertUserToDTO(like.getUser()));
        likeDTO.setCreateAt(like.getCreateAt());
        return likeDTO;
    }
    private UserDTO convertUserToDTO(UserApp user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        // Set other user-related fields if needed
        return userDTO;
    }

    public CommentDTO mapCommentToDTO(Comment comment){
        CommentDTO commentDTO=new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setComment(comment.getComment());
        commentDTO.setCreateAt(comment.getCreateAt());
        commentDTO.setUser(this.mapUserToDTO(comment.getUser()));
        List<LikeDTO> likeDTOS=new ArrayList<>();
        List<LikeComment> listComments=likeCommentRepository.findByComment(comment);
        if (listComments==null){
            likeDTOS.isEmpty();
        }else {
            for (LikeComment likeComment: listComments
            ) {
                LikeDTO like=this.mapLikeToDTO(likeComment);
                likeDTOS.add(like);
            }
        }
        commentDTO.setLikeByUsers(likeDTOS);
        return commentDTO;
    }

    private LikeDTO mapLikeToDTO(LikeComment like) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(like.getId());
        likeDTO.setUser(mapUserToDTO(like.getUser()));
        likeDTO.setCreateAt(like.getCreateAt());
        return likeDTO;
    }
    public UserDTO mapUserToDTO(UserApp user){
        UserDTO userDTO=new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

    @Override
    public void deletePost(Integer userId, Integer postId) {
        UserApp user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found with the given ID.");
        }

        // Check if the post belongs to the specified user
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null || !post.getUser().equals(user)) {
            throw new IllegalArgumentException("User not found with the given ID.");
        }

        postRepository.delete(post);
    }
}
