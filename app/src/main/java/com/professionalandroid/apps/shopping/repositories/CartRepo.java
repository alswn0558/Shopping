package com.professionalandroid.apps.shopping.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.professionalandroid.apps.shopping.models.CartItem;
import com.professionalandroid.apps.shopping.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartRepo {

    private MutableLiveData<List<CartItem>> mutableCartItems = new MutableLiveData<>();


    public LiveData<List<CartItem>> getCart(){
        if(mutableCartItems.getValue() == null){
            initCart();
        }
        return mutableCartItems;
    }

    private void initCart() {
        mutableCartItems.setValue(new ArrayList<CartItem>());
    }

    public boolean addItemToCart(Product product){
        if(mutableCartItems.getValue() == null){
            initCart();
        }

        List<CartItem> cartItemList = new ArrayList<>(mutableCartItems.getValue());
        for(CartItem item :  cartItemList){
            if(item.getProduct().getId().equals(product.getId())){

                if(item.getQuantity() == 5)
                    return false;

                int idx = cartItemList.indexOf(item);
                item.setQuantity(item.getQuantity() + 1);
                cartItemList.set(idx, item);

                mutableCartItems.setValue(cartItemList);

                return true;
            }
        }

        CartItem cartItem = new CartItem(product, 1);
        cartItemList.add(cartItem);
        mutableCartItems.setValue(cartItemList);
        return true;
    }

    public void removeItemFromCart(CartItem cartItem){
        if (mutableCartItems.getValue() == null){
            return;
        }
        List<CartItem> cartItemList = new ArrayList<>(mutableCartItems.getValue());
        cartItemList.remove(cartItem);
        mutableCartItems.setValue(cartItemList);
    }

}
