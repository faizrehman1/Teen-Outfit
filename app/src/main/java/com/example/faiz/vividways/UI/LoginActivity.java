package com.example.faiz.vividways.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faiz.vividways.AppLogs;
import com.example.faiz.vividways.Models.UserModel;
import com.example.faiz.vividways.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity" ;
    public TextView signUpText;
    public FragmentManager fragmentManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private LinearLayout fbLogin;
    private boolean fbSignIn = false;
    private Profile profile;
    private UserModel user = new UserModel();
    private Button loginBtn;
    private EditText useremail, userpass;
    DatabaseReference firebase;
    private LoginManager fbLoginMan;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        fbLoginMan = LoginManager.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fbLogin = (LinearLayout) findViewById(R.id.bSearch2);
        signUpText = (TextView) findViewById(R.id.signup);
        loginBtn = (Button) findViewById(R.id.user_login);
        useremail = (EditText) findViewById(R.id.editText_Loginemail);
        userpass = (EditText) findViewById(R.id.editText_loginpass);
        firebase = FirebaseDatabase.getInstance().getReference();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbSignIn = false;

                String emails = useremail.getText().toString();
                String passo = userpass.getText().toString();


                if (emails.length() == 0) {
                    useremail.setError("This is Required Field");
                } else if (passo.length() == 0 && passo.length() <= 6) {
                    userpass.setError("This is Required Field");
                }


                try {
                    final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Sign In", "Connecting...", true, false);

                    mAuth.signInWithEmailAndPassword(emails, passo).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                AppLogs.logd("signInWithEmail:onComplete:" + task.isSuccessful());
                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                openMainScreen();
                                progressDialog.dismiss();
                            } else if (!task.isSuccessful()) {
                                progressDialog.dismiss();
                                AppLogs.logw("signInWithEmail" + task.getException());
                                Toast.makeText(LoginActivity.this, "" + task.getException(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        fragmentManager = getSupportFragmentManager();


        fbLogin.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           fbSignIn = true;
                                           fbLoginMan.logInWithReadPermissions(LoginActivity.this, Arrays.asList("email",
                                                   "user_birthday", "public_profile"));

                                           fbLoginMan.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                                               @Override
                                               public void onSuccess(LoginResult loginResult) {
                                                   Toast.makeText(LoginActivity.this, "LoginSuccessfully Via Facebook", Toast.LENGTH_SHORT).show();

                                                   AccessToken accessToken = loginResult.getAccessToken();

                                                   Log.d("accessToken", " " + accessToken);


                                                   profile = Profile.getCurrentProfile();


                                                   GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                                                       @Override
                                                       public void onCompleted(JSONObject object, GraphResponse response) {
                                                           try {
                                                               Log.e("Graph Resp Json:", "" + object.toString());
                                                               Log.e("Graph Resp Raw:", "" + response.getRawResponse());
                                                               String token = AccessToken.getCurrentAccessToken().getToken();
                                                               Log.e("Graph Token:", "" + token);

                                                               String first_namefire = object.getString("first_name");
                                                               Log.e("FirstName", "" + first_namefire);
                                                               user.setUser_fname(first_namefire);
                                                               String last_namefire = object.getString("last_name");
                                                               Log.e("LastName", "" + last_namefire);
                                                               user.setUser_lname(last_namefire);
                                                               String emailfire = object.getString("email");
                                                               Log.e("Email", "" + emailfire);
                                                               user.setUser_email(emailfire);
                                                               String birthdayfire = object.getString("birthday");
                                                               Log.e("birthday", "" + birthdayfire);
//                                    user.setDob(birthdayfire);
//                                    String genderfire = object.getString("gender");
//                                    Log.e("gender :", "" + genderfire);
//                                    user.setGender(genderfire);
                                                               String dpfire = Profile.getCurrentProfile().getProfilePictureUri(400, 400).toString();
                                                               Log.e("Graph Dp:", "" + dpfire);
                                                               user.setUser_imgURL(dpfire);


                                                           } catch (Exception ex) {
                                                               ex.printStackTrace();
                                                           }
                                                       }

                                                   });
                                                   Bundle parameters = new Bundle();
                                                   String request_params = "id,name,gender,email,birthday";
                                                   String old_req_params = "id, first_name, last_name, email,gender, birthday, location";
                                                   parameters.putString("fields", old_req_params);
                                                   request.setParameters(parameters);
                                                   request.executeAsync();

                                                   /////////////////FB user data save and Sign in END/////////////////////


                                                   handleFacebookAccessToken(loginResult.getAccessToken());


                                               }

                                               @Override
                                               public void onCancel() {
                                                   fbSignIn = false;
                                                   Log.d("TAG", "facebook:onCancel");
                                               }

                                               @Override
                                               public void onError(FacebookException error) {
                                                   fbSignIn = false;
                                                   Toast.makeText(LoginActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                                                   Log.d("TAG", "facebook:onError " + error);
                                               }
                                           });

                                       }
                                   }
        );


        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction().add(R.id.container, new SignUp_Fragment()).addToBackStack(null).commit();


            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    currentUser.getUid();

                    if (fbSignIn) {
                        /**
                         * Face Book Auth
                         * */
                        user.setUser_userID(currentUser.getUid());
                        user.setUser_password("");
                        user.setUser_imgURL(currentUser.getPhotoUrl().toString());
                        AppLogs.logd("Auth State User ID:" + currentUser.getUid());
                        AppLogs.logd("Auth State User Email:" + currentUser.getEmail());
                        AppLogs.logd("Auth State User PhotoUrl:" + currentUser.getPhotoUrl());
                        AppLogs.logd("Auth State User Name:" + currentUser.getDisplayName());

                        firebase.child("users").child(currentUser.getUid()).setValue(user, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                AppLogs.logd("User Logged In For FB:" + user.getUser_email());
                                //     SharedPref.setCurrentUser(LoginActivity.this, user);
                                openMainScreen();
                            }
                        });
                    }else{
                        try {
                                firebase.child("users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        UserModel user = dataSnapshot.getValue(UserModel.class);
//                                AppLogs.logd("User Logged In For My Auth:" + user.getEmail());
                                        if (user != null) {
                                            openMainScreen();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        AppLogs.loge("Error Logged In MYAUTH");

                                    }
                                });

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                    }
                }else {
                    AppLogs.loge("Auth Listener: User Not Signed In");
                }

            }
        };
    }



    public void handleFacebookAccessToken(AccessToken accessToken) {
        Log.d("TAG", "handleFacebookAccessToken: " + accessToken);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.w("TAG", "signInWithCredential", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Successfully Logged In.", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void openMainScreen() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
