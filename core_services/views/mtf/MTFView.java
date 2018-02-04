package com.creeps.sl_app.quizapp.core_services.views.mtf;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.creeps.sl_app.quizapp.R;
import com.creeps.sl_app.quizapp.core_services.utils.modal.An;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Option;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Question;
import com.creeps.sl_app.quizapp.core_services.utils.modal.QuestionStmt;
import com.creeps.sl_app.quizapp.core_services.views.TestController;
import com.creeps.sl_app.quizapp.core_services.views.TestControllerDataSource;
import com.creeps.sl_app.quizapp.core_services.views.answer_collection.AnswerCollectorCallback;
import com.creeps.sl_app.quizapp.core_services.views.mtf.adapter.MtfAdapter;
import com.creeps.sl_app.quizapp.core_services.views.mtf.listeners.DataPairsHandler;
import com.creeps.sl_app.quizapp.core_services.views.mtf.listeners.DropListener;
import com.creeps.sl_app.quizapp.core_services.views.mtf.listeners.PairMaker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by damian on 17/9/17.
 *
 */

public class MTFView extends RelativeLayout implements BottomBarCallback,PairMaker,AnswerCollectorCallback,View.OnFocusChangeListener,Comparator<DataPairs<QuestionStmt,Option>>{

    private Context mContext;
    private RecyclerView questionRecycler,answerRecycler;
    private final String TAG="MTFView";
    private LinearLayout left,right,bottom;
    private final int initWidth=200;
    private final int initHeight=250;
    private ArrayList<DataPairs<QuestionStmt,Option>> dataPairsArrayList;
    private MtfAdapter mAdapter1,mAdapter2;
    private Button button;

    private BottomBarAdapter mBottomBarAdapter;

    private final int SCREEN_HEIGHT,SCREEN_WIDTH;
    private final static double BUTTON_HEIGHT_PERCENTAGE=0.2,BUTTON_WIDTH_PERCENTAGE=0.2;



    private int questionId;

    public MTFView(Context context){
        this(context,null);

    }
    public MTFView(Context context, AttributeSet attr){
        this(context,attr,0);
    }
    public MTFView(Context context,AttributeSet attr,int defStyle){
        super(context,attr,defStyle);
        this.mContext=context;
        DisplayMetrics displayMetrics=new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.SCREEN_HEIGHT=displayMetrics.heightPixels;
        this.SCREEN_WIDTH=displayMetrics.widthPixels;
        DropListener.setmPairMaker(this);
        Log.d(TAG,"CTOR");
        this.init(context);

    }
    private void init(Context context){
        LayoutParams params;
        this.left=new LinearLayout(context);
        params=new LayoutParams(this.initWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        this.left.setId(99);
        this.left.setLayoutParams(params);


        this.right=new LinearLayout(context);
        params=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.RIGHT_OF,this.left.getId());
        this.right.setLayoutParams(params);




        this.bottom=new LinearLayout(context);
        this.bottom.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        this.bottom.setOrientation(LinearLayout.HORIZONTAL);
        params=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,this.initHeight);
        params.addRule(ALIGN_PARENT_BOTTOM);
        this.bottom.setLayoutParams(params);

        /*this.mBottomNavigationBar=new BottomNavigationView(context);
        this.mBottomNavigationBar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));;
        this.mBottomNavigationBar.setLayoutParams(new RelativeLayout.LayoutParams(this.initHeight, ViewGroup.LayoutParams.MATCH_PARENT));*/


        this.addView(left);
        this.addView(right);
        this.addView(bottom);


        RecyclerView rec=new RecyclerView(context);
        this.dataPairsArrayList=new ArrayList<>();
        this.mBottomBarAdapter=new BottomBarAdapter(context,dataPairsArrayList,this);
        rec.setLayoutManager(new LinearLayoutManager(context,LinearLayout.HORIZONTAL,false));
        rec.setAdapter(this.mBottomBarAdapter);




