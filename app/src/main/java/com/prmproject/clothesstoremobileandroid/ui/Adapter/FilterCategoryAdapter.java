package com.prmproject.clothesstoremobileandroid.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prmproject.clothesstoremobileandroid.Data.model.Category;

import java.util.List;
public class FilterCategoryAdapter extends RecyclerView.Adapter<FilterCategoryAdapter.FilterCategoryViewHolder> {
    private List<Category> categoryList;
    private final OnCategorySelectedListener onCategorySelectedListener;

    public interface OnCategorySelectedListener {
        void onCategoryChoose(Category option);
    }

    public FilterCategoryAdapter(List<Category> options, OnCategorySelectedListener onCategorySelectedListener) {
        this.categoryList = options;
        this.onCategorySelectedListener = onCategorySelectedListener;
    }

    @NonNull
    @Override
    public FilterCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckBox checkButton = new CheckBox(parent.getContext());
        checkButton.setLayoutParams(new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        ));

        return new FilterCategoryViewHolder(checkButton);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterCategoryViewHolder holder, int position) {
        Category option = categoryList.get(position);

        holder.checkBoxButton.setPadding(16, 0, 16, 0);
        holder.bind(option);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class FilterCategoryViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBoxButton;

        public FilterCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxButton = (CheckBox) itemView;
        }

        public void bind(Category category) {
            checkBoxButton.setText(category.getName());

            checkBoxButton.setOnClickListener(v -> {
                onCategorySelectedListener.onCategoryChoose(category);
            });
        }
    }
}
