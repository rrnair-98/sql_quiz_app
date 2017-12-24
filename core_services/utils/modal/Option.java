
package com.creeps.sl_app.quizapp.core_services.utils.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {

    @SerializedName("option_id")
    @Expose
    private Integer optionId;
    @SerializedName("options_url")
    @Expose
    private String optionsUrl;
    @SerializedName("options_text")
    @Expose
    private String optionsText;
    @SerializedName("sub_id")
    @Expose
    private String subId;

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

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

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

}
