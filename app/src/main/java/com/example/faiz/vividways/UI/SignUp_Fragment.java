package com.example.faiz.vividways.UI;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.faiz.vividways.AppLogs;
import com.example.faiz.vividways.Models.UserModel;
import com.example.faiz.vividways.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Faiz on 7/19/2017.
 */

public class SignUp_Fragment extends android.support.v4.app.Fragment {

    private EditText email,userID,password,confirmpass,fname,lname;
    private Button signup;
    private FirebaseAuth mAuth;
    private FirebaseUser firebase_user;
    private DatabaseReference firebase;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CircleImageView profile_image;
    private String imageURL = null;
    private StorageReference rootStorageRef, folderRef, imageRef;
    private static final int Browse_image = 1;
    public View rootView;
    private final int COMPRESS = 100;
    private File temp_path;
    private String selectedImagePath;
    private Uri selectedImage;
    private Bitmap bitmap;
    private String imgPath;
    private Spinner spinner_country;
    String contry_array[] = {"Country","Afghanistan",
            "Albania",
            "Algeria",
            "Andorra",
            "Angola",
            "Antigua & Barbuda",
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.signup_view,null);
        mAuth = FirebaseAuth.getInstance();
        rootStorageRef = FirebaseStorage.getInstance().getReference();
        folderRef = rootStorageRef.child("profileImages");
        if(mAuth.getCurrentUser()!=null) {
            firebase_user = mAuth.getCurrentUser();

        }

        firebase = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();
        editor.clear();
        email = (EditText) rootView.findViewById(R.id.editText_email);
        userID = (EditText) rootView.findViewById(R.id.editText_userID);
        password = (EditText) rootView.findViewById(R.id.editText_password);
        fname = (EditText) rootView.findViewById(R.id.editText_fname);
        lname = (EditText) rootView.findViewById(R.id.editText_lname);
        signup = (Button)rootView.findViewById(R.id.signup_btn);
        profile_image  = (CircleImageView)rootView.findViewById(R.id.profile_img);
        spinner_country=(Spinner)rootView.findViewById(R.id.country_signup_spin);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass = password.getText().toString();
                final String confrim_passwordd = confirmpass.getText().toString();
                //Checking the length of pasword while registering new USER;
                if (pass.length() <= 6) {
                    main(pass);
                }else if(( fname.getText().toString().equals("")
                        || lname.getText().toString().equals("")
                        || userID.getText().toString().equals("")
                        || pass.equals("")
                        || confrim_passwordd.equals("")) ){
                    Toast.makeText(getActivity(),"Fields Should not be left Empty",Toast.LENGTH_SHORT).show();

                }
                else if(email.getText().length()==0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() ){
                    email.setError("Enter Valid Email Address");
                }
                else if(fname.getText().length()== 0 || !fname.getText().toString().matches("[a-zA-Z ]+")){
                    fname.setError("Invalid Name");
                }
                else if(lname.getText().length() == 0 || !lname.getText().toString().matches("[a-zA-Z ]+")){
                    lname.setError("Invalid Name");
                }else if(spinner_country.getSelectedItem().toString()!=null && spinner_country.getSelectedItem().toString().equals("")){
                    spinner_country.getSelectedItem().toString();
                }
                //Checking the length of pasword while registering new USER;
                else if (pass.length() <= 6) {
                    main(pass);
                } else if(imageURL==null) {
                    Toast.makeText(getActivity(),"Upload Image",Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Sign Up", "Connecting...", true, false);

                        mAuth.createUserWithEmailAndPassword((email.getText().toString()), (password.getText().toString())).addOnCompleteListener(getActivity(),
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        String uid = mAuth.getCurrentUser().getUid();
                                            firebase.child("users").child(uid).setValue(new UserModel(email.getText().toString(), pass,uid, fname.getText().toString(), lname.getText().toString(),imageURL,spinner_country.getSelectedItem().toString()));
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Successfull", Toast.LENGTH_SHORT).show();
                                        AppLogs.logd("createUserWithEmail:onComplete: " + task.isSuccessful());
                                        if(getActivity().getSupportFragmentManager().findFragmentById(R.id.container) != null) {
                                            getActivity().getSupportFragmentManager()
                                                    .beginTransaction().
                                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).commit();



                                        }
//                                                } else
                                        if (!task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), " " + task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                AppLogs.d("FailureSignup",e.getMessage());

                            }
                        });

                    } catch (Exception ex) {

                        ex.printStackTrace();
                    }
                }
            }
        });


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Browse_image);
            }
        });





        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == Browse_image) {
            Uri uri = data.getData();
            String[] imgHolder = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(uri, imgHolder, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(imgHolder[0]);
            imgPath = cursor.getString(columnIndex);
            cursor.close();
            startUpload(imgPath);
        } else {
            Toast.makeText(getActivity(), "Nothing Selected !", Toast.LENGTH_LONG).show();
        }
    }

    public void startUpload(String path) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(path, o);
            profile_image.setImageBitmap(bitmap);
            new android.support.v7.app.AlertDialog.Builder(getActivity())
                    .setTitle("Upload Picture")
                    .setMessage("Are you sure you want to upload picture?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, int which) {
                            //     image.setImageBitmap(bitmap);
                            Log.d("File PATH IS ", imgPath + "");
                            try {
                                File fileRef = new File(imgPath);
                                Date date = new Date(System.currentTimeMillis());
                                String filenew = fileRef.getName();
                                Log.d("fileNewName", filenew);
                                int dot = filenew.lastIndexOf('.');
                                String base = (dot == -1) ? filenew : filenew.substring(0, dot);
                                String extension = (dot == -1) ? "" : filenew.substring(dot + 1);
                                Log.d("extensionsss", extension);
                                final ProgressDialog mProgressDialog = ProgressDialog.show(getActivity(), "Upload Image", "loading...", true, false);
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
                                        imageURL = taskSnapshot.getDownloadUrl().toString();
                                        Log.e("Image ka URL", "" + imageURL);
                                        mProgressDialog.dismiss();
                                        //  messageEditText.setText("");
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    }).show();
        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    private String saveGalaryImageOnLitkat(Bitmap bitmap) {
        try {
            File cacheDir;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                cacheDir = new File(Environment.getExternalStorageDirectory(), getResources().getString(R.string.app_name));
            else
                cacheDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!cacheDir.exists())
                cacheDir.mkdirs();
            String filename = System.currentTimeMillis() + ".jpg";
            File file = new File(cacheDir, filename);
            temp_path = file.getAbsoluteFile();

            // if(!file.exists())
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS, out);
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private void main(String pass) {

        Toast.makeText(getActivity(), pass + "\nYou Password is no longer Stronger \nPlease signup Again with \natleast 7 Character of Pasword.\nThanks ", Toast.LENGTH_SHORT).show();
    }
}
