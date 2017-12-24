package com.creeps.sl_app.quizapp.core_services.utils.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FIBItem {

    @SerializedName("question_id")
    @Expose
    private Integer questionId;
    @SerializedName("marks")
    @Expose
    private Integer marks;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("major_stmt")
    @Expose
    private String majorStmt;
    @SerializedName("question_stmt")
    @Expose
    private List<QuestionStmt> questionStmt = null;
    @SerializedName("options")
    @Expose
    private List<Object> options = null;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMajorStmt() {
        return majorStmt;
    }

    public void setMajorStmt(String majorStmt) {
        this.majorStmt = majorStmt;
    }

    public List<QuestionStmt> getQuestionStmt() {
        return questionStmt;
    }

    public void setQuestionStmt(List<QuestionStmt> questionStmt) {
        this.questionStmt = questionStmt;
    }

    public List<Object> getOptions() {
        return options;
    }

    public void setOptions(List<Object> options) {
        this.options = options;
    }

}