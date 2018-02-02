package com.bibin.dictionary.ui.search;


import android.os.Bundle;

import com.bibin.dictionary.R;
import com.bibin.dictionary.data.DataHelper;
import com.bibin.dictionary.data.model.WordDefinition;
import com.bibin.dictionary.data.model.WordResponse;
import com.bibin.dictionary.ui.base.MvpView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;


public class SearchMvpPresenter implements SearchMvpContract.Presenter {

    private static final String STATE_ITEMS = "state_items";
    private static final String STATE_SERVICE = "state_service";
    private static final String STATE_LAST_WORD = "state_last_word";

    private SearchMvpContract.View mView;
    private DataHelper mDataHelper;
    Disposable mDisposible;
    private ArrayList<WordDefinition> mDefinitionList;
    private boolean mServiceRunning;
    private String mLastWord;

    @Inject
    public SearchMvpPresenter(DataHelper dataHelper) {
        mDataHelper = dataHelper;
    }

    @Override
    public void fetchWordMeaning(String word) {
        if (mView.isNetworkConnected()) {
            mServiceRunning = true;
            onServiceResult(mDataHelper.getWordMeaning(word));
            mView.showLoading();
            mLastWord = word;
        } else {
            mView.onError(R.string.network_error);
        }

    }

    private void onServiceResult(Single<WordResponse> observable) {
        mDisposible = observable.subscribeWith(new DisposableSingleObserver<WordResponse>() {
            @Override
            public void onSuccess(WordResponse response) {
                mServiceRunning = false;
                mView.hideLoading();
                mDefinitionList = response.getDefinitionsList();
                refreshUi();
            }

            @Override
            public void onError(Throwable e) {
                mServiceRunning = false;
                mView.hideLoading();
                mView.onError(R.string.error);
            }
        });
    }

    public void refreshUi() {
        if (mDefinitionList == null) {
            mView.onError(R.string.empty_response);
        } else {
            if (mDefinitionList.size() > 0) {
                sortByThumbsUp(mDefinitionList);
                mView.showDefinitions(mDefinitionList);
            } else {
                mView.showEmptyView();
            }
        }
    }

    @Override
    public void sort(int sortBy) {

        if (mDefinitionList == null)
            return;
        switch (sortBy) {
            case 0:
                sortByThumbsUp(mDefinitionList);
                break;
            case 1:
                sortByThumbsDown(mDefinitionList);
                break;
        }
        mView.showDefinitions(mDefinitionList);
    }

    public void sortByThumbsDown(ArrayList<WordDefinition> definitionList) {
        if (definitionList == null)
            return;
        Collections.sort(definitionList, new Comparator<WordDefinition>() {
            @Override
            public int compare(WordDefinition word1, WordDefinition word2) {

                if (word1.getThumbsDown() == word2.getThumbsDown())
                    return 0;
                else if (word1.getThumbsDown() < word2.getThumbsDown())
                    return 1;
                else
                    return -1;
            }
        });
    }


    public void sortByThumbsUp(ArrayList<WordDefinition> definitionList) {


        Collections.sort(definitionList, new Comparator<WordDefinition>() {
            @Override
            public int compare(WordDefinition word1, WordDefinition word2) {

                if (word1.getThumbsUp() == word2.getThumbsUp())
                    return 0;
                else if (word1.getThumbsUp() < word2.getThumbsUp())
                    return 1;
                else
                    return -1;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mDefinitionList != null)
            outState.putSerializable(STATE_ITEMS, mDefinitionList);
        if (mServiceRunning) {
            outState.putBoolean(STATE_SERVICE, mServiceRunning);
            outState.putString(STATE_LAST_WORD, mLastWord);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mServiceRunning = savedInstanceState.getBoolean(STATE_SERVICE, false);
        if (mServiceRunning) {
            mLastWord = savedInstanceState.getString(STATE_LAST_WORD);
            fetchWordMeaning(mLastWord);
            return;
        }
        mDefinitionList = (ArrayList<WordDefinition>) savedInstanceState.getSerializable(STATE_ITEMS);
        if (mDefinitionList != null)
            mView.showDefinitions(mDefinitionList);
    }


    @Override
    public void onAttach(MvpView mvpView) {
        mView = (SearchMvpContract.View) mvpView;
    }

    @Override
    public void onDetach() {
        mView.hideLoading();
        if (mDisposible != null && !mDisposible.isDisposed())
            mDisposible.dispose();
        mView = null;
    }
}
