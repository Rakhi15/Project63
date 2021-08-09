package com.oakspro.zstore;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder>{


    Context context;
    ArrayList<ProductListMember> arrayList;
    private String img_url_dir="https://oakspro.com/projects/project35/deepu/zstore/products/images/";

    public ProductsListAdapter(Context context, ArrayList<ProductListMember> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductListMember member=arrayList.get(position);

        holder.prodName.setText(member.getProd_name());
        holder.prodPrice.setText("₹ "+member.getProd_price());
        Picasso.get().load(img_url_dir+member.getProd_pic()).into(holder.prodImg);
        //Log.i("Pic ppic:","https://oakspro.com/projects/project35/deepu/zstore/products/images"+member.getProd_pic());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView prodImg;
        TextView prodName, prodPrice;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            prodImg=itemView.findViewById(R.id.product_img);
            prodName=itemView.findViewById(R.id.product_name_txt);
            prodPrice=itemView.findViewById(R.id.product_price_txt);


        }
    }
}
