package com.lulu.xutilsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // POST 请求, 传递JSON等字符串的方式
        RequestParams params =
                new RequestParams("http://10.0.153.80:8080/test/post");

        JSONObject json = new JSONObject();
        try {
            json.put("pid", 394384);
            json.put("count", 12);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //以纯字符串的形式, 提交数据
        //需要设置 Content-Type
        params.setBodyContent(json.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "onSuccess: result =>" + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
