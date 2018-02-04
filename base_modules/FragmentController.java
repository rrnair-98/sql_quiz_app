package com.creeps.sl_app.quizapp.base_modules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import com.creeps.sl_app.quizapp.R;
import com.creeps.sl_app.quizapp.base_modules.blog.BlogFragment;
import com.creeps.sl_app.quizapp.base_modules.settings.SettingsFragment;
import com.creeps.sl_app.quizapp.base_modules.test.TestChapterFragment;
import com.creeps.sl_app.quizapp.base_modules.user.UserFragment;

/**
 * Created by rohan on 25/12/17.
 * This class is responsible for adding and replacing fragments. Will host all Fragment references... Should it be a singleton?
 */

public class FragmentController implements BottomNavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemReselectedListener{
    private Context mContext;
    private FragmentManager mFragmentManager;
    private Fragment mNewFragment;



    private final static int FRAGMENT_PLACEHOLDER=R.id.fragment_placeholder;
    private static FragmentController mFragmentController;


    private final static String TAG="FragmentController";
    private FragmentController(Context context,FragmentManager fragmentManager){
        this.mContext=context;
        this.mFragmentManager=fragmentManager;
    }

    public static FragmentController getInstance(Context context,FragmentManager fragmentManager){
        return mFragmentController=mFragmentController==null?new FragmentController(context,fragmentManager):mFragmentController;
    }


    public void replaceFragments(){
        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
        fragmentTransaction.replace(FRAGMENT_PLACEHOLDER,mNewFragment);
        fragmentTransaction.commitAllowingStateLoss();

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG,"itemSelected");
        switch (item.getItemId()){
            case R.id.action_user:
                mNewFragment=new UserFragment();
                break;
            case R.id.action_settings:
                mNewFragment=new SettingsFragment();
                break;
            case R.id.action_test:
                mNewFragment=new TestChapterFragment();
                break;
            case R.id.action_blog:
                mNewFragment=new BlogFragment();
                break;


        }


        replaceFragments();

        return true;
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        /* Todo handle reselection*/
        Log.d(TAG,"navigationItemReselected");
    }
}
