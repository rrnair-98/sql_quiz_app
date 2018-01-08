package com.creeps.sl_app.quizapp.core_services.utils.modal;

/**
 * Created by rohan on 4/1/18.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuestionStatement {

    @SerializedName("major_stmt")
    @Expose
    private String majorStmt;
    @SerializedName("question_stmt")
    @Expose
    private ArrayList<QuestionStmt> questionStmt = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public QuestionStatement() {
    }

    /**
     *
     * @param majorStmt
     * @param questionStmt
     */
    public QuestionStatement(String majorStmt, ArrayList<QuestionStmt> questionStmt) {
        super();
        this.majorStmt = majorStmt;
        this.questionStmt = questionStmt;
    }

    public String getMajorStmt() {
        return majorStmt;
    }

    public void setMajorStmt(String majorStmt) {
        this.majorStmt = majorStmt;
    }

    public ArrayList<QuestionStmt> getQuestionStmt() {
        return questionStmt;
    }

    public void setQuestionStmt(ArrayList<QuestionStmt> questionStmt) {
        this.questionStmt = questionStmt;
    }



}
