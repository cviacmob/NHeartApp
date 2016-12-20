package com.cviac.nheart.nheartapp.restapi;

import com.cviac.nheart.nheartapp.datamodel.CategoriesResponse;
import com.cviac.nheart.nheartapp.datamodel.CategoryProductsResponse;
import com.cviac.nheart.nheartapp.datamodel.GetCartItemsResponse;
import com.cviac.nheart.nheartapp.datamodel.Productdetailresponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by admin1 on 11/24/2016.
 */

public interface OpenCartAPI {

    @GET("http://nheart.cviac.com/index.php?route=api/category")
    Call<CategoriesResponse> getCategories();

    @GET("http://nheart.cviac.com/index.php?route=api/category/getproducts")
    Call<CategoryProductsResponse> getProducts(@Query("categoryid") String categoryid);


    @GET("http://nheart.cviac.com/index.php?route=api/category/getproductdetails")
    Call<Productdetailresponse> getProductdetails(@Query("productid") String categoryid);

    @GET("http://nheart.cviac.com/index.php?route=api/cart/products")
    Call<GetCartItemsResponse> getCartItems();


}
