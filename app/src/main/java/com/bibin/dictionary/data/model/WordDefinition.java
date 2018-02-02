package com.bibin.dictionary.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class for word definition
 */

public class WordDefinition implements Serializable {

    @SerializedName("definition")
    private String definition;

    @SerializedName("thumbs_up")
    private int thumbsUp;

    @SerializedName("thumbs_down")
    private int thumbsDown;

    @SerializedName("example")
    private String example;

    public String getDefinition() {
        return definition;
    }

    public int getThumbsUp() {
        return thumbsUp;
    }

    public int getThumbsDown() {
        return thumbsDown;
    }

    public String getExample() {
        return example;
    }
}
