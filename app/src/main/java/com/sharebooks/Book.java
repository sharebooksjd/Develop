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
    @SerializedName("title")
    private String title;
    @SerializedName("author")
    private String author;
    @SerializedName("description")
    private String description;
    @SerializedName("originalLanguage")
    private String originalLanguage;
    private Uri bookCover;

    public Book(String isbn, String originalLanguage, String title, String author, String description) {
        this.isbn = isbn;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public Book(String isbn, String originalLanguage, String title, String author, String description, Uri bookCover) {
        this.isbn = isbn;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.author = author;
        this.description = description;
        this.bookCover = bookCover;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String id) {
        this.isbn = id;
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
