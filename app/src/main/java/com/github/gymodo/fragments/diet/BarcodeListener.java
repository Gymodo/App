package com.github.gymodo.fragments.diet;

import com.google.mlkit.vision.barcode.Barcode;

/**
 * Interface to be used with the BarcodeAnalyzer.
 */
public interface BarcodeListener {
    void onBarcodeFound(Barcode barcode);
    void onBarcodeNotFound();
}
