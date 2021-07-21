package com.oakspro.zstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText nameEd, emailEd, phoneEd, passwordEd,confirmpassEd;
    Button createBtn;
    TextView msgError;
    private ProgressDialog progressDialog;
    String api_link_signup="https://oakspro.com/projects/project35/deepu/zstore/signup_api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //set ids

        nameEd=findViewById(R.id.nametext);
        phoneEd=findViewById(R.id.phonetext);
        emailEd=findViewById(R.id.mailtext);
        passwordEd=findViewById(R.id.pwdtext1);
        confirmpassEd=findViewById(R.id.pwdtext2);
        createBtn=findViewById(R.id.signup_btn);
        msgError=findViewById(R.id.message_error);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name_s=nameEd.getText().toString();
                String phone_s=phoneEd.getText().toString();
                String email_s=emailEd.getText().toString();
                String pass1=passwordEd.getText().toString();
                String pass2=confirmpassEd.getText().toString();

                if (!TextUtils.isEmpty(name_s)){
                    if (!TextUtils.isEmpty(phone_s) && phone_s.length()==10){
                        if (!TextUtils.isEmpty(email_s)){
                            if (!TextUtils.isEmpty(pass1) && !TextUtils.isEmpty(pass2)){
                                    if (pass2.equals(pass1)){
                                        uploadToServer(name_s, email_s, phone_s, pass1);
                                    }else {
                                        msgError.setText("Password Not Matched");
                                    }
                            }else {
                                msgError.setText("Password is Required");
                                passwordEd.setError("Password is required");
                                confirmpassEd.setError("Required");
                            }
                        }else {
                            msgError.setText("Please Enter Email Address");
                            emailEd.setError("Email is required");
                        }

                    }else {
                     msgError.setText("Please Enter 10 Digit Mobile Number");
                     phoneEd.setError("Mobile is required");
                    }
                }else {
                    msgError.setText("Please Fill Name");
                    nameEd.setError("Name is required");
                }
            }
        });

    }

    private void uploadToServer(String name_s, String email_s, String phone_s, String pass1) {
       progressDialog.show();

       //main logic of volley
        StringRequest request1=new StringRequest(Request.Method.POST, api_link_signup, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String status=jsonObject.getString("status");
                    if (status.equals("1")){
                        printMessage("Signup", jsonObject.getString("message").toString());
                        progressDialog.dismiss();
                    }else{
                        printMessage("Signup", jsonObject.getString("message").toString());
                        progressDialog.dismiss();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SignupActivity.this, "JSON Ex: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data_upload=new HashMap<>();
                data_upload.put("nm", name_s);
                data_upload.put("em", email_s);
                data_upload.put("mb", phone_s);
                data_upload.put("ps", pass1);
                return data_upload;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(request1);
    }

    private void printMessage(String title, String message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage("Respone: "+message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public void open_signin(View view) {
        Intent intent=new Intent(SignupActivity.this, SigninActivity.class);
        startActivity(intent);
    }
}