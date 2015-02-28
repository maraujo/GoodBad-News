package br.com.clickfirme.goodbadnews;

import java.net.URL;

/**
 * Created by matheus on 2/27/15.
 */
public class News {
    String mTitle;
    String mBody;
    Double mScore;
    URL mLink;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public Double getScore() {
        return mScore;
    }

    public void setScore(Double score) {
        mScore = score;
    }

    public URL getLink() {
        return mLink;
    }

    public void setLink(URL link) {
        mLink = link;
    }
}
