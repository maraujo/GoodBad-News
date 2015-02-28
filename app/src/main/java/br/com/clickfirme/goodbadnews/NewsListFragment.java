package br.com.clickfirme.goodbadnews;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;

import java.util.ArrayList;

/**
 * Created by matheus on 2/28/15.
 */
public class NewsListFragment  extends SherlockListFragment {
    protected class NewsAdapter extends ArrayAdapter<News> {
        public NewsAdapter(ArrayList<News> news){
            super(getActivity(), 0, news);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.news_list_item, null);
            }
            News n = getItem(position);
            TextView nameView = (TextView)convertView.findViewById(R.id.item_name);
            nameView.setText(n.getTitle());

            TextView scoreView = (TextView)convertView.findViewById(R.id.item_score);
            scoreView.setText(String.format("%.2f", n.getScore()));
            if (n.getScore() > 0){
                convertView.setBackgroundColor(Color.argb((int) Math.floor(n.getScore() * 255), 0, (int) Math.floor(n.getScore() * 255), 0));
            }
            else if(n.getScore() < 0){
                convertView.setBackgroundColor(Color.argb((int) Math.floor(n.getScore() * 255 * (-1)),  (int) Math.floor(n.getScore() * 255 * (-1)), 0, 0));
            }


            return convertView;
        }
    }
}
