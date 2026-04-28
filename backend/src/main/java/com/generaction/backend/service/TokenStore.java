package com.generaction.backend.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStore {

    private final Map<String, String> tokens = new ConcurrentHashMap<>();

    public void store(String token, String rol) {
        tokens.put(token, rol);
    }

    public String getRol(String token) {
        return tokens.get(token);
    }

    public boolean isAdmin(String token) {
        return "ADMIN".equals(tokens.get(token));
    }

    public void remove(String token) {
        tokens.remove(token);
    }
}
