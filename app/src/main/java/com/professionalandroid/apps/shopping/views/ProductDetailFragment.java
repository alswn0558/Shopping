package com.professionalandroid.apps.shopping.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.professionalandroid.apps.shopping.R;
import com.professionalandroid.apps.shopping.databinding.FragmentProductDetailBinding;
import com.professionalandroid.apps.shopping.viewmodels.ShopViewModel;


public class ProductDetailFragment extends Fragment {

    FragmentProductDetailBinding fragmentProductDetailBinding;
    ShopViewModel viewModel;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       fragmentProductDetailBinding = FragmentProductDetailBinding.inflate(inflater, container, false);
       return fragmentProductDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        fragmentProductDetailBinding.setShopViewModel(viewModel);
    }
}