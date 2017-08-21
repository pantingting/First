package cn.campsg.com.hello.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cn.campsg.com.hello.R;


public class ListFragment extends BaseFragment {
    private ArrayList<String> mDatas;
    private View view;
    private RecyclerView mListView;
    Context mContext;
    String[] categorys_zc;
    String[] categorys_sl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        categorys_zc=getResources().getStringArray(R.array.category_zc);
        categorys_sl=getResources().getStringArray(R.array.category_sl);
        //initData(0,categorys_zc.length);
    }


    @Override
    public void fetchData() {
        /**
         * doNetWork();
         */
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        initView();
        return view;
    }



    private void initView() {
        mListView = (RecyclerView) view.findViewById(R.id.recycle_view);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mListView.setLayoutManager(layoutManager);
       // mListView.setLayoutManager(new LinearLayoutManager(mContext));
        mListView.setAdapter(new ListAdapter());
    }
    public void initData(char a,char b)
    {
        mDatas = new ArrayList<>();
        for (int i = a; i < b; i++)
        {
            mDatas.add("" + (char) i);
        }
    }
    class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>
    {

        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    mContext).inflate(R.layout.big_every_item_layout, parent,
                    false));
            return holder;
        }

        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.zc.setText(mDatas.get(position));
        }

        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView zc;

            public MyViewHolder(View view)
            {
                super(view);
                zc = (TextView) view.findViewById(R.id.text_zc);
            }
        }
    }


}
