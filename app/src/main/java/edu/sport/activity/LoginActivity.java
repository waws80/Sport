package edu.sport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.sport.R;
import edu.sport.application.MyApplication;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText name;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        name= (EditText) findViewById(R.id.name);
        pass= (EditText) findViewById(R.id.pass);
    }


    /**
     * 登陆按钮的点击事件
     * @param view
     */
    public void login(View view){
        if (name.getText().toString().isEmpty()){
            name.setError("请填写用户名");
            return;
        }
        if (pass.getText().toString().isEmpty()){
            pass.setError("请填写密码");
            return;
        }
        String userName=name.getText().toString();
        String password=pass.getText().toString();
        if (MyApplication.getUserPwd(userName).equals(password)){
            MyApplication.setNowUserName(userName);
            Log.w(TAG, "login: "+ MyApplication.getUserPwd(userName));
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }else {
            Toast.makeText(this, "您还没有注册哦，请先注册吧", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 注册按钮的点击事件
     * @param view
     */
    public void register(View view){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
