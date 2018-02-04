package com.creeps.sl_app.quizapp.core_services.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.creeps.sl_app.quizapp.R;
import com.creeps.sl_app.quizapp.core_services.networking.RetrofitApiClient;
import com.creeps.sl_app.quizapp.core_services.networking.Reverberator;
import com.creeps.sl_app.quizapp.core_services.utils.SharedPreferenceHandler;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Chapter;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Question;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Test;
import com.creeps.sl_app.quizapp.core_services.utils.modal.TestAnswer;
import com.creeps.sl_app.quizapp.core_services.views.answer_collection.AnswerCollectorCallback;
import com.creeps.sl_app.quizapp.core_services.views.fibview.FibView;
import com.creeps.sl_app.quizapp.core_services.views.mcqview.McqView;
import com.creeps.sl_app.quizapp.core_services.views.mtf.MTFView;
import com.creeps.sl_app.quizapp.core_services.views.mtf.MainAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohan on 24/12/17.
 * This class will receive all jsons and will add/remove other child views included in this package.
 */

public class TestController extends RelativeLayout implements SharedPreferenceHandler.SharedPreferenceHandlerConstants,Reverberator,View.OnClickListener,TestControllerRecycler.ThePager,TestControllerConstants{

    /* TODO While generating the answers for mtf add the option.sub_id not option.id
    * */
    private Context mContext;
    private final static String TAG="TestController";
    private Test currentTest;
    private Button mButton;
    private TestRecyclerFragment mTestRecyclerFragment;
    private FragmentManager fragmentManager;
    private FrameLayout mPlaceholder;
    private PageHolderFragment mPageHolderFragment;
    private TestAnswer mTestAnswers;

    public TestController(Context context){
        this(context,null);
    }
    public TestController(Context context,AttributeSet attr){
        this(context,attr,0);
    }
    public TestController(Context context, AttributeSet attr,int options){
        super(context,attr,options);
        /* inflating and adding the layout file */
        View view= inflate(context,R.layout.view_test_controller,null);
        this.addView(view);
        Log.d(TAG,"HERE");
        this.mContext=context;
        this.mButton=(Button)view.findViewById(R.id.test_end);
        this.mButton.setOnClickListener(this);
        this.mPlaceholder=(FrameLayout)view.findViewById(R.id.test_fragment_placeholder);

        this.fragmentManager=((AppCompatActivity)this.mContext).getSupportFragmentManager();
        this.mTestRecyclerFragment=new TestRecyclerFragment();
        this.placeFragment(mTestRecyclerFragment);

        TestRecyclerFragment.setThePager(this);
        Log.d(TAG,"done with ctor");
    }

    private void placeFragment(Fragment fragment){
        this.fragmentManager.beginTransaction().replace(this.mPlaceholder.getId(),fragment).commit();
    }
    private void addFragmentToBackStack(Fragment fragment){
        this.fragmentManager.beginTransaction().replace(this.mPlaceholder.getId(),fragment).addToBackStack("old").commit();
    }
    /*
    * this function fetches the test Data and initializes the test
    * */
    public void initRetrofit(List<Chapter> chapters){
        String user_id=SharedPreferenceHandler.getInstance(this.mContext).get(SharedPreferenceHandler.SharedPreferenceHandlerConstants.USER_ID);
        //Log.d(TAG,user_id);
        /* todo REMOVE THIS */
        user_id="1";
        RetrofitApiClient.getInstance(this.mContext).initiateTest(user_id,chapters
                ,DURATION,MARKS,this);
    }

    private List<Chapter> getChaptersFromSharedPref(){
        List list;
        String chapters=SharedPreferenceHandler.getInstance(this.mContext).get(USER_CHAPTERS);
        Type listType = new TypeToken<List<Chapter>>() {
        }.getType();
        list= new Gson().fromJson(chapters,listType);
        Log.d(TAG,list.toString());
        return list;
    }

