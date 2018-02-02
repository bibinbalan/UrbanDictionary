package com.bibin.dictionary.ui.base;

import android.support.annotation.StringRes;

/**
 * Common call backs that View class should implement,which then can be used by Presenter
 */

public interface MvpView {

    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

}
