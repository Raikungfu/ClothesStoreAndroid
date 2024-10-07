package com.prmproject.clothesstoremobileandroid.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prmproject.clothesstoremobileandroid.Data.model.ProductOption;

import java.util.List;
public class FilterProductOptionAdapter extends RecyclerView.Adapter<FilterProductOptionAdapter.FilterProductOptionViewHolder> {
    private List<ProductOption> productOptionList;
    private List<ProductOption> selectedOption;
    private final OnProductOptionSelectedListener onProductOptionSelectedListener;

    public interface OnProductOptionSelectedListener {
        void onOptionChoose(ProductOption option);
    }

    public FilterProductOptionAdapter(List<ProductOption> options, OnProductOptionSelectedListener onProductOptionSelectedListener) {
        this.productOptionList = options;
        this.onProductOptionSelectedListener = onProductOptionSelectedListener;
    }

    @NonNull
    @Override
    public FilterProductOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckBox checkButton = new CheckBox(parent.getContext());
        checkButton.setLayoutParams(new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        ));

        return new FilterProductOptionViewHolder(checkButton);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterProductOptionViewHolder holder, int position) {
        ProductOption option = productOptionList.get(position);

        holder.checkBoxButton.setPadding(16, 0, 16, 0);
        holder.bind(option);
    }

    @Override
    public int getItemCount() {
        return productOptionList.size();
    }

    class FilterProductOptionViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBoxButton;

        public FilterProductOptionViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxButton = (CheckBox) itemView;
        }

        public void bind(ProductOption option) {
            checkBoxButton.setText(option.getName());

            checkBoxButton.setChecked(option.equals(selectedOption));

            checkBoxButton.setOnClickListener(v -> {
                onProductOptionSelectedListener.onOptionChoose(option);
            });
        }
    }
}
