package com.example.faiz.vividways.UI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.faiz.vividways.Adapters.ScrollingLinearLayout;
import com.example.faiz.vividways.Adapters.SectionListDataAdapter;
import com.example.faiz.vividways.AppLogs;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Faiz on 7/20/2017.
 */

public class Home_Fragment extends android.support.v4.app.Fragment {

    public View view;
    public ViewPager mViewPager;
    private  DatabaseReference firebase;
    private FirebaseAuth mAuth;
    private ArrayList<ItemObject> imageURL;
    public RecyclerView my_recycler_view;
    public static Home_Fragment home_fragment;
    public LinearLayoutManager layoutManager;
    private StorageReference rootStorageRef, imageRef, folderRef, fileStorageRef;
    private String imgPath;
    private static final int SELECTED_PICTURE = 1;
    private ProgressDialog mProgressDialog;
    private String downloadURL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_layout,null);
        home_fragment = Home_Fragment.this;
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        MainActivity.appbar_TextView.setText("Home");
        MainActivity.Uploadbutton.setVisibility(View.VISIBLE);
        imageURL = new ArrayList<ItemObject>();
        rootStorageRef = FirebaseStorage.getInstance().getReference();
        folderRef = rootStorageRef.child("postImages");
         my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(my_recycler_view);
       MainActivity.back_image.setVisibility(View.GONE);
        MainActivity.delete_image.setVisibility(View.GONE);
        MainActivity.report_image.setVisibility(View.GONE);
        my_recycler_view.setOnFlingListener(snapHelper);
        my_recycler_view.setHasFixedSize(true);


        MainActivity.Uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
            }
        });


        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/postImages%2Fas.jpg?alt=media&token=d6a071d9-840e-4980-93e7-917100312614"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));
        imageURL.add(new ItemObject("Faiz","https://firebasestorage.googleapis.com/v0/b/vividways-1675b.appspot.com/o/profileImages%2FScreenshot_2017-07-14-19-08-55Wed%20Jul%2019%2020%3A24%3A01%20GMT%2B05%3A00%202017.png?alt=media&token=6bcb2510-3b6f-4b77-a0cd-8971afa128ef"));





        SectionListDataAdapter adapter = new SectionListDataAdapter(getActivity(), imageURL);
      //  layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);

        int duration = getResources().getInteger(R.integer.scroll_duration);
        my_recycler_view.setLayoutManager(new ScrollingLinearLayout(getActivity(), LinearLayoutManager.HORIZONTAL, false, duration));
      //  my_recycler_view.setLayoutManager(layoutManager);
        my_recycler_view.setAdapter(adapter);




        return view;
    }


    public static Home_Fragment getInstance(){
        return home_fragment;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == getActivity().RESULT_OK && requestCode == SELECTED_PICTURE) {
            Uri uri = data.getData();
            String[] imgHolder = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(uri, imgHolder, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(imgHolder[0]);
            imgPath = cursor.getString(columnIndex);
            cursor.close();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Want to Upload Image or not ?");
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view1 = inflater.inflate(R.layout.image_view_alert, null);
            ImageView alertImageView = (ImageView) view1.findViewById(R.id.imageView_Alert);
            if (getActivity() != null) {
                Glide.with(getActivity()).load(uri).into(alertImageView);
            }
            builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    uploadImage(imgPath);
                }
            });
            builder.setNeutralButton("Cancel", null);
            builder.setView(view1);
            builder.create().show();
        } else {
            Toast.makeText(getActivity(), "Nothing Selected !", Toast.LENGTH_LONG).show();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
    private void uploadImage(String imgPath) {
        try {
            File fileRef = new File(imgPath);
            Date date = new Date(System.currentTimeMillis());
            final String filenew = fileRef.getName();
            AppLogs.d("fileNewName", filenew);
            int dot = filenew.lastIndexOf('.');
            String base = (dot == -1) ? filenew : filenew.substring(0, dot);
            final String extension = (dot == -1) ? "" : filenew.substring(dot + 1);
            AppLogs.d("extensionsss", extension);
            mProgressDialog = ProgressDialog.show(getActivity(), "Uploading Image", "loading...", true, false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            UploadTask uploadTask;
            Uri file = Uri.fromFile(new File(imgPath));
            imageRef = folderRef.child(base + "" + String.valueOf(date) + "." + extension);
            uploadTask = imageRef.putFile(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(getActivity(), "UPLOAD FAILD", Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                    Log.e("Image ka URL", "" + downloadUrl);
                    downloadURL = downloadUrl;
                    mProgressDialog.dismiss();
                final DatabaseReference ref = firebase.child("user-post").child(mAuth.getCurrentUser().getUid()).push();
                   final ItemObject itemObject = new ItemObject(ref.getKey().toString(), downloadUrl, false, false, mAuth.getCurrentUser().getUid());
                    ref.setValue(itemObject, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            firebase.child("post").push().setValue(itemObject);
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
