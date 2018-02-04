package com.creeps.sl_app.quizapp.core_services.views.mtf;

/**
 * Created by damian on 23/9/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.creeps.sl_app.quizapp.R;
import com.creeps.sl_app.quizapp.core_services.views.mtf.adapter.MtfAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by damian on 20/9/17.
 */

public class MainAdapter extends MtfAdapter {

    private List arrayList;
    private LayoutInflater inflater;
    private int layoutid;
    private boolean isDroppable;

    public MainAdapter(Context context, ArrayList data, int layout, boolean isDroppable){
        super(data);
        this.inflater=LayoutInflater.from(context);
        this.layoutid=layout;
        this.arrayList=data;
        this.isDroppable=isDroppable;
    }
    @Override
    public MainAdapter.TempViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=(View)this.inflater.inflate(this.layoutid,parent,false);
        return new TempViewHolder(view,this.isDroppable);
    }

    @Override
    public void onBindViewHolder(MtfAdapter.MtfViewHolder holder, int position) {
        ((MainAdapter.TempViewHolder)holder).textView.setText(super.getElementAt(position).toString());
        ((MainAdapter.TempViewHolder)holder).setDragData(this.arrayList.get(position).toString());
    }



    static class TempViewHolder extends MtfAdapter.MtfViewHolder {
        private TextView textView;
        private ImageView imageView;
        public TempViewHolder(View view,boolean isDroppable){
            super(view,isDroppable);
            textView=(TextView)view.findViewById(R.id.tv1);

        }
        /* necessary .... */
        public void setDragData(String x){
            super.setDragDropData(x);
        }

    }
}

