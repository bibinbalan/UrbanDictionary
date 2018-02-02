package com.bibin.dictionary.di;


import com.bibin.dictionary.di.module.UiModule;
import com.bibin.dictionary.di.scope.PerActivity;
import com.bibin.dictionary.ui.search.SearchFragment;

import dagger.Subcomponent;


@PerActivity
@Subcomponent(modules = {UiModule.class})
public interface UiComponent {
    void inject(SearchFragment frgment);
}
