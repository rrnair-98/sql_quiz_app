package com.creeps.sl_app.quizapp.core_services.utils.modal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rohan on 4/1/18.
 */

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Question {

    @SerializedName("question_id")
    @Expose
    private Integer questionId;
    @SerializedName("question_level")
    @Expose
    private Integer questionLevel;
    @SerializedName("question_statement")
    @Expose
    private QuestionStatement questionStatement;
    @SerializedName("question_marks")
    @Expose
    private Integer questionMarks;
    @SerializedName("question_type")
    @Expose
    private Integer questionType;

    @SerializedName("options")
    @Expose
    private ArrayList<Option> options = null;



    /**
     * No args constructor for use in serialization
     *
     */
    

    /**
     *
     * @param questionId
     * @param questionType
     * @param questionStatement
     * @param questionMarks
     * @param questionLevel
     */
    public Question(Integer questionId, Integer questionLevel, QuestionStatement questionStatement, Integer questionMarks, Integer questionType) {
        super();
        this.questionId = questionId;
        this.questionLevel = questionLevel;
        this.questionStatement = questionStatement;
        this.questionMarks = questionMarks;
        this.questionType = questionType;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionLevel() {
        return questionLevel;
    }

    public void setQuestionLevel(Integer questionLevel) {
        this.questionLevel = questionLevel;
    }

    public QuestionStatement getQuestionStmt() {
        return questionStatement;
    }

    public void setQuestionStmt(QuestionStatement questionStatement) {
        this.questionStatement = questionStatement;
    }

    public Integer getQuestionMarks() {
        return questionMarks;
    }

    public void setQuestionMarks(Integer questionMarks) {
        this.questionMarks = questionMarks;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    @Override
    public String toString(){
        return this.questionStatement.toString()+", "+this.options.toString();
    }


}