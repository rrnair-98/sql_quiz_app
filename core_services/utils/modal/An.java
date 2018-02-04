package com.creeps.sl_app.quizapp.core_services.utils.modal;

import java.util.ArrayList;
import java.util.List;

import com.creeps.sl_app.quizapp.core_services.views.mtf.DataPairs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class An {

    @SerializedName("question_id")
    @Expose
    private Integer questionId;
    @SerializedName("answers")
    @Expose
    private List<String> answers = null;
    @SerializedName("type")
    @Expose
    private Integer type;
    transient ArrayList<DataPairs<QuestionStmt,Option>> dataPairs;


    public An(int questionId,int type){
        this(questionId,type,null);
    }
    public An(int questionId,int type,List<String> answers){
        this.questionId=questionId;
        this.type=type;
        this.answers=answers;
    }
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public void setDataPairs(ArrayList<DataPairs<QuestionStmt,Option>> arr){
        this.dataPairs=arr;
    }
    public ArrayList<DataPairs<QuestionStmt,Option>> getDataPairs(){return this.dataPairs;}

    /* for retrofit*/
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        sb.append("\"question_id\"").append(":").append(questionId).append(",\"type\":").append(type);
            sb.append(",\"answers\":[");
        if(this.answers!=null)
            for(String x:this.answers)
                sb.append(x+",");
        else
            sb.append("null");

        if(sb.toString().endsWith(","))sb.replace(sb.length()-1,sb.length(),"");

        sb.append("]},");
        return sb.toString();
    }

}