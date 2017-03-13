package com.cviac.nheart.nheartapp.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.Prefs;
import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.activities.CartItemListActivity;
import com.cviac.nheart.nheartapp.activities.ContinueActivity;
import com.cviac.nheart.nheartapp.datamodel.AddToCartResponse;
import com.cviac.nheart.nheartapp.datamodel.ProductCartInfo;
import com.cviac.nheart.nheartapp.datamodel.removefromCartResponse;
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

/**
 * Created by admin1 on 3/9/2017.
 */

public class PaymentItemAdapter extends BaseAdapter{
    int count;
    String token = Prefs.getString("token", null);
    private static int counter = 0;
    private String stringVal;
    private Context mContext;
    ProgressDialog progressDialog = null;

    private List<ProductCartInfo> list = new ArrayList<>();

    private int layoutid;
    private static Integer deletecounter = 0;

    public PaymentItemAdapter(Context c, int layoutid, List<ProductCartInfo> list) {
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
        public TextView tv, quantity, payprice, price1;
        public ImageView iv, iv2;

        //public RatingBar rating;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View ins = convertView;
        PaymentItemAdapter.ViewHolder holder;
        final ProductCartInfo sinfo = list.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ins = inflater.inflate(layoutid, null);
            holder = new PaymentItemAdapter.ViewHolder();
            holder.tv = (TextView) ins.findViewById(R.id.payname);
            holder.iv = (ImageView) ins.findViewById(R.id.payimage);
            holder.quantity = (TextView) ins.findViewById(R.id.quantitynum);
            holder.payprice = (TextView) ins.findViewById(R.id.payprice);

            ins.setTag(holder);
        } else {
            holder = (PaymentItemAdapter.ViewHolder) ins.getTag();
        }
        holder.payprice.setText(sinfo.getTotal());
        holder.tv.setText(sinfo.getName());
        holder.quantity.setText(sinfo.getQuantity());
        String url = sinfo.getImage();
//        url = url.replace("localhost", "192.168.1.133");
        try {
            Picasso.with(ins.getContext()).load(url).resize(70, 75).into(holder.iv);
//            holder.imgView.setImageURI(Uri.parse(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // holder.iv.setImageResource(sinfo.g);

        return ins;
    }


}
