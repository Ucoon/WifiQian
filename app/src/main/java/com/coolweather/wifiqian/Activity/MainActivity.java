package com.coolweather.wifiqian.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.wifiqian.Model.QianDao;
import com.coolweather.wifiqian.Model.QianTui;
import com.coolweather.wifiqian.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.hello_main)
    TextView hello;
    @Bind(R.id.dao_btn)
    Button dao;
    @Bind(R.id.tui_btn)
    Button tui;
    @Bind(R.id.infodao_btn)
    Button infodao;
    @Bind(R.id.infotui_btn)
    Button infotui;
    @Bind(R.id.quit_btn)
    Button quit;
    public static String IP;
    public static String MAC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        Intent intent=getIntent();
        String realName=intent.getStringExtra("name");
        hello.setText("hello,"+realName);
    }
    //检查连接的是什么网络
    public Integer checkWifi(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo.getState()== NetworkInfo.State.CONNECTED){
            if(networkInfo.getType()==connectivityManager.TYPE_MOBILE){
                return 1;//返回1，连接的是移动网络
            }else if(networkInfo.getType()==connectivityManager.TYPE_WIFI){
                return 2;//返回2，连接的是Wifi
            }
        }
        else {
            return 3;//没有连接
        }
        return 3;
    }
    //获取IP
    public String getLocalIpAddress(){
        try{
            for(Enumeration<NetworkInterface> enumeration=NetworkInterface.getNetworkInterfaces();enumeration.hasMoreElements();){
                NetworkInterface inf=enumeration.nextElement();
                for(Enumeration<InetAddress> en=inf.getInetAddresses();en.hasMoreElements();){
                    InetAddress inetAddress=en.nextElement();
                    if(!inetAddress.isLoopbackAddress()){
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }catch(SocketException e){
            Log.e("IP地址为：",e.toString());
        }
        return null;
    }
    //获取MAC
    public String getLocalMacAddress(){
        WifiManager wifiManager= (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo=wifiManager.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }
    @OnClick(R.id.dao_btn)
    public void Dao(){
        if(checkWifi(MainActivity.this)==1){
            Toast.makeText(MainActivity.this,"您连接的是移动网络，签到失败",Toast.LENGTH_LONG).show();
        }else if(checkWifi(MainActivity.this)==3){
            Toast.makeText(MainActivity.this,"您连接的是移动网络，签到失败",Toast.LENGTH_LONG).show();
        }else if(checkWifi(MainActivity.this)==2){
            MAC=getLocalMacAddress();
            IP=getLocalIpAddress();
            Date date=new Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy/MM/dd HH:mm:ss");
            final String Stime=simpleDateFormat.format(date);
            Intent intent=getIntent();
            String realName=intent.getStringExtra("name");
            String account=intent.getStringExtra("account");

            QianDao qianDao=new QianDao();
            qianDao.setName(realName);
            qianDao.setAccount(account);
            qianDao.setDaoTime(Stime);
            qianDao.setIP(IP);
            qianDao.setMAC(MAC);
            qianDao.save(MainActivity.this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(MainActivity.this,"签到成功 \n IP:"+IP+"\nMAC 地址:"+MAC+"\n时间:"+Stime,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(MainActivity.this,"签到失败!",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    @OnClick(R.id.tui_btn)
    public void Tui(){
        if(checkWifi(MainActivity.this)==1){
            Toast.makeText(MainActivity.this,"您连接的是移动网络，签到失败",Toast.LENGTH_LONG).show();
        }else if(checkWifi(MainActivity.this)==3){
            Toast.makeText(MainActivity.this,"您连接的是移动网络，签到失败",Toast.LENGTH_LONG).show();
        }else if(checkWifi(MainActivity.this)==2) {
            MAC = getLocalMacAddress();
            IP = getLocalIpAddress();
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
            final String ttime = simpleDateFormat.format(date);
            Intent intent = getIntent();
            String realName = intent.getStringExtra("name");
            String account = intent.getStringExtra("account");

            QianTui qianTui=new QianTui();
            qianTui.setName(realName);
            qianTui.setAccount(account);
            qianTui.setTuiTime(ttime);
            qianTui.setIP(IP);
            qianTui.setMAC(MAC);
            qianTui.save(MainActivity.this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(MainActivity.this,"签退成功 \n IP:"+IP+"\nMAC 地址:"+MAC+"\n时间:"+ttime,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(MainActivity.this,"您连接的是移动网络，签退失败",Toast.LENGTH_LONG).show();
                }
            });
        }

    }
    @OnClick(R.id.infodao_btn)
    public void Infodao(){
        Intent intent=getIntent();
        String account=intent.getStringExtra("account");
        BmobQuery<QianDao> query=new BmobQuery<>();
        query.addWhereEqualTo("account",account);
        query.findObjects(MainActivity.this, new FindListener<QianDao>() {
            @Override
            public void onSuccess(List<QianDao> list) {
                String str="";
                for(QianDao a:list){
                    str+="时间:"+a.getDaoTime()+"\nMAC:"+a.getMAC()+"\nIP:"+a.getIP()+"\n\n";
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("签到详情");
                builder.setMessage(str);
                builder.create().show();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(MainActivity.this,"查询失败",Toast.LENGTH_LONG).show();
            }
        });
    }
    @OnClick(R.id.infotui_btn)
    public void Infotui(){
        Intent intent=getIntent();
        String account=intent.getStringExtra("account");
        BmobQuery<QianTui> query=new BmobQuery<>();
        query.addWhereEqualTo("account",account);
        query.findObjects(MainActivity.this, new FindListener<QianTui>() {
            @Override
            public void onSuccess(List<QianTui> list) {
                String str="";
                for(QianTui a:list){
                    str+="时间:"+a.getTuiTime()+"\nMAC:"+a.getMAC()+"\nIP:"+a.getIP()+"\n\n";
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("签退详情");
                builder.setMessage(str);
                builder.create().show();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(MainActivity.this,"查询失败",Toast.LENGTH_LONG).show();
            }
        });
    }
    @OnClick(R.id.quit_btn)
    public void Quit(){
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(MainActivity.this);
    }
}
