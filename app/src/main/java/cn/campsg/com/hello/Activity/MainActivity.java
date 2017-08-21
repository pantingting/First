package cn.campsg.com.hello.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import java.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

import cn.campsg.com.hello.Adapter.ItemAdapter;
import cn.campsg.com.hello.JavaClass.FilterPopUpWindow;
import cn.campsg.com.hello.JavaClass.ListItem;
import cn.campsg.com.hello.JavaClass.MyDatabaseHelper;
import cn.campsg.com.hello.JavaClass.SwipeRefreshListView;
import cn.campsg.com.hello.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener,NavigationView.OnNavigationItemSelectedListener
        //,SwipeRefreshListView.OnSwipeRefreshListener
{
    public int a=0;
    //退出时的时间
    private long mExitTime;
    private MyDatabaseHelper dbHelper;
    private  ListView lv;
    private List<ListItem> listData=new ArrayList<>();
    private View view;
    private TextView text_months;
    private TextView text_totalZC;
    private TextView text_totalSL;
    private ImageView img_main_bg;

//    private FloatingActionButton fab;
    private FilterPopUpWindow filterPopUpWindow;
    private LayoutInflater inflater;
    private View popView;
    private final static int REFRESH_COMPLETE = 0;
    //EditText edit_sentence;
    SQLiteDatabase db;
    String type;
    String description;
    String category;
    String date;
    String money;
    String place;
    String Id;
    String currentTime;
    ListItem listItem;
    ItemAdapter adapter;
    String phone;
    String password;
    byte[] photo=null;
    ByteArrayInputStream bais=null;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置状态栏颜色
        Window window=getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        img_main_bg=(ImageView)findViewById(R.id.img_main_bg);
        lv=(ListView)findViewById(R.id.listView);
        //fab=(FloatingActionButton)findViewById(R.id.fad);

        text_months=(TextView)findViewById(R.id.text_months);
        text_totalSL=(TextView)findViewById(R.id.text_totalSL);
        text_totalZC=(TextView)findViewById(R.id.text_totalZC);

        lv.setDivider(null);

      //创建数据库和表
        dbHelper=new MyDatabaseHelper(this,"AccountStore.db",null,2);
        db=dbHelper.getWritableDatabase();
        initData();
        lv.setOnItemClickListener(this);
        //lv.setOnSwipeRefreshListener(this);
        WindowManager.LayoutParams lp=getWindow().getAttributes();
        lp.flags &=(~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(lp);
        inflater = getLayoutInflater();
        popView=inflater.inflate(R.layout.pop_layout,null);

         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//       fab.setOnClickListener(new View.OnClickListener() {
//    @Override
//          public void onClick(View v) {
//           startActivity(new Intent(MainActivity.this,DetailActivity.class));
//
//    }
//});
        //final View viewHeader=navigationView.getHeaderView(0);
        //左侧侧滑菜单头部里的签名
//        edit_sentence=(EditText)viewHeader.findViewById(R.id.edit_sentence);
//        SharedPreferences pref=getSharedPreferences("save",MODE_PRIVATE);
//        String sentence=pref.getString("sen","");
//        edit_sentence.setText(sentence);
//          viewHeader.setOnTouchListener(new View.OnTouchListener() {
//              @Override
//              public boolean onTouch(View v, MotionEvent event) {
//                  viewHeader.setFocusable(true);
//                  viewHeader.setFocusableInTouchMode(true);
//                  viewHeader.requestFocus();
//                  InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                  imm.hideSoftInputFromWindow(edit_sentence.getWindowToken(), 0);
//                  return false;
//              }
//          });
//        edit_sentence.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                  // 此处为得到焦点时的处理内容
//                    edit_sentence.setTextColor(getResources().getColor(R.color.focused));
//                }else {
//                    // 此处为失去焦点时的处理内容
//                    edit_sentence.setTextColor(getResources().getColor(R.color.nomal));
//                }
//            }
//        });
    }

//    private Handler mHandler = new Handler(){
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case REFRESH_COMPLETE:
//                    //lv.setOnRefreshComplete();
//                    adapter.notifyDataSetChanged();
//                    lv.setSelection(0);
//                    break;
//
//                default:
//                    break;
//            }
//        };
//    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//对返回键进行监听
        if(keyCode== KeyEvent.KEYCODE_BACK &&event.getRepeatCount() == 0){
        exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
     public void exit(){
         if ((System.currentTimeMillis() - mExitTime) > 2000) {
             Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
             mExitTime = System.currentTimeMillis();
         } else {
            // MyConfig.clearSharePre(this, "users");
             finish();
             System.exit(0);
         }
     }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
        } else {
            super.onBackPressed();

        }
    }
