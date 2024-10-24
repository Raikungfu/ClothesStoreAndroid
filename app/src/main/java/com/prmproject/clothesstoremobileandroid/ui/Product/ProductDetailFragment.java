package com.prmproject.clothesstoremobileandroid.ui.Product;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prmproject.clothesstoremobileandroid.Data.model.Chat;
import com.prmproject.clothesstoremobileandroid.Data.model.Option;
import com.prmproject.clothesstoremobileandroid.Data.model.Product;
import com.prmproject.clothesstoremobileandroid.Data.model.Review;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToSend.CartItemDetail;
import com.prmproject.clothesstoremobileandroid.MainActivity;
import com.prmproject.clothesstoremobileandroid.R;
import com.prmproject.clothesstoremobileandroid.databinding.FragmentProductDetailBinding;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.OptionAdapter;
import com.prmproject.clothesstoremobileandroid.ui.Adapter.ReviewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDetailFragment extends Fragment {
    private FragmentProductDetailBinding binding;
    private ProductDetailViewModel productDetailViewModel;
    private NavController navController;
    private LinearLayout containerLayout;
    private RecyclerView reviewRecyclerView;
    private List<Review> reviewList;
    private int productId;
    private int sellerId;
    private ReviewAdapter reviewAdapter;
    private int currentPage = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        productDetailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        reviewRecyclerView = binding.reviewRecyclerView;
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, true));
        reviewAdapter = new ReviewAdapter();
        reviewRecyclerView.setAdapter(reviewAdapter);

        Bundle args = getArguments();
        if (args != null) {
            productId = args.getInt("productId", -1);
        }
        binding.loadMoreReviewBtn.setOnClickListener(v -> {
            ++currentPage;
            loadReview(currentPage);
        });
        binding.addToCartButton.setOnClickListener(v -> addToCart());
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(productId != -1) {
            loadProductDetail();
            loadReview(currentPage);
        }
    }

    private void loadReview(int page) {
        productDetailViewModel.getListReview(productId, page).observe(getViewLifecycleOwner(), reviewListResponse -> {
            if (reviewListResponse != null && reviewListResponse.isSuccess()) {
                if (reviewList == null) {
                    reviewList = new ArrayList<>();
                }
                reviewList.addAll(reviewListResponse.getItems());

                reviewAdapter.submitList(new ArrayList<>(reviewList));
            }
        });
    }

    private void loadProductDetail() {
        productDetailViewModel.getProductDetail(productId).observe(getViewLifecycleOwner(), productResponst -> {
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
                binding.voteCount.setText(product.getRatingCount() + "votes");
                binding.chatButton.setOnClickListener(v -> {
                    productDetailViewModel.postChat(product.getSeller().getUserId()).observe(getViewLifecycleOwner(), response -> {
                        if (response != null && response.isSuccess()) {
                            navigateToChatMessageFragment((Chat) response.getItem());
                        } else {
                            ((MainActivity) requireActivity()).onMessage("Failed to initiate chat: " + response.getErrorMessage() + " Status: " + response.getCodeError());
                        }
                    });
                });

                binding.shopTitleDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putInt("shopId", product.getSellerId());
                        navController.navigate(R.id.action_navigation_product_detail_to_navigation_shop_info, args);
                    }
                });

                containerLayout = binding.productOptions;
                Map<String, List<Option>> groupedOptions = product.getOptions().stream().collect(Collectors.groupingBy(Option::getProductOption));

                groupedOptions.forEach((key, value) -> {
                    createRecyclerView(key, value);
                });

                binding.buyNowButton.setOnClickListener(v -> {
                    Bundle args = new Bundle();
                    args.putFloat("totalPayment", product.getNewPrice());

                    navController.navigate(R.id.action_navigation_product_detail_to_payment, args);
                });
            }
        });
    }

    private void createRecyclerView(String key, List<Option> value) {
        TextView optionTitle = new TextView(requireActivity());
        optionTitle.setText(key);
        optionTitle.setTextSize(18);
        optionTitle.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textPrimary));
        optionTitle.setTypeface(null, Typeface.BOLD);

        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        titleLayoutParams.setMargins(0, 16, 0, 8);
        optionTitle.setLayoutParams(titleLayoutParams);

        RecyclerView recyclerView = new RecyclerView(requireActivity());
        recyclerView.setId(View.generateViewId());
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        OptionAdapter adapter = new OptionAdapter(value, this::onOptionChoose);
        recyclerView.setAdapter(adapter);

        containerLayout.addView(optionTitle);
        containerLayout.addView(recyclerView);
    }

    private void onOptionChoose(Option option){

    }

    private void navigateToChatMessageFragment(Chat chatItemResponse) {
        Bundle args = new Bundle();
        args.putInt("roomId", chatItemResponse.getRoomId());

        navController.navigate(R.id.action_navigation_product_detail_to_navigation_chat, args);
    }
    private void addToCart() {
        CartItemDetail cartItemDetail = new CartItemDetail();
        cartItemDetail.setProductId(productId);
        cartItemDetail.setQuantity(1);

        productDetailViewModel.addCartItem(cartItemDetail).observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.isSuccess()) {
                ((MainActivity) requireActivity()).onMessage("Product added to cart successfully!");
            } else {
                ((MainActivity) requireActivity()).onMessage("Failed to add product to cart: " + response.getErrorMessage() + " Status: " + response.getCodeError());
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
