package com.sharebooks;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mAuthor;
    private TextView mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        String title = getIntent().getExtras().getString("title","");
        String author = getIntent().getExtras().getString("author","");
        String desc = getIntent().getExtras().getString("description","");

        mTitle = findViewById(R.id.detailedBookTitle);
        mAuthor = findViewById(R.id.detailedBookAuthor);
        mDesc = findViewById(R.id.detailedBookDesc);

        mTitle.setText(title);
        mAuthor.setText(author);
        mDesc.setText(desc);

    }

}
