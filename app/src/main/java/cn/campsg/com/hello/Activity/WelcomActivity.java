package cn.campsg.com.hello.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import cn.campsg.com.hello.R;

public class WelcomActivity extends AppCompatActivity {
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom_layout);
        //隐藏标题栏
        getSupportActionBar().hide();
        //隐藏状态栏
        WindowManager.LayoutParams lp=getWindow().getAttributes();
        lp.flags |=WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(lp);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    Intent intent=new Intent(WelcomActivity.this,MainActivity.class);
                    startActivity(intent);
                    onDestroy();}
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                }catch (Exception e){
                }
                handler.sendEmptyMessage(1);
            }
        }).start();
    }
}
