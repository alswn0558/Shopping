package com.professionalandroid.apps.shopping.views;

import android.os.Bundle;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.professionalandroid.apps.shopping.R;
import com.professionalandroid.apps.shopping.adapters.ShopListAdapter;
import com.professionalandroid.apps.shopping.databinding.FragmentShopBinding;
import com.professionalandroid.apps.shopping.models.CartItem;
import com.professionalandroid.apps.shopping.models.Product;
import com.professionalandroid.apps.shopping.viewmodels.ShopViewModel;

import java.util.List;


public class ShopFragment extends Fragment implements ShopListAdapter.ShopInterface {

    private static final String TAG = "ShopFragment";

    FragmentShopBinding fragmentShopBinding;
    FloatingActionButton fab;
    private ShopListAdapter shopListAdapter;
    private ShopViewModel viewModel;
    private NavController navController;


    public ShopFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentShopBinding = FragmentShopBinding.inflate(inflater, container, false);
        return fragmentShopBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shopListAdapter = new ShopListAdapter(this);

        fragmentShopBinding.shopRecyclerView.setAdapter(shopListAdapter);

        viewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        viewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                shopListAdapter.submitList(products);
            }
        });

        navController = Navigation.findNavController(view);

        //???????????? ?????? ????????? ??????????????? ??????
        fragmentShopBinding.goToCartFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_shopFragment_to_cartFragment);
            }
        });
    }

    @Override
    public void addItem(Product product) {

        boolean isAdded = viewModel.addProductToCart(product);

        if(isAdded){
            Snackbar.make(requireView(), product.getName()+"??? ??????????????? ?????? ???????????????.",Snackbar.LENGTH_LONG)
                    .setAction("???????????? ??????", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            navController.navigate(R.id.action_shopFragment_to_cartFragment);
                        }
                    }).show();
        }else {
            Toast.makeText(getContext(), "??? ????????? ??? ?????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(Product product) {
        Log.d(TAG, "onItemClick" + product.toString());
        viewModel.setProduct(product);
        navController.navigate(R.id.action_shopFragment_to_productDetailFragment);
    }

    @Override
    public void directBuy(Product product) {
        Log.d(TAG, "onItemClick" + product.toString());
        viewModel.addOrderList(new CartItem(product,1));
        navController.navigate(R.id.action_shopFragment_to_orderFragment);
    }
}