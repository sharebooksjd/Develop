package com.sharebooks;

/**
 * Created by Jorge on 01/02/2018.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("volumes?q=/{isbn}")
    Call<BooksResponse> getBookbyIsbn(@Query("isbn") String isbn, @Query("key") String apiKey);

    @GET("volumes?q=/{title}")
    Call<Book> getBookbyTitle(@Query("title") String title, @Query("key") String apiKey);

    @GET("volumes?q=/{author}")
    Call<Book> getBookbyAuthor(@Query("author") String author, @Query("key") String apiKey);

    @GET("volumes?q=/{author}/{title}")
    Call<Book> getBookbyAuthorAndTitle(@Path("author") String author, @Path("author") String title, @Query("key") String apiKey);

    @GET("volumes?q=/{isbn}/{title}")
    Call<Book> getBookbyIsbnAndAuthor(@Path("author") String author, @Path("author") String title, @Query("key") String apiKey);
}

