package com.adu.main.mytimertask.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.adu.main.mytimertask.MainActivity;
import com.adu.main.mytimertask.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dell on 2016/5/26.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{

    private TextInputLayout usernameWrapper,passwordWrapper;
    private Button login;
    private ProgressDialog pd;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            pd.dismiss();
            doLogin();
        }
    };

    private TextView tv_other,tv_register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        inirData();
    }

    /**
     * 初始化控件
     */
    private void initView()
    {
        usernameWrapper= (TextInputLayout) findViewById(R.id.usernameWrapper);
        usernameWrapper.setHint("Usernames");
        passwordWrapper= (TextInputLayout) findViewById(R.id.passwordWrapper);
        passwordWrapper.setHint("Passwords");

        tv_other= (TextView) findViewById(R.id.tv_other);
        tv_other.setOnClickListener(this);
        tv_register= (TextView) findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
    }

    private void inirData()
    {
        login= (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&‘()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    //验证用户名为邮箱
//    public boolean validateEmail(String email) {
//        matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
    public boolean validateEmail(String email) {

        return email.length()>5;
    }
    //验证密码不少于5位
    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.login:
                String username=usernameWrapper.getEditText().toString();
                String password=passwordWrapper.getEditText().toString();

                if (!validateEmail(username)){
                    Toast.makeText(getApplicationContext(), username+"", Toast.LENGTH_SHORT).show();
                    usernameWrapper.setError("Not a valid email address!");
                }else if(!validatePassword(password)){
                    Toast.makeText(getApplicationContext(), password+"", Toast.LENGTH_SHORT).show();
                    passwordWrapper.setError("Not a valid password!");
                }else{
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);

                    pd=new ProgressDialog(this);
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pd.setIcon(R.mipmap.icon);
                    pd.setTitle("提示");
                    pd.setMessage("正在登录...");
                    pd.show();
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Thread.sleep(4000);
                                handler.sendEmptyMessage(0);
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }
                break;

            //用其他方式登录
            case R.id.tv_other:
                startActivity(new Intent(getApplicationContext(),OtherLoginActivity.class));
                finish();
                break;

            case R.id.tv_register:

                break;
        }
    }
    //登录方法
    private void doLogin()
    {
//        Toast.makeText(getApplicationContext(), "OK! I‘m performing login.", Toast.LENGTH_SHORT).show();


//                pd.dismiss();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

    /**
     *
     * 触碰屏幕隐藏键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                0);
    }
}
