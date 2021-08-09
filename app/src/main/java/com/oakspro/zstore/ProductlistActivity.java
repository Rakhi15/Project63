package com.oakspro.zstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductlistActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private String api_products="https://oakspro.com/projects/project35/deepu/zstore/products_api.php";
    ArrayList<ProductListMember> arrayList=new ArrayList<>();
    String cat_id=null;
    TextView catNameTxt;

    ProductsListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);

        recyclerView=findViewById(R.id.recycler_products);
        cat_id=getIntent().getStringExtra("cat_id").toString();

        catNameTxt=findViewById(R.id.category_name_txt);
        catNameTxt.setText(getIntent().getStringExtra("cat_name"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getProducts(cat_id);

    }

    private void getProducts(String cat_id) {

        StringRequest request=new StringRequest(Request.Method.POST, api_products, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                arrayList.clear();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if (status.equals("SUCCESS")){

                        JSONArray jsonArray=jsonObject.getJSONArray("products");
                        for (int i=0; i<jsonArray.length(); i++){

                            JSONObject object=jsonArray.getJSONObject(i);
                            ProductListMember promem=new ProductListMember();
                            promem.setProd_id(object.getString("pid"));
                            promem.setProd_name(object.getString("pname"));
                            promem.setProd_price(object.getString("pprice"));
                            promem.setProd_desc(object.getString("pdesc"));
                            promem.setProd_pic(object.getString("ppic"));
                            promem.setProd_stock(object.getString("pstock"));

                            arrayList.add(promem);

                        }
                        adapter=new ProductsListAdapter(ProductlistActivity.this, arrayList);
                        recyclerView.setAdapter(adapter);

                    }else {
                        Toast.makeText(ProductlistActivity.this, "Products error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductlistActivity.this, "Volley Err: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> adata=new HashMap<>();
                adata.put("pack", getPackageName());
                adata.put("cat_id", cat_id);
                return adata;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void back_open(View view) {
        Intent intent=new Intent(ProductlistActivity.this, DashActivity.class);
        startActivity(intent);
    }
}