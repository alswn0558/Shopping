package com.professionalandroid.apps.shopping.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.professionalandroid.apps.shopping.databinding.CartItemBinding;
import com.professionalandroid.apps.shopping.databinding.OrderItemBinding;
import com.professionalandroid.apps.shopping.models.CartItem;

public class OrderListAdapter extends ListAdapter<CartItem, OrderListAdapter.OderViewHolder> {

    //어댑터 생성자
    public OrderListAdapter() {
        super(CartItem.itemCallback);

    }

    @NonNull
    @Override
    public OderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OrderItemBinding orderItemBinding = OrderItemBinding.inflate(layoutInflater, parent, false);
        return new OderViewHolder(orderItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OderViewHolder holder, int position) {
        holder.orderItemBinding.setOrderItem(getItem(position));
        holder.orderItemBinding.executePendingBindings();
    }

    //뷰홀더 클래스
    public class OderViewHolder extends RecyclerView.ViewHolder{
        OrderItemBinding orderItemBinding;

        //뷰홀더 생성자
        public OderViewHolder(@NonNull OrderItemBinding orderItemBinding) {
            super(orderItemBinding.getRoot());
            this.orderItemBinding = orderItemBinding;
        }
    }

    //인터페이스
    public interface OrderInterface{
        int calculateTotalPrice();
    }

}
