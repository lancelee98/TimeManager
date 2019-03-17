package com.lance.timemanager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lance.timemanager.R;

public class sign extends AppCompatActivity {
    private Button Sign;
    private TextView passwordagain;
    private TextView username;
    private TextView password;
    private String Username;
    private String Password;
    private String PasswordAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_sign);
        Sign = findViewById(R.id.Sign);
        passwordagain = findViewById(R.id.passwordagin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = username.getText().toString();
                Password = password.getText().toString();
                PasswordAgain=passwordagain.getText().toString();
                if (Password!=PasswordAgain){
                    Toast.makeText(sign.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
