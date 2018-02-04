package com.creeps.sl_app.quizapp.base_modules.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.creeps.sl_app.quizapp.MainActivity;
import com.creeps.sl_app.quizapp.R;
import com.creeps.sl_app.quizapp.core_services.networking.RetrofitApiClient;
import com.creeps.sl_app.quizapp.core_services.networking.Reverberator;
import com.creeps.sl_app.quizapp.core_services.utils.SharedPreferenceHandler;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Branch;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Student;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Subject;
import com.google.gson.Gson;

import org.w3c.dom.Text;

/**
 * Created by rohan on 25/12/17.
 * A fragment that displays all user information
 */

public class UserFragment extends Fragment implements Reverberator,SharedPreferenceHandler.SharedPreferenceHandlerConstants{

    private TextView name,email,branch,semester,subjects;
    private SharedPreferenceHandler mSharedPreferenceHandler;
    private Button mLogin;
    private View mInflatingView;
    private final static String ERROR="Please check your internet connection. Will use old data if available";
    private final static String LOGIN="login",LOGOUT="logout";
    private final static String TAG="UserFragment";

    private static LoginHandler mLoginHandler;
    public static void setLoginHandler(LoginHandler lo){
        mLoginHandler=lo;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user,container,false);
        this.mInflatingView=view;
        initWidgets(view);
        /* Sending data to server*/

        Log.d(TAG,"inside onCreateView");
        this.mLogin=(Button)view.findViewById(R.id.login);
        mSharedPreferenceHandler= SharedPreferenceHandler.getInstance(this.getContext());
        String email = mSharedPreferenceHandler.get("email");
        Log.d(TAG,"email in userFrag "+email);
        if(email==null){
            this.mLogin.setText(LOGIN);
            this.mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLoginHandler.signIn();
                    /* TODO repaint fragment */
                }
            });
        }else{
            this.mLogin.setText(LOGOUT);
            this.mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLoginHandler.signOut();
                }
            });
            RetrofitApiClient retrofitApiClient=RetrofitApiClient.getInstance(this.getContext());

            retrofitApiClient.getUserDetails(email,this);
        }



        return view;
    }

    private void initWidgets(View view){

        this.name=(TextView)view.findViewById(R.id.name);
        this.email=(TextView)view.findViewById(R.id.email);
        this.branch=(TextView)view.findViewById(R.id.branch);
        //this.subjects=(TextView)view.findViewById(R.id.subject);


        this.semester=(TextView)view.findViewById(R.id.semester);
    }


    private void setData(Student st){

        Log.d(TAG,(semester==null)+"");
        this.semester.setText(st.getStudentSemester().toString());
        this.name.setText(st.getStudentName());
        this.email.setText(st.getStudentEmail());
        this.branch.setText(st.getStudentBranch().toString());

    }



    /* reverberator callback*/
    @Override
    public void reverb(Object obj) {
        SharedPreferenceHandler s = SharedPreferenceHandler.getInstance(this.getContext());

        if(obj!=null) {
            Log.d(TAG,"reverb");
            Student st=(Student)obj;
            this.setData(st);
            s.add(USER_ID,st.getStudentId()+"");
            s.add(USER_NAME, st.getStudentName());
            s.add(USER_EMAIL, st.getStudentEmail());
            s.add(USER_BRANCH, new Gson().toJson(st.getStudentBranch()));
            s.add(USER_SUBJECT, new Gson().toJson(st.getStudentSubjects()));
            Log.d(TAG,s.get(USER_BRANCH));
            Log.d(TAG,s.get(USER_SUBJECT));
        }else{
            //connection issues
            this.toast(ERROR);
            /*TODO use existing data
            Student st=new Student(1,s.get(USER_NAME));
            st.setStudentBranch(new Branch(1,s.get(USER_BRANCH)));
            st.setStudentEmail(s.get(USER_EMAIL));
            st.setStudentSubjects(s.get(new Subject(USER_SUBJECT)));
            */

        }
        Log.d(TAG,"about to toast");

    }
    private void toast(String x){
        Toast.makeText(this.getContext(), x, Toast.LENGTH_SHORT).show();
    }
}

