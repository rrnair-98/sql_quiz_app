package com.creeps.sl_app.quizapp.core_services.views.answer_collection;

import java.util.ArrayList;

/**
 * Created by rohan on 21/1/18.
 * This is to be used for setting answers to the view.
 */

public interface AnswerCollectorCallback {
    public ArrayList<String> getAnswers();
    /* so that a dataPairs list can be obtained from the HashMap */
    public int getQuestionId();
}
