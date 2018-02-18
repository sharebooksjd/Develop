package com.sharebooks;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

import static com.sharebooks.R.id.barcode_scanner;

import com.sharebooks.Utils;

public class ScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    private static final String TAG = "ScanActivity";
    private BarcodeReader barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(barcode_scanner);
    }

    @Override
    public void onScanned(final Barcode barcode) {
        Log.e(TAG, "onScanned: " + barcode.displayValue);
        int length;
        boolean isbnVerified = false;


        barcodeReader.playBeep();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT).show();
            }
        });
        barcodeReader.pauseScanning();

        length = barcode.displayValue.length();
        if (length == 10) {
            isbnVerified = Utils.validateIsbn10(barcode.displayValue);
        } else if (length == 13) {
            isbnVerified = Utils.validateIsbn13(barcode.displayValue);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "ISBN scanned is wrong: " + barcode.displayValue, Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (isbnVerified) {
            Intent intent = new Intent(ScanActivity.this, SearchResultsActivity.class);
            intent.putExtra("isbn", barcode.displayValue);
            finish();
            startActivity(intent);
        } else {
            barcodeReader.resumeScanning();
        }

    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        Log.e(TAG, "onScannedMultiple: " + barcodes.size());

        String codes = "";
        for (Barcode barcode : barcodes) {
            codes += barcode.displayValue + ", ";
        }

        final String finalCodes = codes;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Barcodes: " + finalCodes, Toast.LENGTH_SHORT).show();
            }
        });
        barcodeReader.pauseScanning();
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

}
