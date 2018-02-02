package com.bibin.dictionary.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Model class for word definition response
 */

public class WordResponse {
    @SerializedName("list")
    private ArrayList<WordDefinition> definitionsList;

    public ArrayList<WordDefinition> getDefinitionsList() {
        return definitionsList;
    }

}
