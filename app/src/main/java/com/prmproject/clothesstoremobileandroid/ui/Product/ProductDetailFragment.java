package com.prmproject.clothesstoremobileandroid.ui.Product;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.prmproject.clothesstoremobileandroid.Data.model.Chat;
import com.prmproject.clothesstoremobileandroid.Data.model.Product;
import com.prmproject.clothesstoremobileandroid.MainActivity;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentProductDetailBinding;

public class ProductDetailFragment extends Fragment {
    private FragmentProductDetailBinding binding;
    private ProductDetailViewModel productDetailViewModel;
    NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        productDetailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);

        loadProductsFromArguments();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProductsFromArguments();
    }

    private void loadProductsFromArguments() {
        Bundle args = getArguments();
        if (args != null) {
            int productId = args.getInt("productId", -1);
            if(productId != -1) loadProductDetail(productId);
        }
    }

    private void loadProductDetail(int id) {
        productDetailViewModel.getProductDetail(id).observe(getViewLifecycleOwner(), productResponst -> {
            Product product = (Product) productResponst.getItem();
            if(product != null){
                binding.productName.setText(product.getName());
                binding.productDescription.setText(product.getDescription());
                Glide.with(this).load(product.getImg()).placeholder(R.drawable.logor).error(R.drawable.logor).into(binding.productImage);
                String oldPriceText = "$" + product.getOldPrice();
                SpannableString spannableString = new SpannableString(oldPriceText);
                spannableString.setSpan(new StrikethroughSpan(), 0, oldPriceText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                binding.productOldPrice.setText(spannableString);
                binding.productNewPrice.setText("$" + product.getNewPrice());
                if (product.getOldPrice() > 0) {
                    int discountPercentage = (int) Math.round(((double) (product.getOldPrice() - product.getNewPrice()) / product.getOldPrice()) * 100);
                    binding.productDiscount.setText(discountPercentage + " %");
                } else {
                    binding.productDiscount.setText("0 %");
                }

                ImageView[] stars = { binding.ratingStar1, binding.ratingStar2, binding.ratingStar3, binding.ratingStar4, binding.ratingStar5 };

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

                binding.productSoldQuantity.setText("Sold: " + String.valueOf(product.getQuantitySold()));
                binding.sellerName.setText(product.getSeller().getCompanyName());
                Glide.with(this).load(product.getSeller().getAvt()).placeholder(R.drawable.logor).error(R.drawable.logor).into(binding.sellerAvt);
                binding.sellerAddress.setText(product.getSeller().getAddress());
                binding.voteCount.setText(String.valueOf(product.getRatingCount()));
                binding.chatButton.setOnClickListener(v -> {
                    productDetailViewModel.postChat(product.getSellerId()).observe(getViewLifecycleOwner(), response -> {
                        if (response != null && response.isSuccess()) {
                            navigateToChatMessageFragment((Chat) response.getItem());
                        } else {
                            ((MainActivity) requireActivity()).onMessage("Failed to initiate chat: " + response.getErrorMessage() + " Status: " + response.getCodeError());
                        }
                    });
                });

            }
        });
    }

    private void navigateToChatMessageFragment(Chat chatItemResponse) {
        Bundle args = new Bundle();
        args.putInt("roomId", chatItemResponse.getRoomId());

        navController.navigate(R.id.action_navigation_list_chat_to_navigation_chat, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
