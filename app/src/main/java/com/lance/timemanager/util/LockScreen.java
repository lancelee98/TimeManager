package com.lance.timemanager.util;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;


public class LockScreen {

    private static final int MY_REQUEST_CODE = 9999;
    private DevicePolicyManager policyManager;
    private ComponentName componentName;

    public  LockScreen(DevicePolicyManager policyManager,ComponentName componentName)
    {
        this.componentName=componentName;
        this.policyManager=policyManager;
    }
    /**
     * 锁屏
     */
    public boolean lockScreen() {
        boolean active = policyManager.isAdminActive(componentName);
        if (!active) {   //若无权限
            return false;
        } else {
            policyManager.lockNow();//直接锁屏
        }
        //killSelf ，锁屏之后就立即kill掉我们的Activity，避免资源的浪费;
        killSelf();
        return true;
    }

    /**
     * 激活设备
     */
//    private Intent activeManage() {
//        //启动设备管理(隐式Intent) - 在AndroidManifest.xml中设定相应过滤器
//        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//        //权限列表
//        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
//        //描述(additional explanation)
//        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "激活后才可以使用锁屏功能 ^.^ ");
//        startActivityForResult(intent, MY_REQUEST_CODE);
//    }

//    /**
//     * 透明状态栏和底部导航栏
//     */
//    private void transParentStatusBarAndBottomNavigationBar() {
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();  //获取到了当前界面的DecorView
//            //SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION，表示会让应用的主体内容占用系统导航栏的空间
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//        hideActionBar();
//    }

//    /**
//     * 使Activity显示多少个像素
//     */
//    private void pxActivity() {
//        Window window = getWindow();
//        window.setGravity(Gravity.CENTER);
//        WindowManager.LayoutParams params = window.getAttributes();
//        //下面这两个x，y是X轴Y轴的偏移量
//        params.x = 0;
//        params.y = 0;
//        //以多少的高度和宽度显示Activity
//        params.height = 100;
//        params.width = 100;
//        window.setAttributes(params);
//    }
//
//    /**
//     * 隐藏ActionBar效果
//     */
//    private void hideActionBar() {
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        } else {
//            Log.e("hideActionBar", "ActionBar为空");
//        }
//    }

    /**
     * kill自己
     */
    private void killSelf() {
        //killMyself ，锁屏之后就立即kill掉我们的Activity，避免资源的浪费;
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
