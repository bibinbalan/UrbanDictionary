
package com.bibin.dictionary.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bibin.dictionary.R;
import com.bibin.dictionary.data.model.WordDefinition;
import com.bibin.dictionary.ui.base.BaseFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class SearchFragment extends BaseFragment implements SearchMvpContract.View {

    @Inject
    SearchMvpContract.Presenter mPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.emptyText)
    TextView emptyView;

    @BindView(R.id.btn_search)
    TextView btnSearch;

    @BindView(R.id.edt_search)
    TextView edtSearch;

    @BindView(R.id.lbl_sort)
    TextView tvSortLbl;

    @BindView(R.id.spnr_sort)
    Spinner spnrSort;

    private DefinitionsListAdapter mListAdaper;
    private LinearLayoutManager mLayoutMaanger;


    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        getUiComponent().inject(this);
        setUnBinder(ButterKnife.bind(this, view));
        mPresenter.onAttach(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpList();
        setUpSortSpinner();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    protected void setUpList() {
        mLayoutMaanger = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(mLayoutMaanger);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mListAdaper = new DefinitionsListAdapter();
        recyclerView.setAdapter(mListAdaper);
    }

    protected void setUpSortSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mActivity,
                R.array.sort_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrSort.setAdapter(adapter);
    }

    @OnItemSelected(R.id.spnr_sort)
    public void spinnerItemSelected(Spinner spinner, int position) {
        mPresenter.sort(position);
    }

    @OnClick(R.id.btn_search)
    protected void onClickSearch(View v) {
        mPresenter.fetchWordMeaning(edtSearch.getText().toString());
        hideKeyboard();
        mListAdaper.clearItems();
    }

    @OnTextChanged(R.id.edt_search)
    protected void onSearchTextChange(CharSequence s, int start, int before, int count) {
        btnSearch.setEnabled(s.length() > 0);
    }


    @Override
    public void showDefinitions(ArrayList<WordDefinition> definitions) {
        showSerchResultViews();
        mListAdaper.addItems(definitions);
    }

    @Override
    public void onError(int resId) {
        super.onError(resId);
        hideSerchResultViews();
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        hideSerchResultViews();
    }

    private void showSerchResultViews() {
        if (recyclerView.getVisibility() == View.INVISIBLE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (tvSortLbl.getVisibility() == View.INVISIBLE) {
            tvSortLbl.setVisibility(View.VISIBLE);
            spnrSort.setVisibility(View.VISIBLE);
        }
        if (emptyView.getVisibility() == View.VISIBLE) {
            emptyView.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSerchResultViews() {
        recyclerView.setVisibility(View.INVISIBLE);
        tvSortLbl.setVisibility(View.INVISIBLE);
        spnrSort.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

}
