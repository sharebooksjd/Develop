package com.sharebooks;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AdvancedSearch extends Fragment {

    private View mMainView;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mIsbn;
    private TextView mEditorial;

    private FloatingActionButton mSearch;
    private FloatingActionButton mScan;
    private FloatingActionButton mClear;

    public AdvancedSearch() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_advanced_search, container, false);

        Spinner order_by = (Spinner) mMainView.findViewById(R.id.advancedSearchOrderBy);
        ArrayAdapter<CharSequence> adapter_order_by = ArrayAdapter.createFromResource(getContext(), R.array.order_by_array, android.R.layout.simple_spinner_dropdown_item);
        adapter_order_by.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        order_by.setAdapter(adapter_order_by);

        Spinner language = (Spinner) mMainView.findViewById(R.id.advancedSearchLanguage);
        ArrayAdapter<CharSequence> adapter_language = ArrayAdapter.createFromResource(getContext(), R.array.language_array, android.R.layout.simple_spinner_dropdown_item);
        adapter_language.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(adapter_language);

        mTitle = mMainView.findViewById(R.id.advancedSearchTitle);
        mAuthor = mMainView.findViewById(R.id.advancedSearchAutor);
        mIsbn = mMainView.findViewById(R.id.advancedSearchIsbn);
        mEditorial = mMainView.findViewById(R.id.advancedSearchEditorial);

        mSearch = mMainView.findViewById(R.id.advancedSearchBtnSearch);
        mScan = mMainView.findViewById(R.id.advancedSearchBtnScan);
        mClear = mMainView.findViewById(R.id.advancedSearchBtnClear);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchByFields(mTitle.getText().toString(), mAuthor.getText().toString(), mIsbn.getText().toString(), mEditorial.getText().toString());
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

        return mMainView;
    }

    public void clearFields() {
        mTitle.setText("");
        mAuthor.setText("");
        mIsbn.setText("");
        mEditorial.setText("");
    }

    private void sendToScan() {
        Intent scanIntent = new Intent(getActivity(), ScanActivity.class);
        startActivity(scanIntent);
    }

    private void searchByFields(String title, String author, String isbn, String editorial) {

        if(title.isEmpty() && author.isEmpty() && isbn.isEmpty() && editorial.isEmpty()){
            Toast.makeText(getApplicationContext(), "Es obligatorio informar de al menos uno de los campos de búsqueda", Toast.LENGTH_SHORT).show();
        }else {
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
                intent.putExtra("editorial", editorial);
                getActivity().finish();
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "El código ISBN introducido es inválido: " + isbn.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
