package com.creeps.sl_app.quizapp.core_services.networking;

/**
 * Created by rohan on 30/9/17
 * This interface is to be implemented by all classes that wish to obtain a response from the server. Since enque()[from Rertrofit]
 * schedules a function call for execution,whenever the function may be executed the response from the server is delivered via the Reverberator .
 * You will get a null value if there was an error in transmitting or whenever the server responds with some issue.
 *
 */

public interface Reverberator {
    public void reverb(Object obj);
}
