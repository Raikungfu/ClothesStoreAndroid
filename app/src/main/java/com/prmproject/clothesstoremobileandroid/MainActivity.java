package com.prmproject.clothesstoremobileandroid;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.prmproject.clothesstoremobileandroid.Data.repository.MessageListener;
import com.prmproject.clothesstoremobileandroid.databinding.ActivityMainBinding;
import com.prmproject.clothesstoremobileandroid.ui.Auth.ForgotPasswordFragment;
import com.prmproject.clothesstoremobileandroid.ui.Auth.RegisterFragment;

import java.util.Objects;
public class MainActivity extends AppCompatActivity implements MessageListener {

    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;


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

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_shop,
                R.id.navigation_bag,
                R.id.navigation_favourites,
                R.id.navigation_login)
                .setOpenableLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
