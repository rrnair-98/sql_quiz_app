package com.creeps.sl_app.quizapp.core_services.views.mcqview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import com.creeps.sl_app.quizapp.core_services.utils.ImageFetcher;
import com.creeps.sl_app.quizapp.core_services.utils.PostExecuteCallback;
import com.creeps.sl_app.quizapp.core_services.utils.modal.An;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Option;
import com.creeps.sl_app.quizapp.core_services.views.TestControllerDataSource;
import com.creeps.sl_app.quizapp.core_services.views.answer_collection.AnswerCollectorCallback;

import java.util.ArrayList;
import java.util.List;

import static android.widget.RelativeLayout.BELOW;

/**
 * Created by damian on 22/12/17.
 * Haven't maintained a reference of checkChangeCallback yet
 */

public class McqView extends ScrollView implements AnswerCollectorCallback,View.OnFocusChangeListener{
    private Context mContext;
    private final static String TAG="McqView";
    private final static RelativeLayout.LayoutParams RADIO_GROUP_LAYOUT_PARAMS=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private final RelativeLayout mScrollingHost;

    private static int RADIO_BUTTON_COUNT=222;
    private static int MCQ_VIEW_ID_COUNT=322;
    private int numChildren=1;
    private int currentlySelectedOption=-1;
    private int questionId;
    public McqView(Context context){
        this(context,null);
    }


    public McqView(Context context, AttributeSet attr){
        super(context,attr,0);
        this.mContext=context;
        this.mScrollingHost=new RelativeLayout(this.mContext);
        this.setFocusable(true);
        this.requestFocus();
        this.setOnFocusChangeListener(this);


            //this.requestFocus();


        this.setDescendantFocusability(FOCUS_BEFORE_DESCENDANTS);
        this.mScrollingHost.setOnFocusChangeListener(this);
    }



    public void initView(List<Option> options,int questionId){
        this.questionId=questionId;
        this.mScrollingHost.removeAllViews();
        McqItem m=new McqItem(options.get(0).getOptionStatement().getOptionsText()!=null?options.get(0).getOptionStatement().getOptionsText():null,options.get(0).getOptionStatement().getOptionsUrl()!=null?options.get(0).getOptionStatement().getOptionsUrl():null,options.get(0).getOptionId());
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        m.setLayoutParams(layoutParams);
        this.mScrollingHost.addView(m);
        McqItem previous=m;


        String optionText,optionURLs;
        for(int i=1;i<options.size();i++){
            optionText=options.get(i).getOptionStatement().getOptionsText();
            optionURLs=options.get(i).getOptionStatement().getOptionsUrl();
            m=new McqItem(optionText!=null?optionText:null,optionURLs!=null?optionURLs:null,options.get(i).getOptionId());
            layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(BELOW,previous.getId());
            m.setLayoutParams(layoutParams);

            previous=m;

            this.mScrollingHost.addView(m);

            Log.d(TAG,this.mScrollingHost.getChildAt(i).toString());

        }
        numChildren=options.size();


        this.addView(this.mScrollingHost);
        Log.d(TAG,"added radioGroup");

    }




    private void call(int currentId) {
        Log.d(TAG,"view which generated event has id "+currentId);
        for(int i=0;i<numChildren;i++) {
            McqItem mcqItem = (McqItem) this.mScrollingHost.getChildAt(i);
            this.currentlySelectedOption=mcqItem.getOptionId();
            Log.d(TAG," child "+mcqItem.getText()+" index "+i+ " state "+mcqItem.getRadioButtonState());
            if (mcqItem.getId() == currentId)
                mcqItem.setRadioButtonState(true);
            else
                mcqItem.setRadioButtonState(false);
        }

        Log.d(TAG,"End of listiing");
        An an=TestControllerDataSource.getInstance(this.getContext()).getTestAnswers().getAns(this.questionId);
        ArrayList<String> arr=new ArrayList<>();
        arr.add(this.currentlySelectedOption+"");
                an.setAnswers(arr);

    }



