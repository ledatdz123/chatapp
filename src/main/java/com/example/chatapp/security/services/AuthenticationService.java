package com.example.chatapp.security.services;

import com.example.chatapp.dto.UserDTO;
import com.example.chatapp.dto.UserResponseDTO;
import com.example.chatapp.model.Role;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.repository.RoleRepository;
import com.example.chatapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public UserApp registerUser(String name, String username, String password){

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        return userRepository.save(new UserApp(0, name, username, encodedPassword, authorities));
    }

//    public LoginResponseDTO loginUser(String username, String password){
//
//        try{
//            Authentication auth = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//
//            String token = tokenService.generateJwt(auth);
//
//            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);
//
//        } catch(AuthenticationException e){
//            return new LoginResponseDTO(null, "");
//        }
//    }

    public UserResponseDTO loginUser(String username, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);
            UserApp userApp=userRepository.findByUsername(username);
            UserDTO userDTO=UserDTO.mapUserToDTO(userApp);
            return new UserResponseDTO(userDTO, token);

        } catch(AuthenticationException e){
            return new UserResponseDTO(null, e.getMessage());
        }
    }
}

