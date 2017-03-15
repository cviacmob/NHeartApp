package com.cviac.nheart.nheartapp.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.NheartApp;
import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.adapters.CartItemAdapter;
import com.cviac.nheart.nheartapp.adapters.ContinueGridAdapter;
import com.cviac.nheart.nheartapp.adapters.PaymentItemAdapter;
import com.cviac.nheart.nheartapp.datamodel.Addressinfo;
import com.cviac.nheart.nheartapp.datamodel.CartTotalInfo;
import com.cviac.nheart.nheartapp.datamodel.GeneralResponse;
import com.cviac.nheart.nheartapp.datamodel.GetCartItemsResponse;
import com.cviac.nheart.nheartapp.datamodel.PaymentMethodsInfo;
import com.cviac.nheart.nheartapp.datamodel.PaymentMethodsResponse;
import com.cviac.nheart.nheartapp.datamodel.ProductCartInfo;
import com.cviac.nheart.nheartapp.datamodel.ShippingMethodsResponse;
import com.cviac.nheart.nheartapp.restapi.AddCookiesInterceptor;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;
import com.cviac.nheart.nheartapp.restapi.ReceivedCookiesInterceptor;
import com.cviac.nheart.nheartapp.utilities.NonScrollableListview;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.InjectView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ContinueActivity extends AppCompatActivity {
    TextView tv1, tv2, tv3,tv4,tv5,tv6,tv7, total, pay;
    ListView lv;
    GridView gv;
    ImageButton b1;
    RadioGroup rg;
    RadioButton rb, rb1, rb2, rb3;
    List<ProductCartInfo> cartProducts;
    List<CartTotalInfo> cartTotals;
    List<Addressinfo> addrlist;
    PaymentItemAdapter adapter;
    ContinueGridAdapter adpt;
    String paymethod = "";
    String shipmethod = "flat";
    Addressinfo pay_addr;
    Addressinfo ship_addr;
    String address, add1, add2;
    ProgressDialog progressDialog = null;
    AlertDialog levelDialog = null;
List<PaymentMethodsInfo> pay_mthd;
    @InjectView(R.id.paylist)
    NonScrollableListview nonScrollListView;

    //    String[] web = {
//            "Credit Card",
//            "Debit Card",
//            "Net Banking",
//            "Emi",
//            "Gift Voucher",
//            "Cash on Delivery",
//    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.continue_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadCartItems();
        loadAddresses();

        cartProducts = new ArrayList<>();
        adapter = new PaymentItemAdapter(this, R.layout.activity_paymanetadapter, cartProducts);
        nonScrollListView = (NonScrollableListview) findViewById(R.id.paylist);
        nonScrollListView.setAdapter(adapter);

//        rb = (RadioButton) findViewById(R.id.Credit);
//        rb1 = (RadioButton) findViewById(R.id.Paytm);
//        rb2 = (RadioButton) findViewById(R.id.Debit);
//        rb3 = (RadioButton) findViewById(R.id.cash);
        total = (TextView) findViewById(R.id.amount);
        pay = (TextView) findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOut();
            }
        });
        b1 = (ImageButton) findViewById(R.id.change);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ContinueActivity.this);
                builder.setTitle("Select Address");
                ArrayAdapter adapter1 = new ArrayAdapter(ContinueActivity.this, android.R.layout.select_dialog_singlechoice, addrlist);
                builder.setSingleChoiceItems(adapter1, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        levelDialog.dismiss();
                        if (addrlist != null && addrlist.size() > 0) {
                            pay_addr = addrlist.get(item);
                            ship_addr = addrlist.get(item);

                            setAddress(addrlist.get(item));
                        }
                    }
                });
                levelDialog = builder.create();
                levelDialog.show();
            }
        });

        tv1 = (TextView) findViewById(R.id.afirstname);
        tv2 = (TextView) findViewById(R.id.aaddress);
        tv3 = (TextView) findViewById(R.id.aadreess1);
        tv4 = (TextView) findViewById(R.id.Acity);
        tv5 = (TextView) findViewById(R.id.APincode);
        tv6 = (TextView) findViewById(R.id.Astate);
        tv7 = (TextView) findViewById(R.id.ACountry);
        address = Prefs.getString("Name", "");
        add1 = Prefs.getString("Email", "");
        add2 = Prefs.getString("Phone", "");
        tv1.setText(address);
        tv2.setText(add1);
        tv3.setText(add2);

    }

    public void loadCartItems() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        String token = Prefs.getString("token", null);
        Call<GetCartItemsResponse> call = api.getCartItems();
        call.enqueue(new Callback<GetCartItemsResponse>() {

            public void onResponse(Response<GetCartItemsResponse> response, Retrofit retrofit) {
                GetCartItemsResponse rsp = response.body();
                cartProducts.clear();
                cartProducts.addAll(rsp.getProds());
                adapter.notifyDataSetInvalidated();
                cartTotals = rsp.getTotals();
                total.setText(cartTotals.get(cartTotals.size() - 1).getText());

            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void loadPaymentMethods() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        int c_id = Prefs.getInt("customer_id", -1);
        Call<PaymentMethodsResponse> call = api.getPaymentMethods();
        call.enqueue(new Callback<PaymentMethodsResponse>() {

            public void onResponse(Response<PaymentMethodsResponse> response, Retrofit retrofit) {
                PaymentMethodsResponse rsp = response.body();
                pay_mthd = rsp.getPayment_methods();
                if (pay_mthd != null && pay_mthd.size() > 0) {
                    addRadioButtons(pay_mthd);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void addRadioButtons(final List<PaymentMethodsInfo> methods) {

        for (int row = 0; row < 1; row++) {
            RadioGroup group = new RadioGroup(this);
            group.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 0; i < methods.size(); i++) {
                PaymentMethodsInfo info = methods.get(i);
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId(i);
                rdbtn.setTag(info);
                rdbtn.setText(info.getTitle());
                group.addView(rdbtn);
            }
            ((ViewGroup) findViewById(R.id.radio)).addView(group);
            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    PaymentMethodsInfo info = methods.get(checkedId);
                    paymethod = info.getCode();
                }
            });
        }
    }

    private boolean validateAddress(Addressinfo info) {
        if (info.getFirstname().isEmpty()) {
            return false;
        }
        if (info.getAddress_1().isEmpty()) {
            return false;
        }
        if (info.getAddress_2().isEmpty()) {
            return false;
        }
        if (info.getCity().isEmpty()) {
            return false;
        }
        if (info.getPostcode().isEmpty()) {
            return false;
        }
        if (info.getZone_id().isEmpty()) {
            return false;
        }
        if (info.getCountry_id().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateCheckout() {
        if (paymethod.isEmpty()) {
            Toast.makeText(ContinueActivity.this, "Select a Payment Method", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (validateAddress(pay_addr) == false) {
            Toast.makeText(ContinueActivity.this, "Fill Payment Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (validateAddress(ship_addr) == false) {
            Toast.makeText(ContinueActivity.this, "Fill Shipping Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void checkOut() {
        if (validateCheckout() == true) {
            setCustmoerSession();
        }
    }

    private void setCustmoerSession() {

        String cus_id = Prefs.getInt("customerid", -1) + "";
        String firstname = Prefs.getString("name", "");
        String email = Prefs.getString("email", "");
        String telephone = Prefs.getString("mobile", "");

        progressDialog = new ProgressDialog(ContinueActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        Call<GeneralResponse> call = api.setCustomerSession(cus_id, "1", firstname, firstname, email, telephone, "");
        call.enqueue(new Callback<GeneralResponse>() {

            public void onResponse(Response<GeneralResponse> response, Retrofit retrofit) {
                GeneralResponse rsp = response.body();
                if (rsp.getSuccess() != null && !rsp.getSuccess().isEmpty()) {
                    setPaymentAddress();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ContinueActivity.this, "Place Order - Customer Session Error" + rsp.getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ContinueActivity.this, "Place Order Error - Set Customer Session", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

    }

    private void setPaymentAddress() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        Call<GeneralResponse> call = api.setPaymentAddress(pay_addr.getFirstname(), pay_addr.getLastname(),
                pay_addr.getCompany(), pay_addr.getAddress_1(),
                pay_addr.getAddress_2(), pay_addr.getPostcode(),
                pay_addr.getCity(), pay_addr.getZone_id(), pay_addr.getCountry_id());
        call.enqueue(new Callback<GeneralResponse>() {

            public void onResponse(Response<GeneralResponse> response, Retrofit retrofit) {
                GeneralResponse rsp = response.body();
                if (rsp.getSuccess() != null && !rsp.getSuccess().isEmpty()) {
                    getPaymentMethods();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ContinueActivity.this, "Place Order - Payment Address Error" + rsp.getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ContinueActivity.this, "Place Order Error - Set Payment Address", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void getPaymentMethods() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        int c_id = Prefs.getInt("customer_id", -1);
        Call<PaymentMethodsResponse> call = api.getPaymentMethods();
        call.enqueue(new Callback<PaymentMethodsResponse>() {

            public void onResponse(Response<PaymentMethodsResponse> response, Retrofit retrofit) {
                PaymentMethodsResponse rsp = response.body();
                setPaymentMethod();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(ContinueActivity.this, "Place Order - Get Payment Methods Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setPaymentMethod() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        Call<GeneralResponse> call = api.setPaymentMethod(paymethod);
        call.enqueue(new Callback<GeneralResponse>() {

            public void onResponse(Response<GeneralResponse> response, Retrofit retrofit) {
                GeneralResponse rsp = response.body();
                if (rsp.getSuccess() != null && !rsp.getSuccess().isEmpty()) {
                    setShippingAddress();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ContinueActivity.this, "Place Order - Payment Method Error:" + rsp.getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ContinueActivity.this, "Place Order Error - Set Payment Method", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void setShippingAddress() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        Call<GeneralResponse> call = api.setShippingAddress(ship_addr.getFirstname(), ship_addr.getLastname(),
                ship_addr.getCompany(), ship_addr.getAddress_1(),
                ship_addr.getAddress_2(), ship_addr.getPostcode(),
                ship_addr.getCity(), ship_addr.getZone_id(), ship_addr.getCountry_id());
        call.enqueue(new Callback<GeneralResponse>() {

            public void onResponse(Response<GeneralResponse> response, Retrofit retrofit) {
                GeneralResponse rsp = response.body();
                if (rsp.getSuccess() != null && !rsp.getSuccess().isEmpty()) {
                    getShippingMethods();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ContinueActivity.this, "Place Order - Shipping Address Error" + rsp.getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ContinueActivity.this, "Place Order Error - Set Shipping Address", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void getShippingMethods() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        int c_id = Prefs.getInt("customer_id", -1);
        Call<ShippingMethodsResponse> call = api.getShippingMethods();
        call.enqueue(new Callback<ShippingMethodsResponse>() {

            public void onResponse(Response<ShippingMethodsResponse> response, Retrofit retrofit) {
                ShippingMethodsResponse rsp = response.body();
                setShippingMethod();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setShippingMethod() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        Call<GeneralResponse> call = api.setShippingMethod(shipmethod);
        call.enqueue(new Callback<GeneralResponse>() {

            public void onResponse(Response<GeneralResponse> response, Retrofit retrofit) {
                GeneralResponse rsp = response.body();
                if (rsp.getSuccess() != null && !rsp.getSuccess().isEmpty()) {
                    placeOrder();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ContinueActivity.this, "Place Order - Shipping Method Error" + rsp.getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ContinueActivity.this, "Place Order Error - Set Shipping Method", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void placeOrder() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        Call<ResponseBody> call = api.placeOrder(paymethod, "flat.flat", "test", "1", "1");
        call.enqueue(new Callback<ResponseBody>() {

            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                progressDialog.dismiss();
                ResponseBody rsp = response.body();

                NheartApp app = (NheartApp)  getApplication();
                app.notifyCartChange("order");

                Toast.makeText(ContinueActivity.this, "Placed Order Successfully", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ContinueActivity.this, "Place Order Failure", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    public void loadAddresses() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        int c_id = Prefs.getInt("customer_id", -1);
        Call<List<Addressinfo>> call = api.getAdresses(c_id + "");
        call.enqueue(new Callback<List<Addressinfo>>() {

            public void onResponse(Response<List<Addressinfo>> response, Retrofit retrofit) {
                List<Addressinfo> rsp = response.body();
                addrlist = rsp;
                if (addrlist != null && addrlist.size() > 0) {
                    Addressinfo info = addrlist.get(0);
                    setAddress(info);
                    pay_addr = info;
                    ship_addr = info;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setAddress(Addressinfo info) {

        tv1.setText(info.getFirstname());
        tv2.setText(info.getAddress_1());
        tv3.setText(info.getAddress_2());
        tv4.setText(info.getCity());
        tv5.setText(info.getPostcode());
        tv6.setText(info.getZone());
        tv7.setText(info.getCountry());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
