package com.cviac.nheart.nheartapp.fragments;

        import android.content.Context;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.View;

        import android.view.ViewGroup;
        import android.widget.GridView;
        import android.widget.*;


       // import com.cviac.nheart.nheartapp.activities.GridViewone;
        import com.cviac.nheart.nheartapp.R;
        //import com.squareup.picasso.Picasso;
        import android.widget.AdapterView;

        import com.cviac.nheart.nheartapp.activities.ProductdetailsActivity;

        import com.cviac.nheart.nheartapp.adapters.Productsadapter;
        import com.cviac.nheart.nheartapp.datamodel.CategoryProductsResponse;
        import com.cviac.nheart.nheartapp.datamodel.Product;
        import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;

        import java.util.ArrayList;
        import java.util.List;

        import retrofit.Call;
        import retrofit.Callback;
        import retrofit.GsonConverterFactory;
        import retrofit.Response;
        import retrofit.Retrofit;

public class GiftFragment extends Fragment{
  //private  ListView gv;
    private  GridView gv;
    View cv;
    ImageButton b;
    Context thiscontext;
    List<Product> prodlist;
    Productsadapter adapter;


    


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View cv=inflater.inflate(R.layout.grid_layout,container,false);
        thiscontext = container.getContext();
        //b = (ImageButton) cv.findViewById(R.id.ima1);
       /* b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(thiscontext,CategorylistActivity.class);
                startActivity(i);
            }
        });*/


        prodlist = new ArrayList<Product>();
        //gv=(ListView) cv.findViewById(R.id.listview);
        gv=(GridView) cv.findViewById(R.id.gridView1);
            adapter = new Productsadapter(getActivity(), prodlist);
            gv.setAdapter(adapter);
       // ImageButton b=(ImageButton)cv.findViewById((R.id.catogry));


//        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//            }
//        });


//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl("http://nheart.cviac.com/index.php?route=api/category/getproducts&categoryid")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            OpenCartAPI api = retrofit.create(OpenCartAPI.class);
//
//            final Call<CategoryProductsResponse> call = api.getProducts("61");
//            call.enqueue(new Callback<CategoryProductsResponse>() {
//                    @Override
//                    public void onResponse(Response<CategoryProductsResponse> response, Retrofit retrofit) {
//                        CategoryProductsResponse rsp = response.body();
//                            prodlist.addAll(rsp.getProducts());
//                            // adapter.notifyDataSetChanged();
//                            adapter.notifyDataSetInvalidated();
//
//                    }
//                    @Override
//                    public void onFailure(Throwable t) {
//                            prodlist = null;
//                    }
//            });

        refresh("61");
        return cv;

    }

    public void refresh(String catId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nheart.cviac.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenCartAPI api = retrofit.create(OpenCartAPI.class);

        final Call<CategoryProductsResponse> call = api.getProducts(catId);
        call.enqueue(new Callback<CategoryProductsResponse>() {
            @Override
            public void onResponse(Response<CategoryProductsResponse> response, Retrofit retrofit) {
                CategoryProductsResponse rsp = response.body();
                prodlist.clear();
                prodlist.addAll(rsp.getProducts());
                // adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();

            }

            @Override
            public void onFailure(Throwable t) {
                prodlist = null;
            }
        });


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Product pr = prodlist.get(pos);
                Intent i = new Intent(thiscontext, ProductdetailsActivity.class);
                i.putExtra("productobj", pr);
                startActivity(i);
            }
        });






    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
       menu.findItem(R.id.action_call).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}