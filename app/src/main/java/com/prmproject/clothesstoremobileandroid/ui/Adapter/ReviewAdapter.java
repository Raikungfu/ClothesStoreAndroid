package com.prmproject.clothesstoremobileandroid.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prmproject.clothesstoremobileandroid.Data.model.Review;
import com.prmproject.clothesstoremobileandroid.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private final AsyncListDiffer<Review> differ;

    public ReviewAdapter() {
        differ = new AsyncListDiffer<>(this, new DiffCallback<>());
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review reviewItem = differ.getCurrentList().get(position);

        holder.senderName.setText(reviewItem.getCustomer().getName());
        holder.reviewMessage.setText(reviewItem.getComment());

        ImageView[] stars = { holder.star1, holder.star2, holder.star3, holder.star4, holder.star5 };

        for (int i = 0; i < stars.length; i++) {
            if (i < reviewItem.getRating()) {
                stars[i].setImageResource(R.drawable.star);
            } else {
                stars[i].setImageResource(R.drawable.empty_star);
            }
        }

        Glide.with(holder.itemView.getContext())
                .load(reviewItem.getCustomer().getAvt())
                .placeholder(R.drawable.logor)
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(List<Review> newReview) {
        differ.submitList(newReview);
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView senderName, reviewMessage;
        ImageView star1, star2, star3, star4, star5;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.review_avatar);
            senderName = itemView.findViewById(R.id.review_sender_name);
            reviewMessage = itemView.findViewById(R.id.review_message);

            star1 = itemView.findViewById(R.id.ratingStar1);
            star2 = itemView.findViewById(R.id.ratingStar2);
            star3 = itemView.findViewById(R.id.ratingStar3);
            star4 = itemView.findViewById(R.id.ratingStar4);
            star5 = itemView.findViewById(R.id.ratingStar5);
        }
    }
}
