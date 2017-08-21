package cn.campsg.com.hello.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.BoolRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.campsg.com.hello.JavaClass.ListItem;
import cn.campsg.com.hello.JavaClass.MyDatabaseHelper;
import cn.campsg.com.hello.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.campsg.com.hello.JavaClass.ListItem;
import cn.campsg.com.hello.R;

/**
 * Created by C515 on 2017/5/4.
 */
public class ItemAdapter extends ArrayAdapter<ListItem>

{
public View onlyView=null;
    //private List<ListItem> listData=new ArrayList<>();
    private int resourceId;

    //用来控制CheckBox的选中情况
    private static HashMap<Integer,Boolean> isSelected;
    public ItemAdapter(Context context, int textViewResourcedId, List<ListItem> objects){
        super(context,textViewResourcedId,objects);
        resourceId=textViewResourcedId;
        isSelected=new HashMap<Integer,Boolean>();
       // initDate();
    }
//    private void initDate(){
//    for(int i=0;i<listData.size();i++){
//        getIsSelected().put(i,false);
//    }
//    }
//    class ViewHolder{
//    TextView type_textView;
//    TextView money_textView;
//    TextView description_textView;
//    TextView data_textView;
//}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // ViewHolder holder=null;
         final  ListItem listItem=getItem(position);

        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        onlyView=view;
        TextView type_textView=(TextView)view.findViewById(R.id.type_textView);
        TextView money_textView=(TextView)view.findViewById(R.id.money_textView);
        TextView description_textView=(TextView)view.findViewById(R.id.description_textView);
        TextView data_textView=(TextView)view.findViewById(R.id.data_textView);
        ImageView img_type=(ImageView)view.findViewById(R.id.img_type);
        TextView where_textview=(TextView)view.findViewById(R.id.text_where);
        TextView text_timeType=(TextView)view.findViewById(R.id.text_timeType);
        ImageView img_type_top=(ImageView)view.findViewById(R.id.img_top) ;
        //cb=(CheckBox)view.findViewById(R.id.listview_select_cb);
       // cb.setChecked(getIsSelected().get(position));
      //  cb.setVisibility(View.INVISIBLE);
        type_textView.setText(listItem.getType());

        description_textView.setText(listItem.getCategory()+" "+listItem.getDescription());
        data_textView.setText(listItem.getDate());
        where_textview.setText(listItem.getPlace());
        if(type_textView.getText().equals("支出")){
            type_textView.setTextColor(Color.parseColor("#FFED9286"));
            money_textView.setTextColor(Color.parseColor("#FFED9286"));
            money_textView.setText("-"+String.valueOf(listItem.getMoney()));
            img_type.setImageResource(R.mipmap.img_zhichu);
        }else {
            type_textView.setTextColor(Color.parseColor("#FF7F8BE7"));
            money_textView.setTextColor(Color.parseColor("#FF7F8BE7"));
            money_textView.setText("+"+String.valueOf(listItem.getMoney()));
            img_type.setImageResource(R.mipmap.img_shouru);
        }

        return view;

    }
//   public static HashMap<Integer,Boolean> getIsSelected(){
//    return isSelected;
//}
    public static void setIsSelected(HashMap<Integer,Boolean> isSelected){
        ItemAdapter.isSelected=isSelected;
    }
    public View postView(){
        return onlyView;
    }
}
