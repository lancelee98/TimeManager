package com.lance.timemanager.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lance.timemanager.R;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class login extends AppCompatActivity {
    private Button Login;
    private TextView Sign;
    private TextView username;
    private TextView password;
    private String Username;
    private String Password;
    private String password1 = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_login);
        Login = findViewById(R.id.login);
        Sign=findViewById(R.id.sign);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Sign.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), sign.class);//启动MainActivity
                startActivity(intent);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = username.getText().toString();
                Password = password.getText().toString();
                if (!Username.isEmpty() || !Password.isEmpty()) {
                    new Thread((new Runnable() {
                        @Override
                        public void run() {
                            try {
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder().url("http://118.89.37.35:58123/application/user/getInfo.php?userName=" + Username).build();
                                Response response = client.newCall(request).execute();
                                String responseData = response.body().string();
                                System.out.println("qzxlc" + responseData);
                                System.out.println(password1+"asd1");
                                System.out.println(Password+"asd2");
                                parseJSONWithJSONObject(responseData);
                                System.out.println(password1+"asd2");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })).start();
                    System.out.println(password1+"sdff");
                    if (password1.equals("")) {
                        Toast.makeText(login.this, "用户名不存在！", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Password.equals(password1)) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);//启动MainActivity
                            startActivity(intent);
                        } else {
                            Toast.makeText(login.this, "密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(login.this, "用户名密码不得为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray data = jsonObject.getJSONArray("data");
            JSONObject a=data.getJSONObject(0);
            password1 = a.getString("passwd");
            System.out.println(password1+"asd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//