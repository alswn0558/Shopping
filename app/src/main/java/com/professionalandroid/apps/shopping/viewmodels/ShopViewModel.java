package com.professionalandroid.apps.shopping.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.professionalandroid.apps.shopping.models.CartItem;
import com.professionalandroid.apps.shopping.models.Product;
import com.professionalandroid.apps.shopping.repositories.CartRepo;
import com.professionalandroid.apps.shopping.repositories.ShopRepo;

import java.util.ArrayList;
import java.util.List;

public class ShopViewModel extends ViewModel {


    ShopRepo shopRepo = new ShopRepo();
    CartRepo cartRepo = new CartRepo();

    List<CartItem> OrderList = new ArrayList<>();


    MutableLiveData<Product> productMutableLiveData = new MutableLiveData<>();

    public LiveData<List<Product>> getProducts(){
        return shopRepo.getProducts();
    }

    public void setProduct(Product product){
        productMutableLiveData.setValue(product);
    }

    public LiveData<Product> getProduct(){
        return productMutableLiveData;
    }

    public LiveData<List<CartItem>> getCart(){
        return cartRepo.getCart();
    }

    public boolean addProductToCart(Product product){
        return cartRepo.addItemToCart(product);
    }

    public void removeItemFromCart(CartItem cartItem){
        cartRepo.removeItemFromCart(cartItem);
    }


    public void addOrderList(CartItem cartItem) {
        OrderList.add(cartItem);
    }
    public void removeOrderItem(CartItem cartItem){
        OrderList.remove(cartItem);
    }

    public List<CartItem> getOrderList() {
        return OrderList;
    }

    public void setOrderList(List<CartItem> orderList) {
        OrderList = orderList;
    }

}
