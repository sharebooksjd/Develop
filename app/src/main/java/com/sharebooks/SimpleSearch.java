package com.sharebooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleSearch extends Fragment {

    private TextView mTitle;
    private TextView mAuthor;
    private TextView mIsbn;

    private FloatingActionButton mSearch;
    private FloatingActionButton mScan;
    private FloatingActionButton mClear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simple_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitle = view.findViewById(R.id.simpleSearchTitle);
        mAuthor = view.findViewById(R.id.simpleSearchAutor);
        mIsbn = view.findViewById(R.id.simpleSearchIsbn);

        mSearch = view.findViewById(R.id.simpleSearchBtnSearch);
        mScan = view.findViewById(R.id.simpleSearchBtnScan);
        mClear = view.findViewById(R.id.simpleSearchBtnClear);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchByFields(mTitle.getText().toString(), mAuthor.getText().toString(), mIsbn.getText().toString());
            }
        });

        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToScan();
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
            }
        });
    }

    public void clearFields() {
        mTitle.setText("");
        mAuthor.setText("");
        mIsbn.setText("");
    }

    private void sendToScan() {
        Intent scanIntent = new Intent(getActivity(), ScanActivity.class);
        startActivity(scanIntent);
    }

    private void searchByFields(String title, String author, String isbn) {
        Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
        if (title.isEmpty())title = "";
        if (author.isEmpty())author = "";
        if (isbn.isEmpty())isbn = "";

        intent.putExtra("title", title);
        intent.putExtra("author", author);
        intent.putExtra("isbn", isbn);
        getActivity().finish();
        startActivity(intent);
    }
}
