package com.bibin.dictionary.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.bibin.dictionary.MainApplication;
import com.bibin.dictionary.R;
import com.bibin.dictionary.di.UiComponent;
import com.bibin.dictionary.util.Utility;

import butterknife.Unbinder;


/**
 * Base class containing common functionalities
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private static final String TAG = "CURRENT_FRAGMENT";
    private ProgressDialog mProgressDialog;
    private UiComponent component;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUiComponent();
    }

    private void initUiComponent() {
        component = getMainApplication().getComponent()
                .uiComponent();
    }

    protected MainApplication getMainApplication() {
        return (MainApplication) getApplication();
    }

    protected UiComponent getUiComponent() {
        return component;
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = Utility.createLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showMessage(int resId) {
        showSnackBar(getString(resId));
    }

    @Override
    public void onError(int resId) {
        showMessage(resId);
    }

    @Override
    public boolean isNetworkConnected() {
        return Utility.isNetworkConnected(getApplicationContext());
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    protected void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment, TAG);
        transaction.commit();
    }


    protected void onDestroy() {

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

}
