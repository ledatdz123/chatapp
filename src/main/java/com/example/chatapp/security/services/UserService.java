package com.example.chatapp.security.services;

import com.example.chatapp.model.UserApp;
import com.example.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In the user details service");
        System.out.println(userRepository.findByUsername(username));
        UserApp userApp= userRepository.findByUsername(username);
        if (userApp==null){
            throw new UsernameNotFoundException("User not found");
        }
        else return userApp;
    }
}
