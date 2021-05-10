package com.professionalandroid.apps.shopping.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.professionalandroid.apps.shopping.databinding.ShopItemBinding;
import com.professionalandroid.apps.shopping.models.Product;

public class ShopListAdapter extends ListAdapter<Product, ShopListAdapter.ShopViewHolder> {

    ShopInterface shopInterface;

    //어댑터 생성자
    public ShopListAdapter(ShopInterface shopInterface) {
        super(Product.itemCallBack);
        this.shopInterface = shopInterface;
    }

    //뷰홀더 객체가 생성되면
    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ShopItemBinding shopItemBinding = ShopItemBinding.inflate(layoutInflater, parent, false);
        shopItemBinding.setShopInterface(shopInterface);
        return new ShopViewHolder(shopItemBinding); //뷰홀더 객체 생성
    }

    //뷰홀더 객체가 바인딩되면
    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Product product = getItem(position);

        //입력된 뷰홀더에 인풋된 스케쥴 아이템을 세팅
        holder.shopItemBinding.setProduct(product);
        //
        holder.shopItemBinding.executePendingBindings();

    }

    //뷰홀더 클래스
    class ShopViewHolder extends RecyclerView.ViewHolder {

        ShopItemBinding shopItemBinding;

        //뷰홀더 생성자
        public ShopViewHolder(ShopItemBinding binding) {
            super(binding.getRoot());
            shopItemBinding = binding;
        }


    }

    //인터페이스
    public interface ShopInterface{
        void addItem(Product product);
        void onItemClick(Product product);
        void directBuy(Product product);
    }
}
