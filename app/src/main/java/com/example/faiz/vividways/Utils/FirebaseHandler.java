package com.example.faiz.vividways.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Faiz on 7/25/2017.
 */

public class FirebaseHandler  {
    private DatabaseReference usersRef;
    private DatabaseReference postRef;
    private DatabaseReference user_postRef;
    private DatabaseReference user_takeit_post;
    private DatabaseReference user_takeit_or_leaveit_post;
    private static final String TAG = "FirebaseHandler";
    private static FirebaseHandler ourInstance;
    private DatabaseReference firebaseRef;
    private FirebaseAuth authData;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mfirebaseStorage;



    public static FirebaseHandler getInstance() {
        if (ourInstance == null) {
            AppLogs.d("rootRef", "" + FirebaseDatabase.getInstance().getReference());
            ourInstance = new FirebaseHandler();
        }
        return ourInstance;
    }

    private FirebaseHandler() {
        firebaseRef = FirebaseDatabase.getInstance().getReference();
        mfirebaseStorage = FirebaseStorage.getInstance().getReferenceFromUrl(String.valueOf(FirebaseStorage.getInstance().getReference()));
        initializeChildRefs();
    }

    //  DatabaseStorage
    public StorageReference getRootStorageRef() {
        return mfirebaseStorage;
    }

    //    DatabaseReference
    public DatabaseReference getRootFirebaseRef() {
        return firebaseRef;
    }

    private void initializeChildRefs() {
        usersRef = firebaseRef.child("users");
        postRef = firebaseRef.child("post");
        user_postRef = firebaseRef.child("user-post");
        user_takeit_or_leaveit_post = firebaseRef.child("user-take-or-leave-post");
       // user_takeit_post = firebaseRef.child("user-takeit-post");
    }


    public DatabaseReference getUser_leaveit_post() {
        return user_takeit_or_leaveit_post;
    }

    public DatabaseReference getUsersRef() {
        return usersRef;
    }


    public DatabaseReference getPostRef() {
        return postRef;
    }



    public DatabaseReference getUser_postRef() {
        return user_postRef;
    }


}
