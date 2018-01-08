package com.creeps.sl_app.quizapp.core_services.views.fibview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;


/**
 * Created by damian on 20/12/17.
 * This class is a View which is to be populated given some data...
 * Dependencies/Preqequisites
 * 1. Flexbox
 *          gradle:
 *          compile 'com.google.android:flexbox:0.3.1'
 * 2. Needs constants named MainActivity.DISPLAY_WIDTH and MainActivity.DISPLAY_HEIGHT TO BE DEFINED
 *      they represent the height pixels and width pixel of the device
 */

public class FibView extends FlexboxLayout{

    private Context mContext;
    /* TEMPORARY BUTTON... TO BE REMOVED */
    private Button button;


    private final static String PATTERN_STRING="%blank%";
    private final static int PATTERN_LENGTH=PATTERN_STRING.length();
    private final static int EDIT_TEXT_HEIGHT=100,EDIT_TEXT_WIDTH=200;
    private final static RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(EDIT_TEXT_WIDTH,EDIT_TEXT_HEIGHT);
    private final static String TAG="FIB_VIEW";
    private final static int CHAR_PIXELS=8;
    public FibView(Context context, AttributeSet att){
        super(context,att);

        this.mContext=context;
        this.button=new Button(mContext);
        this.setFlexDirection(FlexDirection.ROW);
        this.setFlexWrap(FlexWrap.WRAP);
        this.setAlignContent(AlignContent.FLEX_START);
    }
    /*
    * @params text- text to be set to TextView
    * */
    private void addTextView(String text){
        if(text==null || text.length()==0)
            return;

        //int remainingSpace= MainActivity.DISPLAY_WIDTH-EDIT_TEXT_WIDTH;
        //int maxCharInSpace= remainingSpace/CHAR_PIXELS;
        /* TODO calculate remaining space and then put in another text view with remainder part of the text. It is assumed that evry characeter occupies about 8 pixels
        * on the screen .*/
        TextView textView=new TextView(this.mContext);
        textView.setText(text);
        Log.d(TAG,"text "+text);

        this.addView(textView);

    }

    /*
    * @params text - the text to be set to the edit text
    * */
    private void addEditText(String text){
        Log.d(TAG,"added EditText");
        EditText editText=new EditText(this.mContext);

        if(text!=null)
            editText.setText(text);

        editText.setLayoutParams(layoutParams);


        this.addView(editText);
    }


    /*
    * this function actually inits and populates the view. To be called whenever the view must be repopulated with new text or with answers.
    * @param content- The string to populate the textviews with.
    * @param answers[]- This array of Strings contains the answers that the user previously gave. Pass in null if you dont want to use it. It just
    * populates the edit texts with values.
    * */
    public void setContentString(String content,String answers[]){
        this.removeAllViews();
        content=content.trim();
        int i=0,answerCounter=0,contentLen=content.length() /*-1-PATTERN_LENGTH  */,lastSubstringPosition=0;

        while( (i= content.indexOf(PATTERN_STRING,lastSubstringPosition)) >=0 ){
            this.addTextView(content.substring(lastSubstringPosition,i));
            this.addEditText(answers==null?null:answers[answerCounter++]);
            lastSubstringPosition=i+PATTERN_LENGTH;

        }
        Log.d(TAG,"content len "+contentLen);
        Log.d(TAG,"lastSub "+lastSubstringPosition);
        if(lastSubstringPosition < contentLen )
            this.addTextView(content.substring(lastSubstringPosition));


    }





}
