package com.lance.timemanager;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button2);
        button.setOnClickListener(this);
        GET_USAGE_ACCESS();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                Log.d("UsageStatsLc","listener");
                UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
                Calendar calendar = Calendar.getInstance();
                long endTime = calendar.getTimeInMillis();
                calendar.add(Calendar.DAY_OF_WEEK, -2);
                long startTime = calendar.getTimeInMillis();
                List<UsageStats> list = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, startTime, endTime);
                if (list.size() == 0) {
                    GET_USAGE_ACCESS();
                    Log.d("UsageStatsLc","0");
                } else {
                    for (UsageStats usageStats : list) {
                        String s = "";
                        s += "包名" + usageStats.getPackageName();
                        s += "最后一次运行时间" + usageStats.getLastTimeUsed();
                        s += "总运行时间" + usageStats.getTotalTimeInForeground();
                        try
                        {
                            Field field=usageStats.getClass().getDeclaredField("mLaunchCount");

                            s+="总运行次数"+field+ (int) field.get(usageStats);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        Log.d("UsageStatsLc",s);
                    }
                }
                break;
        }
    }

    private void GET_USAGE_ACCESS() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            try {
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            } catch (Exception e) {
                Toast.makeText(this, "无法开启允许查看使用情况的页面", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

}
