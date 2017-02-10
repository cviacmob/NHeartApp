package com.cviac.nheart.nheartapp.restapi;

import com.cviac.nheart.nheartapp.activities.InvitationReceived;
import com.cviac.nheart.nheartapp.datamodel.AddToCartResponse;
import com.cviac.nheart.nheartapp.datamodel.CategoriesResponse;
import com.cviac.nheart.nheartapp.datamodel.CategoryProductsResponse;
import com.cviac.nheart.nheartapp.datamodel.GetCartItemsResponse;
import com.cviac.nheart.nheartapp.datamodel.LoginResponse;
import com.cviac.nheart.nheartapp.datamodel.PairStatus;
import com.cviac.nheart.nheartapp.datamodel.Productdetailresponse;
import com.cviac.nheart.nheartapp.datamodel.ReginfoResponse;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
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

    @POST("/N-Heart/invite.php/verifyotp")
    Call<VerifyOTPResponse> verifyOTP(@Body OTPInfo info);

    @POST("/N-Heart/invite.php/insertotp")
    Call<VerifyOTPResponse> resendOTP(@Body OTPInfo mob);




    @GET("/index.php?route=api/category")
    Call<CategoriesResponse> getCategories();



    @GET("/index.php?route=api/category/getproducts")
    Call<CategoryProductsResponse> getProducts(@Query("categoryid") String categoryid);


    @GET("/index.php?route=api/category/getproductdetails")
    Call<Productdetailresponse>
     getProductdetails(@Query("productid") String categoryid);

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

    @FormUrlEncoded
    @POST("/N-Heart/invite.php/sendInvite")
    Call<PairStatus> sendInvitation(@Field("name") String name,
                                         @Field("email")String email,
                                         @Field("mobile") String mobile,
                                         @Field("to_mobile") String to_mobile);


    @POST("/N-Heart/invite.php/resendinvite")
    Call<PairStatus> verifyInvitation(@Body int id);



    @GET("/N-Heart/invite.php/checkInvite/{id}")
    Call<PairStatus> checkInvitation (@Path("id")int id);


    @GET("/N-Heart/invite.php/getInvite/{mobile}")
    Call<List<Invitation>> getInvitation (@Path("mobile")String mobile);

    @POST("/N-Heart/invite.php/updateInvite")
    Call<PairStatus> updateInvite(@Body UpdateInvitation update);


}
