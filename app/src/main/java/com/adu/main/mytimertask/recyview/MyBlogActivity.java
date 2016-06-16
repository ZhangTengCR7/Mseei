package com.adu.main.mytimertask.recyview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import com.adu.main.mytimertask.R;
import com.adu.main.mytimertask.adapter.MyBlogAdapter;
import com.adu.main.mytimertask.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/5/26.
 */
public class MyBlogActivity extends BaseActivity
{

    private RefreshRecyclerView rv;
    private SwipeRefreshLayout srl;
    private List<String> dataList=new ArrayList<>();
    private MyBlogAdapter adapter;
    private Handler handler=new Handler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myblog);

        initView();
        initData();
        initListener();
    }



    /**
     * 初始化控件
     */
    private void initView()
    {
        srl= (SwipeRefreshLayout) findViewById(R.id.srl);
        srl.setColorSchemeResources(android.R.color.holo_red_light,android.R.color.holo_blue_light,android.R.color.holo_green_light);
        rv= (RefreshRecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setLoadMoreEnable(true);            //允许加载更多
        rv.setFooterResource(R.layout.item_footer);//设置脚布局
        adapter=new MyBlogAdapter(dataList);
        rv.setAdapter(adapter);
    }


    private void initData()
    {

        for (int i=0;i<15;i++){
            dataList.add("数据"+i);
        }
        rv.notifyData();
    }

    private void initListener(){
        rv.setOnLoadMoreListener(new RefreshRecyclerView.OnLoadMoreListener()
        {
            @Override
            public void loadMoreListener()
            {
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        for (int i=0;i<10;i++){
                            dataList.add("更多数据"+i);
                        }
                        rv.notifyData();
                    }
                },5000);

            }
        });

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                      srl.setRefreshing(false);
                        dataList.clear();
                        dataList.add(0,"最新数据");
                        dataList.add(0,"最新数据");
                        dataList.add(0,"最新数据");
                        rv.notifyData();
                    }
                },2000);
            }
        });
    }

}





