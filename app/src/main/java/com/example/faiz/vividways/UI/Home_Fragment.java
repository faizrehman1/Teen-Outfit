package com.example.faiz.vividways.UI;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.faiz.vividways.Adapters.ScrollingLinearLayout;
import com.example.faiz.vividways.Adapters.SectionListDataAdapter;
import com.example.faiz.vividways.Models.FilterItem;
import com.example.faiz.vividways.Models.UserModel;
import com.example.faiz.vividways.Utils.AppLogs;
import com.example.faiz.vividways.Utils.FirebaseHandler;
import com.example.faiz.vividways.Models.ItemObject;
import com.example.faiz.vividways.R;
import com.example.faiz.vividways.UI.Activities.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * Created by Faiz on 7/20/2017.
 */

public class Home_Fragment extends android.support.v4.app.Fragment {

    private static final String TAG = "Home_Activity";
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
    private String string_caption;
    public SectionListDataAdapter adapter;
    private static final int CAMERA_REQUEST = 1888;
    private UserModel userModel;
    public FilterItem filterItemObj;
    private static final int REQUEST_READ_CONTACTS = 2;
    public Uri  mCapturedImageURI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_layout,null);
        home_fragment = Home_Fragment.this;
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        MainActivity.appbar_TextView.setText("Home");
        MainActivity.Uploadbutton.setVisibility(View.VISIBLE);
        MainActivity.getInstance().home_image.setImageResource(R.mipmap.sel_home_icon);
        MainActivity.getInstance().home_text.setTextColor(Color.parseColor("#da59a8"));
        MainActivity.getInstance().setting_image.setImageResource(R.mipmap.settings_icon);
        MainActivity.getInstance().setting_text.setTextColor(Color.parseColor("#bfbfbf"));
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
        userModel = new UserModel();
        filterItemObj = new FilterItem();
        adapter = new SectionListDataAdapter(getActivity(), imageURL);
        //  layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        int duration = getResources().getInteger(R.integer.scroll_duration);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        my_recycler_view.setLayoutManager(new ScrollingLinearLayout(getActivity(), LinearLayoutManager.HORIZONTAL, false, duration));
        //  my_recycler_view.setLayoutManager(layoutManager);
        my_recycler_view.setAdapter(adapter);

        MainActivity.Uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Upload Image");
                alert.setMessage("Want to upload image..?");
                alert.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
              != PackageManager.PERMISSION_GRANTED  && ContextCompat.checkSelfPermission(getActivity(),
               Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{
                                            Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    2);
                        }else {
                            String fileName = "temp.jpg";
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, fileName);
                         mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        }
                    }
                });
                alert.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  mayRequestContacts();
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED  && ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{
                                            Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    2);
                        }else {

                            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, SELECTED_PICTURE);
                        }
                    }
                });
                alert.create().show();



            }
        });


        FirebaseHandler.getInstance().getUser_privacy()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null){
                            if(dataSnapshot.getValue()!=null){
                                FilterItem filterItem = dataSnapshot.getValue(FilterItem.class);
                                filterItemObj.setCan_see(filterItem.getCan_see());
                                filterItemObj.setWant_see(filterItem.getWant_see());
                                //      FilterItem.getInstance(filterItem.getCan_see(),filterItem.getWant_see());
                            }
                        }

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




        FirebaseHandler.getInstance().getPostRef()
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            imageURL.clear();
                            if (dataSnapshot.getValue() != null) {
                                AppLogs.d(TAG, "" + dataSnapshot.getValue().toString());
                                for(DataSnapshot data:dataSnapshot.getChildren()) {
                                    ItemObject itemObject = data.getValue(ItemObject.class);
                                    if (itemObject.getUserID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                                    } else {
                                      if(UserModel.getInstanceIfNotNull()!=null){
                                       if(UserModel.getInstanceIfNotNull().getUser_country()!=null) {
                                           if (!itemObject.getCan_see().equals("")) {
                                               if(filterItemObj!=null) {
                                                   if (!filterItemObj.getWant_see().equals("")) {
                                                       if (itemObject.getCan_see().equals(UserModel.getInstanceIfNotNull().getUser_country())) {
                                                           if (itemObject.getCountry().equals(filterItemObj.getWant_see())) {
                                                               imageURL.add(itemObject);
                                                           }
                                                       }
                                                   }
                                               }
                                               }else imageURL.add(itemObject);
                                           }else
                                               imageURL.add(itemObject);
                                       }
                                     ///   adapter.notifyDataSetChanged();
                                    }
                                }
                                filterPost(imageURL);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });






        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //   populateAutoComplete();
            }
        }else{
            Toast.makeText(getActivity(),"Please Allow Storage ..",Toast.LENGTH_SHORT).show();
        }

    }



    private boolean mayRequestContacts() {
      if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED  && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // getActivity() thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA},
                        2);

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA},
                        2);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
            }
        }
        return false;
    }



        private void filterPost(final ArrayList<ItemObject> imageURL) {
        FirebaseHandler.getInstance()
                .getUser_leaveit_post()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null) {
                            if (dataSnapshot.getValue() != null) {
                                AppLogs.d("Hello", dataSnapshot.getValue().toString());
                                for(DataSnapshot data:dataSnapshot.getChildren()) {
                                    for (DataSnapshot data_again : data.getChildren()) {
                                        ItemObject itemObject = data_again.getValue(ItemObject.class);
                                        for (int i = 0; i < imageURL.size(); i++) {
                                            if (imageURL.get(i).getItemID().equals(itemObject.getItemID())) {
                                                imageURL.remove(i);
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                }
                            }else{
                                adapter.notifyDataSetChanged();
                            }
                        }else{
                            adapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }


    public static Home_Fragment getInstance(){
        return home_fragment;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

      if (resultCode == getActivity().RESULT_OK){

              if(requestCode == 1888){
                  //for camera
                  String[] projection = { MediaStore.Images.Media.DATA};
                  Cursor cursor = getActivity().getContentResolver().query(mCapturedImageURI, projection, null, null, null);
                  int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                  cursor.moveToFirst();
                   imgPath = cursor.getString(column_index_data);
                  cursor.close();
              }
             else if(requestCode == 1) {
                  //for gallery
                  mCapturedImageURI = data.getData();
                  String[] imgHolder = {MediaStore.Images.Media.DATA};
                  Cursor cursor = getActivity().getContentResolver().query(mCapturedImageURI, imgHolder, null, null, null);
                  cursor.moveToFirst();
                  int columnIndex = cursor.getColumnIndex(imgHolder[0]);
                  imgPath = cursor.getString(columnIndex);
                  cursor.close();
              }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Want to Upload Image or not ?");
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view1 = inflater.inflate(R.layout.image_view_alert, null);
            ImageView alertImageView = (ImageView) view1.findViewById(R.id.imageView_Alert);
            final EditText editText_caption = (EditText)view1.findViewById(R.id.caption);
       //     if (getActivity() != null) {
                Glide.with(getActivity()).load(mCapturedImageURI).into(alertImageView);
            //    alertImageView.setImageURI(uri);
        //    }
            builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    string_caption = editText_caption.getText().toString();
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
            final Date date = new Date(System.currentTimeMillis());
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
                        if(FilterItem.getInstanceIfNotNull()==null){
                            FilterItem.getInstance("","");
                        }



                    final ItemObject itemObject = new ItemObject(ref.getKey().toString(), downloadUrl,false,false,mAuth.getCurrentUser().getUid()
                            ,string_caption,0,0, UserModel.getInstanceIfNotNull().getUser_country(),
                            FilterItem.getInstanceIfNotNull().getCan_see(), System.currentTimeMillis());
                    ref.setValue(itemObject, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            firebase.child("post").child(String.valueOf(ref.getKey().toString())).setValue(itemObject);
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
