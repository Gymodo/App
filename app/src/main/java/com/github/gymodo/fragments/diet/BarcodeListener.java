package com.github.gymodo.fragments.diet;

import com.google.mlkit.vision.barcode.Barcode;

import java.util.List;

public interface BarcodeListener {
    void onBarcodeFound(List<Barcode> barcodes);
    void onBarcodeNotFound();
}
