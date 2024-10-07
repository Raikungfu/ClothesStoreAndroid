package com.prmproject.clothesstoremobileandroid.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prmproject.clothesstoremobileandroid.Data.model.Option;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    private List<Option> options;
    private Option selectedOption;
    private final OnOptionSelectedListener onOptionSelectedListener;

    public interface OnOptionSelectedListener {
        void onOptionChoose(Option option);
    }

    public OptionAdapter(List<Option> options, OnOptionSelectedListener onOptionSelectedListener) {
        this.options = options;
        this.onOptionSelectedListener = onOptionSelectedListener;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RadioButton radioButton = new RadioButton(parent.getContext());
        radioButton.setLayoutParams(new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        ));

        return new OptionViewHolder(radioButton);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        Option option = options.get(position);

        holder.radioButton.setPadding(16, 0, 16, 0);
        holder.bind(option);
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    class OptionViewHolder extends RecyclerView.ViewHolder {
        private RadioButton radioButton;

        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = (RadioButton) itemView;
        }

        public void bind(Option option) {
            radioButton.setText(option.getName());

            radioButton.setChecked(option.equals(selectedOption));

            radioButton.setOnClickListener(v -> {
                selectedOption = option;
                notifyDataSetChanged();

                onOptionSelectedListener.onOptionChoose(option);
            });
        }
    }
}
