package com.sharebooks;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdvancedSearch extends Fragment {

    private View mMainView;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mIsbn;

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

        Spinner order_by = (Spinner) mMainView.findViewById(R.id.order_by);
        ArrayAdapter<CharSequence> adapter_order_by = ArrayAdapter.createFromResource(getContext(), R.array.order_by_array, android.R.layout.simple_spinner_dropdown_item);
        adapter_order_by.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        order_by.setAdapter(adapter_order_by);

        Spinner language = (Spinner) mMainView.findViewById(R.id.language);
        ArrayAdapter<CharSequence> adapter_language = ArrayAdapter.createFromResource(getContext(), R.array.language_array, android.R.layout.simple_spinner_dropdown_item);
        adapter_language.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(adapter_language);

        return mMainView;
    }


}
