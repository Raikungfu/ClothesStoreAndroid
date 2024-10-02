package com.prmproject.clothesstoremobileandroid.ui.Adapter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prmproject.clothesstoremobileandroid.Data.model.Product;
import com.prmproject.clothesstoremobileandroid.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private OnProductClickListener productClickListener;

    public ProductAdapter(List<Product> productList, OnProductClickListener listener) {
        this.productList = productList;
        productClickListener = listener;
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        if (product != null) {
            holder.productName.setText(product.getName());
            String oldPriceText = "$" + product.getOldPrice();
            SpannableString spannableString = new SpannableString(oldPriceText);
            spannableString.setSpan(new StrikethroughSpan(), 0, oldPriceText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.productOldPrice.setText(spannableString);
            holder.productNewPrice.setText("$" + product.getNewPrice());
            if (product.getOldPrice() > 0) {
                int discountPercentage = (int) Math.round(((double) (product.getOldPrice() - product.getNewPrice()) / product.getOldPrice()) * 100);
                holder.productDiscount.setText(discountPercentage + " %");
            } else {
                holder.productDiscount.setText("0 %");
            }
            Glide.with(holder.itemView.getContext()).load(product.getImg()).placeholder(R.drawable.logor).error(R.drawable.logor).into(holder.productImage);

            ImageView[] stars = { holder.star1, holder.star2, holder.star3, holder.star4, holder.star5 };

            for (int i = 0; i < stars.length; i++) {
                int fullStars = product.getRatingPoint().intValue();
                float decimalPart = product.getRatingPoint() - fullStars;

                if (i < fullStars) {
                    stars[i].setImageResource(R.drawable.star);
                } else if (i == fullStars) {
                    if (decimalPart >= 0.5) {
                        stars[i].setImageResource(R.drawable.star);
                    } else if (decimalPart > 0) {
                        stars[i].setImageResource(R.drawable.half_star);
                    } else {
                        stars[i].setImageResource(R.drawable.empty_star);
                    }
                } else {
                    stars[i].setImageResource(R.drawable.empty_star);
                }
            }


            holder.ratingScoreTextView.setText(String.valueOf(product.getRatingPoint()));
            holder.voteCountTextView.setText("(" + product.getRatingCount() + " votes)");
        }
    }



    private int dpToPx(@NonNull ProductViewHolder holder, int size){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, holder.itemView.getResources().getDisplayMetrics());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productNewPrice, productOldPrice, productDiscount, voteCountTextView, ratingScoreTextView;
        ImageView productImage, star1, star2, star3, star4, star5;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productImage = itemView.findViewById(R.id.productImage);
            productNewPrice = itemView.findViewById(R.id.productNewPrice);
            productOldPrice = itemView.findViewById(R.id.productOldPrice);
            productDiscount = itemView.findViewById(R.id.discountPercent);
            voteCountTextView = itemView.findViewById(R.id.voteCount);
            ratingScoreTextView = itemView.findViewById(R.id.ratingScore);

            star1 = itemView.findViewById(R.id.ratingStar1);
            star2 = itemView.findViewById(R.id.ratingStar2);
            star3 = itemView.findViewById(R.id.ratingStar3);
            star4 = itemView.findViewById(R.id.ratingStar4);
            star5 = itemView.findViewById(R.id.ratingStar5);

            itemView.setOnClickListener(v -> {
                if (productClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        productClickListener.onProductClick(productList.get(position));
                    }
                }
            });
        }
    }
}
