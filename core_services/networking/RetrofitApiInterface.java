package com.creeps.sl_app.quizapp.core_services.networking;

import com.creeps.sl_app.quizapp.core_services.utils.modal.An;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Chapter;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Student;
import com.creeps.sl_app.quizapp.core_services.utils.modal.Test;
import com.creeps.sl_app.quizapp.core_services.utils.modal.TestAnswer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rohan on 30/9/17.
 *
 */

public interface RetrofitApiInterface {

    @FormUrlEncoded
    @POST("/quizapp/studentdetail.php")
    public Call<Student> getStudenInfo(@Field("username") String username, @Field("password") Long password, @Field("api_key") String apiKey);
    @FormUrlEncoded
    @POST("/sl_app/user-init.php")
    public Call<Student> getSomeDetails(@Field("email") String userId, @Field("api_key") String apiKey);

    @FormUrlEncoded
    @POST
    public Call<String>  getStringResponse(@Field("email") String x);

    /*@FormUrlEncoded
    @GET("endpoints/get-test.php")
    public Call<Test> initateTest(@Field("user_id")String x, @Field("chapters[]")List<Chapter> ch, @Field("marks")int a, @Field("duration")Long duration);
*/
    @GET("endpoints/get-test.php")
    public Call<Test> initateTest(@Query("user_id") String x, @Query("chapters[]") List<Chapter> ch, @Query("marks") int a, @Query("duration") Long duration);


    @GET("endpoints/insert-answer.php")
    public Call<String> sendAnswer(@Query(value="answer",encoded = true) TestAnswer ans);


}
