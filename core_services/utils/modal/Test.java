
package com.creeps.sl_app.quizapp.core_services.utils.modal;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test {

    @SerializedName("test_id")
    @Expose
    private Integer testId;
    @SerializedName("test_title")
    @Expose
    private String testTitle;
    @SerializedName("test_marks")
    @Expose
    private Integer testMarks;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;

    /**
     * No args constructor for use in serialization
     */
    public Test() {
    }

    /**
     * @param testTitle
     * @param testMarks
     * @param testId
     * @param questions
     */
    public Test(Integer testId, String testTitle, Integer testMarks, List<Question> questions) {
        this.testId = testId;
        this.testTitle = testTitle;
        this.testMarks = testMarks;
        this.questions = questions;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public Integer getTestMarks() {
        return testMarks;
    }

    public void setTestMarks(Integer testMarks) {
        this.testMarks = testMarks;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }


    @Override
    public String toString(){
        return this.questions.toString();
    }
}