    private void doReverbEssentials(){

        this.mTestRecyclerFragment.setTestControllerList(currentTest.getQuestions());
        this.mTestAnswers=new TestAnswer(this.currentTest.getQuestions().size(),this.currentTest.getQuestions());
        mTestAnswers.setTestId(currentTest.getTestId());
        PageHolderFragment.setTest(this.currentTest);
        TestControllerDataSource dataSource=TestControllerDataSource.getInstance(this.mContext);
        dataSource.setQuestions((ArrayList<Question>)currentTest.getQuestions());
        dataSource.setTestAnswers(this.mTestAnswers);


    }

    @Override
    public void reverb(Object obj){
        Log.d(TAG,"reverb");
        if(obj!=null){
            /* Received entire test data
            * Time to populate the view
            * */
            Log.d(TAG,"obtained test");
            this.currentTest=(Test)obj;
            doReverbEssentials();

            return;
        }
        toast("couldnt fetch data from the server");
    }



    @Override
    public void onClick(View view){
        /* TODO send test result to the server*/

        Log.d(TAG,mTestAnswers.toString());

        RetrofitApiClient.getInstance(this.getContext()).sendTestAnswers(mTestAnswers, new Reverberator() {
            @Override
            public void reverb(Object obj) {
                if(obj!=null)
                Log.d(TAG,"answer response "+obj.toString());
            }
        });
    }


    /* this class is the fragment that inflates a particular layout . It depends on the Bundle that is passed.
    * the callee must pass in these params to the bundle before instantiating this class for proper behavior:
    * position - Tells the fragment which question must be obtained from the list;
    * current - Tells the fragment which view it must inflate. They are defined by the constants MCQ,MTF,FIB
    * */

     public static class QuestionPageFragment extends Fragment{
         private static ArrayList<Question> questions;
         private MTFView mtf;
         private FibView fib;
         private McqView mcq;
         private AnswerCollectorCallback inflated;

         @Nullable
         @Override
         public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

             Bundle bundle=this.getArguments();
             questions= TestControllerDataSource.getInstance(this.getContext()).getQuestions();
             if(bundle!=null) {
                 int type=bundle.getInt("current");
                 int pos=bundle.getInt("position");
                 View view=null;
                 if(questions!=null) {
                     Question question = questions.get(pos);
                     switch (type) {
                         case MCQ:
                             view=inflater.inflate(R.layout.fragment_test_pager_mcq,container,false);
                             McqView mcqView = (McqView) view.findViewById(R.id.mcq);
                             mcqView.initView(question.getOptions(),question.getQuestionId());
                             this.inflated=mcqView;


                             break;

                         case FIB:
                             view=inflater.inflate(R.layout.fragment_test_pager_fib,container,false);
                             FibView fibView = (FibView)view.findViewById(R.id.fib);
                             fibView.setContentString(question.getQuestionStmt().getQuestionStmt().get(0).getText(), null,question.getQuestionId());
                             this.inflated=fibView;
                             break;

                         case MTF:
                             view=inflater.inflate(R.layout.fragment_test__pager_mtf,container,false);
                             MTFView mtfView = (MTFView)view.findViewById(R.id.mtf);
                             RecyclerView r1 = new RecyclerView(this.getContext()), r2 = new RecyclerView(this.getContext());
                             MainAdapter m1 = new MainAdapter(this.getContext(), question.getQuestionStmt().getQuestionStmt(), R.layout.test_mtf_viewholder, true), m2 = new MainAdapter(this.getContext(), question.getOptions(), R.layout.test_mtf_viewholder, false);
                             mtfView.setQuestionRecycler(r1, m1,question.getQuestionId());
                             mtfView.setAnswerRecycler(r2, m2);
                             this.inflated=mtfView;

                     }
                 }


                 return view;

             }

             return null;

         }

         public AnswerCollectorCallback getInflatedView(){
             return this.inflated;
         }

     }
    /* callback fired by the recycler view's onClick*/
    @Override
    public void setCurrentPage(int type, int questionIndex) {
            /* TODO add fragment to pager */

        this.mPageHolderFragment=new PageHolderFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("currentPage",questionIndex);
        this.mPageHolderFragment.setArguments(bundle);
        this.addFragmentToBackStack(this.mPageHolderFragment);
    }

    private void toast(String text){
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }
}
interface TestControllerConstants{
    public final static int MARKS=10;
    public final static long DURATION=10;
    public final static int MCQ=1,FIB=2,MTF=3;
}
