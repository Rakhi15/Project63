package com.oakspro.zstore.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oakspro.zstore.CategoryAdapter;
import com.oakspro.zstore.CategoryMember;
import com.oakspro.zstore.R;
import com.oakspro.zstore.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    private String api_categ="https://oakspro.com/projects/project35/deepu/zstore/category_api.php";
    ArrayList<CategoryMember> arrayList=new ArrayList<>();

    CategoryAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView=root.findViewById(R.id.recyler_cat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        getCategoryServer(getActivity().getPackageName());


        return root;
    }

    private void getCategoryServer(String packageName) {
        StringRequest request=new StringRequest(Request.Method.POST, api_categ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                arrayList.clear();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if (status.equals("1")){

                        JSONArray jsonArray=jsonObject.getJSONArray("categories");
                        for (int i=0; i<jsonArray.length(); i++){

                            JSONObject object=jsonArray.getJSONObject(i);
                            CategoryMember catmem=new CategoryMember();
                            catmem.setCat_id(object.getString("cat_id"));
                            catmem.setCat_name(object.getString("cat_name"));
                            catmem.setCat_img(object.getString("cat_image"));
                            catmem.setCat_status(object.getString("cat_status"));

                            arrayList.add(catmem);

                        }
                        adapter=new CategoryAdapter(getContext(), arrayList);
                        recyclerView.setAdapter(adapter);


                    }else {
                        Toast.makeText(getContext(), "Category error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Volley Err: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> adata=new HashMap<>();
                adata.put("pack", packageName);
                return adata;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}