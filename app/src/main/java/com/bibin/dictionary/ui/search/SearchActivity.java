package com.bibin.dictionary.ui.search;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.bibin.dictionary.R;
import com.bibin.dictionary.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.toolbar_view)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_fragment_container);
        setUnBinder(ButterKnife.bind(this));
        setSupportActionBar(mToolbar);
        setTitle("Dictionary");
        if (savedInstanceState == null)
            addFragment(SearchFragment.newInstance());
    }

}
