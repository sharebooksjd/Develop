package com.sharebooks;

/**
 * Created by Jorge on 01/02/2018.
 */

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("volumes")
    Call<BooksResponse> getBook(@Query("q") String isbn, @Field("q") String title, @Field("q") String author, @Query("key") String apiKey);

    @GET("volumes")
    Call<BooksResponse> getBookByIsbn(@Query("q") String isbn, @Query("key") String apiKey);

    @GET("volumes")
    Call<BooksResponse> getBookbyTitle(@Query("q") String title, @Query("key") String apiKey);

    @GET("volumes")
    Call<BooksResponse> getBookbyAuthor(@Query("q") String author, @Query("key") String apiKey);

    @GET("volumes?q=/{author}/{title}")
    Call<BooksResponse> getBookbyAuthorAndTitle(@Path("author") String author, @Path("author") String title, @Query("key") String apiKey);

    @GET("volumes?q=/{isbn}/{title}")
    Call<BooksResponse> getBookbyIsbnAndAuthor(@Path("author") String author, @Path("author") String title, @Query("key") String apiKey);
}

