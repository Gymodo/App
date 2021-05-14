package com.github.gymodo.fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.gymodo.R;
import com.github.gymodo.exercise.Routine;
import com.github.gymodo.social.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPostFragment extends Fragment {

    static final int GALLERY_REQUEST_CODE = 103;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int CAMERA_PERM_CODE = 101;

    //ImageView
    private ImageView postImage;

    //Images on Listener
    private ImageButton addNewImage;
    private ImageButton addExistingImage;
    private ImageButton addRoutine;

    private EditText newPostContent;
    private Button newPostPublishBtn;
    private Spinner routineSpinner;


    //Post publish info
    private String description;
    private String author;
    private Date date;
    private boolean canPublish = true;

    //Firebase
    FirebaseAuth firebaseAuth;
    String currentPhotoPath;
    private StorageReference mStorageRef;

    private String filePathTmp;
    private Uri contentUriTmp;
    private String imageFullPath;
    private String workoutIdTmp;
    List<String> routineArrayList = new ArrayList<>();
    List<String> routineIdsList = new ArrayList<>();

    public NewPostFragment() {
        // Required empty public constructor
    }

    public static NewPostFragment newInstance(String param1, String param2) {
        NewPostFragment fragment = new NewPostFragment();
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
        return inflater.inflate(R.layout.fragment_new_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Hooks
        newPostContent = view.findViewById(R.id.newPostContent);
        newPostPublishBtn = view.findViewById(R.id.newPostPublishBtn);
        addNewImage = view.findViewById(R.id.addNewImagePost);
        addExistingImage = view.findViewById(R.id.addExisteImagePost);
        addRoutine = view.findViewById(R.id.newPostWorkout);
        postImage = view.findViewById(R.id.newPostImageView);
        routineSpinner = view.findViewById(R.id.routineSpinner);

        //Firebase instances
        firebaseAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //Set adapter
        ArrayAdapter<String> routineArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, routineArrayList);
        routineSpinner.setAdapter(routineArrayAdapter);

        //TODO make sure there is content to publish to prevent an empty post
        newPostPublishBtn.setOnClickListener(v -> {

            if ((newPostContent.getText().toString().isEmpty()) && (postImage.getDrawable() == null)) {
                Toast.makeText(getContext(), "Nothing to post!", Toast.LENGTH_SHORT).show();
            } else if (postImage.getDrawable() != null) {
                uploadImageToFirebase(filePathTmp, contentUriTmp);
            } else {
                publishPost();
            }

        });

        //Add new image
        addNewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });

        //Add existing image
        addExistingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose an image"), GALLERY_REQUEST_CODE);
            }
        });


        addRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addRoutine.setClickable(false);
                //Mostrar spinner
                Routine.listAll().addOnSuccessListener(routines -> {
                    routineArrayList.clear();
                    for (Routine r : routines) {
                        String userId = r.getAuthorId();

                        if (userId == null)
                            continue;

                        if (userId.isEmpty())
                            continue;

                        if (userId.equalsIgnoreCase(firebaseAuth.getUid())) {
                            routineArrayList.add(r.getName());
                            routineIdsList.add(r.getId());
                        }
                    }
                    routineArrayAdapter.notifyDataSetChanged();
                    routineSpinner.setVisibility(View.VISIBLE);
                });
            }
        });

        routineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                workoutIdTmp = routineIdsList.get(position);
                Log.d("workoutid", workoutIdTmp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Nothing selected", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void askCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
            } else {
                dispatchTakePictureIntent();
            }
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Set taken image into imageview
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {

            postImage.setVisibility(View.VISIBLE);

            //galleryAddPic
            File f = new File(currentPhotoPath);
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            getActivity().sendBroadcast(mediaScanIntent);

            //Save file path and content uri in local variable
            filePathTmp = f.getName();
            contentUriTmp = contentUri;

            //Show image in imageview
            postImage.setImageURI(contentUriTmp);

        }

        //Show selected image in imageview
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {

            postImage.setVisibility(View.VISIBLE);

            //Crear un nom per la imatge
            Uri contentURI = data.getData();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            //Extension
            ContentResolver contentResolver = getActivity().getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String ext = mime.getExtensionFromMimeType(contentResolver.getType(contentURI));

            filePathTmp = "JPEG_" + timeStamp + "." + ext;

            Log.d("contenturi", contentURI.toString() + "");

            //Set image
            postImage.setImageURI(contentURI);
            contentUriTmp = contentURI;

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.d("error-while-creating-image", ex + "");

        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(getContext(),
                    "com.example.android.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }

    }

    public void uploadImageToFirebase(String name, Uri contentUri) {

        File f = new File("images", FirebaseAuth.getInstance().getUid() + "/" + name);

        StorageReference imageRef = mStorageRef.child(f.toString());
        imageRef.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(getContext(), "Upload Image Url", Toast.LENGTH_SHORT).show();
                        imageFullPath = f.toString();

                        Log.d("image", "before call post method");
                        Log.d("image", imageFullPath);

                        publishPost();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Image Upload Failled!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void publishPost() {
        date = Calendar.getInstance().getTime();
        author = firebaseAuth.getCurrentUser().getUid();
        description = newPostContent.getText().toString();

        Log.d("image", "publishPost method");
        Log.d("image", imageFullPath + "");

        Post post = new Post();
        post.setAuthorId(author);
        post.setDescription(description);
        post.setCreatedAt(date);
        post.setImageUrl(imageFullPath);
        post.setRoutineId(workoutIdTmp);
        post.save().addOnSuccessListener(s -> {
            NavController navController = Navigation.findNavController(getView());
            navController.popBackStack();
        });

    }
}