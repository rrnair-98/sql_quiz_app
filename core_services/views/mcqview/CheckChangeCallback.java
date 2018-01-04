package com.creeps.sl_app.quizapp.core_services.views.mcqview;

/**
 * Created by rohan on 23/12/17.
 * This callback must be implemented by the class that add an McqItem and wishes to maintain a RadioGroup[custom implementation]
 * This function will be called whenever the state of the radio button is changed.
 */

public interface CheckChangeCallback {
    public void call(int currentId);
}
