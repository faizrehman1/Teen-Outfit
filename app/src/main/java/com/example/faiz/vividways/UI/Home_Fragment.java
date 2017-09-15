package com.example.faiz.vividways.UI;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Faiz on 7/20/2017.
 */

public class Home_Fragment extends android.support.v4.app.Fragment {

    private static final String TAG = "Home_Activity";
    public View view;
    public ViewPager mViewPager;
    private DatabaseReference firebase;
    private FirebaseAuth mAuth;
    private ArrayList<ItemObject> imageURL;
    public RecyclerView my_recycler_view;
    public static Home_Fragment home_fragment;
    public LinearLayoutManager layoutManager;
    private StorageReference rootStorageRef, imageRef, folderRef, fileStorageRef;
    private String imgPath;
    private static final int SELECTED_PICTURE = 9162;
    private ProgressDialog mProgressDialog;
    private String downloadURL;
    private String string_caption;
    public SectionListDataAdapter adapter;
    private static final int CAMERA_REQUEST = 1888;
    private UserModel userModel;
    public FilterItem filterItemObj;
    private static final int REQUEST_READ_CONTACTS = 2;
    public Uri mCapturedImageURI;
    public Button leave_btn, take_btn;
    private static int displayedposition = 0;
    private static int firstVisibleInListview;
    private float itemWidth;
    private float padding;
    private float firstItemWidth;
    private float allPixels;
    private int mLastPosition;
    int counterOne = 0,counterTwo = 0;
    public int position=0;
    public int i=0;
    private Camera camera;
   // private CameraPreview mPreview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_layout, null);
        home_fragment = Home_Fragment.this;
        leave_btn = (Button) view.findViewById(R.id.leave_btn);
        take_btn = (Button) view.findViewById(R.id.take_btn);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();

        Display display =getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        itemWidth = getResources().getDimension(R.dimen.item_width);
        padding = (size.x - itemWidth) / 2;
        firstItemWidth = getResources().getDimension(R.dimen.padding_item_width);

        allPixels = 0;

     //   camera = getCameraInstance();


        MainActivity.appbar_TextView.setText("Home");
        MainActivity.Uploadbutton.setVisibility(View.VISIBLE);
        MainActivity.getInstance().home_image.setImageResource(R.mipmap.sel_home_icon);
        MainActivity.getInstance().home_text.setTextColor(Color.parseColor("#da59a8"));
        //  MainActivity.getInstance().setting_image.setImageResource(R.mipmap.settings_icon);
        //   MainActivity.getInstance().setting_text.setTextColor(Color.parseColor("#bfbfbf"));
        imageURL = new ArrayList<ItemObject>();
        rootStorageRef = FirebaseStorage.getInstance().getReference();
        folderRef = rootStorageRef.child("postImages");
        my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(my_recycler_view);
        MainActivity.back_image.setVisibility(View.GONE);
        MainActivity.delete_image.setVisibility(View.GONE);
        MainActivity.report_image.setVisibility(View.GONE);
        //    Toast.makeText(getActivity(),"Your Location : \n California City, California",Toast.LENGTH_SHORT).show();
        my_recycler_view.setOnFlingListener(snapHelper);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.stopNestedScroll();
        my_recycler_view.stopScroll();
        userModel = new UserModel();
        filterItemObj = new FilterItem();
        adapter = new SectionListDataAdapter(getActivity(), imageURL);
      //  layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        int duration = getResources().getInteger(R.integer.scroll_duration);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
     //   my_recycler_view.setLayoutManager();
        ScrollingLinearLayout scrollingLinearLayout  = new ScrollingLinearLayout(getActivity(), LinearLayoutManager.HORIZONTAL, false, duration);
         my_recycler_view.setLayoutManager(scrollingLinearLayout);
     //   scrollingLinearLayout.setSmoothScrollbarEnabled(false);
        //  my_recycler_view.setLayoutManager(layoutManager);
        my_recycler_view.setAdapter(adapter);
      //  firstVisibleInListview = layoutManager.findFirstVisibleItemPosition();
     //   my_recycler_view.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
       //     @Override
     //       public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                // Stop only scrolling.
     //           return rv.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING;
     //       }
     //   });



        leave_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"Leave It. ",Toast.LENGTH_SHORT).show();

            //    Home_Fragment.getInstance().my_recycler_view.smoothScrollToPosition(i--);
