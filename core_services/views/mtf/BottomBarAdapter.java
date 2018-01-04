package com.creeps.sl_app.quizapp.core_services.views.mtf;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.creeps.sl_app.quizapp.R;

/**
 * Created by rohan on 1/10/17.
 */

public class BottomBarAdapter extends RecyclerView.Adapter<BottomBarAdapter.BottomBarViewHolder> {
    private ArrayList<DataPairs> arrayList;
    private LayoutInflater layoutInflater;
    private BottomBarCallback mBottomBarCallback;/* for the class within which its being placed*/
    private final static String TAG="BottomBarAdapter";
    public BottomBarAdapter(Context context, ArrayList<DataPairs> dataPairses,BottomBarCallback bottomBarCallback){
        this.layoutInflater=LayoutInflater.from(context);
        this.arrayList=dataPairses;
        this.mBottomBarCallback=bottomBarCallback;
    }


    @Override
    public int getItemCount(){
        return this.arrayList.size();
    }

    @Override
    public BottomBarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG,"createdViewHolder");
        View view=this.layoutInflater.inflate(R.layout.mtf_bottom_bar_view_holder,parent,false);
        return new BottomBarViewHolder(view,this.mBottomBarCallback);
    }

    @Override
    public void onBindViewHolder(BottomBarViewHolder holder, int position) {
        Log.d(TAG,"binding viewholder");
        DataPairs dataPairs=this.arrayList.get(position);
         /* this is temporary ... will need to display the option name ... not data ... Data is too long*/
        holder.key.setText(dataPairs.getKeyData().toString());
        holder.val.setText(dataPairs.getPairedData().toString());
        Log.d(TAG,dataPairs.getKeyData().toString()+" "+dataPairs.getPairedData().toString());

    }


    public DataPairs getData(int pos){
        DataPairs dp=this.arrayList.get(pos);
        this.arrayList.remove(pos);
        this.notifyDataSetChanged();
        return dp;
    }


    public class BottomBarViewHolder extends RecyclerView.ViewHolder{
        TextView key,val;
        Button button;
        private final BottomBarCallback callback;


        public BottomBarViewHolder(View view,BottomBarCallback callback){
            super(view);
            Log.d(TAG,"in ctor of viewholder");
            key=(TextView)view.findViewById(R.id.key);
            val=(TextView)view.findViewById(R.id.val);
            button=(Button)view.findViewById(R.id.unpair);
            this.callback=callback;

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataPairs dp=BottomBarAdapter.this.getData(getAdapterPosition());
                    BottomBarViewHolder.this.callback.unpair(dp.getKeyData(),dp.getPairedData());
                }
            });
        }




    }


}
