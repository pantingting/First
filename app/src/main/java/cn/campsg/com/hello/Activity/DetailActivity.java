package cn.campsg.com.hello.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import cn.campsg.com.hello.JavaClass.CustomDatePicker;
import cn.campsg.com.hello.JavaClass.ListItem;
import cn.campsg.com.hello.JavaClass.MyDatabaseHelper;
import cn.campsg.com.hello.JavaClass.TypePickerDialog;
import cn.campsg.com.hello.R;

public class DetailActivity extends AppCompatActivity {
    private TextView date_text;
    private TextView test_picker;
    private EditText money;
    private EditText description;
    private TextView Where;
    private  String type="一般";
    private  String category="支出";
    private MyDatabaseHelper dbHelper;
    public LocationClient mLocationClient;
    private CustomDatePicker customDatePicker;
    private LinearLayout lay_selectTime;
    StringBuilder currentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.detail_layout);
        //设置状态栏颜色
        Window window=getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setTitle("记一笔");
        Where=(TextView)findViewById(R.id.Where_Text);
        initGPS();
        date_text=(TextView)findViewById(R.id.date_text) ;
        money=(EditText)findViewById(R.id.money_EditText);
        description=(EditText)findViewById(R.id.description_EditText);
        test_picker=(TextView)findViewById(R.id.test_piker);
        lay_selectTime=(LinearLayout)findViewById(R.id.lay_selectTime);
        //下拉列表
        test_picker.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View v) {
              TypePickerDialog.Builder builder=new TypePickerDialog.Builder(DetailActivity.this);
              TypePickerDialog dialog=builder.setOnTypeSelectedListener(new TypePickerDialog.OnTypeSelectedListener() {
                  @Override
                  public void onRegionSelected(String[] cityAndArea) {
                          test_picker.setText(cityAndArea[0]+"/"+cityAndArea[1]);
                          category=cityAndArea[1];
                          type=cityAndArea[0];
                  }
              }).create();
              dialog.show();
            }
            });
        dbHelper=new MyDatabaseHelper(this,"AccountStore.db",null,2);
        //获取系统时间为默认值
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date=simpleDateFormat.format(new Date());
        date_text.setText(date);
                //.format(new java.util.Date());
        /*日期选择器实现代码start*/
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                date_text.setText(time);
            }
        }, "2010-01-01 00:00", "2022-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动
        lay_selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDatePicker.show(date_text.getText().toString());
            }
        });
        /*日期选择器实现代码end*/



        /*列表选择器的点击事件 开始*/
        /*列表选择器的点击事件 结束*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_save,menu);
        return true;
    }
    private void initGPS(){
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(DetailActivity.this,Manifest.
                permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(DetailActivity.this,Manifest.
                permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(DetailActivity.this,Manifest.
                permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(DetailActivity.this,permissions,1);
        }else {
            requestLocation();
        }
    }
    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }
    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//保存按钮点击事件
        switch (item.getItemId()){
            case R.id.save_btn:
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                if(money.getText().toString().trim().equals("")){
                    AlertDialog.Builder ad=new AlertDialog.Builder(DetailActivity.this);

                    ad.setIcon(getDrawable(R.drawable.dialog_icon));
                    ad.setTitle("笨蛋");
                    ad.setMessage("你还没说多少钱呢！");
                    ad.setNegativeButton("好吧！", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    ad.show();
                }else {
                    values.put("date",date_text.getText().toString());
                    values.put("place",Where.getText().toString());
                    values.put("money",money.getText().toString());
                    values.put("description",description.getText().toString());
                    values.put("category",category);
                    values.put("type",type);
                    db.insert("account",null,values);
                    startActivity(new Intent(DetailActivity.this,MainActivity.class));

                }
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(DetailActivity.this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(DetailActivity.this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener{
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
         currentPosition=new StringBuilder();
        currentPosition.append(bdLocation.getProvince()).append(bdLocation.getCity()).append(bdLocation.getDistrict())
                .append(bdLocation.getStreet());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);

            }
        }).start();
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }
}
private android.os.Handler handler=new android.os.Handler(){
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case 1:
                Where.setText(currentPosition);
                break;
            default:
                break;
        }
    }
};
}
