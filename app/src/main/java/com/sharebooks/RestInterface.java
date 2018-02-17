package com.sharebooks;

/**
 * Created by Jorge on 01/02/2018.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("book/{isbn}")
    Call<Book> getBookbyIsbn(@Path("isbn") String isbn, @Query("key") String apiKey);

    @GET("book/{title}")
    Call<Book> getBookbyTitle(@Path("title") String title, @Query("key") String apiKey);

    @GET("book/{author}")
    Call<Book> getBookbyAuthor(@Path("author") String author, @Query("key") String apiKey);

    @GET("book/{author}/{title}")
    Call<Book> getBookbyAuthorAndTitle(@Path("author") String author, @Path("author") String title, @Query("key") String apiKey);

    @GET("book/{isbn}/{title}")
    Call<Book> getBookbyIsbnAndAuthor(@Path("author") String author, @Path("author") String title, @Query("key") String apiKey);
}

