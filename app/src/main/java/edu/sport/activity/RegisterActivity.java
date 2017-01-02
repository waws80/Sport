package edu.sport.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.sport.R;
import edu.sport.application.MyApplication;
import edu.sport.entity.UserInfo;

public class RegisterActivity extends AppCompatActivity {

    private static String GENDER="boy";

    private EditText name,pass,height,weight;
    private RadioGroup mRadioGroup;
    private RadioButton boy,girl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        initListener();
    }



    /**
     * 初始化控件
     */
    private void init() {
        name= (EditText) findViewById(R.id.name);
        pass= (EditText) findViewById(R.id.pass);
        height= (EditText) findViewById(R.id.height);
        weight= (EditText) findViewById(R.id.weight);
        mRadioGroup= (RadioGroup) findViewById(R.id.rb);
        boy= (RadioButton) findViewById(R.id.boy);
        girl= (RadioButton) findViewById(R.id.girl);

    }

    /**
     * 单选框的选择事件
     */
    private void initListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.boy:
                        boy.setChecked(true);
                        GENDER="boy";
                        break;
                    case R.id.girl:
                        girl.setChecked(true);
                        GENDER="girl";
                        break;
                }
            }
        });
    }

    /**
     * 注册按钮点击事件
     * @param view
     */
    public void regist(View view){
        String userName=name.getText().toString();
        String password=pass.getText().toString();
        String h=height.getText().toString();
        String w=weight.getText().toString();
        if (userName.isEmpty()){
            name.setError("请输入用户名");
            return;
        }
        if (password.isEmpty()){
            pass.setError("请输入密码");
            return;
        }
        if (h.isEmpty()){
            height.setError("请输入身高");
            return;
        }
        if (w.isEmpty()){
            weight.setError("请输入体重");
            return;
        }
        if (!MyApplication.getUserPwd(userName).isEmpty()){
            Toast.makeText(this, "亲，您已注册过请登录吧！", Toast.LENGTH_SHORT).show();
            return;
        }
        MyApplication.setUserBaseInfo(userName,password);
        UserInfo userInfo=new UserInfo(this,userName);
        userInfo.setUserInfo(UserInfo.NAME,userName);
        userInfo.setUserInfo(UserInfo.PASS,password);
        userInfo.setUserInfo(UserInfo.HEIGHT,h);
        userInfo.setUserInfo(UserInfo.WEIGHT,w);
        if (boy.isChecked()){
            userInfo.setUserInfo(UserInfo.GENDER,GENDER);
        }else if (girl.isChecked()){
            userInfo.setUserInfo(UserInfo.GENDER,GENDER);
        }
        Toast.makeText(this, "恭喜您注册成功！", Toast.LENGTH_SHORT).show();
        MyApplication.setNowUserName(userName);
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }
}
