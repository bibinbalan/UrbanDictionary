package com.bibin.dictionary.data;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Request interceptor to add headers
 */

public class RequestInterceptor implements Interceptor {

    @Inject
    String apiKey;

    public RequestInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("X-Mashape-Key", apiKey)
                .header("Accept", "text/plain")
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }
}
