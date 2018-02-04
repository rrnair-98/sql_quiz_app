package com.creeps.sl_app.quizapp.core_services.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creeps.sl_app.quizapp.R;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Question;

import java.util.List;

/**
 * Created by rohan on 14/1/18.
 */
 /*
    *
    * this class hosts the pager and the recycler view
    * */
public class TestRecyclerFragment extends Fragment {
    private RecyclerView mRecycler;
    private TestControllerRecycler mTestControllerAdapter;
    private static TestControllerRecycler.ThePager thePager;
    public static void setThePager(TestControllerRecycler.ThePager ref){
        thePager=ref;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_test_questions, container, false);


        this.mRecycler = (RecyclerView) view.findViewById(R.id.test_questions_recycler);
        this.mRecycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        this.mTestControllerAdapter = new TestControllerRecycler(TestRecyclerFragment.this.getContext(), TestControllerDataSource.getInstance(this.getContext()).getQuestions(), null,thePager);
        this.mRecycler.setAdapter(this.mTestControllerAdapter);
        return view;
    }

    public void setTestControllerList(List<Question> lsit) {
        if (this.mTestControllerAdapter == null) {
            this.mTestControllerAdapter = new TestControllerRecycler(TestRecyclerFragment.this.getContext(), lsit, null,thePager);
            this.mRecycler.setAdapter(this.mTestControllerAdapter);
        } else
            this.mTestControllerAdapter.setQuestions(lsit);
    }


}

