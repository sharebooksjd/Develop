package com.sharebooks;

/**
 * Created by Jorge on 01/02/2018.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RestInterface {

    @GET("book/{isbn}")
    Call<Book> getBookbyIsbn(@Path("isbn") int isbn, @Query("api_key") String apiKey);
}

