package com.lance.timemanager;

import android.content.Intent;
import android.widget.Toast;

import com.lance.timemanager.activity.MainActivity;
import com.lance.timemanager.activity.login;
import com.lance.timemanager.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        new Thread((new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder formBody = new FormBody.Builder();
                    formBody.add("user_name", "lance");
                    Request request = new Request.Builder().url("http://118.89.37.35:8081/user/login").post(formBody.build()).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
//                    if (realPasswd.equals("")) {
////                        ToastUtils.show(login.this,"用户名不存在！");
//                    } else {
////                        if (Password.equals(realPasswd)) {
////                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);//启动MainActivity
////                            intent.putExtra("username", Username);
////                            startActivity(intent);
////                            ToastUtils.show(login.this, "登录成功！");
////                        } else {
////                            ToastUtils.show(login.this, "密码错误！");
////                        }
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        })).start();
    }


    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray code = jsonObject.getJSONArray("code");
            JSONArray msg = jsonObject.getJSONArray("msg");
            int Code = code.getInt(0);
            String Msg = msg.getString(0);
            System.out.println(Code + Msg);
            JSONArray data = jsonObject.getJSONArray("data");
            if (data.length() == 0) {
//        realPasswd = "";
//        realCredit = 0;
            } else {
                JSONObject a = data.getJSONObject(0);
//        realPasswd = a.getString("passwd");
//        realCredit= a.getInt("credit");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}