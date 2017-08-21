package cn.campsg.com.hello.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.campsg.com.hello.R;

/**
 * Created by 八月 on 2017/7/24.
 */

public class BigItemAdapter extends ArrayAdapter<String> {
    private int resoursedId;
    public BigItemAdapter(Context context, int id, List<String> arraylist){
        super(context,id,arraylist);
        resoursedId=id;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(getContext()).inflate(resoursedId,parent,false);
        TextView text_timetype=(TextView)view.findViewById(R.id.text_timeType);
        TextView text_totalZcSl=(TextView)view.findViewById(R.id.text_totalZcSl);
        ListView listView_small=(ListView)view.findViewById(R.id.listview_small);
        try {
            //listView_small.setAdapter(ItemAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
}
