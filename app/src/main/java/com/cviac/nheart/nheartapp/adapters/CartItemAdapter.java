package com.cviac.nheart.nheartapp.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cviac.nheart.nheartapp.NheartApp;
import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.activities.CartItemListActivity;
import com.cviac.nheart.nheartapp.activities.ContinueActivity;
import com.cviac.nheart.nheartapp.activities.ProductdetailsActivity;
import com.cviac.nheart.nheartapp.datamodel.AddToCartResponse;
import com.cviac.nheart.nheartapp.datamodel.CartItemInfo;
import com.cviac.nheart.nheartapp.datamodel.ProductCartInfo;
import com.cviac.nheart.nheartapp.datamodel.removefromCartResponse;
import com.cviac.nheart.nheartapp.datamodel.updatecartresponse;
import com.cviac.nheart.nheartapp.restapi.AddCookiesInterceptor;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;
import com.cviac.nheart.nheartapp.restapi.ReceivedCookiesInterceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class CartItemAdapter extends BaseAdapter {
    int count;
    String token = Prefs.getString("token", null);
    private static int counter = 0;
    private String stringVal;
    private Context mContext;
    ProgressDialog progressDialog = null;

    private List<ProductCartInfo> list = new ArrayList<>();

    private int layoutid;
    private static Integer deletecounter = 0;

    public CartItemAdapter(Context c, int layoutid, List<ProductCartInfo> list) {
        mContext = c;
        this.list = list;
        this.layoutid = layoutid;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView tv, tv2, price, price1;
        public ImageView iv, iv2;

        //public RatingBar rating;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View ins = convertView;
        ViewHolder holder;
        final ProductCartInfo sinfo = list.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ins = inflater.inflate(layoutid, null);
            holder = new ViewHolder();
            holder.tv = (TextView) ins.findViewById(R.id.name);
            holder.iv = (ImageView) ins.findViewById(R.id.ima1);
            final TextView tv2 = (TextView) ins.findViewById(R.id.items);
            holder.tv2 = tv2;
            holder.price = (TextView) ins.findViewById(R.id.price);
            ImageButton txtplus = (ImageButton) ins.findViewById(R.id.add);
            txtplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart(sinfo, 1);
                }
            });
            ImageButton txtminus = (ImageButton) ins.findViewById(R.id.remove);
            txtminus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sinfo.getQuantity().equals("1")) {
                        return;
                    }
                    addToCart(sinfo, -1);
                }
            });
            ImageButton dele = (ImageButton) ins.findViewById(R.id.delete);
            dele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(sinfo);
                }
            });
            ins.setTag(holder);
        } else {
            holder = (ViewHolder) ins.getTag();
        }
        holder.price.setText(sinfo.getTotal());
        holder.tv.setText(sinfo.getName());
        holder.tv2.setText(sinfo.getQuantity());
        String url = sinfo.getImage();
        url = url.replace("localhost", "192.168.1.133");
        try {
            Picasso.with(ins.getContext()).load(url).resize(70, 75).into(holder.iv);
//            holder.imgView.setImageURI(Uri.parse(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // holder.iv.setImageResource(sinfo.g);

        return ins;
    }

    private void remove(final ProductCartInfo sinfo) {
        progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Removing...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mContext.getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        final Call<removefromCartResponse> call = api.remove(sinfo.getCart_id());
        call.enqueue(new Callback<removefromCartResponse>() {
            @Override
            public void onResponse(Response<removefromCartResponse> response, Retrofit retrofit) {
                progressDialog.dismiss();
                removefromCartResponse rsp = response.body();

                NheartApp app = (NheartApp) ((CartItemListActivity) mContext).getApplication();
                app.notifyCartChange("remove");

                CartItemListActivity actv1 = (CartItemListActivity) mContext;
                actv1.loadCartItems();
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void addToCart(final ProductCartInfo sinfo, final int quantity) {
        progressDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Updating...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mContext.getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        String prod_id = sinfo.getProduct_id();

        final Call<AddToCartResponse> call = api.addToCart(prod_id, quantity + "");
        call.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Response<AddToCartResponse> response, Retrofit retrofit) {
                progressDialog.dismiss();
                AddToCartResponse rsp = response.body();
                CartItemListActivity actv = (CartItemListActivity) mContext;
                actv.loadCartItems();

            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

}
