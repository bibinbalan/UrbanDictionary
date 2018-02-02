package com.bibin.dictionary.di;


import com.bibin.dictionary.di.module.ApplicationModule;
import com.bibin.dictionary.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class, NetworkModule.class})
public interface MainComponent {
    UiComponent uiComponent();
}
