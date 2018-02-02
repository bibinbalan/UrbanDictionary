package com.bibin.dictionary.data;

import android.content.Context;

import com.bibin.dictionary.R;
import com.bibin.dictionary.data.model.WordResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.inject.Singleton;


/**
 * Test to class to mock service response
 */

@Singleton
public class DataManagerTest {

    Context context;
/*

    public DataManagerTest(Context context) {
        this.context = context;
    }

    @Override
    public Singleton<WordResponse> getWordMeaning(String word) {
        return Single.just(getTestData());
    }
*/

    private WordResponse getTestData() {
        Gson gson = new Gson();
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.sample_response)));
        WordResponse respose = gson.fromJson(reader, WordResponse.class);
        return respose;
    }
}

