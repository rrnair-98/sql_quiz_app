package com.creeps.sl_app.quizapp.core_services.utils.modal;

/**
 * Created by rohan on 26/12/17.
 */

public class BlogItem {
    int blog_id;
    private String shortDesc;
    private String author;
    private String date;
    private String urlToActual;
    private String urlToImage;
    public BlogItem(int blog_id, String shortDesc, String author, String date, String urlToActual, String urlToImage) {
        this.blog_id = blog_id;
        this.shortDesc = shortDesc;
        this.author = author;
        this.date = date;
        this.urlToActual = urlToActual;
        this.urlToImage=urlToImage;
    }


    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public int getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrlToActual() {
        return urlToActual;
    }

    public void setUrlToActual(String urlToActual) {
        this.urlToActual = urlToActual;
    }
}
