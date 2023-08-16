package com.example.chatapp;

import com.example.chatapp.model.Role;
import com.example.chatapp.model.UserApp;
import com.example.chatapp.repository.RoleRepository;
import com.example.chatapp.repository.UserRepository;
import lombok.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ChatappApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChatappApplication.class, args);
	}
//	@Bean
//	public WebMvcConfigurer corsConfigurer(){
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
//			}
//		};
//	}
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args -> {
			Role adminRole = roleRepository.save(new Role(1l,"ADMIN"));
			Role userRole=roleRepository.save(new Role(2l,"USER"));
		};
		//java -jar chatapp-0.0.1-SNAPSHOT.jar
	}
}
