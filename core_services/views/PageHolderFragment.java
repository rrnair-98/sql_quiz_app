package com.creeps.sl_app.quizapp.core_services.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creeps.sl_app.quizapp.R;
import com.creeps.sl_app.quizapp.core_services.utils.modal.An;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Question;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Test;
import com.creeps.sl_app.quizapp.core_services.utils.modal.TestAnswer;
import com.creeps.sl_app.quizapp.core_services.views.answer_collection.AnswerCollector;
import com.creeps.sl_app.quizapp.core_services.views.answer_collection.AnswerCollectorCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.creeps.sl_app.quizapp.core_services.views.TestControllerConstants.FIB;
import static com.creeps.sl_app.quizapp.core_services.views.TestControllerConstants.MCQ;
import static com.creeps.sl_app.quizapp.core_services.views.TestControllerConstants.MTF;

/**
 * Created by rohan on 14/1/18.
 * A fragment that holds a QuestionFragment
 */

public class PageHolderFragment extends Fragment {
    private static Test mTest;
    private final String TAG="PageHolderFragment";
    public static void setTest(Test ref){
            mTest=ref;
    }
    private ViewPager mPager;
    private static MyPageChangeListener mMyPageChangeListener;
    private static MyPagerAdapter mPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_page_holder,container,false);
        Bundle b=this.getArguments();

        mPagerAdapter= new MyPagerAdapter(getFragmentManager(),(ArrayList<Question>) mTest.getQuestions());


        this.mPager=(ViewPager)view.findViewById(R.id.mPager);

        this.mPager.setAdapter(mPagerAdapter);
        mMyPageChangeListener=new MyPageChangeListener(mPagerAdapter.getCallbacks(),TestControllerDataSource.getInstance(this.getContext()).getTestAnswers());

        this.mPager.addOnPageChangeListener(mMyPageChangeListener);

        if(b!=null) {this.mPager.setCurrentItem(b.getInt("currentPage"));}
        return view;
    }
    public MyPageChangeListener getmMyPageChangeListener(){return mMyPageChangeListener;}


    /* The pagelistener . Holds a List of AnswerCollectorCallback callbacks to be fired whenever a particular */

     class MyPageChangeListener implements ViewPager.OnPageChangeListener{

        private TestAnswer mTestAnswers;
        private ArrayList<MyPagerAdapter.CallbackWrapper> callbacks;
         private int currentPosition=-1;
        public MyPageChangeListener(ArrayList<MyPagerAdapter.CallbackWrapper> answerCollectorCallbacks, TestAnswer testAnswer){
            this.callbacks=answerCollectorCallbacks;
            /*Todo obtain a reference of the TestAnswer object present in TestController  */
            this.mTestAnswers=testAnswer;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }



        @Override
        public void onPageSelected(int position) {
            /* TODO load older answers*/
            Question currentQestion=mTest.getQuestions().get(position);
            if(currentPosition!=-1) {
                Log.d(TAG,"current "+currentPosition);
                Log.d(TAG,"position "+position);
                AnswerCollectorCallback currentCallback=callbacks.get(currentPosition).getAnswerCollectorCallback();
                Log.d(TAG,"callback null "+(currentCallback==null));
                ArrayList<String> arr;
                if(currentCallback!=null)
                    arr=currentCallback.getAnswers();
                //this.mTestAnswers.getAns(currentCallback.getQuestionId()).setAnswers(arr);
            }
            this.currentPosition=position;

        }
        public TestAnswer getmTestAnswers(){
            return this.mTestAnswers;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }




    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Question> questions;
        private ArrayList<CallbackWrapper> answerCollectorCallbacks;
        private TestController.QuestionPageFragment questionPageFragment=null;
        public MyPagerAdapter(FragmentManager fm, ArrayList<Question> question){
            super(fm);
            this.questions=question;
            this.answerCollectorCallbacks=new ArrayList<>();
            for(int i=0;i<question.size();i++)
                answerCollectorCallbacks.add(new CallbackWrapper());

        }
        public void setQuestions(ArrayList<Question> question){
            this.questions=question;
            this.notifyDataSetChanged();
        }


        public ArrayList<CallbackWrapper> getCallbacks(){return this.answerCollectorCallbacks;}
        private final static String TAG="PagerAdapter";

        @Override
        public Fragment getItem(int position) {
            TestController.QuestionPageFragment current=this.getType(questions.get(position).getQuestionType(),position);
            AnswerCollectorCallback currentCallback=current.getInflatedView();
            Log.d(TAG,"at position "+position);
            Log.d(TAG,"is callback null "+(current==null));
           /* if( questionPageFragment!=null) {
                Log.d(TAG,"is visible "+questionPageFragment.isVisible());
                this.answerCollectorCallbacks.get(position).setAnswerCollectorCallback(questionPageFragment.getInflatedView());
            }*/
            questionPageFragment= current;
            Log.d(TAG,"added to answerCollector");
            /* todo load and set older answers*/


            return current;
        }

        public class CallbackWrapper{
            private AnswerCollectorCallback answerCollectorCallback;

            public CallbackWrapper(AnswerCollectorCallback collectorCallback){
                this.answerCollectorCallback=collectorCallback;
            }
            public CallbackWrapper(){
                this(null);
            }
            public AnswerCollectorCallback getAnswerCollectorCallback(){return this.answerCollectorCallback;}
            public void setAnswerCollectorCallback(AnswerCollectorCallback callback){
                if( callback!=null)
                    this.answerCollectorCallback=callback;
            }
        }




        private TestController.QuestionPageFragment getType(int type, int position){
            TestController.QuestionPageFragment fragment=new TestController.QuestionPageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position",position);
            switch (type){
                case MTF:
                    bundle.putInt("current",MTF);

                    break;
                case FIB:
                    bundle.putInt("current",FIB);
                    break;
                case MCQ:
                    bundle.putInt("current",MCQ);
            }
            fragment.setArguments(bundle);
            return fragment;
        }





        @Override
        public int getCount() {
            return questions.size();
        }
    }
}
/* 10 ,3, 6,5 2
* 10,13,19,24,26
* 65,23,12,9,3
* 65,88,100,109,112
* t(n)= n^2-(n-1)^2
* find s(n)=t(1)+t(2)...t(n)
* answer = answer%10^9 + 7
* */