//    @Override
//    public void onRefresh() {
//       new Thread(new Runnable() {
//        @Override
//        public void run() {
//            try {
//                Thread.sleep(2000);
//            }catch (InterruptedException e){e.printStackTrace();}
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    listData.clear();
//                    initData();
//                    mHandler.sendEmptyMessage(REFRESH_COMPLETE);
//                }
//            });
//       }
//    }).start();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_btn) {
            startActivity(new Intent(MainActivity.this,DetailActivity.class));
            //showPopUpWindow();
        }
        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
         if (id == R.id.nav_share) {
             DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
             drawer.closeDrawer(GravityCompat.START);
             shareMsg("分享","分享","软件下载网址","");
        } else if (id == R.id.nav_about) {
             startActivity(new Intent(MainActivity.this,About_Activity.class));
             DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
             drawer.closeDrawer(GravityCompat.START);

        }else if(id == R.id.nav_update){
             DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
             drawer.closeDrawer(GravityCompat.START);
             Toast.makeText(this, "已经是最新版本！", Toast.LENGTH_SHORT).show();
         }else if(id == R.id.nav_advice){
             startActivity(new Intent(MainActivity.this,ContactUsActivity.class));
             DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
             drawer.closeDrawer(GravityCompat.START);

         } else if(id == R.id.nav_suggest){
             startActivity(new Intent(MainActivity.this,SuggestActivity.class));
             DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
             drawer.closeDrawer(GravityCompat.START);
         }
        return true;
    }
    public void showPopUpWindow(){
        if(null==filterPopUpWindow){
            int height = getResources().getDisplayMetrics().heightPixels;
            int width = getResources().getDisplayMetrics().widthPixels;
            filterPopUpWindow=new FilterPopUpWindow(width,height);
            filterPopUpWindow.setBackgroundDrawable(new ColorDrawable(0x33000000));
            filterPopUpWindow.setAnimationStyle(0);
            filterPopUpWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
            filterPopUpWindow.setFocusable(true);
            filterPopUpWindow.setClippingEnabled(false);
            filterPopUpWindow.setAllowScrollingAnchorParentEx(false);
            filterPopUpWindow.setCustomContentView(popView);
            filterPopUpWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                }
            });

        }
       filterPopUpWindow.showAsDropDown(toolbar);
        final TextView DefaultMonthsText=(TextView)popView.findViewById(R.id.text_defaultMonths);
        final TextView FilterThreeDays=(TextView)popView.findViewById(R.id.text_fliter_ThreeDay);
        final TextView FilterAWeek=(TextView)popView.findViewById(R.id.text_fliter_AWeek);
        final TextView otherMonths=(TextView)popView.findViewById(R.id.text_fliter_otherMonths);
        if(currentTime.substring(5,6).equals("0")){
            DefaultMonthsText.setText(currentTime.substring(6,7)+"月");
        }else {
            DefaultMonthsText.setText(currentTime.substring(5,7)+"月");
        }
        FilterThreeDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterThreeDays.setTextColor(getResources().getColor(R.color.base_red));
                FilterAWeek.setTextColor(getResources().getColor(R.color.text_444444));
                otherMonths.setTextColor(getResources().getColor(R.color.text_444444));
                filterPopUpWindow.dismiss();
            }
        });
        FilterAWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterThreeDays.setTextColor(getResources().getColor(R.color.text_444444));
                FilterAWeek.setTextColor(getResources().getColor(R.color.base_red));
                otherMonths.setTextColor(getResources().getColor(R.color.text_444444));
                filterPopUpWindow.dismiss();
            }
        });
        otherMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterThreeDays.setTextColor(getResources().getColor(R.color.text_444444));
                FilterAWeek.setTextColor(getResources().getColor(R.color.text_444444));
                otherMonths.setTextColor(getResources().getColor(R.color.base_red));
                filterPopUpWindow.dismiss();
            }
        });

    }
    public void shareMsg(String activityTitle, String msgTitle, String msgText, String imgPath) {
         Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
             intent.setType("text/plain"); // 纯文本  
             }else {
             File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT,msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, activityTitle));
    }
    public void initData(){
        //从数据库中查询数据
        Cursor cursor=db.query("account",null,null,null,null,null,null);
        if(cursor.moveToLast()){
            do{
              type=cursor.getString(cursor.getColumnIndex("type"));
              money=cursor.getString(cursor.getColumnIndex("money"));
                category=cursor.getString(cursor.getColumnIndex("category"));
               description=cursor.getString(cursor.getColumnIndex("description"));
                 date =  cursor.getString(cursor.getColumnIndex("date"));
                place=  cursor.getString(cursor.getColumnIndex("place"));

                showDate();
                Log.d("TAG",type);
                Log.d("TAG",money);
                Log.d("TAG",category);
                Log.d("TAG",description);
                Log.d("TAG",date);

            }while (cursor.moveToPrevious());
        }
        cursor.close();




    }



public void showDate(){
    listItem=new ListItem(type,category,money,description,date,place);
    listData.add(listItem);
     adapter=new ItemAdapter(MainActivity.this,R.layout.every_item_layout,listData);
    view=adapter.postView();
    lv.setAdapter(adapter);
    if(listData!=null){
        img_main_bg.setVisibility(View.GONE);
    }
    int ZC=0;
    int SL=0;
    for(int i=0;i<listData.size();i++){
        try{
            String time=listData.get(i).getDate();
            String months=time.substring(5, 7);
            //获取系统时间为默认值
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            currentTime=simpleDateFormat.format(new java.util.Date());
            if(currentTime.substring(5,7).equals(months)){
                if(listData.get(i).getType().equals("支出")){
                    ZC+=Integer.valueOf(listData.get(i).getMoney()).intValue();
                }else {
                    SL+=Integer.valueOf(listData.get(i).getMoney()).intValue();
                }
            }
        }catch (IndexOutOfBoundsException e){e.printStackTrace();}

    }


    text_totalSL.setText("+"+String.valueOf(SL));
    text_totalZC.setText("-"+String.valueOf(ZC));
    if(currentTime.substring(5,6).equals("0")){
        text_months.setText(currentTime.substring(6,7)+"月");
    }else {
        text_months.setText(currentTime.substring(5,7)+"月");
    }
    lv.setOnItemClickListener(this);

}
    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ListItem listItem=listData.get(position);
        String  pos=String.valueOf(position);
        Intent intent=new Intent(MainActivity.this,ClickActivity.class);
        String [] a={
                listItem.getType(),//0
                listItem.getMoney(),//1
                listItem.getDate(),//2
                listItem.getCategory(),//3
                listItem.getDescription(),//4
                pos,//5
        listItem.getPlace()};//6
        intent.putExtra("extra_data",a);
        startActivity(intent);

    }

}

