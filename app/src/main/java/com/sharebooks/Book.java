package com.sharebooks;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.*;

/**
 * Created by Jorge on 01/02/2018.
 */

public class Book {

    @SerializedName("isbn")
    private String isbn;
    @SerializedName("volumeInfo")
    private VolumeInfo volInfo;
    @SerializedName("author")
    private String author;
    @SerializedName("description")
    private String description;
    @SerializedName("originalLanguage")
    private String originalLanguage;
    private Uri bookCover;

    public Book(String isbn, VolumeInfo volInfo, String author, String description, String originalLanguage, Uri bookCover) {
        this.isbn = isbn;
        this.volInfo = volInfo;
        this.author = author;
        this.description = description;
        this.originalLanguage = originalLanguage;
        this.bookCover = bookCover;
    }

    public VolumeInfo getVolInfo() {
        return volInfo;
    }

    public void setVolInfo(VolumeInfo volInfo) {
        this.volInfo = volInfo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String id) {
        this.isbn = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getBookCover() {
        return bookCover;
    }

    public void setBookCover(Uri bookCover) {
        this.bookCover = bookCover;
    }
}

class VolumeInfo {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("imageLinks")
    private ImageLinks imageLinks;


    public VolumeInfo(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

}

class Author {

/*    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }*/
}

class ImageLinks {

    @SerializedName("smallThumbnail")
    private String smallThumbnail;

    @SerializedName("thumbnail")
    private String thumbnail;

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}