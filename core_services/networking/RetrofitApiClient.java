package com.creeps.sl_app.quizapp.core_services.networking;

import android.content.Context;
import android.util.Log;

import com.creeps.sl_app.quizapp.R;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Chapter;
import com.creeps.sl_app.quizapp.core_services.utils.modal.DataHolder;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Student;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Test;
import com.creeps.sl_app.quizapp.core_services.utils.modal.TestAnswer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by rohan on 30/9/17.
 * this class acts as a wrapper for the retrofit client. It has a factory method which requires Context( to prevent gcing) which returns an instance of the class.
 * The context is also used to obtain the BASE_URL from the string.xml file.
 * It has the following functions
 *  1. authorizeUser() sends an object in the Reverberator provided. if the response fails it passes in null;
 *  2. getBaseURL() returns the BASE_URL which is specified in Resources/String file with id base_url
 *  3. getUserDetailsWithID() sends an object in the Reverberator provided. if the response fails it passes in null
 */

public class RetrofitApiClient {
    private static RetrofitApiClient mRetrofitApiClient;
    private Context mContext;
    private final String BASE_URL;
    private final Retrofit mRetrofitInstance;
    private final RetrofitApiInterface mRetrofitApiInterface;
    private final static String TAG="RetrofitApiClient";
    private final static int READ_TIMEOUT=60,WRITE_TIMEOUT=60;
    private DataHolder dataHolder;



        /*
        * An interceptor is fired before any request is made to the server. Its job is to encode the URL. This implementation is
        * for preventing special char ascii to hex conversion whenever its URLEncoded. Retrofit has no support to stop URL encoding as of now
        * This might change in future releases.*/
    private Interceptor mInterceptor=new Interceptor() {
        private final static String TAG="CustomInterceptor";
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request req=chain.request();
            String body= this.reqToString(req.body());
            String newPostBody= URLDecoder.decode(body,"UTF-8");
            Log.d(TAG,"new post body "+newPostBody);
            RequestBody requestBody=req.body(),otherBody=null;
            if(requestBody!=null){
                otherBody= RequestBody.create(requestBody.contentType(),newPostBody);

            }
            Request request;
            if(req.method().equals("get"))
                    request=req.newBuilder()
                            .method(req.method(), req.body())
                            .get()
                            .build();
            else if(req.method().equals("post"))
                request=req.newBuilder()
                        .method(req.method(), req.body())
                        .post(requestBody)
                        .build();
            else
                request=req.newBuilder().method(req.method(),req.body()).build();

            if(request!=null)
                Log.d(TAG,request.toString());


            return chain.proceed(request);
        }

        final public String reqToString(RequestBody request){
            try{
                final RequestBody requestBodyCopy= request;
                final Buffer buffer=new Buffer();
                if(requestBodyCopy!=null)
                    requestBodyCopy.writeTo(buffer);
                else return "";
                return buffer.readUtf8();

            }catch (IOException ioe){
                ioe.printStackTrace();
                return "didnt work";
            }
        }
    };


    private final static String API_KEY=null;


    /* Context is being takes as a param to prevent the object from getting gc'd. As long as a reference to the other object is alive and is in the young pool the other object can
    * not be gc'd hence the current wont be gcd*/
    private RetrofitApiClient(Context context){
        this.mContext=context;
        this.BASE_URL=this.mContext.getResources().getString(R.string.base_url);
        Gson gson=new GsonBuilder().setLenient().create();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).addInterceptor(this.mInterceptor)
                .build();
 this.mRetrofitInstance= new Retrofit.Builder().baseUrl(this.BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();



        this.mRetrofitApiInterface= this.mRetrofitInstance.create(RetrofitApiInterface.class);
        this.dataHolder=DataHolder.getInstance(context);


    }
    /* use this factory method to obtain an instance of the class*/
    public static RetrofitApiClient getInstance(Context context){
        if(RetrofitApiClient.mRetrofitApiClient==null){
            RetrofitApiClient.mRetrofitApiClient = new RetrofitApiClient(context);
        }
        return RetrofitApiClient.mRetrofitApiClient;
    }
    /*this function is a generalized one to be used for all call objects ... will pass in the value returned by response ...
    it is the responsibility of the class which implements Reverberator to cast it to his/her liking
    * */

    private void enque(Call call,final Reverberator reverberator){
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(reverberator!=null) {
                    if(response!=null)
                        Log.d(TAG,response.toString());
                    reverberator.reverb(response.body());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG,"Couldnt fetch data from server ... read the stackTrace for more info");
                t.printStackTrace();
                if(reverberator!=null)
                    reverberator.reverb(null);
            }
        });
    }

    public String getBaseUrl() {
        return this.BASE_URL;
    }

    /* @params username - username to be sent to server
    * @param password - hashed password to be sent to server
    * @param reverberator- callback which returns data fetched from the server is null if it fails.*/
    public void authorizeUser(String username,Long password,Reverberator reverberator){

        Call<Student> studentCall=this.mRetrofitApiInterface.getStudenInfo(username,password,API_KEY);

        this.enque(studentCall,reverberator);

    }


    /* @params id - id of the user information to be fetched.
    * @param reverberator- callback which returns data fetched from the server is null if it fails.*/
    public void getUserDetails(String email,Reverberator reverberator){
        if(this.dataHolder.getStudent()!=null){
            reverberator.reverb(this.dataHolder.getStudent());
            return;
        }
        Call<Student> studentCall=this.mRetrofitApiInterface.getSomeDetails(email,API_KEY);
        this.enque(studentCall,reverberator);
    }


    /* function to be called from the callback on the outside ... this function is being used to cache certain data members*/
    public void setStudentRef(Student studentRef){
        this.dataHolder.setStudent(studentRef);
    }



    public void initiateTest(String user_id, List<Chapter> chapters, long duration, int marks, Reverberator reverberator){
        Log.d(TAG,"initiatingTest");
        Call<Test> testCall=this.mRetrofitApiInterface.initateTest(user_id,chapters,marks,duration);
        Log.d(TAG,"url "+testCall.request().url().url().toString());
        this.enque(testCall,reverberator);

    }

    public void sendTestAnswers(TestAnswer testAnswer,Reverberator reverberator){
        Log.d(TAG,"sending Answers to the server ");
        Call<String> sendAnswer=this.mRetrofitApiInterface.sendAnswer(testAnswer);
        Log.d(TAG,sendAnswer.request().url().url().toString());
        this.enque(sendAnswer,reverberator);
    }







}
