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
import com.lance.timemanager.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;

public class login extends AppCompatActivity {
    private Button Login;
    private TextView Sign;
    private TextView username;
    private TextView password;
    private String Username;
    private String Password;
    private String realPasswd = "";
    private int realCredit = 0;
    private int Code;
    private String Msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_login);
        Login = findViewById(R.id.login);
        Sign = findViewById(R.id.sign);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Sign.setOnClickListener(new View.OnClickListener() {
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
//                                OkHttpClient client = new OkHttpClient();
////                                FormBody.Builder formBody = new FormBody.Builder();
////                                formBody.add("user_name", Username);
////                                Request request = new Request.Builder().url("http://119.3.164.11:8081/user/login").post(formBody.build()).build();
////                                Response response = client.newCall(request).execute();
////                                String responseData = response.body().string();
////                                parseJSONWithJSONObject(responseData);
////                                if (Code!=200) {
////                                    ToastUtils.show(login.this, Msg);
////                                } else {
////                                    if (Password.equals(realPasswd)) {
                                if(true){
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);//启动MainActivity
                                        intent.putExtra("username", Username);
                                        intent.putExtra("credit",realCredit);
                                        startActivity(intent);
                                        ToastUtils.show(login.this, "登录成功！");
                                    } else {
                                        ToastUtils.show(login.this, "密码错误！");
                                    }
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })).start();

                } else {
                    Toast.makeText(login.this, "用户名密码不得为空！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            Code = jsonObject.getInt("code");
            Msg = jsonObject.getString("msg");
            System.out.println(Code + Msg);
            JSONArray data = jsonObject.getJSONArray("data");
            if (data.length() == 0) {
                realPasswd = "";
                realCredit = 0;
            } else {
                JSONObject a = data.getJSONObject(0);
                realPasswd = a.getString("passwd");
                realCredit = a.getInt("credit");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
