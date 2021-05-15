package com.github.gymodo.fragments.diet;

import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

/**
 * A image analyzer to find barcodes.
 */
public class BarcodeAnalyzer implements ImageAnalysis.Analyzer {

    BarcodeListener listener;

    public BarcodeAnalyzer(BarcodeListener listener) {
        this.listener = listener;
    }

    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {

        Image mediaImage = imageProxy.getImage();
        if (mediaImage != null) {
            InputImage image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());

            // Pass image to an ML Kit Vision API

            BarcodeScanner scanner = BarcodeScanning.getClient();

            scanner.process(image)
                    .addOnSuccessListener(barcodes -> {
                        for (Barcode barcode : barcodes) {
                            listener.onBarcodeFound(barcode);
                        }
                        imageProxy.close();
                    })
                    .addOnFailureListener(e -> {
                        Log.d("BARCODE", "failure: " + e.getLocalizedMessage());
                        // Task failed with an exception
                        // ...
                    });
        }

    }
}
