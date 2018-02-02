package com.bibin.dictionary;

import android.app.Application;

import com.bibin.dictionary.di.DaggerMainComponent;
import com.bibin.dictionary.di.MainComponent;
import com.bibin.dictionary.di.module.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class MainApplication extends Application {

    private MainComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        // Setup memory leak detection
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        init();
    }

    private void init() {
        initDagger();
        /*  Timber always on */
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initDagger() {
        component = DaggerMainComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }


    public MainComponent getComponent() {
        return component;
    }
}
