package com.oakspro.zstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView prodName, prodPrice, prodStock, prodDesc;
    ImageView prodImg;

    String prod_name, prod_price, prod_id, prod_stock, pro_desc, prod_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        //set ids

        prodName=findViewById(R.id.product_name);
        prodPrice=findViewById(R.id.product_price);
        prodDesc=findViewById(R.id.product_desc);
        prodStock=findViewById(R.id.product_stock);
        prodImg=findViewById(R.id.product_image);

        prod_name=getIntent().getStringExtra("prod_name");
        prod_id=getIntent().getStringExtra("prod_id");
        prod_price=getIntent().getStringExtra("prod_price");
        prod_stock=getIntent().getStringExtra("prod_stock");
        prod_img=getIntent().getStringExtra("prod_img");
        pro_desc=getIntent().getStringExtra("prod_desc");

        prodName.setText(prod_name);
        prodPrice.setText("Price : ₹ "+prod_price);
        prodStock.setText("Stock : "+prod_stock);
        prodDesc.setText(pro_desc);
        Picasso.get().load(prod_img).into(prodImg);


    }

    public void back_open(View view) {
        Intent intent=new Intent(ProductDetailsActivity.this, DashActivity.class);
        startActivity(intent);
    }
}