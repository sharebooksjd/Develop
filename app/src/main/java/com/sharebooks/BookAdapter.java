package com.sharebooks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.sharebooks.R;
import com.sharebooks.Book;
/**
 * Created by Jorge on 17/02/2018.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private List<Book> books;
    private int rowLayout;
    private Context context;


    public static class BookViewHolder extends RecyclerView.ViewHolder {
        LinearLayout booksLayout;
        ImageView bookCover;
        TextView bookTitle;
        TextView author;
        TextView bookDescription;

        public BookViewHolder(View v) {
            super(v);
            booksLayout = (LinearLayout) v.findViewById(R.id.books_layout);
            bookTitle = (TextView) v.findViewById(R.id.title);
            author = (TextView) v.findViewById(R.id.author);
            bookCover = (ImageView) v.findViewById(R.id.coverPage);
            bookDescription = (TextView) v.findViewById(R.id.description);
        }
    }

    public BookAdapter(List<Book> books, int rowLayout, Context context) {
        this.books = books;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new BookViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BookViewHolder holder, final int position) {
        holder.bookTitle.setText(books.get(position).getTitle());
        holder.author.setText(books.get(position).getAuthor());
        holder.bookCover.setImageURI(books.get(position).getBookCover());
        holder.bookDescription.setText(books.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
