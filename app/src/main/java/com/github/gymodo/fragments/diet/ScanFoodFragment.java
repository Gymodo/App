package com.github.gymodo.fragments.diet;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.gymodo.R;
import com.github.gymodo.food.Food;
import com.github.gymodo.viewmodels.FoodViewModel;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static android.content.Context.CAMERA_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanFoodFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    Button scanAgain;
    TextView debugText;
    boolean found;
    RequestQueue queue;

    FoodViewModel foodViewModel;

    public static ScanFoodFragment newInstance() {
        ScanFoodFragment fragment = new ScanFoodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_scan_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        foodViewModel = new ViewModelProvider(requireActivity()).get("ScannedFood", FoodViewModel.class);

        debugText = view.findViewById(R.id.ScanFoodDebugText);
        scanAgain = view.findViewWithTag(R.id.ScanFoodAgain);
        previewView = view.findViewById(R.id.ScanFoodPreview);
        found = false;
        queue = Volley.newRequestQueue(view.getContext());
        queue.start();

        cameraProviderFuture = ProcessCameraProvider.getInstance(view.getContext());
        requestCamera();
    }

    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(getContext(), "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(getContext(), "Error starting camera " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(getContext()));
    }

    private String getFoodUrl(String id) {
        return String.format("https://world.openfoodfacts.org/api/v0/product/%s.json", id);
    }

    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {
        if (previewView != null) {
            previewView.setImplementationMode(PreviewView.ImplementationMode.PERFORMANCE);

            Preview preview = new Preview.Builder()
                    .build();

            CameraSelector cameraSelector = new CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build();

            preview.setSurfaceProvider(previewView.getSurfaceProvider());

            ImageAnalysis imageAnalysis =
                    new ImageAnalysis.Builder()
                            .setTargetResolution(new Size(1280, 720))
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build();

            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(getContext()), new BarcodeAnalyzer(new BarcodeListener() {
                @Override
                public void onBarcodeFound(List<Barcode> barcodes) {
                    if (found)
                        return;

                    for (Barcode barcode : barcodes) {
                        debugText.setText("Found barcode:" + barcode.getRawValue());

                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getFoodUrl(barcode.getRawValue()),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            Log.d("BARCODE", "BARCODE FOUND: " + barcode.getRawValue());
                                            JSONObject product = response.getJSONObject("product");
                                            String name = product.getString("product_name");
                                            debugText.setText("Product found: " + name);

                                            Food food = new Food();
                                            food.setName(product.getString("product_name"));
                                            food.setBarcode(barcode.getRawValue());

                                            if (product.has("image_url"))
                                                food.setImageUrl(product.getString("image_url"));

                                            JSONObject nutriments = product.getJSONObject("nutriments");

                                            if (nutriments.has("energy-kcal"))
                                                food.setCalories(nutriments.getDouble("energy-kcal"));
                                            if (nutriments.has("cholesterol"))
                                                food.setCholesterol(nutriments.getDouble("cholesterol"));
                                            if (nutriments.has("carbohydrates"))
                                                food.setTotalCarboHydrate(nutriments.getDouble("carbohydrates"));
                                            if (nutriments.has("proteins"))
                                                food.setProtein(nutriments.getDouble("proteins"));
                                            if (nutriments.has("sodium"))
                                                food.setSodium(nutriments.getDouble("sodium"));
                                            if (nutriments.has("fat"))
                                                food.setTotalFat(nutriments.getDouble("fat"));

                                            foodViewModel.setFood(food);

                                            NavController navController = Navigation.findNavController(getView());
                                            navController.popBackStack();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            found = false;
                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        debugText.setText("Error finding product: " + error.getLocalizedMessage());
                                        found = false;
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<>();
                                headers.put("User-agent", "Gymodo - Android - Version 1.0 - https://github.com/Gymodo/App");
                                return headers;
                            }
                        };

                        queue.add(request);

                        found = true;

                        break;
                    }
                }

                @Override
                public void onBarcodeNotFound() {

                }
            }));

            Camera camera = cameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, imageAnalysis, preview);
        }

    }
}