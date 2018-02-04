package com.creeps.sl_app.quizapp.core_services.views;

import android.content.Context;

import com.creeps.sl_app.quizapp.core_services.utils.modal.Question;
import com.creeps.sl_app.quizapp.core_services.utils.modal.TestAnswer;
import com.creeps.sl_app.quizapp.core_services.views.mtf.DataPairs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rohan on 14/1/18.
 * Just a singleton to hold data
 */

public class TestControllerDataSource {
    private Context mContext;
    private ArrayList<Question> questions;
    private TestAnswer mTestAnswer;
    private static TestControllerDataSource mDataSource;

    private TestControllerDataSource(Context context){
        this(context,null,null);
    }
    private TestControllerDataSource(Context context,ArrayList<Question> questions,TestAnswer testAnswer){
        this.mContext=context;
        this.questions=questions;
        this.mTestAnswer=testAnswer;
    }
    public static TestControllerDataSource getInstance(Context context){
        return mDataSource= mDataSource==null?new TestControllerDataSource(context):mDataSource;
    }
    public void setQuestions(ArrayList<Question> questions){
        this.questions=questions;
    }

    public ArrayList getQuestions(){
        return mDataSource.questions;
    }
    public void setTestAnswers(TestAnswer testAnswers){
        this.mTestAnswer=testAnswers;
    }
    public TestAnswer getTestAnswers(){return this.mTestAnswer;}



}
