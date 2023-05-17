package com.tatonimatteo.waterclient.network.security;

import androidx.annotation.NonNull;

import com.tatonimatteo.waterclient.configuration.AppConfiguration;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String token = AppConfiguration.getInstance().getToken();
        Request.Builder builder = chain.request().newBuilder();
        if (token != null) builder.addHeader("Authorization", "Bearer " + token);
        return chain.proceed(builder.build());
    }
}
