package com.creeps.sl_app.quizapp.core_services.utils.modal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptionStatement {

    @SerializedName("options_url")
    @Expose
    private String optionsUrl;
    @SerializedName("options_text")
    @Expose
    private String optionsText;

    public String getOptionsUrl() {
        return optionsUrl;
    }

    public void setOptionsUrl(String optionsUrl) {
        this.optionsUrl = optionsUrl;
    }

    public String getOptionsText() {
        return optionsText;
    }

    public void setOptionsText(String optionsText) {
        this.optionsText = optionsText;
    }

}