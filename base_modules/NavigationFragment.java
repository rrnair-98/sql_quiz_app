package com.creeps.sl_app.quizapp.base_modules;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.creeps.sl_app.quizapp.R;

/**
 * Created by rohan on 25/12/17.
 */

public class NavigationFragment extends Fragment{


    private final static String fragmentNames[]={"Me","Test"};

    private FragmentSwapper mFragmentSwapper;
    private Fragment mCurrentFragment;
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_navigation,container,false);

        return view;
    }




    /* In this case the MainActivity houses all Fragments. Hence the need of this interface.
    * The function replace is selfExplanatory
    * getFragment returns a Fragment at specified position
    * closeDrawer closes the navigation Drawer. In l and m there were instances when clicking on an item never closed the drawer.
    * */
    public interface FragmentSwapper{
        public void replaceFragments(int oldFragmentId,Fragment newFragment);
        public Fragment getFragment(int index);
        public void closeDrawer();
    }





}