//                counterOne++;
//
//                int leave_it_count=0;
//                //     Home_Fragment.getInstance().my_recycler_view.smoothScrollToPosition(i+1);
//                AppLogs.d(TAG,imageURL.get(position).leaveit_count+"");
//            //    Home_Fragment.getInstance().my_recycler_view.smoothScrollToPosition(i+1);
//                AppLogs.d(TAG,imageURL.get(position).getLeaveit_count()+"");
//                leave_it_count = imageURL.get(position).getLeaveit_count()+1;
//                imageURL.get(position).setLeaveit_count(leave_it_count);
//                final int finalLeave_it_count = leave_it_count;
//                FirebaseHandler.getInstance().getPostRef()
//                        .child(String.valueOf(imageURL.get(position).getItemID()))
//                        .child("leaveit_count")
//                        .setValue(leave_it_count, new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//
//                                //      ItemObject itemObject = new ItemObject(itemsList.get(i).getItemID(),itemsList.get(i).getItemImageURl(),true,false);
//                                FirebaseHandler.getInstance().getPostRef().child(String.valueOf(imageURL.get(position).getItemID())).child("leave_it_check").setValue(true);
//                                FirebaseHandler.getInstance().getUser_postRef().child(imageURL.get(position).getUserID()).child(imageURL.get(position).getItemID()).child("leave_it_check").setValue(true);
//                                FirebaseHandler.getInstance().getUser_postRef().child(imageURL.get(position).getUserID()).child(imageURL.get(position).getItemID()).child("leaveit_count").setValue(finalLeave_it_count);
//
//                                FirebaseHandler.getInstance().getUser_leaveit_post()
//                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
//                                        .child("user-leave-posts")
//                                        .child(imageURL.get(position).getItemID())
//                                        .setValue(new ItemObject(imageURL.get(position).getItemID(),
//                                                imageURL.get(position).getItemImageURl(),imageURL.get(position).isTake_it_check(),
//                                                true, imageURL.get(position).getUserID(),imageURL.get(position).getCaption(),
//                                                imageURL.get(position).getLeaveit_count(),imageURL.get(position).getTakeit_count(),
//                                                imageURL.get(position).getCountry(),imageURL.get(position).getCan_see(),System.currentTimeMillis()), new DatabaseReference.CompletionListener() {
//                                            @Override
//                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                            }
//                                        });
//                            }
//                        });
//
//


            }
        });

        take_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"Take it",Toast.LENGTH_SHORT).show();

//                int takit_count = 0;
             //   Home_Fragment.getInstance().my_recycler_view.smoothScrollToPosition(i++);
//                if (imageURL.size() > 0) {
//                    AppLogs.d(TAG, imageURL.get(position).getTakeit_count() + "");
//                    takit_count = imageURL.get(position).getTakeit_count() + 1;
//                    imageURL.get(position).setTakeit_count(takit_count);
//                    final int finalTakit_count = takit_count;
//                    FirebaseHandler.getInstance().getPostRef()
//                            .child(String.valueOf(imageURL.get(position).getItemID()))
//                            .child("takeit_count")
//                            .setValue(takit_count, new DatabaseReference.CompletionListener() {
//                                @Override
//                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
////
//                                    //  ItemObject itemObject = new ItemObject(itemsList.get(i).getItemID(),itemsList.get(i).getItemImageURl(),true,false);
//                                    FirebaseHandler.getInstance().getPostRef().child(String.valueOf(imageURL.get(position).getItemID())).child("take_it_check").setValue(true);
//                                    FirebaseHandler.getInstance().getUser_postRef().child(imageURL.get(position).getUserID()).child(imageURL.get(position).getItemID()).child("take_it_check").setValue(true);
//                                    FirebaseHandler.getInstance().getUser_postRef().child(imageURL.get(position).getUserID()).child(imageURL.get(position).getItemID()).child("takeit_count").setValue(finalTakit_count);
//
//                                    FirebaseHandler.getInstance().getUser_leaveit_post()
//                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
//                                            .child("user-take-posts")
//                                            .child(imageURL.get(position).getItemID())
//                                            .setValue(new ItemObject(imageURL.get(position).getItemID(), imageURL.get(position).getItemImageURl(), true,
//                                                    imageURL.get(position).isLeave_it_check(), imageURL.get(position).getUserID(), imageURL.get(position).getCaption(),
//                                                    imageURL.get(position).getLeaveit_count(), imageURL.get(position).getTakeit_count(), imageURL.get(position).getCountry(),
//                                                    imageURL.get(position).getCan_see(), System.currentTimeMillis()), new DatabaseReference.CompletionListener() {
//
//                                                @Override
//                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                                    Home_Fragment.getInstance().my_recycler_view.smoothScrollToPosition(counterOne + 1);
//                                                    counterOne++;
//                                                }
//                                            });
//                                }
//                            });

