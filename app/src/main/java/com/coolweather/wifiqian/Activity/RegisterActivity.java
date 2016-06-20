package com.coolweather.wifiqian.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coolweather.wifiqian.Model.User;
import com.coolweather.wifiqian.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by ZongJie on 2016/6/18.
 */
public class RegisterActivity extends AppCompatActivity {
    @Bind(R.id.register_input)
    EditText register_input;
    @Bind(R.id.register_password)
    EditText register_password;
    @Bind(R.id.register_password_again)
    EditText register_password_again;
    @Bind(R.id.realName)
    EditText realname;
    @Bind(R.id.register_sure__btn)
    Button register;
    @Bind(R.id.quit_register__btn)
    Button quit_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(RegisterActivity.this);
    }
    @OnClick(R.id.register_sure__btn)
    public void registerSure(){
        String account=register_input.getText().toString();
        String password=register_password.getText().toString();
        String password_again=register_password_again.getText().toString();
        String name=realname.getText().toString();
        if(!password.equals(password_again)){
            Toast.makeText(RegisterActivity.this,"两次输入的密码不一致，请重新输入",Toast.LENGTH_LONG).show();
        }else if(account.isEmpty()||account.length()!=11){
            Toast.makeText(RegisterActivity.this,"输入的手机号不合法，请重新输入",Toast.LENGTH_LONG).show();
        }else if(name.isEmpty()){
            Toast.makeText(RegisterActivity.this,"请输入您的真实姓名",Toast.LENGTH_LONG).show();
        }else {
            User user=new User();
            user.setAccount(account);
            user.setName(name);
            user.setPassword(password);
            user.save(RegisterActivity.this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(RegisterActivity.this,"注册成功，请返回登录",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(RegisterActivity.this,"注册失败，请重试",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(RegisterActivity.this);
    }
}
