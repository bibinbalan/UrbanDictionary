
package com.bibin.dictionary.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.bibin.dictionary.R;


/**
 * Utility functions
 */

public final class Utility {

    private static final String TAG = "CommonUtils";

    private Utility() {
        // This utility class is not publicly instantiable
    }

    public static ProgressDialog createLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    public static void showViewWithHtmlText(TextView tv, Spanned txt) {
        if (txt != null) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(txt);
        } else {
            tv.setVisibility(View.INVISIBLE);
        }
    }

    public static void showViewWithText(TextView tv, String txt) {
        if (txt != null) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(txt);
        } else {
            tv.setVisibility(View.INVISIBLE);
        }
    }


    public static Spanned textFromHtml(String txt) {
        if (txt != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(txt, Html.FROM_HTML_MODE_LEGACY);
            } else {
                return Html.fromHtml(txt);
            }
        }
        return null;
    }


}
