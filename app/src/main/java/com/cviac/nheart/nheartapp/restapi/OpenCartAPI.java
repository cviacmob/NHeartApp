package com.cviac.nheart.nheartapp.restapi;

import com.cviac.nheart.nheartapp.activities.InvitationReceived;
import com.cviac.nheart.nheartapp.datamodel.AddToCartResponse;
import com.cviac.nheart.nheartapp.datamodel.Addressinfo;
import com.cviac.nheart.nheartapp.datamodel.CategoriesResponse;
import com.cviac.nheart.nheartapp.datamodel.CategoryProductsResponse;
import com.cviac.nheart.nheartapp.datamodel.CountryInfo;
import com.cviac.nheart.nheartapp.datamodel.GeneralResponse;
import com.cviac.nheart.nheartapp.datamodel.GetCartItemsResponse;
import com.cviac.nheart.nheartapp.datamodel.LoginResponse;
import com.cviac.nheart.nheartapp.datamodel.PairStatus;
import com.cviac.nheart.nheartapp.datamodel.PaymentMethodsResponse;
import com.cviac.nheart.nheartapp.datamodel.Productdetailresponse;
import com.cviac.nheart.nheartapp.datamodel.ReginfoResponse;
import com.cviac.nheart.nheartapp.datamodel.ShippingMethodsResponse;
import com.cviac.nheart.nheartapp.datamodel.ZoneInfo;
import com.cviac.nheart.nheartapp.datamodel.removefromCartResponse;
import com.cviac.nheart.nheartapp.datamodel.updatecartresponse;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface OpenCartAPI {

    @FormUrlEncoded
    @POST("/index.php?route=api/account/register")
    Call<ReginfoResponse> register(@Field("firstname") String firstname,
                                   @Field("lastname") String lastname,
                                   @Field("email") String email,
                                   @Field("telephone") String telephone,
                                   @Field("password") String password,
                                   @Field("confirm") String confirm);

    @POST("/N-Heart/invite.php/verifyotp")
    Call<VerifyOTPResponse> verifyOTP(@Body OTPInfo info);

    @POST("/N-Heart/invite.php/insertotp")
    Call<VerifyOTPResponse> resendOTP(@Body OTPInfo mob);

    @GET("/index.php?route=api/category")
    Call<CategoriesResponse> getCategories();

    @GET("/index.php?route=api/search")
    Call<CategoryProductsResponse> search(@Query("search") String search);

    @GET("/index.php?route=api/category/getproducts")
    Call<CategoryProductsResponse> getProducts(@Query("categoryid") String categoryid);

    @GET("/index.php?route=api/search")
    Call<CategoryProductsResponse> search(@Query("search") String search);

    @GET("/index.php?route=api/category/getproductdetails")
    Call<Productdetailresponse> getProductdetails(@Query("productid") String productid);

    @FormUrlEncoded
    @POST("/index.php?route=api/account/login")
    Call<LoginResponse> login(@Field("email") String email,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("/index.php?route=api/login")
    Call<LoginResponse> login(@Field("key") String apikey);

    @FormUrlEncoded
    @POST("/index.php?route=api/cart/add")
    Call<AddToCartResponse> addToCart(@Field("product_id") String prodid,
                                      @Field("quantity") String quantity
    );

    @GET("/index.php?route=api/cart/products")
    Call<GetCartItemsResponse> getCartItems();

    @FormUrlEncoded
    @POST("/index.php?route=api/cart/remove")
    Call<removefromCartResponse> remove(@Field("key") String cartid);
    // @Field("quantity") String quantity


    //
//    @FormUrlEncoded
//    @POST("/index.php?route=api/cart/edit")
//    Call<updatecartresponse> update(@Query("token") String token,
//                                    @Field("key") String prodid,
//                                    @Field("quantity") String quantity
//    );
    @FormUrlEncoded
    @POST("/N-Heart/invite.php/sendInvite")
    Call<PairStatus> sendInvitation(@Field("name") String name,
                                    @Field("email") String email,
                                    @Field("mobile") String mobile,
                                    @Field("to_mobile") String to_mobile,
                                    @Field("pushid") String pushid);

    @FormUrlEncoded
    @POST("/N-Heart/invite.php/resendinvite")
    Call<PairStatus> verifyInvitation(@Field("id") int id);

    @GET("/N-Heart/invite.php/checkInvite/{id}")
    Call<PairStatus> checkInvitation(@Path("id") int id);

    @GET("/N-Heart/invite.php/getInvite/{mobile}")
    Call<List<Invitation>> getInvitation(@Path("mobile") String mobile);

    @POST("/N-Heart/invite.php/updateInvite")
    Call<PairStatus> updateInvite(@Body UpdateInvitation update);

    @FormUrlEncoded
    @POST("/index.php?route=api/address/add")
    Call<GeneralResponse> addAddress(@Field("customer_id") String customer_id,
                                     @Field("firstname") String firstname,
                                     @Field("lastname") String lastname,
                                     @Field("company") String company,
                                     @Field("address_1") String address_1,
                                     @Field("address_2") String address_2,
                                     @Field("postcode") String postcode,
                                     @Field("city") String city,
                                     @Field("zone_id") String zonecode,
                                     @Field("country_id") String country_id);

    @FormUrlEncoded
    @POST("/index.php?route=api/address/edit")
    Call<GeneralResponse> editAddress(@Field("address_id") String address_id,
                                      @Field("customer_id") String customer_id,
                                      @Field("firstname") String firstname,
                                      @Field("lastname") String lastname,
                                      @Field("company") String company,
                                      @Field("address_1") String address_1,
                                      @Field("address_2") String address_2,
                                      @Field("postcode") String postcode,
                                      @Field("city") String city,
                                      @Field("zone_id") String zone_id,
                                      @Field("country_id") String country_id);

    @GET("/index.php?route=api/address/getList")
    Call<List<Addressinfo>> getAdresses(@Query("customer_id") String customer_id);

    @GET("/index.php?route=api/address/delete")
    Call<GeneralResponse> deleteAddress(@Query("address_id") String address_id,
                                        @Query("customer_id") String customer_id);

    @GET("/index.php?route=api/address/getCountries")
    Call<List<CountryInfo>> getCountries();

    @GET("/index.php?route=api/address/getZones")
    Call<List<ZoneInfo>> getZones(@Query("country_id") String country_id);


    @POST("/fcm/send")
    Call<FCMSendMessageResponse> sendPushMessage(@Header("Authorization") String key, @Body PushMessageInfo info);


    @GET("/index.php?route=api/payment/methods")
    Call<PaymentMethodsResponse> getPaymentMethods();

    @GET("/index.php?route=api/shipping/methods")
    Call<ShippingMethodsResponse> getShippingMethods();

    // Order Placement APIs
    @FormUrlEncoded
    @POST("/index.php?route=api/customer")
    Call<GeneralResponse> setCustomerSession(@Field("customer_id") String customer_id,
                                             @Field("customer_group_id") String customer_group_id,
                                             @Field("firstname") String firstname,
                                             @Field("lastname") String lastname,
                                             @Field("email") String email,
                                             @Field("telephone") String telephone,
                                             @Field("fax") String fax);

    @FormUrlEncoded
    @POST("/index.php?route=api/payment/address")
    Call<GeneralResponse> setPaymentAddress(@Field("firstname") String firstname,
                                            @Field("lastname") String lastname,
                                            @Field("company") String company,
                                            @Field("address_1") String address_1,
                                            @Field("address_2") String address_2,
                                            @Field("postcode") String postcode,
                                            @Field("city") String city,
                                            @Field("zone_id") String zone_id,
                                            @Field("country_id") String country_id);

    @FormUrlEncoded
    @POST("/index.php?route=api/payment/method")
    Call<GeneralResponse> setPaymentMethod(@Field("payment_method") String payment_method);

    @FormUrlEncoded
    @POST("/index.php?route=api/shipping/address")
    Call<GeneralResponse> setShippingAddress(@Field("firstname") String firstname,
                                             @Field("lastname") String lastname,
                                             @Field("company") String company,
                                             @Field("address_1") String address_1,
                                             @Field("address_2") String address_2,
                                             @Field("postcode") String postcode,
                                             @Field("city") String city,
                                             @Field("zone_id") String zone_id,
                                             @Field("country_id") String country_id);

    @FormUrlEncoded
    @POST("/index.php?route=api/shipping/method")
    Call<GeneralResponse> setShippingMethod(@Field("shipping_method") String shipping_method);

    @FormUrlEncoded
    @POST("/index.php?route=api/order/add")
    Call<ResponseBody> placeOrder(@Field("payment_method") String payment_method,
                                  @Field("shipping_method") String shipping_method,
                                  @Field("comment") String comment,
                                  @Field("affiliate_id") String affiliate_id,
                                  @Field("order_status_id") String order_status_id);
}
