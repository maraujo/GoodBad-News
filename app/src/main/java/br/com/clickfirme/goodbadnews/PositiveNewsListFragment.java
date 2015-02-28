package br.com.clickfirme.goodbadnews;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;

import java.util.ArrayList;

/**
 * Created by matheus on 2/27/15.
 */
public class PositiveNewsListFragment extends NewsListFragment{
    private ArrayList<News> mNews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().setTitle(R.string.news_list_title);

        mNews = NewsLab.get(getActivity()).getPositiveNews();
//        ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(getActivity(),android.R.layout.simple_list_item_1,mContacts);
        ArrayAdapter<News> adapter = new NewsAdapter(mNews);
        setListAdapter(adapter);
    }

}
