package com.cviac.nheart.nheartapp.restapi;

import com.cviac.nheart.nheartapp.datamodel.AddToCartResponse;
import com.cviac.nheart.nheartapp.datamodel.CategoriesResponse;
import com.cviac.nheart.nheartapp.datamodel.CategoryProductsResponse;
import com.cviac.nheart.nheartapp.datamodel.GetCartItemsResponse;
import com.cviac.nheart.nheartapp.datamodel.LoginResponse;
import com.cviac.nheart.nheartapp.datamodel.Productdetailresponse;
import com.cviac.nheart.nheartapp.datamodel.ReginfoResponse;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;


public interface OpenCartAPI {

    @FormUrlEncoded
    @POST("/index.php?route=api/account/register")
    Call<ReginfoResponse> register(@Field("firstname") String firstname,
                                   @Field("lastname") String lastname,
                                   @Field("email")String email,
                                   @Field("telephone") String telephone,
                                   @Field("password") String password,
                                   @Field("confirm") String confirm);


    @GET("/index.php?route=api/category")
    Call<CategoriesResponse> getCategories();

    @GET("/index.php?route=api/category/getproducts")
    Call<CategoryProductsResponse> getProducts(@Query("categoryid") String categoryid);


    @GET("/index.php?route=api/category/getproductdetails")
    Call<Productdetailresponse> getProductdetails(@Query("productid") String categoryid);

    @FormUrlEncoded
    @POST("/index.php?route=api/login")
    Call<LoginResponse> login(@Field("key") String apikey);

    @FormUrlEncoded
    @POST("/index.php?route=api/cart/add")
    Call<AddToCartResponse> addToCart(@Query("token") String token,
                                      @Field("product_id") String prodid,
                                      @Field("quantity") String quantity
                                  );

    @GET("/index.php?route=api/cart/products")
    Call<GetCartItemsResponse> getCartItems(@Query("token") String token);


}
