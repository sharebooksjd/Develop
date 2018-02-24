package com.sharebooks;

import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity {

    private final static String API_KEY = "AIzaSyDbzq5M2nflOHORE7TFkoReopWbTE2_YFo";
    private final static String USER_ID = "110246020694123690476";

    TextView mIsbn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        String isbn = getIntent().getExtras().getString("isbn","");
        String title = getIntent().getExtras().getString("title","");
        String author = getIntent().getExtras().getString("author","");
        String editorial = getIntent().getExtras().getString("editorial","");

        if(isbn != null && isbn != "defaultKey"){
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.books_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            RestInterface apiService =
                    RestClient.getClient().create(RestInterface.class);

            String llamada = componeLlamada(isbn, title, author, editorial);

            Call<BooksResponse> call = apiService.getBook(llamada, API_KEY);

            call.enqueue(new Callback<BooksResponse>() {
                @Override
                public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                    int statusCode = response.code();
                    List<Book> books = response.body().getItems();
                    if(books==null){

                    }else{
                        recyclerView.setAdapter(new BookAdapter(books, R.layout.list_item_book, SearchResultsActivity.this));
                    }
                }

                @Override
                public void onFailure(Call<BooksResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("SearchResults", t.toString());
                }
            });
        }
    }

    @NonNull
    private String componeLlamada(String isbn, String title, String author, String editorial) {
        String llamada="";
        if(isbn.length()!=0){
            llamada+="+isbn:"+isbn;
        }
        if(title.length()!=0){
            llamada+="+intitle:"+title.replace(" ","+");
        }
        if(author.length()!=0){
            llamada+="+inauthor:"+author.replace(" ","+");
        }
        if(editorial.length()!=0){
            llamada+="+inpublisher:"+editorial.replace(" ","+");
        }
        return llamada;
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}