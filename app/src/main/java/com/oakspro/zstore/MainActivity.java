package com.oakspro.zstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView titleTv, captionTv;
    ImageView IntroPic;
    private SharedPreferences preferences;

    private boolean isLog=false;

    private static int SCREEN_TIME=7000;
    Animation topAnim, bottomAnim, sideAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences=getApplicationContext().getSharedPreferences("LoginZstore", MODE_PRIVATE);
        isLog=preferences.getBoolean("isLogged", false);

        //set ids
        titleTv=findViewById(R.id.title_tv);
        captionTv=findViewById(R.id.caption_tv);
        IntroPic=findViewById(R.id.intro_pic_img);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        sideAnim=AnimationUtils.loadAnimation(this, R.anim.side_animation);


        titleTv.setAnimation(topAnim);
        captionTv.setAnimation(sideAnim);
        IntroPic.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //logic
                if (isLog==true){
                    Intent intent=new Intent(MainActivity.this, DashActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(MainActivity.this, SigninActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SCREEN_TIME);


    }
}