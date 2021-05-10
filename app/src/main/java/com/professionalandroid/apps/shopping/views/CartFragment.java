package com.professionalandroid.apps.shopping.views;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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

import com.professionalandroid.apps.shopping.R;
import com.professionalandroid.apps.shopping.adapters.CartListAdapter;
import com.professionalandroid.apps.shopping.databinding.FragmentCartBinding;
import com.professionalandroid.apps.shopping.models.CartItem;
import com.professionalandroid.apps.shopping.viewmodels.ShopViewModel;

import java.util.List;


public class CartFragment extends Fragment implements CartListAdapter.CartInterface {

    private static final String TAG = "CartFragment";
    private ShopViewModel shopViewModel;
    private FragmentCartBinding cartBinding;
    private NavController navController;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cartBinding = FragmentCartBinding.inflate(inflater, container, false);
        return cartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);

        //어댑터 객체 생성
        final CartListAdapter adapter = new CartListAdapter(this);
        navController = Navigation.findNavController(view);
        cartBinding.cartRecyclerView.setAdapter(adapter);
        cartBinding.cartRecyclerView.addItemDecoration(
                new DividerItemDecoration(requireContext(),
                        DividerItemDecoration.VERTICAL
                ));

        //버튼 클릭시
        cartBinding.placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shopViewModel.getOrderList().size()==0) {
                    Toast.makeText(getContext(), "결제할 상품을 선택 하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                navController.navigate(R.id.action_cartFragment_to_orderFragment);
            }
        });


        //
        shopViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                adapter.submitList(cartItems);
            }
        });


    }

    @Override
    public void deleteItem(CartItem cartItem) {
        shopViewModel.removeItemFromCart(cartItem);
    }

    @Override
    public void addOrderList(CartItem cartItem) {
        shopViewModel.addOrderList(cartItem);
        Log.d(TAG, shopViewModel.getOrderList().toString());
    }

    @Override
    public void removeOrderItem(CartItem cartItem) {
        shopViewModel.removeOrderItem(cartItem);
        Log.d(TAG, shopViewModel.getOrderList().toString());
    }

}