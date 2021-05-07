package com.github.gymodo.fragments.diet;

import com.google.mlkit.vision.barcode.Barcode;

public interface BarcodeListener {
    void onBarcodeFound(Barcode barcode);
    void onBarcodeNotFound();
}