//                }
            }
        });


        MainActivity.Uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Upload Image");
                alert.setMessage("Want to upload image..?");
                alert.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{
                                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    2);
                        } else {
                    //       if(Build.VERSION.SDK_INT >16) {
                            File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Images");
                            imagesFolder.mkdirs();
                            File image = new File(imagesFolder.getPath(), "MyImage_.jpg");
                       //        String fileName = "temp.jpg";
                               ContentValues values = new ContentValues();
                               values.put(MediaStore.Images.Media.TITLE, image.getAbsolutePath());
                               mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                      //     }


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
                                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{
                                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    2);
                        } else {

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
                        if (dataSnapshot != null) {
                            if (dataSnapshot.getValue() != null) {
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
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                ItemObject itemObject = data.getValue(ItemObject.class);
                                if (itemObject.getUserID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                                } else {
                                    if (UserModel.getInstanceIfNotNull() != null) {
                                        if (UserModel.getInstanceIfNotNull().getUser_country() != null) {
                                            if (!itemObject.getCan_see().equals("")) {
                                                if (filterItemObj != null) {
                                                    if (!filterItemObj.getWant_see().equals("")) {
                                                        if (itemObject.getCan_see().equals(UserModel.getInstanceIfNotNull().getUser_country())) {
                                                            if (itemObject.getCountry().equals(filterItemObj.getWant_see())) {
                                                                imageURL.add(itemObject);
                                                            }
                                                        }
                                                    }
                                                }
                                            } else imageURL.add(itemObject);
                                        } else
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
        } else {
            Toast.makeText(getActivity(), "Please Allow Storage ..", Toast.LENGTH_SHORT).show();
        }

    }


    private boolean mayRequestContacts() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
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
                        if (dataSnapshot != null) {
                            if (dataSnapshot.getValue() != null) {
                                AppLogs.d("Hello", dataSnapshot.getValue().toString());
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
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
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }


    public static Home_Fragment getInstance() {
        return home_fragment;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == getActivity().RESULT_OK) {

            //    if (requestCode == Crop.REQUEST_PICK && resultCode == getActivity().RESULT_OK) {

            //   }
            // else if (requestCode == Crop.REQUEST_CROP) {
            //     handleCrop(resultCode, data);
            //  }


            if (requestCode == 1888) {
                //for camera
//
//                if(Build.VERSION.SDK_INT <=19) {
//                    Intent intent = new Intent("com.android.camera.action.CROP");
//                    intent.setData(data.getData());
//                    intent.putExtra("crop", true);
//                    intent.putExtra("aspectX", 1);
//                    intent.putExtra("aspectY", 1);
//                    intent.putExtra("outputX", 96);
//                    intent.putExtra("outputY", 96);
//                    intent.putExtra("noFaceDetection", true);
//                    intent.putExtra("return-data", true);
//                    startActivityForResult(intent, 2);
//
//                }


                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(mCapturedImageURI, projection, null, null, null);
                int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                imgPath = cursor.getString(column_index_data);
                cursor.close();

                if(Build.VERSION.SDK_INT <=19){
                    handleCrop(resultCode,data,mCapturedImageURI);
                }else{
                    beginCrop(data.getData());
                }

            } else if (requestCode == 9162) {
                //for gallery
                //   beginCrop(data.getData());

                mCapturedImageURI = data.getData();
                String[] imgHolder = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(mCapturedImageURI, imgHolder, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(imgHolder[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();


                if(Build.VERSION.SDK_INT >19) {
                    beginCrop(data.getData());
                }else{
                    handleCrop(resultCode,data,mCapturedImageURI);
                }
            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(resultCode, data, mCapturedImageURI);
            }

        } else {
            Toast.makeText(getActivity(), "Nothing Selected !", Toast.LENGTH_LONG).show();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImage(ImageView imgPath) {
        try {
            //   File fileRef = new File(imgPath);


            final Date date = new Date(System.currentTimeMillis());
            //   final String filenew = fileRef.getName();
            //    AppLogs.d("fileNewName", filenew);
            //    int dot = filenew.lastIndexOf('.');
            //     String base = (dot == -1) ? filenew : filenew.substring(0, dot);
            //     final String extension = (dot == -1) ? "" : filenew.substring(dot + 1);
            //     AppLogs.d("extensionsss", extension);
            mProgressDialog = ProgressDialog.show(getActivity(), "Uploading Image", "loading...", true, false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            UploadTask uploadTask;
            // Uri file = Uri.fromFile(new File(imgPath));
            imageRef = folderRef.child(String.valueOf(date) + ".png");

            imgPath.setDrawingCacheEnabled(true);
            imgPath.buildDrawingCache();
            Bitmap b = imgPath.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            //  UploadTask uploadTask = ref.child(id + ".png").putBytes(bytes);


            uploadTask = imageRef.putBytes(bytes);
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
                    if (FilterItem.getInstanceIfNotNull() == null) {
                        FilterItem.getInstance("", "");
                    }


                    final ItemObject itemObject = new ItemObject(ref.getKey().toString(), downloadUrl, false, false, mAuth.getCurrentUser().getUid()
                            , string_caption, 0, 0, UserModel.getInstanceIfNotNull().getUser_country(),
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

    private Uri beginCrop(Uri source) {

        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(getActivity(), Home_Fragment.this);
//        Crop.of(source,destination).withMaxSize(100,100).start(getActivity(), TabFragment1.this);

        return destination;
    }

    private void handleCrop(int resultCode, final Intent result, Uri mCapturedImageURI) {

        // imgPath = null;
        if (resultCode == getActivity().RESULT_OK) {
            Bitmap bitmap = null;
            try {
               if(Build.VERSION.SDK_INT <=19){
                  //     bitmap =(Bitmap) mCapturedImageURI.
                //   InputStream image_stream =getActivity().getContentResolver().openInputStream(mCapturedImageURI);

                 //   bitmap= BitmapFactory.decodeStream(image_stream);
                       bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mCapturedImageURI);

               }else {
                   bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Crop.getOutput(result));
               }
                   AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Want to Upload Image or not ?");
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.image_view_alert, null);
                final ImageView alertImageView = (ImageView) view1.findViewById(R.id.imageView_Alert);
                final EditText editText_caption = (EditText) view1.findViewById(R.id.caption);
                //     if (getActivity() != null) {
                //            mCapturedImageURI = Crop.getOutput(result);
//                String[] imgHolder = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getActivity().getContentResolver().query(mCapturedImageURI, imgHolder, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(imgHolder[0]);
//                imgPath = cursor.getString(columnIndex);
//                cursor.close();
         //      if(Build.VERSION.SDK_INT <=19){
                //   bitmap = decodeFile(imgPath);
             //      bitmap = Bitmap.createBitmap(bitmap, 0, 0,
               //            bitmap.getWidth(), bitmap.getHeight(), , true);
              //     bitmap = Bitmap.createScaledBitmap(myPictureBitmap, editText_caption.getWidth(),editText_caption.getHeight(),true);
         //      }
              alertImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
               alertImageView.setImageBitmap(bitmap);
                alertImageView.invalidate();
             //   alertImageView.setImageURI(mCapturedImageURI);
                //    Glide.with(getActivity()).load(mCapturedImageURI).into(alertImageView);
                //    alertImageView.setImageURI(uri);
                //    }
                builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        string_caption = editText_caption.getText().toString();
                        uploadImage(alertImageView);


                    }
                });
                builder.setNeutralButton("Cancel", null);
                builder.setView(view1);
                builder.create().show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void calculatePositionAndScroll(RecyclerView recyclerView) {
        int expectedPosition = Math.round((allPixels + padding - firstItemWidth) / itemWidth);
        // Special cases for the padding items
        if (expectedPosition == -1) {
            expectedPosition = 0;
        } else if (expectedPosition >= recyclerView.getAdapter().getItemCount() - 2) {
            expectedPosition--;
        }
        scrollListToPosition(recyclerView, expectedPosition);
    }

    private void scrollListToPosition(RecyclerView recyclerView, int expectedPosition) {
        float targetScrollPos = expectedPosition * itemWidth + firstItemWidth - padding;
        float missingPx = targetScrollPos - allPixels;
        if (missingPx != 0) {
            recyclerView.smoothScrollBy((int) missingPx, 0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("param",mCapturedImageURI);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mCapturedImageURI = savedInstanceState.getParcelable("param");
        }
    }

    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }
    public static Camera getCameraInstance(){
        Camera c = null;

        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            Log.d("cam", "Camera is not available - in use or does not exist");
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
