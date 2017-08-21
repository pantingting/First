package cn.campsg.com.hello.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import cn.campsg.com.hello.JavaClass.MyDatabaseHelper;
import cn.campsg.com.hello.R;
public class ClickActivity extends AppCompatActivity {
Button edit_btn;
    TextView category;
    TextView money;
    TextView time;
    TextView where;
    TextView description;
    String a[];
    String type="";
    SQLiteDatabase db;
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.click_layout);
        setTitle("详情");
       //设置状态栏颜色
        Window window=getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        category=(TextView)findViewById(R.id.type_show);
        money=(TextView)findViewById(R.id.money_show);
        time=(TextView)findViewById(R.id.time);
        where=(TextView)findViewById(R.id.where);
        description=(TextView)findViewById(R.id.description);
        dbHelper=new MyDatabaseHelper(this,"AccountStore.db",null,2);
        db=dbHelper.getWritableDatabase();
        final Intent intent= getIntent();
        a= intent.getStringArrayExtra("extra_data");
        type=a[0];
        category.setText(a[3]);
        money.setText(a[1]);
        time.setText(a[2]);
        description.setText(a[4]);
        where.setText(a[6]);
        edit_btn=(Button)findViewById(R.id.edit_btn);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String  b[]={
                      category.getText().toString(),//0
                      money.getText().toString(),//1
                      time.getText().toString(),//2
                      description.getText().toString(),//3
                      type//4 收入或支出
              };
                Intent intentEdit=new Intent(ClickActivity.this,EditDetailActivity.class);
                intentEdit.putExtra("Edit",b);
                startActivity(intentEdit);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_delete,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_btn:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("确认要删除这条数据吗？");
                builder.setTitle("Tally Book");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //listData.remove(a[5]);
                      // adapter.notifyDataSetChanged();
                       db.delete("account","date = ?",new String[]{time.getText().toString()});
                        startActivity(new Intent(ClickActivity.this,MainActivity.class));
                    }
                });
                builder.create().show();
            }


        return super.onOptionsItemSelected(item);
    }
}
