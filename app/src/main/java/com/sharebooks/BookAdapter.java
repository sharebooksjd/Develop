package com.sharebooks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by Jorge on 17/02/2018.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private List<Book> books;
    private int rowLayout;
    private Context context;


    public class BookViewHolder extends RecyclerView.ViewHolder {
        LinearLayout booksLayout;
        ImageView bookCover;
        TextView bookTitle;
        TextView author;
        TextView bookDescription;

        public BookViewHolder(View v) {
            super(v);
            booksLayout = v.findViewById(R.id.books_layout);
            bookTitle = v.findViewById(R.id.title);
            author = v.findViewById(R.id.author);
            bookCover = v.findViewById(R.id.coverPage);
            bookDescription = v.findViewById(R.id.description);
        }

    }

    public BookAdapter(List<Book> books, int rowLayout, Context context) {
        this.books = books;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new BookViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final BookViewHolder holder, final int position) {

        holder.bookTitle.setText((CharSequence) books.get(position).getVolumeInfo().getTitle());

        int authors_size = 0;
        String authors = "";
        if(books.get(position).getVolumeInfo().getAuthors() != null)
         authors_size = books.get(position).getVolumeInfo().getAuthors().size();
        authors = "";
        for(int i=0; i < authors_size; i++){
            if (i != 0) {
                authors += ",";
            }
            authors += books.get(position).getVolumeInfo().getAuthors().get(i);
        }
        holder.author.setText(authors);
        if ( (books.get(position).getVolumeInfo().getImageLinks() != null) && !(books.get(position).getVolumeInfo().getImageLinks().getThumbnail().isEmpty())){
            Picasso.with(context)
                    .load(books.get(position).getVolumeInfo().getImageLinks().getThumbnail())
                    .resize(40,60)
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_STORE)
                    .into(holder.bookCover);
        }
        holder.bookDescription.setText(books.get(position).getVolumeInfo().getDescription());

        final String finalAuthors = authors;
        holder.bookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                //intent.putExtra("isbn", bookDescription.getText().toString());
                intent.putExtra("author", finalAuthors);
                intent.putExtra("title", holder.bookTitle.getText().toString());
                intent.putExtra("description", holder.bookDescription.getText().toString());
                //intent.putExtra("image", holder.bookDescription.getUrls().toString());
                context.startActivity(intent);
            }
        });

        holder.bookDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                //intent.putExtra("isbn", bookDescription.getText().toString());
                intent.putExtra("author",  holder.bookTitle.getText().toString());
                intent.putExtra("title", holder.bookTitle.getText().toString());
                intent.putExtra("description", holder.bookDescription.getText().toString());
                //intent.putExtra("image", holder.bookDescription.getUrls().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
