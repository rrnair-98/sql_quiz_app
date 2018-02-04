
package com.creeps.sl_app.quizapp.core_services.utils.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {

    @SerializedName("option_id")
    @Expose
    private Integer optionId;
    @SerializedName("option_statement")
    @Expose
    private OptionStatement optionStatement;

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public OptionStatement getOptionStatement() {
        return optionStatement;
    }

    public void setOptionStatement(OptionStatement optionStatement) {
        this.optionStatement = optionStatement;
    }

}