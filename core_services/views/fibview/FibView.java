package com.creeps.sl_app.quizapp.core_services.views.fibview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.creeps.sl_app.quizapp.MainActivity;
import com.creeps.sl_app.quizapp.core_services.utils.modal.An;
import com.creeps.sl_app.quizapp.core_services.views.TestControllerDataSource;
import com.creeps.sl_app.quizapp.core_services.views.answer_collection.AnswerCollectorCallback;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;


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

public class FibView extends FlexboxLayout implements AnswerCollectorCallback,View.OnFocusChangeListener{

    private Context mContext;
    /* TEMPORARY BUTTON... TO BE REMOVED */
    private Button button;


    private final static String PATTERN_STRING="%blank%";
    private final static int PATTERN_LENGTH=PATTERN_STRING.length();
    private final static int EDIT_TEXT_HEIGHT=100,EDIT_TEXT_WIDTH=200;
    private final static RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(EDIT_TEXT_WIDTH,EDIT_TEXT_HEIGHT);
    private final static String TAG="FIB_VIEW";
    private final static int CHAR_PIXELS=8;
    private ArrayList<String> answers;
    private List<EditText> editTexts;
    private int questionId;
    private String contentString;
    private int x=0;
    public FibView(Context context, AttributeSet att){
        super(context,att);
        this.mContext=context;
        this.button=new Button(mContext);
        this.setFlexDirection(FlexDirection.ROW);
        this.setFlexWrap(FlexWrap.WRAP);
        this.setAlignContent(AlignContent.FLEX_START);
        this.setOnFocusChangeListener(this);
        this.answers=new ArrayList<>();
    }
    /*
    * @params text- text to be set to TextView
    * */
    private void addTextView(String text){
        if(text==null || text.length()==0)
            return;

        //int remainingSpace= MainActivity.DISPLAY_WIDTH-EDIT_TEXT_WIDTH;
        //int maxCharInSpace= remainingSpace/CHAR_PIXELS;
        /* TODO calculate remaining space and then put in another text view with remainder part of the text. It is assumed that every character occupies about 8 pixels
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
        x++;
        if(text!=null)
            editText.setText(text);

        editText.setLayoutParams(layoutParams);
        editText.addTextChangedListener(new MyTextWatcher(x));
        this.editTexts.add(editText);



        this.addView(editText);
    }


    private class MyTextWatcher implements TextWatcher{
        int index;
        String x;
        public MyTextWatcher(int index){
            this.index=index;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            x=s.toString();
            FibView.this.answers.add(index,x);
        }

        @Override
        public void afterTextChanged(Editable s) {
            An an=TestControllerDataSource.getInstance(FibView.this.getContext()).getTestAnswers().getAns(FibView.this.questionId);
            an.setAnswers(FibView.this.answers);
        }
    }





    /*
    * this function actually inits and populates the view. To be called whenever the view must be repopulated with new text or with answers.
    * @param content- The string to populate the textviews with.
    * @param answers[]- This array of Strings contains the answers that the user previously gave. Pass in null if you dont want to use it. It just
    * populates the edit texts with values.
    * */
    public void setContentString(String content,String answers[],int questionId){
        this.editTexts=new ArrayList<>();
        this.questionId=questionId;
        this.removeAllViews();
        content=content.trim();
        this.contentString=content;
        int i=0,answerCounter=0,contentLen=content.length() /*-1-PATTERN_LENGTH  */,lastSubstringPosition=0;

        while( (i= content.indexOf(PATTERN_STRING,lastSubstringPosition)) >=0 ){
            this.addTextView(content.substring(lastSubstringPosition,i));
            this.addEditText(answers==null?null:answers[answerCounter++]);
            lastSubstringPosition=i+PATTERN_LENGTH;
            this.answers.add(null);

        }
        Log.d(TAG,"content len "+contentLen);
        Log.d(TAG,"lastSub "+lastSubstringPosition);
        if(lastSubstringPosition < contentLen )
            this.addTextView(content.substring(lastSubstringPosition));


    }


    /*Overriding AnswerCollectorCallback
    Callback fired by adapter whenever the fragments are switched*/
    @Override
    public ArrayList<String> getAnswers(){
        return this.addToDataSource();
    }


    private void setAnswers(ArrayList answers){
        ArrayList<String> ac=(ArrayList<String>)answers;
        String arr[]=(String[])ac.toArray();
        this.setContentString(this.contentString,arr,this.questionId);

    }
    @Override
    public int getQuestionId(){return this.questionId;}


    /* Overriding focus change callbacks*/
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.d(TAG,"viewGainedFocus");

    }

    private ArrayList<String> addToDataSource(){
        An current=TestControllerDataSource.getInstance(this.getContext()).getTestAnswers().getAns(this.questionId);

        ArrayList<String> answers=new ArrayList<>();
        for(EditText ed:this.editTexts)
            answers.add(ed.getText().toString());

        current.setAnswers(answers);
        return answers;
    }
}
