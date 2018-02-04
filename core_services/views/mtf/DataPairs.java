package com.creeps.sl_app.quizapp.core_services.views.mtf;

import com.creeps.sl_app.quizapp.core_services.utils.modal.Option;
import com.creeps.sl_app.quizapp.core_services.utils.modal.QuestionStmt;

/**
 * Created by rohan on 1/10/17.
 * This is a Wrapper for pairing Data and checking for whether or not they contain data
 */

public class DataPairs<Drag,Drop> {
    private String keyData,pairedData;
    /* these are going to be used to populate the RecyclerView of objects*/
    private Contains<Drag,Drop> containsChecker;

    /* The droppable part of mcqview*/
    private Drop mDroppable;
    /* The draggable part of mcqdata*/
    private Drag mDraggable;

    public DataPairs(Drag draggable,Drop droppable,String dragData,String dropData,Contains<Drag,Drop> containsChecker){
        this.mDraggable=draggable;
        this.mDroppable=droppable;
        this.keyData=dragData;
        this.pairedData=dropData;
        this.containsChecker=containsChecker;
    }
    public boolean containsDrag(Drag dragData){
        if(this.containsChecker!=null)
            return containsChecker.containsDrag(dragData);
        return this.mDraggable.equals(dragData);
    }
    public boolean containsDrop(Drop dropData){
        if(this.containsChecker!=null)
            return containsChecker.containsDrop(dropData);
        return this.mDroppable.equals(dropData);
    }
    public Object getKeyData(){
        return this.keyData;
    }
    public Object getPairedData(){
        return this.pairedData;
    }
    public Drag getDragData(){return this.mDraggable;}
    public Drop getDropData(){return this.mDroppable;}
    interface Contains<T,K>{
        public boolean containsDrag(T dragData);
        public boolean containsDrop(K dropData);
    }

}
