package com.example.chatapp.security.oauth2;

import java.util.Map;

public class OAuth2UserGoogle extends OAuth2UserDetails{
    @Override
    public String getName() {
        return null;
    }

    public OAuth2UserGoogle(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getEmail() {
        return null;
    }
}
