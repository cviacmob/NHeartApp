package com.cviac.nheart.nheartapp.adapters;

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
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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

    private List<ProductCartInfo> list=new ArrayList<>();

    private int layoutid;
    private static Integer deletecounter= 0;

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
            final TextView tv2 = (TextView)ins.findViewById(R.id.items);
            holder.tv2 = tv2;
            holder.price = (TextView) ins.findViewById(R.id.price);
            ImageButton txtplus  = (ImageButton) ins.findViewById(R.id.add);
            txtplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("src", "Increasing value...");
                    counter++;
                    stringVal = Integer.toString(counter);
                    tv2.setText(stringVal);
                    update(sinfo.getProduct_id(), "1");
                }
            });
            ImageButton txtminus = (ImageButton) ins.findViewById(R.id.remove);
            txtminus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("src", "Decreasing value...");
                    counter--;
                    stringVal = Integer.toString(counter);
                    tv2.setText(stringVal);
                    if (counter < 0)
                    {
                        tv2.setText("0");
                    }
                    update(sinfo.getProduct_id(), "1");
                }
            });
            ImageButton dele=(ImageButton)ins.findViewById(R.id.delete);
            dele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Remove(token,sinfo.getProduct_id());
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
        url = url.replace("localhost","192.168.1.133");
        try {
            Picasso.with(ins.getContext()).load(url).resize(70,75).into( holder.iv);
//            holder.imgView.setImageURI(Uri.parse(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // holder.iv.setImageResource(sinfo.g);

        return ins;
    }
    private void Remove( String token ,String prodId) {

        if (token != null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.133")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            OpenCartAPI api = retrofit.create(OpenCartAPI.class);

            final Call<removefromCartResponse> call = api.remove(token,prodId);
            call.enqueue(new Callback<removefromCartResponse>() {
                @Override
                public void onResponse(Response<removefromCartResponse> response, Retrofit retrofit) {
                    removefromCartResponse rsp = response.body();
                    // getAndSetCartCount();
                    Toast.makeText(mContext, "Removed Item from Cart", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(mContext, "Server Error", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    private void update(String prodId, String quantity) {
        String token = Prefs.getString("token", null);
        if (token != null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.133")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            OpenCartAPI api = retrofit.create(OpenCartAPI.class);

            final Call<updatecartresponse> call = api.update(token, prodId, quantity);
            call.enqueue(new Callback<updatecartresponse>() {
                @Override
                public void onResponse(Response<updatecartresponse> response, Retrofit retrofit) {
                    updatecartresponse rsp = response.body();
                    Toast.makeText(mContext, "modified cart successfully", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(mContext, "Server Error", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
