package br.com.clickfirme.goodbadnews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Created by matheus on 2/27/15.
 */
public abstract class SingleFragmentActivity extends SherlockFragmentActivity{

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.activity_container);
        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.activity_container, fragment).commit();
        }
    }

}