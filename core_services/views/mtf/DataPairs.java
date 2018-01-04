package com.creeps.sl_app.quizapp.core_services.views.mtf;

/**
 * Created by rohan on 1/10/17.
 */

public class DataPairs {
    private Object keyData,pairedData;
    /* these are going to be used to populate the RecyclerView of objects*/
    private String jsonKey,jsonPair;
    public DataPairs(Object x,Object y,String jsonKey,String jsonPair){
        this.keyData=x;
        this.pairedData=y;
        this.jsonKey=jsonKey;
        this.jsonPair=jsonPair;
    }

    public String getJsonKey() {
        return this.jsonKey;
    }

    public String getJsonPair() {
        return this.jsonPair;
    }

    public Object getKeyData(){
        return this.keyData;
    }
    public Object getPairedData(){
        return this.pairedData;
    }
}
