package com.bibin.dictionary;

import com.bibin.dictionary.data.DataHelper;
import com.bibin.dictionary.data.model.WordResponse;
import com.bibin.dictionary.ui.search.SearchMvpContract;
import com.bibin.dictionary.ui.search.SearchMvpPresenter;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import io.reactivex.Single;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


/**
 * local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchPressenterTest {


    Gson gson = new Gson();

    DataHelper dataHelper;
    SearchMvpContract.View view;
    SearchMvpPresenter presenter;

    @Before
    public void setup() {
        dataHelper = mock(DataHelper.class);
        view = mock(SearchMvpContract.View.class);
        presenter = new SearchMvpPresenter(dataHelper);
        presenter.onAttach(view);
    }

    @Test
    public void loadProductsFromRepositoryAndLoadIntoView() throws IOException {

        String word = "cat";
        String json = IOUtils.readStringFromResourcePath("sample_response.json");
        WordResponse response = gson.fromJson(json, WordResponse.class);

        when(view.isNetworkConnected()).thenReturn(true);
        when(dataHelper.getWordMeaning(word)).thenReturn(Single.just(response));

        presenter.fetchWordMeaning(word);
        verify(view).showLoading();
        verify(dataHelper).getWordMeaning(word);
        verify(view).hideLoading();
        verify(view).showDefinitions(response.getDefinitionsList());
        verifyNoMoreInteractions(dataHelper);
    }


    @Test
    public void loadProductsFromRepositoryFailsAndPresentErrorLayout() {

        String word = "cat";
        when(view.isNetworkConnected()).thenReturn(true);
        when(dataHelper.getWordMeaning(word)).thenReturn(Single.<WordResponse>error(new NullPointerException()));
        presenter.fetchWordMeaning(word);

        verify(dataHelper).getWordMeaning(word);
        verify(view).onError(R.string.error);
        verify(view).hideLoading();
        verifyNoMoreInteractions(dataHelper);
    }


}