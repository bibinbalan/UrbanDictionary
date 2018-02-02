package com.bibin.dictionary.ui.search;


import android.os.Bundle;

import com.bibin.dictionary.data.model.WordDefinition;
import com.bibin.dictionary.ui.base.MvpPresenter;
import com.bibin.dictionary.ui.base.MvpView;

import java.util.ArrayList;

public interface SearchMvpContract {

    interface View extends MvpView {
        void showDefinitions(ArrayList<WordDefinition> definitions);
        void showEmptyView();
    }

    interface Presenter extends MvpPresenter {
        void fetchWordMeaning(String word);
        void sort(int sortBy);
        void onSaveInstanceState(Bundle b);
        void onRestoreInstanceState(Bundle b);
    }
}
