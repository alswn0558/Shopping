package com.professionalandroid.apps.shopping.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.professionalandroid.apps.shopping.databinding.CartItemBinding;
import com.professionalandroid.apps.shopping.models.CartItem;

public class CartListAdapter extends ListAdapter<CartItem, CartListAdapter.CartViewHolder> {

    private CartInterface cartInterface;//구현해야하는 인터페이스

    //어댑터 생성자
   public CartListAdapter(CartInterface cartInterface) {
        super(CartItem.itemCallback);
        this.cartInterface = cartInterface;
    }

    //뷰홀더 객체가 생성되면
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CartItemBinding cartItemBinding = CartItemBinding.inflate(layoutInflater, parent, false); //카트 아이템(낱개로 하나의 뷰) 바운딩

        return new CartViewHolder(cartItemBinding); //뷰홀더 객체 생성해서 리턴
    }

    //뷰홀더 객체가 바인딩 되면
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.cartItemBinding.setCartItem(getItem(position));
        holder.cartItemBinding.executePendingBindings();
    }

    //뷰홀더 클래스
    public class CartViewHolder extends RecyclerView.ViewHolder{
        CartItemBinding cartItemBinding;

        //뷰홀더 객체 생성자
        public CartViewHolder(@NonNull CartItemBinding cartItemBinding) {//뷰를 하나를 입력인자로 넣으면
            super(cartItemBinding.getRoot());
            this.cartItemBinding = cartItemBinding; //입력한 뷰를 이 객체의 뷰로 덮어씌워

            cartItemBinding.cartItemDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartInterface.deleteItem(getItem(getAdapterPosition()));
                }
            });

            cartItemBinding.cartItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                        cartInterface.addOrderList(getItem(getAdapterPosition()));
                    else
                        cartInterface.removeOrderItem(getItem(getAdapterPosition()));
                }
            });
        }
    }

    //어댑터 객체 생성을 위해 무조건 구현해야하는 인터페이스
    public interface CartInterface{
        void deleteItem(CartItem cartItem);
        void addOrderList(CartItem cartItem);
        void removeOrderItem(CartItem cartItem);
    }

}
