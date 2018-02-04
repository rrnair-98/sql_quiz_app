
package com.creeps.sl_app.quizapp.core_services.utils.modal;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.creeps.sl_app.quizapp.core_services.views.mtf.DataPairs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestAnswer {

    transient private final String TAG="TestAns";
    transient private final int MAX=30;
    @SerializedName("test_id")
    @Expose
    private Integer testId;
    @SerializedName("ans")
    @Expose
    private HashMap<Integer,An> ans;

    public TestAnswer(int size,List<Question> list){
        this.ans=new HashMap<>();
        for(int i=0;i<size;i++) {
            Question current=list.get(i);
            this.ans.put(current.getQuestionId(),new An(current.getQuestionId(), current.getQuestionType()));
        }
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public An getAns(int key) {
        return ans.get(key);
    }



    public void addAnswer(int key,List<String> answerList){
        this.ans.get(key).setAnswers(answerList);
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        sb.append("\"test_id\":");
        sb.append(testId);
        Log.d(TAG,ans.toString());
        sb.append(",\"ans\":[");
        for(int x:this.ans.keySet())
                sb.append(this.ans.get(x).toString());
        if(sb.toString().endsWith(","))sb.replace(sb.length()-1,sb.length(),"");
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }




}