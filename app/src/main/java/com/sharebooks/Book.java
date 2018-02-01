package com.sharebooks;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.*;
/**
 * Created by Jorge on 01/02/2018.
 */

public class Book {

    @SerializedName("id")
    private Integer id;
    @SerializedName("originalLanguage")
    private String originalLanguage;
    @SerializedName("title")
    private String title;
    @SerializedName("author")
    private String author;

    public Book(Integer id, String originalLanguage, String title, String author) {
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
