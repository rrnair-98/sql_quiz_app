package com.creeps.sl_app.quizapp.core_services.utils.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionStmt {

    @SerializedName("sub_id")
    @Expose
    private Integer subId;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("text_image")
    @Expose
    private String textImage;

    public int getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextImage() {
        return textImage;
    }

    public void setTextImage(String textImage) {
        this.textImage = textImage;
    }

}