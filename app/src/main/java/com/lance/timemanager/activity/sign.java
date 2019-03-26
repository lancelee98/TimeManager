package com.lance.timemanager.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lance.timemanager.R;
import com.lance.timemanager.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class sign extends AppCompatActivity {
    private Button Sign;
    private TextView passwordagain;
    private TextView username;
    private TextView password;
    private String Username;
    private String Password;
    private String PasswordAgain;
    private String msg;
    private ImageView Exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_sign);
        Sign = findViewById(R.id.Sign);
        passwordagain = findViewById(R.id.passwordagin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Exit=findViewById(R.id.imageView2);
        Exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login.class);//启动MainActivity
                startActivity(intent);
            }
        });
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = username.getText().toString();
                Password = password.getText().toString();
                PasswordAgain = passwordagain.getText().toString();
                if (Username.isEmpty() || Password.isEmpty() || PasswordAgain.isEmpty()) {
                    Toast.makeText(sign.this, "不得为空！", Toast.LENGTH_SHORT).show();
                } else {
                    if (!Password.equals(PasswordAgain)) {
                        Toast.makeText(sign.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                    } else {
                        new Thread((new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder().url("http://118.89.37.35:58123/application/user/addUser.php?userName=" + Username + "&passwd=" + Password).build();
                                    Response response = client.newCall(request).execute();
                                    String responseData = response.body().string();
                                    parseJSONWithJSONObject(responseData);
                                    System.out.println(msg + "sdf");
                                    if (!msg.equals("success")) {
                                        ToastUtils.show(sign.this, "用户名已存在！");
                                    } else {
                                        ToastUtils.show(sign.this, "注册成功！");
                                        Intent intent = new Intent(getApplicationContext(), login.class);//返回登录界面
                                        startActivity(intent);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })).start();
                    }

                }
            }
        });
    }
    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            msg=jsonObject.getString("msg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
