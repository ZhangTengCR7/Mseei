package com.adu.main.mytimertask.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;
import com.adu.main.mytimertask.MainActivity;
import com.adu.main.mytimertask.R;
import com.adu.main.mytimertask.base.BaseActivity;
import com.adu.main.mytimertask.utils.AccessTokenKeeper;
import com.adu.main.mytimertask.utils.Util;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by dell on 2016/5/27.
 *
 *  用QQ和weibo进行登录
 */
public class OtherLoginActivity extends BaseActivity implements View.OnClickListener
{

    Bitmap bitmap = null;
    //qq
    private ImageView iv_qqLogin,iv_WeiboLogin,mUserLogo;
    public static Tencent mTencent;
    private static boolean isServerSideLogin = false;
    private TextView mUserInfo;
    private UserInfo mInfo;
    public QQToken qqToken;
    private String mAppid="1105357085";
    public String openid;// 作为username
    public String qq_icon;
    public String nickname;

    //weibo
    private AuthInfo mAuthInfo;
    /** 登陆认证对应的listener */
//    private AuthListener mLoginListener = new AuthListener();
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherlogin);

        initView();
        initData();
    }


    /**
     * 初始化QQ控件
     */
    private void initView() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(mAppid, this);
        }

        iv_qqLogin= (ImageView) findViewById(R.id.iv_qq);
        iv_qqLogin.setOnClickListener(this);


        mUserLogo= (ImageView) findViewById(R.id.user_logo);
        mUserInfo= (TextView) findViewById(R.id.user_nickname);
    }

    /**
     *初始化Weibo控件
     */
    private void initData(){

        iv_WeiboLogin= (ImageView) findViewById(R.id.iv_weibo);
        iv_WeiboLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_qq:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        onClickLogin();
                    }
                }).start();
                break;
            case R.id.iv_weibo:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                          weiboLogin();
                    }
                }).start();

                break;
        }
    }



    //进行qq登录
    private void onClickLogin() {
        mTencent=Tencent.createInstance(mAppid,this);
        if(!mTencent.isSessionValid() && mTencent != null){
            mTencent.login(this,"all",new BaseUiListener());
        }
    }

    /**
     * qq登录回调的方法
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {

            JSONObject respons = (JSONObject) response;
            if (respons.has("openid")){
                try
                {
                    openid = respons.getString("openid");  //获得唯一标志 openid
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            initOpenidAndToken(respons);
            if (mTencent==null){
                mTencent = Tencent.createInstance(mAppid,OtherLoginActivity.this);
            }
            qqToken = mTencent.getQQToken();    //得到qq令牌
            if (qqToken != null){
                mInfo = new UserInfo(getApplicationContext(),qqToken);
                mInfo.getUserInfo(new IUiListener()
                {
                    @Override
                    public void onComplete(Object o)
                    {
                        JSONObject object = (JSONObject) o;
                        try
                        {
                            nickname = object.getString("nickname");    //qq名字
                            qq_icon = object.getString("figureurl_qq_2");   //图像的url


                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("qq_name",nickname);
                            intent.putExtra("qq_image",qq_icon);
                            Log.i("Main**********",nickname+"########################"+qq_icon);
                            startActivity(intent);
                            finish();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        mTencent.logout(OtherLoginActivity.this);
                    }

                    @Override
                    public void onError(UiError uiError)
                    {
                        Toast.makeText(getApplicationContext(),uiError.errorMessage,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel()
                    {

                    }
                });
            }



        }

        @Override
        public void onError(UiError e) {
           // Util.toastMessage(this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
           // Util.toastMessage(this, "onCancel: ");
            Util.dismissDialog();
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
        }
    }




    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            Log.i("111",token+"***"+expires+"***"+openId);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);

            }

        } catch(Exception e) {

        }
    }

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {

                }

                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    new Thread(){

                        @Override
                        public void run() {
                            JSONObject json = (JSONObject)response;
                            if(json.has("figureurl")){
                                Bitmap bitmap = null;
                                try {
                                    bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
                                } catch (JSONException e) {

                                }
                                Message msg = new Message();
                                msg.obj = bitmap;
                                msg.what = 1;
                                mHandler.sendMessage(msg);
                            }
                        }

                    }.start();
                }

                @Override
                public void onCancel() {

                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        } else {
            mUserInfo.setText("11");
            mUserInfo.setVisibility(android.view.View.GONE);
            mUserLogo.setVisibility(android.view.View.GONE);

        }
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        mUserInfo.setVisibility(android.view.View.VISIBLE);
                        mUserInfo.setText(response.getString("nickname"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else if(msg.what == 1){
                Bitmap bitmap = (Bitmap)msg.obj;
                mUserLogo.setImageBitmap(bitmap);
                mUserLogo.setVisibility(android.view.View.VISIBLE);
            }
        }
    };


    //==========================================================================
    /**
	 * 微博登录 api 调用
	 */
    private void weiboLogin()
    {
        // 创建授权认证信息
        mAuthInfo = new AuthInfo(OtherLoginActivity.this,"3099111849", Util.REDIRECT_URL, Util.SCOPE);
        mSsoHandler = new SsoHandler(OtherLoginActivity.this, mAuthInfo);
        mSsoHandler.authorizeClientSso(new WeiboAuthListener()
        {
            @Override
            public void onComplete(Bundle bundle)
            {
                mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
                if (mAccessToken.isSessionValid()) {
                    // 保存 Token 到 SharedPreferences
                    AccessTokenKeeper.writeAccessToken(OtherLoginActivity.this, mAccessToken);
                    finish();

                    Toast.makeText(getApplicationContext(),"使用微博成功",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
//                    intent.putExtra("name",);
                    intent.setClass(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                } else {
                    // 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
                    String code = bundle.getString("code", "");
                    Toast.makeText(getApplicationContext(),code,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onWeiboException(WeiboException e)
            {
                Log.d("111",e.getMessage());
            }

            @Override
            public void onCancel()
            {

            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {

            Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());
        }

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}