        /* set custom attributes with TypedArrays*/
        this.button=new Button(context);
        this.setClickListeners();
        this.button.setHeight((int)(this.SCREEN_HEIGHT*BUTTON_HEIGHT_PERCENTAGE));
        this.button.setWidth((int)(this.SCREEN_WIDTH*BUTTON_WIDTH_PERCENTAGE));
        this.button.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_clear_black_24dp));
        this.bottom.addView(this.button);
        this.bottom.addView(rec);

    }

    private void setClickListeners(){
        this.button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MTFView.this.mAdapter1.reset();
                MTFView.this.mAdapter2.reset();
                MTFView.this.mBottomBarAdapter.reset();
            }
        });


        this.button.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext, "will reset your choices", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }





    public void setQuestionRecycler(RecyclerView recycler,MtfAdapter adapter,int questionId){
        this.questionId=questionId;
        this.questionRecycler=recycler;
        this.questionRecycler.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.questionRecycler.setAdapter(adapter);
        this.mAdapter1=adapter;


        //TODO ADD THE RECYLERVIEW TO THE MAINVIEW
        this.left.addView(this.questionRecycler);


    }

    public void setAnswerRecycler(RecyclerView recycler,MtfAdapter adapter){
        this.answerRecycler=recycler;
        this.answerRecycler.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.answerRecycler.setAdapter(adapter);
        this.mAdapter2=adapter;
        //TODO ADD THE RECYLERVIEW TO THE MAINVIEW

        this.right.addView(answerRecycler);


    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"in on measure width "+widthMeasureSpec+" height "+heightMeasureSpec);
    }

    private void removeFromBothAdapters(int ad1,int ad2) {
        this.mAdapter1.removeItem(ad1);
        this.mAdapter2.removeItem(ad2);
    }

    @Override
    public void unpair(Object x,Object y){
        /* add these elements back into the list of each adapter*/
        this.mAdapter1.addToList(x);
        this.mAdapter2.addToList(y);
    }


    /* methods of PairMaker*/
    @Override
    public void makePairs(View v,View v2,int pos1,int pos2){
        DataPairs <QuestionStmt,Option>dataPairs=new DataPairs(this.mAdapter1.getElementAt(pos1),this.mAdapter2.getElementAt(pos2),
                v.getTag().toString(),v2.getTag().toString(),null);
        Log.d(TAG,"paired "+dataPairs.getKeyData().toString()+" "+dataPairs.getPairedData().toString());
        this.dataPairsArrayList.add(dataPairs);
        this.mBottomBarAdapter.notifyDataSetChanged();
        this.removeFromBothAdapters(pos1,pos2);
        An an=TestControllerDataSource.getInstance(this.getContext()).getTestAnswers().getAns(this.questionId);
        this.dataPairsArrayList.sort(this);
        ArrayList<String> arr=new ArrayList<>();
        for(DataPairs dp:this.dataPairsArrayList)
            arr.add(((Option)dp.getDropData()).getOptionId()+"");

        an.setAnswers(arr);
    }





    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.d(TAG,"focusChanged");
        /*if(hasFocus) {

        }else{
            //lost focus
            TestControllerDataSource dataSource=TestControllerDataSource.getInstance(this.getContext());
            An current = dataSource.getTestAnswers().getAns(this.questionId);
            ArrayList<DataPairs<QuestionStmt,Option>> arr=current.getDataPairs();
            if(arr!=null || arr.size()>0) {
                this.dataPairsArrayList = arr;
                *//* Todo remove elements from adapter lists... *//*
            }
        }*/
    }

    /* overriding AnswerCollector callbacks*/

    @Override
    public ArrayList<String> getAnswers(){return this.addToDataSource();}


    private ArrayList<String> addToDataSource(){
        TestControllerDataSource dataSource = TestControllerDataSource.getInstance(this.getContext());
        An current = dataSource.getTestAnswers().getAns(this.questionId);
        ArrayList<String> arr = new ArrayList<>();
        for (DataPairs dp : dataPairsArrayList)
            arr.add(((Option) dp.getPairedData()).getOptionId() + "");
        current.setAnswers(arr);
        current.setDataPairs(this.dataPairsArrayList);
        return arr;
    }
    public void setAnswers(ArrayList answers){
        Log.d(TAG,"setAnswers AnswerCollector");
        //this.dataPairsArrayList=(ArrayList<DataPairs<QuestionStmt,>>) answers;
        /* Todo remove all elements from the arraylist that arent */
    }
    @Override
    public int getQuestionId(){return this.questionId;}


    @Override
    public int compare(DataPairs<QuestionStmt, Option> o1, DataPairs<QuestionStmt, Option> o2) {
        if(o1.getDragData().getSubId() > o2.getDragData().getSubId())
                return 1;
        else if(o1.getDragData().getSubId() == o2.getDragData().getSubId())
            return 0;
        return -1;
    }
}
