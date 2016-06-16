package com.adu.main.mytimertask;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.adu.main.mytimertask.R;
import com.adu.main.mytimertask.adapter.ZhiHuAdapter;
import com.adu.main.mytimertask.base.BaseActivity;
import com.adu.main.mytimertask.bean.ZhiHuBean;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yalantis.phoenix.PullToRefreshView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/6/3.
 */
public class ZhiHuActivity extends BaseActivity
{
    private ListView mListView;

    private RequestQueue queue;

    private List<ZhiHuBean> mList =new ArrayList<ZhiHuBean>();

    private ZhiHuAdapter adapter;

    private PullToRefreshView mPullToRefreshView;

    private List<String> urlList = new ArrayList<String>();

    private List<String> titleList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_zhihu);

        findView();
    }

    /**
     * 初始化View
     * @param
     */
    private void findView()
    {
        mListView = (ListView) findViewById(R.id.list_view);

        getNews();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
                intent.putExtra("title",titleList.get(position));
                intent.putExtra("url",urlList.get(position));
                startActivity(intent);

            }
        });

        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                },4000);
            }
        });
    }

    private void getNews()
    {

        String url="http://v.juhe.cn/weixin/query?key=78f723dccf85aea324a3cf0daac97f35";

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String s)
            {
                Volley_news(s);


            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

            }
        });

        queue.add(request);

    }


    private static  List<ZhiHuBean> getList(String jsonString)
    {
       List<ZhiHuBean> mList = new ArrayList<ZhiHuBean>();

        Gson gson = new Gson();


            mList = gson.fromJson(jsonString,new TypeToken<List<ZhiHuBean>>(){}.getType());
            Log.i("llllllll","asdasd"+mList);
        return mList;
    }
    /**
     * 解析Json
     *
     * @param json
     */
    private void Volley_news(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonresult = jsonObject.getJSONObject("result");
            JSONArray jArray = jsonresult.getJSONArray("list");

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jb = (JSONObject) jArray.get(i);
                ZhiHuBean bean = new ZhiHuBean();
                bean.setTitle(jb.getString("title"));
                bean.setType(jb.getString("source"));
                bean.setUrl(jb.getString("firstImg"));
                mList.add(bean);

                urlList.add(jb.getString("url"));
                titleList.add(jb.getString("title"));
            }
            adapter = new ZhiHuAdapter(getApplicationContext(),mList);
            mListView.setAdapter(adapter);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
