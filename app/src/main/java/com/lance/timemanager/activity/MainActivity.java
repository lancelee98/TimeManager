package com.lance.timemanager.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieEntry;
import com.lance.timemanager.R;
import com.lance.timemanager.entity.AppInformation;
import com.lance.timemanager.pictureManagerClass.biscalPirctureManager;
import com.lance.timemanager.util.AdminReceiver;
import com.lance.timemanager.util.LockScreen;
import com.lance.timemanager.util.StatisticsInfo;
import com.lance.timemanager.util.ToolUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 9999;
    private DevicePolicyManager policyManager;
    private ComponentName componentName;
    private DrawerLayout drawerLayout;
    private TextView Credit;
    public String username;
    public int credit;
    private int style;
    private long totalTime;
    private int totalTimes;
    private TextView Time;
    private TextView Nowtime;
    private PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nac_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        credit = intent.getIntExtra("credit",0);
        if (navigationView.getHeaderCount() > 0) {
            View header = navigationView.getHeaderView(0);
            TextView un = (TextView) header.findViewById(R.id.use);
            Credit = header.findViewById(R.id.credit);
            Credit.setText("积分:" + credit);
            un.setText("用户名:" + username);
        }

        try {//初始化权限
            if (!isStatAccessPermissionSet(MainActivity.this)) {
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));   //查看是否为应用设置了权限
                Toast toast = Toast.makeText(getApplicationContext(), "请开启TimeManager的使用权限", Toast.LENGTH_SHORT);    //显示toast信息
                toast.show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        this.style = StatisticsInfo.DAY;
        Button buttonday = (Button) findViewById(R.id.daybuttonlist3);
        buttonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style != StatisticsInfo.DAY) {
                    style = StatisticsInfo.DAY;
                    onResume();
                }
            }
        });
        Button buttonweek = (Button) findViewById(R.id.weekbuttonlist3);
        buttonweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style != StatisticsInfo.WEEK) {
                    style = StatisticsInfo.WEEK;
                    onResume();
                }
            }
        });
        Button buttonmonth = (Button) findViewById(R.id.monthbuttonlist3);
        buttonmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style != StatisticsInfo.MONTH) {
                    style = StatisticsInfo.MONTH;
                    onResume();
                }
            }
        });
        Button buttonyear = (Button) findViewById(R.id.yearbuttonlist3);
        buttonyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(style != StatisticsInfo.YEAR) {
                    style = StatisticsInfo.YEAR;
                    onResume();
                }
            }
        });
    }

    private void SetButtonColor() {//改变按钮颜色
        Button buttonday = (Button) findViewById(R.id.daybuttonlist3);
        Button buttonmonth = (Button) findViewById(R.id.monthbuttonlist3);
        Button buttonyear = (Button) findViewById(R.id.yearbuttonlist3);
        Button buttonweek = (Button) findViewById(R.id.weekbuttonlist3);

        buttonday.setTextColor(Color.BLACK);
        buttonday.setBackgroundColor(Color.WHITE);
        buttonmonth.setTextColor(Color.BLACK);
        buttonmonth.setBackgroundColor(Color.WHITE);
        buttonweek.setTextColor(Color.BLACK);
        buttonweek.setBackgroundColor(Color.WHITE);
        buttonyear.setTextColor(Color.BLACK);
        buttonyear.setBackgroundColor(Color.WHITE);

        switch (style) {
            case StatisticsInfo.DAY:
                buttonday.setTextColor(Color.WHITE);
                buttonday.setBackgroundColor(Color.parseColor("#41240c"));
                break;
            case StatisticsInfo.MONTH:
                buttonmonth.setTextColor(Color.WHITE);
                buttonmonth.setBackgroundColor(Color.parseColor("#41240c"));
                break;
            case StatisticsInfo.WEEK:
                buttonweek.setTextColor(Color.WHITE);
                buttonweek.setBackgroundColor(Color.parseColor("#41240c"));
                break;
            case StatisticsInfo.YEAR:
                buttonyear.setTextColor(Color.WHITE);
                buttonyear.setBackgroundColor(Color.parseColor("#41240c"));
                break;
        }
    }

    //每次重新进入界面的时候加载listView
    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {//重新加载列表控件
        super.onResume();
        SetButtonColor();
        List<Map<String,Object>> datalist = null;
        StatisticsInfo statisticsInfo = new StatisticsInfo(this,this.style);
        totalTime = statisticsInfo.getTotalTime();
        totalTimes = statisticsInfo.getTotalTimes();
        datalist = getDataList(statisticsInfo.getShowList());
        initPieView(datalist);
        ListView listView = (ListView)findViewById(R.id.AppStatisticsList);
        SimpleAdapter adapter = new SimpleAdapter(this,datalist,R.layout.inner_list,
                new String[]{"label","info","times","icon"},
                new int[]{R.id.label,R.id.info,R.id.times,R.id.icon});
        listView.setAdapter(adapter);
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if(view instanceof ImageView && o instanceof Drawable){

                    ImageView iv=(ImageView)view;
                    iv.setImageDrawable((Drawable)o);
                    return true;
                }
                else return false;
            }
        });
        ToolUtils.setListViewHeightBasedOnChildren(listView);
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日 HH:mm");
        Date curDate =  new Date(System.currentTimeMillis());
        Nowtime=findViewById(R.id.nowtime);
        String nowtime=formatter.format(curDate);
        Nowtime.setText("统计时间:"+nowtime);
        TextView textView = (TextView)findViewById(R.id.time);
        textView.setText( ToolUtils.parseDate(totalTime / 1000));
    }

    private void initPieView(List<Map<String,Object>> datalist) {
        pieChart = (PieChart) findViewById(R.id.pie);
        pieChart.removeAllViews();

        List<PieEntry> yvals = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        String [] strColors={"#cdbaac","#FFCCCC","#99CC99","#CCFFFF","#FFFFCC"};
        if(datalist.size()>5)
        {
            for(int i=0;i<5;i++)
            {
                Long value=Long.valueOf(datalist.get(i).get("time").toString());
                String lable=datalist.get(i).get("label").toString();
                yvals.add(new PieEntry( value,lable));
                colors.add(Color.parseColor(strColors[i]));
            }
        }
        else
        {
            for(int i=0;i<datalist.size();i++)
            {
                Long value=Long.valueOf(datalist.get(i).get("time").toString());
                String lable=datalist.get(i).get("label").toString();
                yvals.add(new PieEntry( value,lable));
                colors.add(Color.parseColor(strColors[i]));
            }
        }
        biscalPirctureManager pieChartManagger=new biscalPirctureManager(pieChart);
        pieChartManagger.showRingPieChart(yvals,colors);

        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }

    private List<Map<String,Object>> getDataList(ArrayList<AppInformation> ShowList) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        for(AppInformation appInformation : ShowList) {
            try {
                Map<String,Object> map = new HashMap<String,Object>();
                if(appInformation.getLabel().equals("王者荣耀")) continue;
                if(appInformation.getLabel().equals("系统桌面")) continue;
                String runTime=ToolUtils.parseDate(appInformation.getUsedTimebyDay() / 1000);
                if(runTime.equals("00分钟"))continue;
                map.put("label",appInformation.getLabel());
                map.put("info","运行时间: " +runTime);
                map.put("times","本次开机操作次数: " + appInformation.getTimes());
                map.put("icon",appInformation.getIcon());
                map.put("time",appInformation.getUsedTimebyDay() / 1000);
                dataList.add(map);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return dataList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    private void activeManage() {
        //启动设备管理(隐式Intent) - 在AndroidManifest.xml中设定相应过滤器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        //权限列表
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        //描述(additional explanation)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "激活后才可以使用锁屏功能 。 ");
        startActivityForResult(intent, MY_REQUEST_CODE);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean isStatAccessPermissionSet(Context c) throws PackageManager.NameNotFoundException {
        PackageManager pm = c.getPackageManager();
        ApplicationInfo info = pm.getApplicationInfo(c.getPackageName(), 0);
        AppOpsManager aom = (AppOpsManager) c.getSystemService(Context.APP_OPS_SERVICE);
        aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName);
        return aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName)
                == AppOpsManager.MODE_ALLOWED;
    }
}
