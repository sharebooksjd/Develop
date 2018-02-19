package com.sharebooks;

/**
 * Created by Jorge on 01/02/2018.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("volumes")
    Call<BooksResponse> getBookbyIsbn(@Query("q") String isbn, @Query("key") String apiKey);

    @GET("volumes")
    Call<Book> getBookbyTitle(@Query("q") String title, @Query("key") String apiKey);

    @GET("volumes")
    Call<Book> getBookbyAuthor(@Query("q") String author, @Query("key") String apiKey);

    @GET("volumes?q=/{author}/{title}")
    Call<Book> getBookbyAuthorAndTitle(@Path("author") String author, @Path("author") String title, @Query("key") String apiKey);

    @GET("volumes?q=/{isbn}/{title}")
    Call<Book> getBookbyIsbnAndAuthor(@Path("author") String author, @Path("author") String title, @Query("key") String apiKey);
}

