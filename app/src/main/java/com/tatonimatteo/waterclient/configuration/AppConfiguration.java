package com.tatonimatteo.waterclient.configuration;

import com.tatonimatteo.waterclient.network.HttpManager;

public class AppConfiguration {

    private static AppConfiguration instance;
    private final HttpManager httpManager;
    private String token;

    private AppConfiguration() {
        this.httpManager = new HttpManager();
    }

    public static AppConfiguration getInstance() {
        if (instance == null) instance = new AppConfiguration();
        return instance;
    }

    public HttpManager getHttpManager() {
        return httpManager;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuthenticated() {
        return !(token == null || token.isBlank());
    }
}
