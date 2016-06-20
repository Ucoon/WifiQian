package com.coolweather.wifiqian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coolweather.wifiqian.Model.User;
import com.coolweather.wifiqian.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by ZongJie on 2016/6/18.
 */
public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.input)
    EditText input_num;
    @Bind(R.id.password)
    EditText password_input;
    @Bind(R.id.login_btn)
    Button login;
    @Bind(R.id.register_btn)
    Button register;
    @Bind(R.id.quit_login__btn)
    Button quit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(LoginActivity.this, "de8e8d04b82319cd3423dbd4085aa188");
        ButterKnife.bind(LoginActivity.this);
    }
    @OnClick(R.id.login_btn)
    public void login(){
        final String account=input_num.getText().toString();
        final String password=password_input.getText().toString();
        if(account.equals("")){
            Toast.makeText(LoginActivity.this, "请输入您的手机号", Toast.LENGTH_LONG).show();
        }else if(password.equals("")){
            Toast.makeText(LoginActivity.this, "请输入您的密码", Toast.LENGTH_LONG).show();
        }else{
            BmobQuery<User> query=new BmobQuery<>();
            query.addWhereEqualTo("account",account);
            query.findObjects(LoginActivity.this, new FindListener<User>() {
                @Override
                public void onSuccess(List<User> list) {
                    if(list.size()==0){
                        Toast.makeText(LoginActivity.this, "账户不存在", Toast.LENGTH_LONG).show();
                        return;
                    }
                    for(User a:list){
                        if(!a.getPassword().equals(password)){
                            Toast.makeText(LoginActivity.this, "账号或密码有误，请重新输入", Toast.LENGTH_LONG).show();
                        }else {
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("account",account);
                            intent.putExtra("name",a.getName());
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onError(int i, String s) {
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    @OnClick(R.id.register_btn)
    public void register(){
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.quit_login__btn)
    public void quit_login(){
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
