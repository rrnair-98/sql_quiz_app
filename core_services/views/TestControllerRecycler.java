package com.creeps.sl_app.quizapp.core_services.views;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creeps.sl_app.quizapp.R;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Question;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Test;

import java.util.List;

/**
 * Created by rohan on 13/1/18.
 */

public class TestControllerRecycler extends  RecyclerView.Adapter<TestControllerRecycler.TestControllerViewHolder>{

    private List<Question> mList;
    private LayoutInflater mLayoutInflater;
        private final String mcq,fib,mtf;
    private List<String> mAnswers;
    private ThePager thePager;
    public TestControllerRecycler(Context context,List test,List<String> answers,ThePager thePager){
        this.mLayoutInflater=LayoutInflater.from(context);
        this.mList=test;
        this.mAnswers=answers;
        this.thePager=thePager;
        Resources res=context.getResources();
        this.fib=res.getString(R.string.fib);
        this.mcq=res.getString(R.string.mcq);
        this.mtf=res.getString(R.string.mtf);

    }

    @Override
    public TestControllerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=this.mLayoutInflater.inflate(R.layout.view_test_controller_view_holder,parent,false);
        return new TestControllerViewHolder(view);
    }


    public void setQuestions(List<Question> questions){
        this.mList=questions;
        this.notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(TestControllerViewHolder holder, final int position) {
        String a=position+"";
        holder.srno.setText(a);
        if(this.mList!=null) {
            final Question current = this.mList.get(position);
            holder.testTitle.setText(current.getQuestionStmt().getQuestionStmt().get(0).getText());
            holder.testType.setText(this.getTestType(current.getQuestionType()));
            if (this.mAnswers != null)
                holder.userAnswers.setText(this.mAnswers.get(position));

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /* TODO show a viewpager with clicked item*/
                    TestControllerRecycler.this.thePager.setCurrentPage(current.getQuestionType(), position);

                }
            });
        }

    }
    private String getTestType(int type){
        switch (type) {
            case TestController.MCQ:
                return this.mcq;
            case TestController.FIB:
                return this.fib;
            case TestController.MTF:
                return this.mtf;
        }
        return null;

    }

    @Override
    public int getItemCount() {
        return this.mList!=null?this.mList.size():0;
    }

    static class TestControllerViewHolder extends RecyclerView.ViewHolder{

        TextView srno,testTitle,testType,userAnswers;
        View view;
        public TestControllerViewHolder(View view){
            super(view);
            this.view=view;
            this.srno=(TextView) view.findViewById(R.id.sr_no);
            this.testTitle=(TextView)view.findViewById(R.id.test_title);
            this.testType=(TextView) view.findViewById(R.id.test_type);;
            this.userAnswers=(TextView)view.findViewById(R.id.user_answers);
        }

    }


    public interface ThePager{
        public void setCurrentPage(int type,int questionIndex);

    }
}
