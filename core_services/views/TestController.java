package com.creeps.sl_app.quizapp.core_services.views;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.creeps.sl_app.quizapp.R;
import com.creeps.sl_app.quizapp.core_services.networking.RetrofitApiClient;
import com.creeps.sl_app.quizapp.core_services.networking.Reverberator;
import com.creeps.sl_app.quizapp.core_services.utils.SharedPreferenceHandler;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Chapter;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Question;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Test;
import com.creeps.sl_app.quizapp.core_services.views.fibview.FibView;
import com.creeps.sl_app.quizapp.core_services.views.mcqview.McqView;
import com.creeps.sl_app.quizapp.core_services.views.mtf.MTFView;
import com.creeps.sl_app.quizapp.core_services.views.mtf.MainAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by rohan on 24/12/17.
 * This class will receive all jsons and will add/remove other child views included in this package.
 */

public class TestController extends RelativeLayout implements SharedPreferenceHandler.SharedPreferenceHandlerConstants,Reverberator,View.OnClickListener{

    /* TODO While generating the answers for mtf add the option.sub_id not option.id
    * */
    private Context mContext;
    private final static String TAG="TestController";
    private final static int MARKS=100;
    private final static long DURATION=10;
    private final static int MCQ=1,FIB=2,MTF=3;
    private Test currentTest;
    private View currentView;
    private Button mButton;
    private RelativeLayout mTestViewHolder;
    private ListIterator listIterator;

