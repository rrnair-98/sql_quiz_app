package com.creeps.sl_app.quizapp.core_services.views.mtf.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.creeps.sl_app.quizapp.core_services.views.mtf.listeners.DropListener;
import com.creeps.sl_app.quizapp.core_services.views.mtf.listeners.LongPressListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by damian on 23/9/17.
 */

public abstract class MtfAdapter extends RecyclerView.Adapter<MtfAdapter.MtfViewHolder> {

    /* to be used for watching List items ie whenever the drop event is fired ... To be extended by the  draggable adapter only...*/
    /* will need a reference of the other adapter. */
    private static final String TAG="MtfAdapter";
    private ArrayList<Object> mainData,tempData;
    public MtfAdapter(final ArrayList data){
        this.tempData=new ArrayList();
        this.tempData.addAll(data);
        this.mainData=data;

    }

    public void reset(){
        this.tempData.clear();
        this.tempData.addAll(this.mainData);
        this.notifyDataSetChanged();
    }
    public void addToList(Object x){
        this.tempData.add(x);this.notifyDataSetChanged();
    }



    public ArrayList getList(){
        return this.mainData;
    }


    final public void removeItem(int pos){
        Log.d(TAG,"receieved pos "+pos);
        if(pos!=-1) {
            this.tempData.remove(pos);
            this.notifyDataSetChanged();
        }
    }

    /* to be used by base class ... */
    final public Object getElementAt(int index){
        return this.tempData.get(index);
    }
    @Override
    final public int getItemCount(){
        return this.tempData.size();
    }






     public static abstract class MtfViewHolder extends RecyclerView.ViewHolder{
        private View view;
        private final static String TAG="MtfViewHolder";

        /* If is isDraggable is true a LongPressListener is attatched to the viewHolder. The LongPressListener gives a call to startDrag
        * thus making it draggable. The viewHolder is attatched to a DropListener by defualt so that it can be dropped*/
        public MtfViewHolder(View view,boolean isDroppable){
            super(view);
            this.view=view;
            Log.d(TAG,"setting listeners");
            if(isDroppable)
                view.setOnDragListener(new DropListener(this));
            else
                view.setOnLongClickListener(new LongPressListener(this));

        }

        public final void setDragDropData(String x){
            this.view.setTag(x);

        }


    }








}
