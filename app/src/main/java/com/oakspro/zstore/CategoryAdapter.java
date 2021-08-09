package com.oakspro.zstore;

import android.content.Context;
import android.content.Intent;
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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<CategoryMember> arrayList;
    private String img_url_dir="https://oakspro.com/projects/project35/deepu/zstore/categories/";

    public CategoryAdapter(Context context, ArrayList<CategoryMember> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_swipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        CategoryMember member=arrayList.get(position);

        holder.catName.setText(member.getCat_name());
        Picasso.get().load(img_url_dir+member.getCat_img()).into(holder.catImg);


        holder.catLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ProductlistActivity.class);
                intent.putExtra("cat_id", member.getCat_id());
                intent.putExtra("cat_name", member.getCat_name());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView catImg;
        TextView catName;
        LinearLayout catLL;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            catImg=itemView.findViewById(R.id.cat_img);
            catName=itemView.findViewById(R.id.cat_name_tx);
            catLL=itemView.findViewById(R.id.cat_ll);


        }
    }
}
