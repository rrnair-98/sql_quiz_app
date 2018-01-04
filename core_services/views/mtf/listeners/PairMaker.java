package com.creeps.sl_app.quizapp.core_services.views.mtf.listeners;

import android.view.View;

/**
 * Created by damian on 23/9/17.
 */

public interface PairMaker {
    //need source postion to delete the element from the list... passing in views for their tags...
    public void makePairs(View dest, View source, int sourcePosition, int destPosition);
}
