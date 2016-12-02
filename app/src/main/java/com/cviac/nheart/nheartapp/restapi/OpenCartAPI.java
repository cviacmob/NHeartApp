package com.cviac.nheart.nheartapp.restapi;

import com.cviac.nheart.nheartapp.datamodel.CategoriesResponse;
import com.cviac.nheart.nheartapp.datamodel.CategoryProductsResponse;

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



}
