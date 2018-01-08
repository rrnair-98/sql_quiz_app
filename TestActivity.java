package com.creeps.sl_app.quizapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.creeps.sl_app.quizapp.base_modules.test_controller.TestControllerFragment;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Chapter;

import java.util.List;

/**
 * Created by rohan on 4/1/18.
 */

public class TestActivity extends AppCompatActivity {
    private static List<Chapter> selectedChapters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_test);
        /* TODO place TestControllerFragment and initiate test*/
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.test_frame,new TestControllerFragment()).commit();


    }

    public static List<Chapter> getSelectedChapters(){
        return selectedChapters;
    }

    public static Intent getIntent(Context context,List<Chapter> chapters){
        Intent intent=new Intent(context, TestActivity.class);
        selectedChapters=chapters;
        return intent;
    }



}
