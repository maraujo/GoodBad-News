package br.com.clickfirme.goodbadnews;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by matheus on 2/27/15.
 */
public class NewsLab {
    private static NewsLab sNewsLab;
    private ArrayList<News> mNewsList;
    private ArrayList<News> mPositiveNewsList;
    private ArrayList<News> mNegativeNewsList;
    private Context mAppContext;
    private final OkHttpClient client = new OkHttpClient();

    private NewsLab(Context appContext){
        mAppContext = appContext;
        mNewsList = downloadNews();

//        for (int i = 0; i < 100; i++) {
//            News c = new News();
//            c.setTitle("News #" + i);
//            c.setScore(Math.random()*2-1);
//            mNewsList.add(c);
//        }
        downloadNews();
        setNegativeNewsList();
        setPositiveNewsList();
    }

    public static  NewsLab get(Context c){
        if(sNewsLab == null){
            sNewsLab = new NewsLab(c.getApplicationContext());
        }
        return sNewsLab;
    }

    public ArrayList<News> getNews(){
        return mNewsList;
    }

    public ArrayList<News> getPositiveNews(){
        return mPositiveNewsList;
    }

    public ArrayList<News> getNegativeNews(){
        return mNegativeNewsList;
    }


    public void setPositiveNewsList() {
        mPositiveNewsList = new ArrayList<News>();
        for(int i = 0; i < mNewsList.size(); i++){
            if(mNewsList.get(i).getScore() > 0){
                mPositiveNewsList.add(mNewsList.get(i));
            }
        }
    }

    public void setNegativeNewsList() {
        mNegativeNewsList = new ArrayList<News>();
        for(int i = 0; i < mNewsList.size(); i++){
            if(mNewsList.get(i).getScore() < 0){
                mNegativeNewsList.add(mNewsList.get(i));
            }
        }
    }

    public News getContact(String title){
        for(News n : mNewsList){
            if(n.getTitle().equals(title)){
                return n;
            }
        }
        return null;
    }

    private ArrayList<News> downloadNews(){
        ArrayList<News> NewsList = new ArrayList<News>();
        JSONObject jsonResponse = null;
        JSONArray jsonListResponse = null;
        int numberNews = 20;


        Request request = new Request.Builder()
                .url("http://api.nytimes.com/svc/mostpopular/v2/mostshared/all-sections/7.json?api-key=36104532a5405a4dd62e13b2d1e939bc:15:71482252")
                .build();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        try{
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            jsonResponse = new JSONObject(response.body().string());
            jsonListResponse = jsonResponse.getJSONArray("results");;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(jsonListResponse != null){
            try{
                for(int i = 0; i < jsonListResponse.length(); i++){
                    JSONObject jsonNews = jsonListResponse.getJSONObject(i);
                    News news = new News();

                    news.setTitle(jsonNews.get("title").toString());
                    news.setBody(jsonNews.get("abstract").toString().replaceAll("[^a-zA-Z0-9]", " "));
                    news.setScore(Math.random() * 2 - 1);

                    NewsList.add(news);
                }

            }
            catch (Exception e){

            }

            avaliateNewsIFeel();

        }

        return NewsList;
    }

    private void avaliateNewsIFeel(){
        JSONObject listAbstract = new JSONObject();
        String postBody = null;
        JSONObject jsonResponse = null;

        MediaType MEDIA_JSON = MediaType.parse("application/json");
            try{
                for(Integer i = 0; i < mNewsList.size(); i++) {
                    listAbstract.put(i.toString(), mNewsList.get(i).getBody());
                }
                postBody = listAbstract.toString();
            }
            catch (Exception e){

            }
        if(postBody != null){
            Request request = new Request.Builder()
                    .url("http://blackbird.dcc.ufmg.br:8080/api")
                    .post(RequestBody.create(MEDIA_JSON,postBody))
                    .build();

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            Log.e("STRING", postBody);
            try{
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                jsonResponse = new JSONObject(response.body().string());
                for(Integer i = 0; i < mNewsList.size(); i++){
                    JSONObject aux;
                    aux = new JSONObject(jsonResponse.get(i.toString()).toString());
                    aux = new JSONObject(aux.get("analyses_verbose").toString());
                    mNewsList.get(i).setScore(Double.parseDouble(aux.get("SenticNet").toString()));
                    Log.e("Senticnet",mNewsList.get(i).getScore().toString());
                }
//                jsonListResponse = jsonResponse.getJSONArray("results");;

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }


    }
}
