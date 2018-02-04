package com.creeps.sl_app.quizapp.core_services.views.mtf.listeners;

import android.content.Context;
import android.util.SparseArray;
import com.creeps.sl_app.quizapp.core_services.views.mtf.DataPairs;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rohan on 31/1/18.
 * A singleton for handling multiple MTF view DataPairs Lists
 */
public class DataPairsHandler {
    private SparseArray<ArrayList> mMap;
    private static DataPairsHandler mDataPairsHandler;
    private Context mContext;

    private DataPairsHandler(Context ctx){
        this.mContext=ctx;
        this.mMap=new SparseArray<>();
    }
    public static DataPairsHandler getInstance(Context context){
        return mDataPairsHandler= mDataPairsHandler==null?new DataPairsHandler(context):mDataPairsHandler;
    }
    /* Stores a list in the SparseArray.
    * @param key- The key to the list
    * @param arr- The ArrayList that stores DataPairs*/
    public void addDataPairsList(int key,ArrayList arr){
        this.mMap.put(key,arr);
    }
    /* Returns a preexisting list mapped to the key. Returns null if the key doesnt map to any list
    *@param key- The key to the list*/
    public ArrayList getList(int key){
        return this.mMap.get(key);
    }
    public void clear(){
        this.mMap.clear();
    }
}
