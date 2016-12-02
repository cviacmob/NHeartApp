package com.cviac.nheart.nheartapp.fragments;

        import android.app.ActionBar;
        import android.content.Context;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;

        import android.view.ViewGroup;
        import android.widget.GridView;
        import android.widget.*;


       // import com.cviac.nheart.nheartapp.activities.GridViewone;
        import com.cviac.nheart.nheartapp.R;
        //import com.squareup.picasso.Picasso;
        import android.widget.AdapterView;

        import com.cviac.nheart.nheartapp.activities.CategorylistActivity;
        import com.cviac.nheart.nheartapp.activities.MainActivity;
        import com.cviac.nheart.nheartapp.activities.ProductlistActivity;
        import com.cviac.nheart.nheartapp.adapters.CategoryAdapter;
        import com.cviac.nheart.nheartapp.adapters.CatogryAdapter;

        import com.cviac.nheart.nheartapp.adapters.Productsadapter;
        import com.cviac.nheart.nheartapp.datamodel.CategoriesResponse;
        import com.cviac.nheart.nheartapp.datamodel.Category;
        import com.cviac.nheart.nheartapp.datamodel.CategoryProductsResponse;
        import com.cviac.nheart.nheartapp.datamodel.CatogryInfo;
        import com.cviac.nheart.nheartapp.datamodel.Gift;
        import com.cviac.nheart.nheartapp.datamodel.Product;
        import com.cviac.nheart.nheartapp.datamodel.ServiceInfo;
        import com.cviac.nheart.nheartapp.restapi.OpenCartAPI;

        import java.util.ArrayList;
        import java.util.List;

        import retrofit.Call;
        import retrofit.Callback;
        import retrofit.GsonConverterFactory;
        import retrofit.Response;
        import retrofit.Retrofit;

public class GiftFragment extends Fragment implements View.OnClickListener{
  //private  ListView gv;
    private  GridView gv;
    View cv;
    Button b;
    Context thiscontext;
    List<Product> prodlist;
    Productsadapter adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View cv=inflater.inflate(R.layout.grid_layout,container,false);
        thiscontext = container.getContext();
        b = (Button) cv.findViewById(R.id.catogry);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(thiscontext,CategorylistActivity.class);
                startActivity(i);
            }
        });


        prodlist = new ArrayList<Product>();
        //gv=(ListView) cv.findViewById(R.id.listview);
        gv=(GridView) cv.findViewById(R.id.gridView1);
            adapter = new Productsadapter(getActivity(), prodlist);
            gv.setAdapter(adapter);
        Button b=(Button)cv.findViewById((R.id.catogry));


//        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//            }
//        });


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://nheart.cviac.com/index.php?route=api/category/getproducts&categoryid")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            OpenCartAPI api = retrofit.create(OpenCartAPI.class);

            final Call<CategoryProductsResponse> call = api.getProducts("61");
            call.enqueue(new Callback<CategoryProductsResponse>() {
                    @Override
                    public void onResponse(Response<CategoryProductsResponse> response, Retrofit retrofit) {
                        CategoryProductsResponse rsp = response.body();
                            prodlist.addAll(rsp.getProducts());
                            // adapter.notifyDataSetChanged();
                            adapter.notifyDataSetInvalidated();

                    }
                    @Override
                    public void onFailure(Throwable t) {
                            prodlist = null;
                    }
            });

        return cv;
    }


    @Override
    public void onClick(View view) {
       final Context context;
        context=cv.getContext();
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


            }
        });
    }
}