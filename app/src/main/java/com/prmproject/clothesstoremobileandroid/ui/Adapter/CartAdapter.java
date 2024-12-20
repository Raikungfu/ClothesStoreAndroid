package com.prmproject.clothesstoremobileandroid.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.prmproject.clothesstoremobileandroid.Data.model.CartItem;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.ObjectResponse;
import com.prmproject.clothesstoremobileandroid.Data.repository.CartRepository;
import com.prmproject.clothesstoremobileandroid.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartItem> cartItems;
    private CartRepository cartRepository;
    private OnQuantityChangeListener quantityChangeListener;

    public interface OnQuantityChangeListener {
        void onQuantityChanged(int total);
    }
    //    public CartAdapter(List<CartItem>cartItems) {
//        this.cartItems = cartItems;
//    }
    public CartAdapter(List<CartItem> cartItems, OnQuantityChangeListener listener) {
        this.cartItems = cartItems;
        this.quantityChangeListener = listener;
        this.cartRepository = new CartRepository();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bag_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.cartProductName.setText(cartItem.getProduct().getName());
        holder.cartProductQuantity.setText("Quantity: " + cartItem.getQuantity());
        holder.cartProductDescription.setText(cartItem.getProduct().getDescription());
        holder.cartProductPrice.setText("$" + cartItem.getProduct().getNewPrice());

        Glide.with(holder.itemView.getContext()).load(cartItem.getProduct().getImg()).into(holder.cartProductImage);

        holder.btnPlus.setOnClickListener(v -> {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            holder.cartProductQuantity.setText("Quantity: " + cartItem.getQuantity());
            updateTotal();
        });
        holder.deleteCartItemBtn.setOnClickListener(v -> {
            cartRepository.deleteCartItem(cartItem.getCartItemId()).observe((LifecycleOwner) holder.itemView.getContext(), new Observer<ObjectResponse<Void>>() {
                @Override
                public void onChanged(ObjectResponse<Void> response) {
                    if (response.isSuccess()) {
                        cartItems.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, cartItems.size());
                        updateTotal();
                    }
                }
            });
        });
        holder.btnMinus.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                holder.cartProductQuantity.setText("Quantity: " + cartItem.getQuantity());
                updateTotal();   //
            }
        });
        updateTotal();   //
    }


    private void updateTotal() {
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuantity() * item.getProduct().getNewPrice();
        }
        if (quantityChangeListener != null) {
            quantityChangeListener.onQuantityChanged(total);
        }
    }



    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cartProductName, cartProductQuantity, cartProductDescription, cartProductPrice;
        ImageView cartProductImage;
        ImageButton btnPlus, btnMinus;
        Button deleteCartItemBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartProductName = itemView.findViewById(R.id.CartProductName);
            cartProductQuantity = itemView.findViewById(R.id.CartProductQuantity);
            cartProductDescription = itemView.findViewById(R.id.CartProductDescription);
            cartProductPrice = itemView.findViewById(R.id.CartProductPrice);
            cartProductImage = itemView.findViewById(R.id.CartProductImage);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            deleteCartItemBtn = itemView.findViewById(R.id.deleteCartItem_btn);
        }
    }
}