    /**
     * Created by damian on 22/12/17.
     * Range of ids for this view starts from 222... This is temporary and will be changed soon. This class is a ViewGroup containing a RadioGroup and an ImageView
     * The purpose of this is to display an imageview below the radio button if image urls are provided.
     * Remember since this is a viewgroup a custom implementation of RadioGroup will have to be made. The class that uses this ViewGroup must do so. Hence the function
     * setOnCheckChangedListener(Callback call). This function will be called whenever a RadioButton is selected. It is the responsibilty of the outer class to set the states
     * of the other views.
     */

    public class McqItem extends RelativeLayout implements OnClickListener,PostExecuteCallback,McqItemConstants{
        private RadioButton mRadioButton;
        private ImageView mImageView;
        private LayoutParams IMAGE_VIEW_LAYOUT_PARAMS;

        private int optionId;



        public McqItem(String text,String imageURL,int optionId){
            super(McqView.this.mContext);
            this.setId(MCQ_VIEW_ID_COUNT++);
            this.mRadioButton=new RadioButton(McqView.this.mContext);
            this.mRadioButton.setSelected(false);
            this.mRadioButton.setLayoutParams(RADIO_BUTTON_LAYOUT_PARAMS);
            this.mRadioButton.setOnClickListener(this);
            this.addView(this.mRadioButton);
            this.optionId=optionId;
            this.mRadioButton.setId(++RADIO_BUTTON_COUNT);

            if(text!=null)
                this.mRadioButton.setText(text);
            if(imageURL!=null) {
                this.IMAGE_VIEW_LAYOUT_PARAMS=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                this.mImageView=new ImageView(McqView.this.mContext);
                this.IMAGE_VIEW_LAYOUT_PARAMS.addRule(BELOW,RADIO_BUTTON_COUNT);
                this.mImageView.setLayoutParams(IMAGE_VIEW_LAYOUT_PARAMS);

                this.mImageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* TODO code to open a larger fullscreen zoomable version of the image... */
                    }
                });
                this.setImageView(imageURL);
            }

        }

        public int getOptionId(){return this.optionId;}



        public CharSequence getText(){return this.mRadioButton.getText();}
        private void setImageView(String url){
            new ImageFetcher(this.mImageView,this).execute(url);
        }



        public boolean getRadioButtonState(){return mRadioButton.isChecked();}
        public void setRadioButtonState(boolean state){this.mRadioButton.setChecked(state);}


        @Override
        public void postExecute(){
            this.addView(this.mImageView);
        }

        @Override
        public void onClick(View v) {
            McqView.this.call(this.getId());
        }



    }




    private interface McqItemConstants{

        public final static int RADIO_IMG_HEIGTH=100,RADIO_IMG_WIDTH=100;
        public final static RelativeLayout.LayoutParams RADIO_BUTTON_LAYOUT_PARAMS=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        public final static String TAG="McqITEM";

    }


    /* Overriding answers Collector*/
    @Override
    public ArrayList<String> getAnswers(){
        return this.addToDataSource();
    }


    @Override
    public void onFocusChanged(boolean hasFocus,int direction,Rect previous){
        super.onFocusChanged(hasFocus,direction,previous);
        Log.d(TAG,"onFocusChanged");
    }
    /* Overriding answer collector callback*/
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        /* Todo add to the datasource*/
        Log.d(TAG,"focusChanged");

    }
    private ArrayList<String> addToDataSource(){
        ArrayList<String> newList=new ArrayList<String>();

        if(this.currentlySelectedOption!=-1) {
            newList.add(this.currentlySelectedOption+"");
            TestControllerDataSource.getInstance(this.getContext()).getTestAnswers().getAns(this.questionId).setAnswers(newList);
            Log.d(TAG,"added to dataSource");
        }
        return newList;
    }


    /* Overriding AnswerCollector*/
    @Override
    public int getQuestionId(){return this.questionId;}
}
