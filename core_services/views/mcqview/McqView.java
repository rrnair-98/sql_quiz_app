package com.creeps.sl_app.quizapp.core_services.views.mcqview;

import android.content.Context;
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

import static android.widget.RelativeLayout.BELOW;

/**
 * Created by damian on 22/12/17.
 */

public class McqView extends ScrollView {
    private Context mContext;
    private final static String TAG="McqView";
    private final static RelativeLayout.LayoutParams RADIO_GROUP_LAYOUT_PARAMS=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private final RelativeLayout mScrollingHost;

    private static int RADIO_BUTTON_COUNT=222;
    private static int MCQ_VIEW_ID_COUNT=322;
    private int numChildren=1;

    public McqView(Context context){
        this(context,null);
    }


    public McqView(Context context, AttributeSet attr){
        super(context,attr,0);
        this.mContext=context;
        this.mScrollingHost=new RelativeLayout(this.mContext);
    }



    public void initView(String optionText[],String optionURLs[]){
        this.mScrollingHost.removeAllViews();
        McqItem m=new McqItem(optionText!=null?optionText[0]:null,optionURLs!=null?optionURLs[0]:null);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        m.setLayoutParams(layoutParams);
        this.mScrollingHost.addView(m);
        McqItem previous=m;



        for(int i=1;i<optionText.length;i++){
            m=new McqItem(optionText!=null?optionText[i]:null,optionURLs!=null?optionURLs[i]:null);
            layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(BELOW,previous.getId());
            m.setLayoutParams(layoutParams);

            previous=m;

            this.mScrollingHost.addView(m);

            Log.d(TAG,this.mScrollingHost.getChildAt(i).toString());

        }
        numChildren=optionText.length;


        this.addView(this.mScrollingHost);
        Log.d(TAG,"added radioGroup");

    }




    private synchronized void call(int currentId) {
        Log.d(TAG,"view which generated event has id "+currentId);
        for(int i=0;i<numChildren;i++) {
            McqItem mcqItem = (McqItem) this.mScrollingHost.getChildAt(i);
            Log.d(TAG," child "+mcqItem.getText()+" index "+i+ " state "+mcqItem.getRadioButtonState());
            if (mcqItem.getId() == currentId)
                mcqItem.setRadioButtonState(true);
            else
                mcqItem.setRadioButtonState(false);
        }
        Log.d(TAG,"End of listiing");

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





        public McqItem(String text,String imageURL){
            super(McqView.this.mContext);
            this.setId(MCQ_VIEW_ID_COUNT++);
            this.mRadioButton=new RadioButton(McqView.this.mContext);
            this.mRadioButton.setSelected(false);
            this.mRadioButton.setLayoutParams(RADIO_BUTTON_LAYOUT_PARAMS);
            this.mRadioButton.setOnClickListener(this);
            this.addView(this.mRadioButton);

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








}
