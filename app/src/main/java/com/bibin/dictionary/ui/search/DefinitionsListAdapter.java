package com.bibin.dictionary.ui.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bibin.dictionary.R;
import com.bibin.dictionary.data.model.WordDefinition;
import com.bibin.dictionary.util.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DefinitionsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<WordDefinition> mDefinitionList;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).onBind(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_definition_view, parent, false));
    }

    @Override
    public int getItemCount() {
        return mDefinitionList != null ? mDefinitionList.size() : 0;
    }

    public void addItems(ArrayList<WordDefinition> definitions) {
        if (mDefinitionList == null)
            mDefinitionList = new ArrayList<>();
        else
            mDefinitionList.clear();
        mDefinitionList.addAll(definitions);
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (mDefinitionList != null)
            mDefinitionList.clear();
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.definition)
        TextView tvDefinition;

        @BindView(R.id.thumbs_up)
        TextView tvThumbsUp;

        @BindView(R.id.thumbs_down)
        TextView tvThumbsDown;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {
            WordDefinition definition = mDefinitionList.get(position);
            Utility.showViewWithHtmlText(tvDefinition, Utility.textFromHtml(definition.getDefinition()));
            tvThumbsUp.setText(String.valueOf(definition.getThumbsUp()));
            tvThumbsDown.setText(String.valueOf(definition.getThumbsDown()));
        }
    }


}
