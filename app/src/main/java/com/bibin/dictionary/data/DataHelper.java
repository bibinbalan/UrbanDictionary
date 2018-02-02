package com.bibin.dictionary.data;

import com.bibin.dictionary.data.model.WordResponse;

import io.reactivex.Single;

/**
 * Interface to repository
 */
public interface DataHelper {

    Single<WordResponse> getWordMeaning(String word);
}
