package com.cviac.nheart.nheartapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.cviac.nheart.nheartapp.activities.AddNewAddressActivity;
import com.cviac.nheart.nheartapp.activities.ChangeAddressActivity;
import com.cviac.nheart.nheartapp.datamodel.Addressinfo;
import com.cviac.nheart.nheartapp.datamodel.Category;
import com.cviac.nheart.nheartapp.datamodel.GeneralResponse;
import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class AddressAdapter extends BaseAdapter {
    private ChangeAddressActivity mContext;
    private List<Addressinfo> additm;

    public AddressAdapter(ChangeAddressActivity mContext, List<Addressinfo> additm) {
        this.mContext = mContext;
        this.additm = additm;
    }

    @Override
    public int getCount() {
        return additm.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder {
        public TextView tv1, tv2, tv4, tv5, tv6, tv7, tv8, tv9;
        public ImageButton edt, del;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View ord = view;
        AddressAdapter.ViewHolder holder;
        final Addressinfo adinfo = additm.get(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ord = inflater.inflate(R.layout.activity_addressadapter, null);
            holder = new AddressAdapter.ViewHolder();
            holder.tv1 = (TextView) ord.findViewById(R.id.addname);
            holder.tv2 = (TextView) ord.findViewById(R.id.addlname);
            holder.tv4 = (TextView) ord.findViewById(R.id.addr1);
            holder.tv5 = (TextView) ord.findViewById(R.id.addr2);
            holder.tv6 = (TextView) ord.findViewById(R.id.city);
            holder.tv7 = (TextView) ord.findViewById(R.id.stat);
            holder.tv8 = (TextView) ord.findViewById(R.id.zip);
            holder.tv9 = (TextView) ord.findViewById(R.id.cntry);
            holder.del = (ImageButton) ord.findViewById(R.id.delbtn);
            holder.edt = (ImageButton) ord.findViewById(R.id.editaddr);

            ord.setTag(holder);
        } else {
            holder = (ViewHolder) ord.getTag();
        }
        holder.tv1.setText(adinfo.getFirstname());
        holder.tv2.setText(adinfo.getLastname());
        holder.tv4.setText(adinfo.getAddress_1());
        holder.tv5.setText(adinfo.getAddress_2());
        holder.tv6.setText(adinfo.getCity());
        holder.tv7.setText(adinfo.getZone());
        holder.tv8.setText(adinfo.getPostcode());
        holder.tv9.setText(adinfo.getCountry());

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String add_id = adinfo.getAddress_id();
                int c_id = Prefs.getInt("customer_id", -1);
                deleteAddress(add_id, c_id + "");
            }
        });
        holder.edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent edit = new Intent(mContext, AddNewAddressActivity.class);
                edit.putExtra("Address", adinfo);
                mContext.startActivityForResult(edit, 141);
            }
        });

        return ord;
    }

    private void deleteAddress(String address_id, String customer_id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mContext.getString(R.string.domainname))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        Call<GeneralResponse> call = api.deleteAddress(address_id, customer_id);
        call.enqueue(new Callback<GeneralResponse>() {

            public void onResponse(Response<GeneralResponse> response, Retrofit retrofit) {
                GeneralResponse resp = response.body();

                if (resp.getCode() == 0) {
                    mContext.loadAddresses();
                } else {
                    Toast.makeText(mContext, "Address Deletion Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}