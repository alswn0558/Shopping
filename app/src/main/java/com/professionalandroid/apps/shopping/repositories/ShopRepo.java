package com.professionalandroid.apps.shopping.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.professionalandroid.apps.shopping.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopRepo {

    private static final String TAG = "ShopRepo";
    private MutableLiveData<List<Product>> mutableProductList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public LiveData<List<Product>> getProducts(){
        if(mutableProductList == null){
            mutableProductList = new MutableLiveData<>();
            loadProducts();
        }
        return mutableProductList;
    }

    private void loadProducts() {
        //call api(room, firebase)
        List<Product> productList = new ArrayList<>();

        db.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                 Product product =document.toObject(Product.class);
                                 productList.add(product);
                                 mutableProductList.setValue(productList);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


//    private void uploadDate(){
//        List<Product> productList = new ArrayList<>();
//        // fake data
//        Product a = new Product("1",
//                "at8040-57e",
//                810000,true,
//                "gs://shopping-mall-86596.appspot.com/productImg/at8040.jpg");
//
//        Product b = new Product("2",
//                "iphone12",
//                950000,true,
//                "gs://shopping-mall-86596.appspot.com/productImg/iphone12.jpg");
//
//        Product c = new Product("3",
//                "bread",
//                4500,false,
//                "gs://shopping-mall-86596.appspot.com/productImg/bread.jpg");
//
//        Product d = new Product("4",
//                "gram 17인치",
//                1800000,true,
//                "gs://shopping-mall-86596.appspot.com/productImg/gram17.jpg");
//        Product e = new Product("5",
//                "갤럭시 note20",
//                550000,true,
//                "gs://shopping-mall-86596.appspot.com/productImg/note20.jpg");
//
//        Product f = new Product("6",
//                "축구공",
//                34000,false,
//                "gs://shopping-mall-86596.appspot.com/productImg/ball.jpg");
//        Product g = new Product("7",
//                "꼼데가르송 가디건",
//                410000,true,
//                "gs://shopping-mall-86596.appspot.com/productImg/Cardigan.jpg");
//
//
//
//
//
//        productList.add(a);
//        productList.add(b);
//        productList.add(c);
//        productList.add(d);
//        productList.add(e);
//        productList.add(f);
//        productList.add(g);
//
//
//        for(Product item :productList) {
//            db.collection("products").document().set(item);
//        }
//    }
}
