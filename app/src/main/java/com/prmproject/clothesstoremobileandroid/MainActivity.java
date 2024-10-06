package com.prmproject.clothesstoremobileandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.prmproject.clothesstoremobileandroid.Data.model.Category;
import com.prmproject.clothesstoremobileandroid.Data.model.ProductOption;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.FilterListResponse;
import com.prmproject.clothesstoremobileandroid.Data.model.dataToReceive.StatusMessage;
import com.prmproject.clothesstoremobileandroid.Data.repository.MessageListener;
import com.prmproject.clothesstoremobileandroid.Data.repository.ProductRepository;
import com.prmproject.clothesstoremobileandroid.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity implements MessageListener {

    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private SearchView searchView;
    private ProductRepository productRepository;
    private LinearLayout containerFilterLayout;
    private ArrayList<Integer> selectedOption;
    private ArrayList<Integer> selectedCategory;
    private SearchView searchViewFilter;
    private Button clearButton, filterButton;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        DrawerLayout drawerLayout = binding.drawerLayout;
        BottomNavigationView navView = binding.navView;

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_shop,
                R.id.navigation_bag,
                R.id.navigation_list_chat,
                R.id.navigation_login)
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() == itemId) {
                return true;
            }
            navController.popBackStack(itemId, false);
            return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
        });

        searchView = findViewById(R.id.search_view);
        if (searchView == null) {
            Log.e("MainActivity", "searchView is null");
        }else{
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMessage("Search clicked!");
                }
            });

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    Toast.makeText(MainActivity.this, "SearchView Closed", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Bundle args = new Bundle();
                    try{
                        args.putInt("categoryId", -1);
                        args.putString("name", query);
                        navController.navigate(R.id.navigation_shop, args);
                    }catch (Exception e){
                        Log.e("Error", e.getMessage());
                    }
                    hideKeyboard();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    return false;
                }
            });

            searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                }
            });
        }

        listenFromServer();
        loadListFilter();
    }

    private void loadListFilter(){
        productRepository = new ProductRepository();
        NavigationView navigationHeaderView = binding.navViewDrawer;
        View headerView = navigationHeaderView.getHeaderView(0);
        selectedCategory = new ArrayList<>();
        selectedOption = new ArrayList<>();

        containerFilterLayout = headerView.findViewById(R.id.filter_container);
        try {
            productRepository.getFilterList().observe(this, filterListResponst -> {
                if (filterListResponst != null && filterListResponst.isSuccess()) {
                    FilterListResponse list = (FilterListResponse) filterListResponst.getItem();
                    Map<String, List<ProductOption>> groupedOptions = list.getProductOptionsList().stream().collect(Collectors.groupingBy(ProductOption::getName));

                    createCategoryLayout("Category", list.getCategoryList());

                    groupedOptions.forEach((key, value) -> {
                        createProductOptionLayout(key, value);
                    });
                }
            });
        } catch (Exception e) {
            Log.e("Error", "Error message: " + e.getMessage());
        }

        searchViewFilter = headerView.findViewById(R.id.search_view_filter);
        clearButton = headerView.findViewById(R.id.clearButton);
        filterButton = headerView.findViewById(R.id.filterButton);

        clearButton.setOnClickListener(v -> clearOptionsAndSearch());

        filterButton.setOnClickListener(v -> submitSearch());
    }

    private void clearOptionsAndSearch() {
        searchView.setQuery("", false);
        searchViewFilter.setQuery("", false);

        for (int i = 0; i < containerFilterLayout.getChildCount(); i++) {
            View child = containerFilterLayout.getChildAt(i);
            if (child instanceof FlexboxLayout) {
                FlexboxLayout flexboxLayout = (FlexboxLayout) child;
                for (int j = 0; j < flexboxLayout.getChildCount(); j++) {
                    View checkBoxView = flexboxLayout.getChildAt(j);
                    if (checkBoxView instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) checkBoxView;
                        checkBox.setChecked(false);
                    }
                }
            }
        }

        selectedOption.clear();
        selectedCategory.clear();
    }

    private void submitSearch() {
        Bundle args = new Bundle();
        try{
            args.putInt("categoryId", -1);

            args.putString("name", searchViewFilter.getQuery().toString());

            int[] listOptionArray = new int[selectedOption.size()];
            for (int i = 0; i < selectedOption.size(); i++) {
                listOptionArray[i] = selectedOption.get(i);
            }
            args.putIntArray("listOptionId", listOptionArray);

            int[] listCategoryArray = new int[selectedCategory.size()];
            for (int i = 0; i < selectedCategory.size(); i++) {
                listCategoryArray[i] = selectedCategory.get(i);
            }

            args.putIntArray("listCategoryId", listCategoryArray);
            navController.navigate(R.id.navigation_shop, args);
        }catch (Exception e){
            Log.e("Error", e.getMessage());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void listenFromServer(){
        ((ClothesStore) getApplication()).getSignalRService().getHubConnection().on("ErrorResponse", (StatusMessage errorMessage) -> {
            onMessage(errorMessage.getStatus() + ": " + errorMessage.getContent());
        }, StatusMessage.class);
    }

    private void createProductOptionLayout(String key, List<ProductOption> value) {
        TextView title = new TextView(this);
        title.setText(key);
        title.setTextSize(18);
        title.setTextColor(ContextCompat.getColor(this, R.color.textPrimary));
        title.setTypeface(null, Typeface.BOLD);

        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        titleLayoutParams.setMargins(0, 16, 0, 8);
        title.setLayoutParams(titleLayoutParams);

        FlexboxLayout flexboxLayout = new FlexboxLayout(this);
        flexboxLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        flexboxLayout.setFlexWrap(FlexWrap.WRAP);
        flexboxLayout.setJustifyContent(JustifyContent.FLEX_START);

        for (ProductOption option : value) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(option.getNameDescription());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (!selectedOption.contains(option.getProductOptionsId())) {
                        selectedOption.add(option.getProductOptionsId());
                    }
                } else {
                    if (selectedOption.contains(option.getProductOptionsId())) {
                        selectedOption.remove(Integer.valueOf(option.getProductOptionsId()));
                    }
                }
            });

            flexboxLayout.addView(checkBox);
        }

        containerFilterLayout.addView(title);
        containerFilterLayout.addView(flexboxLayout);
    }

    private void createCategoryLayout(String key, List<Category> value) {
        TextView title = new TextView(this);
        title.setText(key);
        title.setTextSize(18);
        title.setTextColor(ContextCompat.getColor(this, R.color.textPrimary));
        title.setTypeface(null, Typeface.BOLD);

        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        titleLayoutParams.setMargins(0, 16, 0, 10);
        title.setLayoutParams(titleLayoutParams);

        FlexboxLayout flexboxLayout = new FlexboxLayout(this);
        flexboxLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        flexboxLayout.setFlexWrap(FlexWrap.WRAP);
        flexboxLayout.setJustifyContent(JustifyContent.FLEX_START);

        for (Category category : value) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(category.getName());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (!selectedCategory.contains(category.getCategoryId())) {
                        selectedCategory.add(category.getCategoryId());
                    }
                } else {
                    if (selectedCategory.contains(category.getCategoryId())) {
                        selectedCategory.remove(Integer.valueOf(category.getCategoryId()));
                    }
                }
            });

            flexboxLayout.addView(checkBox);
        }

        containerFilterLayout.addView(title);
        containerFilterLayout.addView(flexboxLayout);
    }
}
