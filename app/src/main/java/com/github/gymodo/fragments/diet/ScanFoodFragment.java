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
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
        View view = inflater.inflate(R.layout.fragment_scan_food, container, false);

        debugText = view.findViewById(R.id.ScanFoodDebugText);
        scanAgain = view.findViewWithTag(R.id.ScanFoodAgain);
        previewView = view.findViewById(R.id.ScanFoodPreview);
        found = false;
        queue = Volley.newRequestQueue(view.getContext());
        queue.start();

        /*
        BarcodeScannerOptions options =
                new BarcodeScannerOptions.Builder()
                        .build();

         */

        /*
        NavController navController = Navigation.findNavController(view);
        navController.getPreviousBackStackEntry().getSavedStateHandle().getLiveData("scanData").postValue(new Food());
        navController.popBackStack();

         */

        cameraProviderFuture = ProcessCameraProvider.getInstance(view.getContext());
        requestCamera();

        return view;
    }

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }

    /**
     * Get the angle by which an image must be rotated given the device's current
     * orientation.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, boolean isFrontFacing)
            throws CameraAccessException {
        // Get the device's current rotation relative to its "native" orientation.
        // Then, from the ORIENTATIONS table, look up the angle the image must be
        // rotated to compensate for the device's rotation.
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        // Get the device's sensor orientation.
        CameraManager cameraManager = (CameraManager) activity.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);

        if (isFrontFacing) {
            rotationCompensation = (sensorOrientation + rotationCompensation) % 360;
        } else { // back-facing
            rotationCompensation = (sensorOrientation - rotationCompensation + 360) % 360;
        }
        return rotationCompensation;
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
                public void onBarcodeFound(Barcode barcode) {
                    if (found)
                        return;
                    debugText.setText("Found barcode:" + barcode.getRawValue());

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getFoodUrl(barcode.getRawValue()),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        Log.d("BARCODE","BARCODE FOUND: " + barcode.getRawValue());
                                        Toast.makeText(getContext(), "GOT RESPONSE", Toast.LENGTH_SHORT).show();
                                        JSONObject product = response.getJSONObject("product");
                                        String name = product.getString("product_name");
                                        Toast.makeText(getContext(), "Found product: " + name, Toast.LENGTH_SHORT).show();
                                        debugText.setText("Product found: " + name);

                                        Food food = new Food();
                                        food.setName(product.getString("product_name"));

                                        JSONObject nutriments = product.getJSONObject("nutriments");

                                        if(nutriments.has("energy-kcal"))
                                            food.setCalories(nutriments.getDouble("energy-kcal"));
                                        if(nutriments.has("cholesterol"))
                                            food.setCholesterol(nutriments.getDouble("cholesterol"));
                                        if(nutriments.has("carbohydrates"))
                                            food.setTotalCarboHydrate(nutriments.getDouble("carbohydrates"));
                                        if(nutriments.has("proteins"))
                                            food.setProtein(nutriments.getDouble("proteins"));
                                        if(nutriments.has("sodium"))
                                            food.setSodium(nutriments.getDouble("sodium"));
                                        if(nutriments.has("fat"))
                                            food.setTotalFat(nutriments.getDouble("fat"));

                                        NavController navController = Navigation.findNavController(getView());
                                        navController.getPreviousBackStackEntry()
                                                .getSavedStateHandle()
                                                .getLiveData("scanData", new Food())
                                                .postValue(food);
                                        navController.popBackStack();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

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
                }

                @Override
                public void onBarcodeNotFound() {

                }
            }));

            Camera camera = cameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, imageAnalysis, preview);
        }

    }
}