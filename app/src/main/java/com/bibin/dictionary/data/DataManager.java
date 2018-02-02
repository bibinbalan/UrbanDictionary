package com.bibin.dictionary.data;

/**
 * The repository that fetches the data
 */


import com.bibin.dictionary.data.model.WordResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * repository implementation which does the actual data fetching
 */
public class DataManager implements DataHelper {

    ServiceApi api;

    @Inject
    public DataManager(ServiceApi api) {
        this.api = api;
    }

    @Override
    public Single<WordResponse> getWordMeaning(String word) {
        return api.getWordDefinition(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}