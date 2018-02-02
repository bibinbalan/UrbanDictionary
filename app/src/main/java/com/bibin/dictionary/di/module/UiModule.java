package com.bibin.dictionary.di.module;

import com.bibin.dictionary.data.DataHelper;
import com.bibin.dictionary.di.scope.PerActivity;
import com.bibin.dictionary.ui.search.SearchMvpContract;
import com.bibin.dictionary.ui.search.SearchMvpPresenter;

import dagger.Module;
import dagger.Provides;


@Module
public class UiModule {

    @PerActivity
    @Provides
    SearchMvpContract.Presenter provideSearchPresenter(DataHelper dataHelper) {
        return new SearchMvpPresenter(dataHelper);
    }

}
