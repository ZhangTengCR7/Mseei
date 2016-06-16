package com.adu.main.mytimertask;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.preference.DialogPreference;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.adu.main.mytimertask.adapter.MyAdapter;
import com.adu.main.mytimertask.fragment.TodayActivity;
import com.adu.main.mytimertask.recyview.MyBlogActivity;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;


import java.util.Arrays;



public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView rv;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fab;

    private Integer [] images={R.mipmap.image1,R.mipmap.image2,R.mipmap.image3,R.mipmap.image4,R.mipmap.image5,R
            .mipmap.image6,R.mipmap.image7,R.mipmap.image8};

    private View headerView;

    private TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initView()
    {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView= (NavigationView) findViewById(R.id.navigation_view);
        //获取头部圆形
        headerView = navigationView.getHeaderView(0);
        headerView.findViewById(R.id.iv_icon).setOnClickListener(this);
        headerView=navigationView.getHeaderView(1);

//        headerView.findViewById(R.id.tv_csdn).setOnClickListener(this);

        Intent intent=getIntent();
        String user_name=intent.getStringExtra("qq_name");

        String user_image= intent.getStringExtra("qq_image");
        Log.i("Main",user_name+"########################"+user_image);
//        ImageLoader.getInstance().displayImage(user_image, (ImageView) headerView);

        rv = (RecyclerView) findViewById(R.id.rv);
        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsingToolBarLayout);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);



    }
    private void init()
    {
        setSupportActionBar(toolbar);
        //侧滑菜单
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        ){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //打开
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //关闭
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());




        rv.setLayoutManager(new LinearLayoutManager(this));
        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(new MyAdapter(this, Arrays.asList(images)));
        slideAdapter.setFirstOnly(false);
        slideAdapter.setDuration(400);
        rv.setAdapter(slideAdapter);


        collapsingToolbarLayout.setTitle("主界面");


    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.fab:

            Snackbar snackbar = Snackbar.make(collapsingToolbarLayout,"早就出来了",Snackbar.LENGTH_LONG)
                        .setAction("来点我啊", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Snackbar.make(collapsingToolbarLayout,"哎呦，轻点疼",Snackbar.LENGTH_SHORT).show();
                            }
                        });
                ((TextView)snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(0xff03A9F4);
                snackbar.show();

                break;
            case R.id.iv_icon:
                Toast.makeText(getApplicationContext(),"头像",Toast.LENGTH_LONG).show();
                break;
        }
    }



    //NavigationView
    private class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (item.getItemId()) {
                case R.id.nav_blog:
//                    setToolbarTitle(item.getTitle() + "");
                    startActivity(new Intent(getApplicationContext(),MyBlogActivity.class));
                    break;
                case R.id.nav_about:
//                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(getApplicationContext(),ZhiHuActivity.class));

//                    Toast.makeText(MainActivity.this,"option_1",Toast.LENGTH_SHORT).show();

                    break;
                case R.id.nav_version:

                    startActivity(new Intent(getApplicationContext(), TodayActivity.class));
                    break;
                case R.id.nav_sub1:
                    setToolbarTitle(item.getTitle() + "");
                    break;
                case R.id.nav_sub2:
                    setToolbarTitle(item.getTitle() + "");
                    break;
            }
            return true;
        }
    }

    public void setToolbarTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
    }

    /**
     * 加载fragment
     *
     * @param fragment
     */
    private void initPagerContent(android.app.Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        //会话
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.myContents, fragment);
        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_1:
//                Toast.makeText(MainActivity.this,"option_1",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MyBlogActivity.class));
                break;
            case R.id.option_2:
                Toast.makeText(MainActivity.this,"option_2",Toast.LENGTH_SHORT).show();
                break;
            case R.id.option_3:
                Toast.makeText(MainActivity.this,"option_3",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    @Override public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确定要退出？")
            .setIcon(R.mipmap.aaa)
            .setPositiveButton("确定",new DialogInterface.OnClickListener(){

                @Override public void onClick(DialogInterface dialog, int which) {
                    //finish();
                    System.exit(0);
                }
            })
            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {

                }
            }).show();

    }
}
