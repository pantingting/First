package cn.campsg.com.hello.Activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

import cn.campsg.com.hello.JavaClass.CustomDatePicker;
import cn.campsg.com.hello.JavaClass.MyDatabaseHelper;
import cn.campsg.com.hello.JavaClass.TypePickerDialog;
import cn.campsg.com.hello.R;
import java.text.SimpleDateFormat;
public class EditDetailActivity extends AppCompatActivity {
    private TextView date_text;
    private EditText money;
    private EditText description;
    private TextView test_picker;
    private  String type;
    private  String category;
    private MyDatabaseHelper dbHelper;
    private CustomDatePicker customDatePicker;
    private LinearLayout lay_selectTime;
    String[] b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_detail_layout);
        //设置状态栏颜色
        Window window=getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setTitle("编辑");
        test_picker=(TextView)findViewById(R.id.test_piker);
        lay_selectTime=(LinearLayout)findViewById(R.id.lay_selectTime);
        date_text=(TextView)findViewById(R.id.date_text) ;
        money=(EditText)findViewById(R.id.money_EditText);
        description=(EditText)findViewById(R.id.description_EditText);
        dbHelper=new MyDatabaseHelper(this,"AccountStore.db",null,2);
        Intent intent=getIntent();
        b=intent.getStringArrayExtra("Edit");

        money.setText(b[1]);
        date_text.setText(b[2]);
        description.setText(b[3]);
        type=b[4];
        category=b[0];
        test_picker.setText(type+"/"+category);


        //获取系统时间为默认值
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=simpleDateFormat.format(new java.util.Date());
        /*日期选择器实现代码start*/
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                date_text.setText(time);
            }
        }, "2010-01-01 00:00", date); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
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
        test_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypePickerDialog.Builder builder=new TypePickerDialog.Builder(EditDetailActivity.this);
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
        /*列表选择器的点击事件 结束*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//保存按钮点击事件
        switch (item.getItemId()){
            case R.id.save_btn:
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("type",type);
                values.put("category",category);
                values.put("date",date_text.getText().toString());
                values.put("money",money.getText().toString());
                values.put("description",description.getText().toString());
                db.update("account",values,"date = ?",new String[]{b[2]});
                startActivity(new Intent(EditDetailActivity.this,MainActivity.class));
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

}

