package com.example.chatapp.services.comment;

import com.example.chatapp.dto.CommentDTO;
import com.example.chatapp.dto.LikeDTO;
import com.example.chatapp.dto.UserDTO;
import com.example.chatapp.model.Comment;
import com.example.chatapp.model.LikeComment;
import com.example.chatapp.model.Post;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.repository.CommentRepository;
import com.example.chatapp.repository.LikeCommentRepository;
import com.example.chatapp.repository.PostRepository;
import com.example.chatapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeCommentRepository likeCommentRepository;
    @Override
    public CommentDTO createComment(Comment comment, Integer postId, Integer userId) {
        UserApp userApp=userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Post post=postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        comment.setPost(post);
        comment.setUser(userApp);
        comment.setComment(comment.getComment());
        comment.setCreateAt(LocalDateTime.now());
        Comment saveComment=commentRepository.save(comment);
        CommentDTO commentDTO=new CommentDTO();
        commentDTO=this.mapCommentDTO(saveComment);
        return commentDTO;
    }
    @Override
    public void deleteComment(Integer comId, Integer postId) {

    }

    @Override
    public CommentDTO findCommentById(Integer comId) {
        Comment comment=commentRepository.findById(comId)
                .orElseThrow(() -> new EntityNotFoundException("Comment Id not found"));
        CommentDTO commentDTO=new CommentDTO();
        commentDTO=this.mapCommentDTO(comment);
        return commentDTO;
    }

    @Override
    public List<CommentDTO> findCommentByPost(Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Id not found"));
        List<Comment> list=commentRepository.findByPost(post);
        List<CommentDTO> commentDTOS=new ArrayList<>();
        for (Comment comment:list
        ) {
            CommentDTO commentDTO=this.mapCommentDTO(comment);
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }

    public CommentDTO mapCommentDTO(Comment comment){
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
}
