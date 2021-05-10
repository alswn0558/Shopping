package com.professionalandroid.apps.shopping.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.professionalandroid.apps.shopping.R;
import com.professionalandroid.apps.shopping.models.CartItem;
import com.professionalandroid.apps.shopping.viewmodels.ShopViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    NavController navController;
    ShopViewModel shopViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // nav 컨트롤러 참조
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // actionbar 설정
        NavigationUI.setupActionBarWithNavController(this, navController);
        // viewModel
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
        shopViewModel.getCart().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                Log.d(TAG, "onChanged: "+cartItems.toString());
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavDestination destination = navController.getCurrentDestination();
        String label = destination.getLabel().toString();
        if(label.equals("장바구니") || label.equals("결제")){
            shopViewModel.getOrderList().clear();
            Log.d(TAG, destination.getLabel().toString());
            Log.d(TAG, shopViewModel.getOrderList().toString());
        }
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        NavDestination destination = navController.getCurrentDestination();
        String label = destination.getLabel().toString();
        if(label.equals("장바구니") || label.equals("결제")){
            shopViewModel.getOrderList().clear();
            Log.d(TAG, destination.getLabel().toString());
            Log.d(TAG, shopViewModel.getOrderList().toString());
        }
        super.onBackPressed();
    }
}