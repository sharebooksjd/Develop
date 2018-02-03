package com.sharebooks;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Daniel on 03/02/2018.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                SimpleSearch requestsFragment = new SimpleSearch();
                return requestsFragment;
            case 1:
                AdvancedSearch chatsFragment = new AdvancedSearch();
                return chatsFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){

        switch (position){
            case 0:
                return "Simple";
            case 1:
                return "Avanzada";
            default:
                return null;
        }
    }

}
