package com.creeps.sl_app.quizapp.base_modules.test;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.creeps.sl_app.quizapp.R;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Chapter;

import java.util.List;

/**
 * Created by rohan on 4/1/18.
 */

public class CustomChapterAdapter extends ArrayAdapter{
    private List<Chapter> mChapters;
    private LayoutInflater mLayoutInflater;
    private boolean itemState[];
    private final static String TAG="CustomChapterAdapter";

    public CustomChapterAdapter(Context context, List<Chapter> chapters){
        super(context, R.layout.fragment_test_chapter_item);
        this.itemState=new boolean[chapters.size()];
        this.mChapters=chapters;
        this.mLayoutInflater=LayoutInflater.from(context);
        Log.d(TAG,this.mChapters.toString());
    }


    public void setChapters(List<Chapter> list){
        this.mChapters=list;
        this.itemState=new boolean[list.size()];
        this.notifyDataSetChanged();
    }
    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
        Log.d(TAG,"dataSetChanged");
    }


    @Override
    public int getCount() {
        Log.d(TAG,"count "+this.mChapters.size());
        return this.mChapters.size();
    }

    @Override
    public Chapter getItem(int position) {
        return this.mChapters.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        Log.d(TAG,"getView");
        if(convertView== null){
            convertView=mLayoutInflater.inflate(R.layout.fragment_test_chapter_item,null);
            holder=new ViewHolder();
            holder.mCheckBox=(CheckBox)convertView.findViewById(R.id.checkbox_item);
            convertView.setTag(holder);
        }else
            holder=(ViewHolder) convertView.getTag();

        String x=this.getItem(position).getChapterName();
        Log.d(TAG,x);
        holder.setCheckBoxText(this.getItem(position).getChapterName());
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean current=holder.getCheckBoxState();
                CustomChapterAdapter.this.itemState[position]=current;
                }
        });

        return convertView;
    }

    static final class ViewHolder {
       private CheckBox mCheckBox;

        public void setmCheckBox(CheckBox cb){
            this.mCheckBox=cb;
        }
        public void setCheckBoxState(boolean b){
            this.mCheckBox.setChecked(b);
        }
        public boolean getCheckBoxState(){
            return this.mCheckBox.isChecked();
        }
        public void setCheckBoxText(String a){
            this.mCheckBox.setText(a);
        }
        public CharSequence getCheckBoxText(){
            return this.mCheckBox.getText();
        }
    }
    
    
    public boolean[] getItemStates(){
        return this.itemState;
    }

}
