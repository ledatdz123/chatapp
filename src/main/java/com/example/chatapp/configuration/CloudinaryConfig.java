package com.example.chatapp.configuration;

import com.cloudinary.Cloudinary;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    public static String cloudName="dwud38cxf";
    public static String  apiKey="755629723188281";
    public static String apiSecret="zT3QBVoBNRGfphsJ5ZnuuWU63tE";

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("cloud_name", cloudName);
        valueMap.put("api_key", apiKey);
        valueMap.put("api_secret", apiSecret);
        return new Cloudinary(valueMap);
    }
}
