package cn.campsg.com.hello.JavaClass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 八月 on 2017/5/11.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public  static final String CREATE_TABLE="create table account("
    +"id integer primary key autoincrement,"
    +"type text,"
    +"category text,"
    +"date text,"
    +"money text,"
    +"place text,"
    +"description text)";

    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
