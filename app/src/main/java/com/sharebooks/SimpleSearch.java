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
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;

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
                searchByFields(mTitle.getText().toString().trim(), mAuthor.getText().toString().trim(), mIsbn.getText().toString().trim());
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

        if (title.isEmpty() && author.isEmpty() && isbn.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Es obligatorio informar de al menos uno de los campos de búsqueda", Toast.LENGTH_SHORT).show();
        } else {
            boolean isbnVerified = false;

            if (isbn.length() == 10) {
                isbnVerified = Utils.validateIsbn10(isbn);
            } else if (isbn.length() == 13) {
                isbnVerified = Utils.validateIsbn13(isbn);
            }else if (isbn.length()==0){
                isbnVerified=true;
            } else {
                isbnVerified=false;
            }

            if(isbnVerified==true) {
                Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("author", author);
                intent.putExtra("isbn", isbn);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "El código ISBN introducido es inválido: " + isbn.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
