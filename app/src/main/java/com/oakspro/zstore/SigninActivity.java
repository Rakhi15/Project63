package com.oakspro.zstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {

    EditText mobileEd, passwordEd;
    TextView errorMsg;
    Button signinBtn;
    private ProgressDialog progressDialog;
    String api_link_login="https://oakspro.com/projects/project35/deepu/zstore/signin_api.php";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //set ids

        preferences=getApplicationContext().getSharedPreferences("LoginZstore", MODE_PRIVATE);
        editor=preferences.edit();

        mobileEd=findViewById(R.id.mobile_ed);
        passwordEd=findViewById(R.id.password_ed);
        errorMsg=findViewById(R.id.error_msg);
        signinBtn=findViewById(R.id.signin_btn);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);


        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mob_s=mobileEd.getText().toString();
                String pass_s=passwordEd.getText().toString();

                if (!TextUtils.isEmpty(mob_s) && !TextUtils.isEmpty(pass_s)){
                    if (mob_s.length()==10){
                        errorMsg.setText("");

                        ServerCheckCredentials(mob_s, pass_s);


                    }else{
                        errorMsg.setText("Please Enter 10 digit Mobile number");
                    }
                }else{
                    errorMsg.setText("Please Enter Valid Details");
                }


            }
        });
    }

    /*

    {
	status:"1",
	message: "User Found";
	name: "Rakesh",
	email: "rakesh@gmail.com",
	address: "Hyderabad"
    }
    ----------------------------------------------------------

    {
        status: "0",
        message: "Mobile Number Not Found"

    }
    -----------------------------------------------------------
    {
        status: "0",
        message: "Incorrect Password"
    }

     */

    private void ServerCheckCredentials(String mob_s, String pass_s) {
        progressDialog.show();

        StringRequest request_login=new StringRequest(Request.Method.POST, api_link_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");

                    if (status.equals("1")){
                        String message=jsonObject.getString("message");
                        String name=jsonObject.getString("name");
                        String email=jsonObject.getString("email");
                        String address=jsonObject.getString("address");

                        //move to homeactivity

                        editor.putBoolean("isLogged", true);
                        editor.putString("name", name);
                        editor.putString("email", email);
                        editor.putString("address", address);
                        editor.putString("mobile", mob_s);
                        editor.commit();

                        Toast.makeText(SigninActivity.this, "Login Success \n Name: "+name+"\n Email: "+email+"\n Address: "+address, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SigninActivity.this, DashActivity.class);
                        startActivity(intent);
                        finish();

                    }else {

                        String message=jsonObject.getString("message");
                        errorMsg.setText(message);
                    }
                    progressDialog.dismiss();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SigninActivity.this, "JSONEX: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SigninActivity.this, "Volley Er: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=new HashMap<>();
                data.put("mobile", mob_s);
                data.put("pass", pass_s);
                return data;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request_login);


    }

    public void open_signup(View view) {
        Intent intent=new Intent(SigninActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}