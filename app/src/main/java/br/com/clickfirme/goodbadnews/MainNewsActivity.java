package br.com.clickfirme.goodbadnews;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;


import android.os.Bundle;

import android.view.MenuItem;

import com.actionbarsherlock.app.ActionBar;


public class MainNewsActivity extends SingleFragmentActivity {

    private ActionBar actionBar;

    @Override
    protected Fragment createFragment() {
        return new PositiveNewsListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(R.string.news_list_title);

        ActionBar.Tab Frag1Tab = actionBar.newTab();
        ActionBar.Tab Frag2Tab = actionBar.newTab();

        Fragment f1 = new PositiveNewsListFragment();
        Fragment f2 = new NegativeNewsListFragment();

        Frag1Tab.setTabListener(new BinaryTabListener(f1));
        Frag2Tab.setTabListener(new BinaryTabListener(f2));

        Frag1Tab.setText("Positive News");
        Frag2Tab.setText("Negative News");


        actionBar.addTab(Frag1Tab);
        actionBar.addTab(Frag2Tab);

    }

    class BinaryTabListener implements ActionBar.TabListener{
        public Fragment mFragment;
        public BinaryTabListener(Fragment fragment){
            this.mFragment = fragment;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            fragmentTransaction.replace(R.id.activity_container, mFragment);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }
    }

}