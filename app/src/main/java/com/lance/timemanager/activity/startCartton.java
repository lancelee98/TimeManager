package com.lance.timemanager.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.lance.timemanager.R;

import java.util.Timer;
import java.util.TimerTask;

public class startCartton extends AppCompatActivity {
    private Button go;
    private int i;
    private Timer timer;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case 200:
                    go.setText("跳过 : "+ i);
                    i--;
                    if (i<0){
                        //关闭定时器
                        timer.cancel();
                        //跳往主界面
                        Intent it=new Intent(getApplicationContext(),login.class);//启动MainActivity
                        startActivity(it);
                        //关闭启动页
                        finish();
                    }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_start_cartton);
        go = (Button) findViewById(R.id.go);
        Countdown();
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭定时器
                timer.cancel();
                //跳往主界面
                Intent intent=new Intent(getApplicationContext(),login.class);//启动MainActivity
                startActivity(intent);
                //关闭启动页
                finish();
            }
        });
    }
    private void Countdown() {

        //初始倒计时3秒
        i = 3;

        //定时器
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //向handler发送状态值
                handler.sendEmptyMessage(200);
            }
        };

        //开启定时器，时间差值为1000毫秒
        timer.schedule(task,1,1000);
    }


}
