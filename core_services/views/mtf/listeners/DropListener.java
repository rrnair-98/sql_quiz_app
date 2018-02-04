package com.creeps.sl_app.quizapp.core_services.views.mtf.listeners;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import com.creeps.sl_app.quizapp.core_services.views.mtf.adapter.MtfAdapter;


/**
 * Created by damian on 8/9/17.
 * This makes a view listen to dragEvents . For any view that doesnt want to listen to dropEvent just pass in false to the ctor;
 * You can also set this with the help of the public setter.
 * By default all views attached to this DropListener are Droppable.
 */

public class DropListener implements View.OnDragListener {

    /*if droppable is set the view is droppable ... ie it listens to dropEvent*/
    /* cant use getAdapterPosition as a param to the ctor at the time of making since the view hasnt been painted yet.. hence it returns -1... will have to use the ref
    * of the obj...*/
    /* need viewHolder for adapter position. Hence not using PairMaker*/
    private MtfAdapter.MtfViewHolder viewHolder;
    private static PairMaker mPairMaker;



    public DropListener( MtfAdapter.MtfViewHolder viewHolder){
        log("ctor droplistener ");
        this.viewHolder=viewHolder;

    }
    public static void setmPairMaker(PairMaker pairMaker){
        mPairMaker=pairMaker;
    }



    @Override
    public boolean onDrag(View viewDroppedUpon , DragEvent de){
        /* handling dragEvents here */


        log("adapter pos"+this.viewHolder.getAdapterPosition());
        switch (de.getAction()){

            case DragEvent.ACTION_DRAG_STARTED:
                //log("StartingDrag"+DragEvent.ACTION_DRAG_STARTED);
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                //log("Ending Drag"+DragEvent.ACTION_DRAG_ENDED);
                return true;

            case DragEvent.ACTION_DRAG_ENTERED:
                //log("Drag entered "+DragEvent.ACTION_DRAG_ENTERED);
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                //log("Drag ended "+DragEvent.ACTION_DRAG_ENDED);

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                //log("Drag location "+DragEvent.ACTION_DRAG_LOCATION);
                return true;

            case DragEvent.ACTION_DROP:
                //log("droppable is "+this.droppable);

                    log("Action dropped " + DragEvent.ACTION_DROP);

                    View dropped=(View)de.getLocalState();


                    log("viewDroppedUpon tag "+viewDroppedUpon.getTag());
                    log("dropped tag "+dropped.getTag());
                    int pos=this.viewHolder.getAdapterPosition();



                    mPairMaker.makePairs(dropped,viewDroppedUpon,pos,LongPressListener.getPosition());





                return true;
        }



        return false;
    }


    @Override
    public void finalize()throws Throwable{
        super.finalize();
    }

    private void log(String x){
        Log.d("DropListener",x);
    }

}
