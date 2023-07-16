package com.example.chatapp.services;

import com.example.chatapp.dto.request.GroupChatRequest;
import com.example.chatapp.exception.ChatException;
import com.example.chatapp.exception.UserException;
import com.example.chatapp.model.Chat;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService{
    @Autowired
    private ChatRepository chatRepo;
    @Autowired
    private UserService userService;
    @Override
    public Chat createChat(UserApp redUser, Integer useId2) throws UserException {
        UserApp userApp=userService.finUserById(useId2);
        Chat isChatExits=chatRepo.findSingleChatByUserIds(userApp, redUser);
        if (isChatExits!=null){
            return isChatExits;
        }
        Chat chat=new Chat();
        chat.setCreateBy(redUser);
        chat.getUsers().add(userApp);
        chat.getUsers().add(redUser);
        chat.setGroup(false);
        chatRepo.save(chat);
        return chat;
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat=chatRepo.findById(chatId);
        if (chat.isPresent()){
            return chat.get();
        }
        throw new ChatException("Chat not found with id"+chatId);
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
        UserApp userApp=userService.finUserById(userId);
        List<Chat> chats=chatRepo.findChatByUserId(userApp.getUserId());
        return chats;
    }

    @Override
    public Chat createGroup(GroupChatRequest red, UserApp redUser) throws UserException {
        Chat group=new Chat();
        group.setGroup(true);
        group.setChat_image(red.getChat_image());
        group.setChat_name(red.getChat_name());
        group.setCreateBy(redUser);
        group.getUsers().add(redUser);
        group.getAdmins().add(redUser);
        for (Integer userId:red.getUserIds()
        ) {
            UserApp user = userService.finUserById(userId);
            group.getUsers().add(user);
        }
        Chat saveGroup=this.chatRepo.save(group);
        return saveGroup;
    }

    @Override
    public Chat addUserToGroup(Integer uId, Integer chatId, UserApp redUser) throws UserException, ChatException {
        Optional<Chat> opt=chatRepo.findById(chatId);
        UserApp user=userService.finUserById(uId);
        if (opt.isPresent()){
            Chat chat=opt.get();
            if (chat.getAdmins().contains(redUser)){
                chat.getUsers().add(user);
                return chatRepo.save(chat);
            }
            else{
                throw new UserException("You are not admin");
            }
        }
        throw new ChatException("chat not found with id"+chatId);
    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, UserApp redUser) throws ChatException, UserException {
        Optional<Chat> opt=chatRepo.findById(chatId);
        if (opt.isPresent()){
            Chat chat=opt.get();
            if ((chat.getUsers().contains(redUser))){
                chat.setChat_name(groupName);
                return chatRepo.save(chat);
            }
            throw new UserException("You are not member of this group");
        }
        throw new ChatException("chat not found with id"+chatId);
    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, UserApp redUser) throws ChatException, UserException {
        Optional<Chat> opt=chatRepo.findById(chatId);
        UserApp user=userService.finUserById(userId);
        if (opt.isPresent()){
            Chat chat=opt.get();
            if (chat.getAdmins().contains(redUser)){
                chat.getUsers().remove(user);
                return chatRepo.save(chat);
            }
            else if(chat.getUsers().contains(redUser)){
                if (user.getUserId().equals(redUser.getUserId())){
                    chat.getUsers().remove(user);
                    return chatRepo.save(chat);
                }
            }
            throw new UserException("You can't remove another user");
        }
        throw new ChatException("chat not found with id"+chatId);
    }

    @Override
    public void deleteChat(Integer chatId, Integer uId) throws UserException, ChatException {
        Optional<Chat> opt=chatRepo.findById(chatId);
        if (opt.isPresent()){
            Chat chat=opt.get();
            chatRepo.deleteById(chat.getId());
        }
    }
}
