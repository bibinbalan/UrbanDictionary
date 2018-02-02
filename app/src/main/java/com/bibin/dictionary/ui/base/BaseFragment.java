

package com.bibin.dictionary.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.bibin.dictionary.di.UiComponent;
import com.bibin.dictionary.util.Utility;

import butterknife.Unbinder;


/**
 * Base class containing common functionalities
 */

public abstract class BaseFragment extends Fragment implements MvpView {

    protected BaseActivity mActivity;
    private ProgressDialog mProgressDialog;
    private Unbinder mUnBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
        }
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = Utility.createLoadingDialog(this.getContext());
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void showMessage(int resId) {
        if (mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    public void onError(int resId) {

        if (mActivity != null) {
            mActivity.onError(resId);
        }

    }


    public boolean isNetworkConnected() {
        if (mActivity != null) {
            return mActivity.isNetworkConnected();
        }
        return false;
    }

    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }
    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }


    public UiComponent getUiComponent() {
        if (mActivity != null) {
            return mActivity.getUiComponent();
        }
        return null;
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    public void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

}
