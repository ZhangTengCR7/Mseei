package com.adu.main.mytimertask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.adu.main.mytimertask.login.LoginActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dell on 2016/6/15.
 */
public class WelcomeActivity extends AppCompatActivity{

    //知乎日报接口
    private String url = "http://news-at.zhihu.com/api/4/start-image/1080*1776";

    private ImageView iv_welcome;
    private TextView tv_context;
    private Handler handler = new Handler(){
        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);

            startActivity(intent);
            finish();
        }
    };

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();

        new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);// 睡眠2000
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        }.start();
    }



    private void initView()
    {
        iv_welcome= (ImageView) findViewById(R.id.iv_welcome);
        tv_context = (TextView) findViewById(R.id.tv_content);
        //解析json
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override public void onResponse(String json) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        tv_context.setText(jsonObject.getString("text"));

                        Glide.with(WelcomeActivity.this).load(jsonObject.getString("img")).into(iv_welcome);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },new Response.ErrorListener(){

        @Override public void onErrorResponse(VolleyError volleyError) {

        }
     });
        queue.add(request);
    }

}
