package com.bibin.dictionary.di.module;

import android.app.Application;
import android.content.Context;

import com.bibin.dictionary.BuildConfig;
import com.bibin.dictionary.data.DataHelper;
import com.bibin.dictionary.data.DataManager;
import com.bibin.dictionary.data.RequestInterceptor;
import com.bibin.dictionary.data.ServiceApi;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final int CACHE_SIZE_10_MB = 10 * 1024 * 1024;
    public static final String BASE_URL = "BASE_URL";
    public static final String API_KEY = "API_KEY";

    @Provides
    @Singleton
    Cache providesOkHttpCache(Application application) {
        int cacheSize = CACHE_SIZE_10_MB;
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    Gson providesGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    RequestInterceptor providesRequestInterceptor(@Named(API_KEY) String key) {
        return new RequestInterceptor(key);
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor, RequestInterceptor requestInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(requestInterceptor).build();
//                .cache(cache).build();
    }

    @Provides
    @Singleton
    GsonConverterFactory providesGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory providesRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(GsonConverterFactory gsonConverterFactory, OkHttpClient okHttpClient,
                              RxJava2CallAdapterFactory rxJavaCallAdapterFactory,
                              @Named(BASE_URL) String baseUrl) {
        return new Retrofit.Builder().addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }


    @Singleton
    @Provides
    DataHelper provideRepository(ServiceApi retrofitService) {
        return new DataManager(retrofitService);
       // return new DataManagerTest(context);
    }


    @Singleton
    @Provides
    ServiceApi providesRetrofitService(Retrofit retrofit) {
        return retrofit.create(ServiceApi.class);
    }

    @Provides
    @Singleton
    @Named(BASE_URL)
    String providesServerUrl(Context context) {
        return BuildConfig.BASE_URL;
    }

    @Provides
    @Singleton
    @Named(API_KEY)
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

}
