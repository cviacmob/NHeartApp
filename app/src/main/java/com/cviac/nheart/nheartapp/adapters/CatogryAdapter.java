package com.cviac.nheart.nheartapp.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cviac.nheart.nheartapp.R;
import com.cviac.nheart.nheartapp.datamodel.Addcartinfo;
import com.cviac.nheart.nheartapp.datamodel.CatogryInfo;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.value;

public class CatogryAdapter extends BaseAdapter {
    int count;
    private static int counter = 0;
    private String stringVal;
    private Context mContext;

    private List<Addcartinfo> list=new ArrayList<>();

    private int layoutid;
    private static Integer deletecounter= 0;

    public CatogryAdapter(Context c, int layoutid, List<Addcartinfo> list) {
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
        Addcartinfo sinfo = list.get(position);

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ins = inflater.inflate(layoutid, null);
            holder = new ViewHolder();

            holder.tv = (TextView) ins.findViewById(R.id.name);
            holder.iv = (ImageView) ins.findViewById(R.id.ima1);
//            Button btn = (Button) ins.findViewById(R.id.remove);
             final TextView tv2 = (TextView)ins.findViewById(R.id.items);
           // holder.iv2 = (ImageView) ins.findViewById(R.id.ima4);

//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                     count = Integer.parseInt((String) tv2.getText());
//                    count++;
//                    tv2.setText("" + count);
//
//
//                }
//            });
            ImageButton txtplus  = (ImageButton) ins.findViewById(R.id.add);
            txtplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("src", "Increasing value...");
                    counter++;
                    stringVal = Integer.toString(counter);
                    tv2.setText(stringVal);
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

                }
            });
            ImageButton Delete = (ImageButton) ins.findViewById(R.id.delete);
            Delete.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              // Loop through and remove all the products that are selected
                                              // Loop backwards so that the remove works correctly

                                              for (int i = list.size() - 1; i >= 0; i--) {

                                                  if (list.get(i).selected) {
                                                      list.remove(i);
                                                      deletecounter++;
                                                  }
                                              }
// THIS IS THE CODE I USED TO RETURN DATA TO PREVIOUS ACTIVITY BUT UserActivity STARTS AUTOMATICALLY AFTER DELETION OF SELECTED PRODUCTS AS SOON AS I CLICK THE DELETE BUTTON EVEN WHEN THERE ARE PRODUCTS IN THE CART.
                                              //                  if(deletecounter!=0) {
//                    Intent i = new Intent(HawkActivity.this, UserActivity.class);
//                    startActivityForResult(i, 1);
//                    Intent returnIntent = new Intent();
//                    returnIntent.putExtra("DeleteCounter", deletecounter);
//                    setResult(RESULT_OK, returnIntent);
//                }
//                                              mProductAdapter.notifyDataSetChanged();
                                              Snackbar.make(view,"Selected items deleted successfully",Snackbar.LENGTH_SHORT).show();
                                          }
                                      }
            );


            ins.setTag(holder);
        } else {
            holder = (ViewHolder) ins.getTag();
        }
        holder.tv.setText(sinfo.getName());
        holder.iv.setImageResource(sinfo.getImgID());


        return ins;
    }
}