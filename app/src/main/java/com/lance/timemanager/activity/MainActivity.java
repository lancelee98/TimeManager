package com.lance.timemanager.activity;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lance.timemanager.R;
import com.lance.timemanager.util.AdminReceiver;
import com.lance.timemanager.util.LockScreen;


public class MainActivity extends AppCompatActivity {


    private static final int MY_REQUEST_CODE = 9999;
    private DevicePolicyManager policyManager;
    private ComponentName componentName;
    private DrawerLayout drawerLayout;
    private TextView Credit;
    public String username;
    public int credit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nac_view);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        credit=intent.getIntExtra("credit",0);
        System.out.println(username+"qzx");
        if(navigationView.getHeaderCount() > 0) {
            View header = navigationView.getHeaderView(0);
            TextView un = (TextView) header.findViewById(R.id.use);
            Credit=header.findViewById(R.id.credit);
            Credit.setText("积分:"+credit);
            un.setText("用户名:"+username);
        }
//        Button button = (Button) findViewById(R.id.OpenButton);
//        Button seeButton = (Button) findViewById(R.id.button);
//        Button lockButton = (Button) findViewById(R.id.lockBotton);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    if (!isStatAccessPermissionSet(MainActivity.this)) {
//                        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));   //查看是否为应用设置了权限
//                        Toast toast = Toast.makeText(getApplicationContext(), "请开启应用统计的使用权限", Toast.LENGTH_SHORT);    //显示toast信息
//                        toast.show();
//                    }
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        seeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    if ((isStatAccessPermissionSet(view.getContext()))) {
//                        Intent intent3 = new Intent(MainActivity.this, ListActivity.class);
//                        startActivity(intent3);
//                        finish();
//                    }
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        lockButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
//                    //AdminReceiver 继承自 DeviceAdminReceiver
//                    componentName = new ComponentName(view.getContext(), AdminReceiver.class);
//                    LockScreen lockScreen=new LockScreen(policyManager,componentName);
//                    if(!lockScreen.lockScreen())activeManage();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return  true;
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        try {
//            if((isStatAccessPermissionSet(this))){
//                Intent intent3 = new Intent(MainActivity.this, ListActivity.class);
//                startActivity(intent3);
//                finish();
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean isStatAccessPermissionSet(Context c) throws PackageManager.NameNotFoundException {
        PackageManager pm = c.getPackageManager();
        ApplicationInfo info = pm.getApplicationInfo(c.getPackageName(), 0);
        AppOpsManager aom = (AppOpsManager) c.getSystemService(Context.APP_OPS_SERVICE);
        aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName);
        return aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName)
                == AppOpsManager.MODE_ALLOWED;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        textView = findViewById(R.id.textView);
//        button = findViewById(R.id.button2);
//
//        //设置标题栏
//        topBar = (QMUITopBar) findViewById(R.id.topbar);
//        TextView title = topBar.setTitle("test");
//        title.setTextSize(23);
//        title.setTextColor(getResources().getColor(R.color.qmui_config_color_white));
//        topBar.setBackgroundColor(getResources().getColor(R.color.qmui_config_color_50_blue));
//        topBar.showTitleView(true);
//
//
////        mTabSegment=findViewById(R.id.tabSegment);
////        mTabSegment.setHasIndicator(true);  //是否需要显示indicator
////        mTabSegment.setIndicatorPosition(false);//true 时表示 indicator 位置在 Tab 的上方, false 时表示在下方
////        mTabSegment.setIndicatorWidthAdjustContent(false);//设置 indicator的宽度是否随内容宽度变化
////        mTabSegment.addTab(new QMUITabSegment.Tab("1"));
////        mTabSegment.addTab(new QMUITabSegment.Tab("2"));
//
//        button.setOnClickListener(this);
////        GET_USAGE_ACCESS();
//    }


//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.button2:
//                new QMUIDialog.MessageDialogBuilder(this)
//                        .setTitle("QMUI对话框标题")
//                        .setMessage("这是QMUI框架对话框的内容")
//                        .addAction("取消", new QMUIDialogAction.ActionListener() {
//                            @Override
//                            public void onClick(QMUIDialog dialog, int index) {
//                                dialog.dismiss();
//                                Toast.makeText(MainActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
//
//                            }
//                        })
//                        .addAction("确定", new QMUIDialogAction.ActionListener() {
//                            @Override
//                            public void onClick(QMUIDialog dialog, int index) {
//                                dialog.dismiss();
//                                Toast.makeText(MainActivity.this, "点击了确定按钮", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .show();
//        }

//                Log.d("UsageStatsLc","listener");
//                UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
//                Calendar calendar = Calendar.getInstance();
//                long endTime = calendar.getTimeInMillis();
//                calendar.add(Calendar.DAY_OF_WEEK, -2);
//                long startTime = calendar.getTimeInMillis();
//                List<UsageStats> list = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, startTime, endTime);
//                if (list.size() == 0) {
//                    GET_USAGE_ACCESS();
//                    Log.d("UsageStatsLc","0");
//                } else {
//                    for (UsageStats usageStats : list) {
//                        String s = "";
//                        s += "包名" + usageStats.getPackageName();
//                        s += "最后一次运行时间" + usageStats.getLastTimeUsed();
//                        s += "总运行时间" + usageStats.getTotalTimeInForeground();
//                        try
//                        {
//                            Field field=usageStats.getClass().getDeclaredField("mLaunchCount");
//
//                            s+="总运行次数"+field+ (int) field.get(usageStats);
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                        Log.d("UsageStatsLc",s);
//                    }
//                }
//                break;
//        }
//    }

//    private void GET_USAGE_ACCESS() {
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            try {
//                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//            } catch (Exception e) {
//                Toast.makeText(this, "无法开启允许查看使用情况的页面", Toast.LENGTH_LONG).show();
//                e.printStackTrace();
//            }
//        }
//    }

}
