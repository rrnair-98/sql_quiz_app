package com.creeps.sl_app.quizapp.base_modules.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.creeps.sl_app.quizapp.TestActivity;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Chapter;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import com.creeps.sl_app.quizapp.R;
import com.creeps.sl_app.quizapp.core_services.utils.SharedPreferenceHandler;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Subject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohan on 25/12/17.
 * This class is will act as viewcontroller[tentative] and also carry out tests based on the values fed in  by the user.
 * It needs a RetrofitClient reference to communicate with the server.
 *
 */

public class TestFragment extends Fragment implements SharedPreferenceHandler.SharedPreferenceHandlerConstants{


    private final static String TAG="TestFragment";
    private List<Subject> mSubjects;
    private ListView mListView;

    private AppCompatSpinner mSpinner;
    private Button mButton;
    private CustomChapterAdapter mAdapter;

    private Subject mCurrentSubject;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_test_chapters,container,false);
        /* TODO init views*/

        this.initSubjects();
        this.initViews(view);


        return view;
    }

    private void initViews(View view){

        this.mButton=(Button)view.findViewById(R.id.init_test);
        this.mSpinner=(AppCompatSpinner)view.findViewById(R.id.subject_spinner);

        this.mSpinner.setAdapter(new ArrayAdapter<Subject>(this.getContext(),android.R.layout.simple_spinner_item,this.mSubjects));
        this.mListView=(ListView)view.findViewById(R.id.fragment_list_chapters);

        this.mAdapter=new CustomChapterAdapter(TestFragment.this.getContext(),TestFragment.this.mSubjects.get(0).getChapters());
        this.mListView.setAdapter(this.mAdapter);

        Log.d(TAG,TestFragment.this.mSubjects.get(0).getChapters().toString());

        this.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TestFragment.this.mCurrentSubject=TestFragment.this.mSubjects.get(position);
                TestFragment.this.mAdapter.setChapters(mCurrentSubject.getChapters());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        this.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean []itemStates=TestFragment.this.mAdapter.getItemStates();
                List<Chapter> chapters=new ArrayList<Chapter>();
                for (int i=0;i<itemStates.length;i++){
                    if(itemStates[i]){
                        /* TODO make a json string requesting test ie make a call to the RetrofitApiClient*/
                        Log.d(TAG,TestFragment.this.mCurrentSubject.getChapters().get(i).toString());
                        chapters.add(TestFragment.this.mCurrentSubject.getChapters().get(i));
                    }
                }


                /* TODO initiate a fragmentTransaction / Activity that has ViewController which will conduct the Test*/
                Intent intent=TestActivity.getIntent(TestFragment.this.getContext(),chapters);
                startActivity(intent);

            }
        });
    }



    private void initSubjects(){

        String subjectArray=SharedPreferenceHandler.getInstance(this.getContext()).get(SharedPreferenceHandler.SharedPreferenceHandlerConstants.USER_SUBJECT);


        Type listType = new TypeToken<List<Subject>>() {
        }.getType();
        this.mSubjects= new Gson().fromJson(subjectArray,listType);
        Log.d(TAG,this.mSubjects.toString());

    }











}
