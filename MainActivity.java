package com.creeps.sl_app.quizapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.creeps.sl_app.quizapp.base_modules.FragmentController;
import com.creeps.sl_app.quizapp.base_modules.user.LoginHandler;
import com.creeps.sl_app.quizapp.base_modules.user.UserFragment;
import com.creeps.sl_app.quizapp.core_services.utils.SharedPreferenceHandler;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;



public class MainActivity extends AppCompatActivity implements LoginHandler{
        private static final int RC_SIGN_IN=123;//constant for result code for startActivityForResult in firebase UI
        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListener;
        private GoogleApiClient mGoogleApiClient;
        private final static String TAG="MainActivity";
        private final static String EMAIL="email";


        private BottomNavigationView bottomNavigationView;
        private FragmentController mFragmentController;


        public static int DISPLAY_WIDTH;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d(TAG,"ehherera");

            setContentView(R.layout.activity_main);


            Log.d(TAG,"ehherera");
            UserFragment.setLoginHandler(this);

            bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavigation);

            mFragmentController=FragmentController.getInstance(this,this.getSupportFragmentManager());

            bottomNavigationView.setOnNavigationItemSelectedListener(mFragmentController);
            bottomNavigationView.setOnNavigationItemReselectedListener(mFragmentController);
            bottomNavigationView.setSelectedItemId(R.id.action_blog);
            mFragmentController.onNavigationItemSelected(bottomNavigationView.getMenu().findItem(R.id.action_blog));

            initAuth();
            this.initAuthListener();


        }

        public void initAuth(){

            Log.d(TAG,"init");
            mAuth = FirebaseAuth.getInstance();

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                    // An unresolvable error has occurred and Google APIs (including Sign-In) will not
                    // be available.
                    Log.d("LoginActivity", "onConnectionFailed:" + connectionResult);
                    showToast("Google Play Services error.");
                }

            };
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(MainActivity.this /* FragmentActivity */, connectionFailedListener /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();


            //mAuth.signOut();

            // Configure Google Sign In


        }

        private void initAuthListener(){
            Log.d(TAG,"authListenerinited");
            mAuth = FirebaseAuth.getInstance();
            mAuthListener = mAuthListener==null?new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    if (user != null) {
                        // User is signed in
                        Log.d("LoginActivity", "onAuthStateChanged:signed_in:" + user.getUid());
                        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        showToast("signed in as "+email);

                        SharedPreferenceHandler.getInstance(MainActivity.this).add(MainActivity.EMAIL,email);
                        //startActivity(HelloWorldActivity.newIntent(MainActivity.this));

                    } else {
                        // User is signed out
                        Log.d("LoginActivity", "onAuthStateChanged:signed_out");
                        SharedPreferenceHandler.getInstance(MainActivity.this).remove(MainActivity.EMAIL);
                        showToast("access denied");
                        signIn();
                    }
                }
            }:mAuthListener;
            mAuth.addAuthStateListener(mAuthListener);
        }
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            Log.d(TAG,"activity result");
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    // Google Sign In was successful, authenticate with Firebase
                    showToast("bhai bhai bhai");
                    GoogleSignInAccount account = result.getSignInAccount();
                    firebaseAuthWithGoogle(account);
                    SharedPreferenceHandler.getInstance(MainActivity.this).add(MainActivity.EMAIL,account.getEmail());


                    //startActivity(FeedPagerActivity.newIntent(MainActivity.this));
                    //IpClass.getInstance().setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                } else {
                    showToast("koi nagar paika ko bulao");
                    // Google Sign In failed, update UI appropriately
                    Log.i("REQ-CODE",new String(String.valueOf(resultCode)));
                    //updateUI(null);


                }
                //forcing repaint

                FragmentController.getInstance(this,getSupportFragmentManager()).onNavigationItemSelected(bottomNavigationView.getMenu().findItem(R.id.action_user));

            }
        }
        private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
            Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.getId());



            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("LoginActivity", "signInWithCredential:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w("LoginActivity", "signInWithCredential", task.getException());
                                showToast("Firebase Authentication failed.");
                            }


                        }
                    });
        }
        public void signIn() {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

        public void signOut() {
            // Firebase sign out
            try {
                mAuth.signOut();
                SharedPreferenceHandler.getInstance(this).remove(MainActivity.EMAIL);
                // Google sign out
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                //updateUI(null);
                            }
                        });
            } catch (Exception e) {
                    e.printStackTrace();
            }
        }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
        public void onStart() {
            super.onStart();
            Log.d(TAG,"onstart");

        }

        @Override
        public void onStop() {
            super.onStop();
            if (mAuthListener != null) {
                mAuth.removeAuthStateListener(mAuthListener);
            }
        }

        private void showToast(String msg)
        {
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        }

        public void toast(String x){
            Toast.makeText(MainActivity.this.getApplicationContext(),x,Toast.LENGTH_SHORT).show();
        }






}