    private final static int BUTTON_ID=455;
    private static int MAX_SCREEN_LEN=0,MAX_SCREEN_WIDTH=0;
    private static double BUTTON_HEIGHT=.1;
    public TestController(Context context){
        this(context,null);
    }
    public TestController(Context context,AttributeSet attr){
        this(context,attr,0);
    }
    public TestController(Context context, AttributeSet attr,int options){
        super(context,attr,options);
        Log.d(TAG,"HERE");
        this.mContext=context;
        if(MAX_SCREEN_LEN==0) {
            DisplayMetrics displayMetrics=new DisplayMetrics();
            ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            MAX_SCREEN_LEN=displayMetrics.heightPixels;
            MAX_SCREEN_WIDTH=displayMetrics.widthPixels;
        }




        this.mButton=new Button(this.mContext);
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)(MAX_SCREEN_LEN*BUTTON_HEIGHT));
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        this.mButton.setLayoutParams(lp);
        this.mButton.setId(BUTTON_ID);
        this.addView(this.mButton);

        this.mTestViewHolder=new RelativeLayout(this.mContext);
        RelativeLayout.LayoutParams testViewHolderLayoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        testViewHolderLayoutParams.addRule(RelativeLayout.ABOVE,this.mButton.getId());
        this.mTestViewHolder.setLayoutParams(testViewHolderLayoutParams);
        this.addView(mTestViewHolder);
        this.mButton.setOnClickListener(this);
        Log.d(TAG,"done with ctor");
    }
    /*
    * this function fetches the test Data and initializes the test
    * */
    public void initRetrofit(List<Chapter> chapters){
        RetrofitApiClient.getInstance(this.mContext).initiateTest(SharedPreferenceHandler.getInstance(this.mContext).get(SharedPreferenceHandler.SharedPreferenceHandlerConstants.USER_ID),chapters
                ,DURATION,MARKS,this);
    }

    private List<Chapter> getChaptersFromSharedPref(){
        List list;
        String chapters=SharedPreferenceHandler.getInstance(this.mContext).get(USER_CHAPTERS);
        Type listType = new TypeToken<List<Chapter>>() {
        }.getType();
        list= new Gson().fromJson(chapters,listType);
        Log.d(TAG,list.toString());
        return list;

    }


    @Override
    public void reverb(Object obj){
        Log.d(TAG,"reverb");
        if(obj!=null){
            /* Received entire test data
            * Time to populate the view
            * */
            this.currentTest=(Test)obj;
            this.startTest();

            this.listIterator=currentTest.getQuestions().listIterator();
        }
        /* must remove ....*/
        else{
            Log.d(TAG,"starting fake");
            String x="{\n" +
                    "\t\"test_id\": 1102,\n" +
                    "\t\"test_title\": \"Test #\",\n" +
                    "\t\"test_marks\": 7,\n" +
                    "\t\"questions\": [{\n" +
                    "\t\t\"question_id\": 31821,\n" +
                    "\t\t\"question_level\": 2,\n" +
                    "\t\t\"question_statement\": {\n" +
                    "\t\t\t\"major_stmt\": \"Match the following\",\n" +
                    "\t\t\t\"question_stmt\": [{\n" +
                    "\t\t\t\t\"sub_id\": \"3\",\n" +
                    "\t\t\t\t\"text\": \"sub-Question 3\",\n" +
                    "\t\t\t\t\"text_image\": \"\"\n" +
                    "\t\t\t}, {\n" +
                    "\t\t\t\t\"sub_id\": \"1\",\n" +
                    "\t\t\t\t\"text\": \"sub-Question 1\",\n" +
                    "\t\t\t\t\"text_image\": \"\"\n" +
                    "\t\t\t}, {\n" +
                    "\t\t\t\t\"sub_id\": \"2\",\n" +
                    "\t\t\t\t\"text\": \"sub-Question 2\",\n" +
                    "\t\t\t\t\"text_image\": \"\"\n" +
                    "\t\t\t}, {\n" +
                    "\t\t\t\t\"sub_id\": \"4\",\n" +
                    "\t\t\t\t\"text\": \"sub-Question 4\",\n" +
                    "\t\t\t\t\"text_image\": \"\"\n" +
                    "\t\t\t}]\n" +
                    "\t\t},\n" +
                    "\t\t\"question_marks\": 2,\n" +
                    "\t\t\"question_type\": 3,\n" +
                    "\t\t\"options\": [{\n" +
                    "\t\t\t\"option_id\": 41661,\n" +
                    "\t\t\t\"option_statement\": {\n" +
                    "\t\t\t\t\"sub_id\": \"1\",\n" +
                    "\t\t\t\t\"options_url\": \"\",\n" +
                    "\t\t\t\t\"options_text\": \"option 1\"\n" +
                    "\t\t\t}\n" +
                    "\t\t}, {\n" +
                    "\t\t\t\"option_id\": 41663,\n" +
                    "\t\t\t\"option_statement\": {\n" +
                    "\t\t\t\t\"sub_id\": \"3\",\n" +
                    "\t\t\t\t\"options_url\": \"\",\n" +
                    "\t\t\t\t\"options_text\": \"option 3\"\n" +
                    "\t\t\t}\n" +
                    "\t\t}, {\n" +
                    "\t\t\t\"option_id\": 41662,\n" +
                    "\t\t\t\"option_statement\": {\n" +
                    "\t\t\t\t\"sub_id\": \"2\",\n" +
                    "\t\t\t\t\"options_url\": \"\",\n" +
                    "\t\t\t\t\"options_text\": \"option 2\"\n" +
                    "\t\t\t}\n" +
                    "\t\t}, {\n" +
                    "\t\t\t\"option_id\": 41664,\n" +
                    "\t\t\t\"option_statement\": {\n" +
                    "\t\t\t\t\"sub_id\": \"4\",\n" +
                    "\t\t\t\t\"options_url\": \"\",\n" +
                    "\t\t\t\t\"options_text\": \"option 4\"\n" +
                    "\t\t\t}\n" +
                    "\t\t}]\n" +
                    "\t}, {\n" +
                    "\t\t\"question_id\": 34021,\n" +
                    "\t\t\"question_level\": 2,\n" +
                    "\t\t\"question_statement\": {\n" +
                    "\t\t\t\"major_stmt\": \"Match the following\",\n" +
                    "\t\t\t\"question_stmt\": [{\n" +
                    "\t\t\t\t\"sub_id\": \"1\",\n" +
                    "\t\t\t\t\"text\": \"sub-Question 1\",\n" +
                    "\t\t\t\t\"text_image\": \"\"\n" +
                    "\t\t\t}, {\n" +
                    "\t\t\t\t\"sub_id\": \"3\",\n" +
                    "\t\t\t\t\"text\": \"sub-Question 3\",\n" +
                    "\t\t\t\t\"text_image\": \"\"\n" +
                    "\t\t\t}, {\n" +
                    "\t\t\t\t\"sub_id\": \"4\",\n" +
                    "\t\t\t\t\"text\": \"sub-Question 4\",\n" +
                    "\t\t\t\t\"text_image\": \"\"\n" +
                    "\t\t\t}, {\n" +
                    "\t\t\t\t\"sub_id\": \"2\",\n" +
                    "\t\t\t\t\"text\": \"sub-Question 2\",\n" +
                    "\t\t\t\t\"text_image\": \"\"\n" +
                    "\t\t\t}]\n" +
                    "\t\t},\n" +
                    "\t\t\"question_marks\": 2,\n" +
                    "\t\t\"question_type\": 3,\n" +
                    "\t\t\"options\": [{\n" +
                    "\t\t\t\"option_id\": 52663,\n" +
                    "\t\t\t\"option_statement\": {\n" +
                    "\t\t\t\t\"sub_id\": \"3\",\n" +
                    "\t\t\t\t\"options_url\": \"\",\n" +
                    "\t\t\t\t\"options_text\": \"option 3\"\n" +
                    "\t\t\t}\n" +
                    "\t\t}, {\n" +
                    "\t\t\t\"option_id\": 52661,\n" +
                    "\t\t\t\"option_statement\": {\n" +
                    "\t\t\t\t\"sub_id\": \"1\",\n" +
                    "\t\t\t\t\"options_url\": \"\",\n" +
                    "\t\t\t\t\"options_text\": \"option 1\"\n" +
                    "\t\t\t}\n" +
                    "\t\t}, {\n" +
                    "\t\t\t\"option_id\": 52664,\n" +
                    "\t\t\t\"option_statement\": {\n" +
                    "\t\t\t\t\"sub_id\": \"4\",\n" +
                    "\t\t\t\t\"options_url\": \"\",\n" +
                    "\t\t\t\t\"options_text\": \"option 4\"\n" +
                    "\t\t\t}\n" +
                    "\t\t}, {\n" +
                    "\t\t\t\"option_id\": 52662,\n" +
                    "\t\t\t\"option_statement\": {\n" +
                    "\t\t\t\t\"sub_id\": \"2\",\n" +
                    "\t\t\t\t\"options_url\": \"\",\n" +
                    "\t\t\t\t\"options_text\": \"option 2\"\n" +
                    "\t\t\t}\n" +
                    "\t\t}]\n" +
                    "\t}, {\n" +
                    "\t\t\"question_id\": 35332,\n" +
                    "\t\t\"question_level\": 2,\n" +
                    "\t\t\"question_statement\": {\n" +
                    "\t\t\t\"major_stmt\": \"Match the following\",\n" +
                    "\t\t\t\"question_stmt\": [{\n" +
                    "\t\t\t\t\"sub_id\": \"1\",\n" +
                    "\t\t\t\t\"text\": \"sub-Question 1\",\n" +
                    "\t\t\t\t\"text_image\": \"\"\n" +
                    "\t\t\t}, {\n" +
                    "\t\t\t\t\"sub_id\": \"4\",\n" +
                    "\t\t\t\t\"text\": \"sub-Question 4\",\n" +
                    "\t\t\t\t\"text_image\": \"\"\n" +
                    "\t\t\t}, {\n" +
                    "\t\t\t\t\"sub_id\": \"3\",\n" +
                    "\t\t\t\t\"text\": \"sub-Question 3\",\n" +
                    "\t\t\t\t\"text_image\": \"\"\n" +
                    "\t\t\t}, {\n" +
                    "\t\t\t\t\"sub_id\": \"2\",\n" +
                    "\t\t\t\t\"text\": \"sub-Question 2\",\n" +
                    "\t\t\t\t\"text_image\": \"\"\n" +
                    "\t\t\t}]\n" +
                    "\t\t},\n" +
                    "\t\t\"question_marks\": 2,\n" +
                    "\t\t\"question_type\": 3,\n" +
                    "\t\t\"options\": [{\n" +
                    "\t\t\t\"option_id\": 59218,\n" +
                    "\t\t\t\"option_statement\": {\n" +
                    "\t\t\t\t\"sub_id\": \"3\",\n" +
                    "\t\t\t\t\"options_url\": \"\",\n" +
                    "\t\t\t\t\"options_text\": \"option 3\"\n" +
                    "\t\t\t}\n" +
                    "\t\t}, {\n" +
                    "\t\t\t\"option_id\": 59217,\n" +
                    "\t\t\t\"option_statement\": {\n" +
                    "\t\t\t\t\"sub_id\": \"2\",\n" +
                    "\t\t\t\t\"options_url\": \"\",\n" +
                    "\t\t\t\t\"options_text\": \"option 2\"\n" +
                    "\t\t\t}\n" +
                    "\t\t}, {\n" +
                    "\t\t\t\"option_id\": 59219,\n" +
                    "\t\t\t\"option_statement\": {\n" +
                    "\t\t\t\t\"sub_id\": \"4\",\n" +
                    "\t\t\t\t\"options_url\": \"\",\n" +
                    "\t\t\t\t\"options_text\": \"option 4\"\n" +
                    "\t\t\t}\n" +
                    "\t\t}, {\n" +
                    "\t\t\t\"option_id\": 59216,\n" +
                    "\t\t\t\"option_statement\": {\n" +
                    "\t\t\t\t\"sub_id\": \"1\",\n" +
                    "\t\t\t\t\"options_url\": \"\",\n" +
                    "\t\t\t\t\"options_text\": \"option 1\"\n" +
                    "\t\t\t}\n" +
                    "\t\t}]\n" +
                    "\t}, {\n" +
                    "\t\t\"question_id\": 41922,\n" +
                    "\t\t\"question_level\": 2,\n" +
                    "\t\t\"question_statement\": {\n" +
                    "\t\t\t\"major_stmt\": \"Fill in the blanks with proper articles\",\n" +
                    "\t\t\t\"question_stmt\": [{\n" +
                    "\t\t\t\t\"text\": \"%blank% dog came running towards Cynthia. She got scared looking at %blank% dog\",\n" +
                    "\t\t\t\t\"text_image\": \"\"\n" +
                    "\t\t\t}]\n" +
                    "\t\t},\n" +
                    "\t\t\"question_marks\": 1,\n" +
                    "\t\t\"question_type\": 2,\n" +
                    "\t\t\"options\": []\n" +
                    "\t}]\n" +
                    "}";
            Type questionType = new TypeToken<Test>() {
            }.getType();
            this.currentTest=new Gson().fromJson(x,questionType);
            Log.d(TAG,this.currentTest.toString());
            this.listIterator=this.currentTest.getQuestions().listIterator();

            this.startTest();

        }


    }






    private void startTest(){
        this.anaylyzeQuestion((Question) this.listIterator.next());
    }

    private void anaylyzeQuestion(Question question){
        int type = question.getQuestionType();
        Log.d(TAG,"question is "+question.toString());
        View view=null;
        switch (type) {
            case MCQ:
                McqView mcqView = new McqView(this.mContext);
                mcqView.initView(question.getOptions());
                view=mcqView;
                //this.addView(mcqView);
                this.mTestViewHolder.removeAllViewsInLayout();
                this.mTestViewHolder.addView(mcqView);
                break;

            case FIB:
                FibView fibView = new FibView(this.mContext, null);
                fibView.setContentString(question.getQuestionStmt().getQuestionStmt().get(0).getText(), null);
                //this.addView(fibView);
                view=fibView;
                this.mTestViewHolder.removeAllViewsInLayout();
                this.mTestViewHolder.addView(fibView);
                break;

            case MTF:
                MTFView mtfView = new MTFView(this.mContext);
                RecyclerView r1 = new RecyclerView(this.mContext), r2 = new RecyclerView(this.mContext);
                MainAdapter m1 = new MainAdapter(this.mContext, question.getQuestionStmt().getQuestionStmt(), R.layout.test_mtf_viewholder, true), m2 = new MainAdapter(this.mContext, question.getOptions(), R.layout.test_mtf_viewholder, false);
                mtfView.setQuestionRecycler(r1, m1);
                mtfView.setAnswerRecycler(r2, m2);
                view=mtfView;
                this.mTestViewHolder.removeAllViewsInLayout();
                this.mTestViewHolder.addView(mtfView);
        }

    }

    private void next(){

        if(this.listIterator.hasNext()) {
            Question question=(Question) this.listIterator.next();
            this.anaylyzeQuestion(question);

        }else{
            Toast.makeText(this.mContext, "Ended", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view){
        this.next();
    }





}
