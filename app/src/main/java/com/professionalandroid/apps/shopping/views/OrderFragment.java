package com.professionalandroid.apps.shopping.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.professionalandroid.apps.shopping.R;
import com.professionalandroid.apps.shopping.adapters.OrderListAdapter;
import com.professionalandroid.apps.shopping.databinding.FragmentCartBinding;
import com.professionalandroid.apps.shopping.databinding.FragmentOrderBinding;
import com.professionalandroid.apps.shopping.models.CartItem;
import com.professionalandroid.apps.shopping.viewmodels.ShopViewModel;

public class OrderFragment extends Fragment implements OrderListAdapter.OrderInterface {

    private ShopViewModel shopViewModel;
    private FragmentOrderBinding orderBinding;
    private NavController navController;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        orderBinding = FragmentOrderBinding.inflate(inflater, container, false);
        return orderBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        OrderListAdapter adapter = new OrderListAdapter();
        orderBinding.oderRecyclerView.setAdapter(adapter);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        adapter.submitList(shopViewModel.getOrderList());
        orderBinding.setOderInterface(this);
        // 결제이후 로직
        orderBinding.paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(CartItem purchasedItem : shopViewModel.getOrderList()){
                    shopViewModel.removeItemFromCart(purchasedItem);
                }
                shopViewModel.getOrderList().clear();
                Toast.makeText(getContext(), "상품 결제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_orderFragment_to_shopFragment);
            }
        });
    }

    @Override
    public int calculateTotalPrice() {
        if(shopViewModel.getOrderList() == null)
            return 0;
        int totalPrice = 0;
        for( CartItem item : shopViewModel.getOrderList()){
           totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }
        return totalPrice;
    }
}