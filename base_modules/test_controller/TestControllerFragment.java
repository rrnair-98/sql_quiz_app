package com.creeps.sl_app.quizapp.base_modules.test_controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.creeps.sl_app.quizapp.R;
import com.creeps.sl_app.quizapp.TestActivity;
import com.creeps.sl_app.quizapp.core_services.views.TestController;

/**
 * Created by rohan on 4/1/18.
 */

public class TestControllerFragment extends Fragment {


    TestController mTestController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_test_controller,container,false);
        mTestController=(TestController)view.findViewById(R.id.controller_test);
        mTestController.initRetrofit(TestActivity.getSelectedChapters());

        return view;
    }

}
