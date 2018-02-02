package com.bibin.dictionary.data;


import com.bibin.dictionary.data.model.WordResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Service API interface
 */


public interface ServiceApi {

    @GET("/define")
    Single<WordResponse> getWordDefinition(@Query("term") String word);

}
