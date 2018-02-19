package com.sharebooks;

import android.util.Log;

/**
 * Created by Jorge on 18/02/2018.
 */

public class Utils {

    public static boolean validateIsbn13(String isbn) {
        if (isbn == null) {
            return false;
        }

        //remove any hyphens
        isbn = isbn.replaceAll("-", "");

        //must be a 13 digit ISBN
        if (isbn.length() != 13) {
            return false;
        }

        try {
            int tot = 0;
            int digit = Integer.parseInt(isbn.substring(0, 3));
            if(digit != 978){
                return false;
            }
            for (int i = 0; i < 12; i++) {
                digit = Integer.parseInt(isbn.substring(i, i + 1));
                Log.e("SearchResults", "i:" + i +":" + digit);
                tot += (i % 2 == 0) ? digit * 1 : digit * 3;
            }

            //checksum must be 0-9. If calculated as 10 then = 0
            int checksum = 10 - (tot % 10);
            if (checksum == 10) {
                checksum = 0;
            }

            return checksum == Integer.parseInt(isbn.substring(12));
        } catch (NumberFormatException nfe) {
            //to catch invalid ISBNs that have non-numeric characters in them
            return false;
        }
    }

    public static boolean validateIsbn10(String isbn) {
        if (isbn == null) {
            return false;
        }

        //remove any hyphens
        isbn = isbn.replaceAll("-", "");

        //must be a 10 digit ISBN
        if (isbn.length() != 10) {
            return false;
        }

        try {
            int tot = 0;
            int digit = Integer.parseInt(isbn.substring(0, 3));
//            if(digit != 978){
//                return false;
//            }
            for (int i = 0; i < 9; i++) {
                digit = Integer.parseInt(isbn.substring(i, i + 1));
                Log.e("SearchResults", "i:" + i +":" + digit);
                tot += ((10 - i) * digit);
            }

            String checksum = Integer.toString((11 - (tot % 11)) % 11);
            if ("10".equals(checksum)) {
                checksum = "X";
            }

            return checksum.equals(isbn.substring(9));
        } catch (NumberFormatException nfe) {
            //to catch invalid ISBNs that have non-numeric characters in them
            return false;
        }
    }
}
