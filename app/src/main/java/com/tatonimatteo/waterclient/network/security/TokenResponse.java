package com.tatonimatteo.waterclient.network.security;

public class TokenResponse {
    private String token;

    public String getToken() {
        return token != null ? token : "";
    }

    public void setToken(String token) {
        this.token = token;
    }
}
