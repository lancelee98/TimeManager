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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class login extends AppCompatActivity {
    private Button Login;
    private TextView username;
    private TextView password;
    private String Username;
    private String Password;
    private String realPasswd = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_login);
        Login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
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
                                System.out.println("getRespondData" + responseData);
                                parseJSONWithJSONObject(responseData);
                                System.out.println("getRealPassWd"+realPasswd);
                                if (realPasswd.equals("")) {
                                    ToastUtils.show(login.this,"用户名不存在！");
                                } else {
                                    if (Password.equals(realPasswd)) {
                                        ToastUtils.show(login.this,"登录成功！");
//                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);//启动MainActivity
//                                        startActivity(intent);
                                    } else {
                                        ToastUtils.show(login.this,"密码错误！");
                                    }
                                }
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
            JSONArray data = jsonObject.getJSONArray("data");
            if(data.length()==0)
                realPasswd="";
            else
                {
                JSONObject a=data.getJSONObject(0);
                realPasswd = a.getString("passwd");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
