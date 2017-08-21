package cn.campsg.com.hello.JavaClass;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.campsg.com.hello.R;

/**
 * Created by 八月 on 2017/8/7.
 */

public class TypePickerDialog  extends Dialog {
    private Params params;
    public TypePickerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
    private void setParams(TypePickerDialog.Params params) {
        this.params = params;
    }
    private static final class Params {
        private boolean shadow = true;
        private boolean canCancel = true;
        private LoopView loopCity;
        private LoopView loopArea;
        private int initSelection;
        private OnTypeSelectedListener callback;
        private Map<String, List<String>> dataList;

    }
    public interface OnTypeSelectedListener {
        void onRegionSelected(String[] cityAndArea);
    }
    public static class Builder{
        private final Context context;
        private final Params params;
        public Builder(Context context) {
            this.context = context;
            params=new Params();
            try {
                InputStreamReader inputReader = new InputStreamReader(context.getAssets().open("type_data.json"));
                BufferedReader bufReader = new BufferedReader(inputReader);
                String line = "";
                StringBuffer result = new StringBuffer();
                while ((line = bufReader.readLine()) != null) {
                    result.append(line);
                }
                params.dataList= new Gson().fromJson(result.toString(), new TypeToken<Map<String, List<String>>>() {
                }.getType());
            }catch (Exception e){
                e.printStackTrace();}
        }
        private final String[] getCurrRegionValue() {
            return new String[]{params.loopCity.getCurrentItemValue(), params.loopArea.getCurrentItemValue()};
        }
        public Builder setSelection(int initSelection) {
            params.initSelection = initSelection;
            return this;
        }
        public Builder setOnTypeSelectedListener(OnTypeSelectedListener onRegionSelectedListener) {
            params.callback = onRegionSelectedListener;
            return this;
        }
        public TypePickerDialog create(){
            final TypePickerDialog dialog=new TypePickerDialog(context,params.shadow ? R.style.Theme_Light_NoTitle_Dialog: R.style.Theme_Light_NoTitle_NoShadow_Dialog);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_picker_region,null);
            view.findViewById(R.id.tx_finish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    params.callback.onRegionSelected(getCurrRegionValue());
                }
            });
            view.findViewById(R.id.tx_title).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            final LoopView loopCity=(LoopView)view.findViewById(R.id.loop_city);
            loopCity.setArrayList(new ArrayList(params.dataList.keySet()));
            loopCity.setNotLoop();
            if (params.dataList.size() > 0) loopCity.setCurrentItem(params.initSelection);
            final LoopView loopArea=(LoopView)view.findViewById(R.id.loop_area);
            String selectedCity = loopCity.getCurrentItemValue();
            loopArea.setArrayList(params.dataList.get(selectedCity));
            loopArea.setNotLoop();

            loopCity.setListener(new LoopListener() {
                @Override
                public void onItemSelect(int item) {
                    String selectedCity = loopCity.getCurrentItemValue();
                    loopArea.setArrayList(params.dataList.get(selectedCity));
                }
            });
            Window win = dialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
            win.setGravity(Gravity.BOTTOM);
            win.setWindowAnimations(R.style.Animation_Bottom_Rising);

            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(params.canCancel);
            dialog.setCancelable(params.canCancel);

            params.loopCity = loopCity;
            params.loopArea = loopArea;
            dialog.setParams(params);
            return dialog;
        }
    }

}
