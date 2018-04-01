package com.sharebooks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.credentials.CredentialsOptions;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {

    private ImageButton mFacebookLoginBtn;
    private ImageButton mGoogleLoginBtn;

    private Button mSignUpBtn;
    private Button mSignInBtn;

    private TextInputLayout mMailLayout;
    private TextInputLayout mPassLayout;

    private TextInputEditText mMailField;
    private TextInputEditText mPassField;

    private Dialog registerDlg;

    private TextInputLayout mMailRegisterLayout;
    private TextInputLayout mPassRegisterLayout;
    private TextInputLayout mNameRegisterLayout;
    private TextInputEditText registerMail;
    private TextInputEditText registerPass;
    private TextInputEditText registerName;
    private Button registerBtn;
    private MaterialDialog dialog;

    private CallbackManager mCallbackManager;

    private static final String Facebook_TAG = "Facebook login";
    private static final String Google_TAG = "Google login";
    private static final String TAG = "Mail login";

    private static final int Google_SIGN_IN = 1;
    private static final int Facebook_SIGN_IN = 64206;

    private GoogleSignInClient mGoogleSignInClient;
    CredentialsClient mCredentialsClient;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Fabric.with(this, new Crashlytics());

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseFirestore.getInstance();

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookLoginBtn = findViewById(R.id.fbLoginButton);
        mGoogleLoginBtn = findViewById(R.id.googleLoginButton);

        mSignUpBtn = findViewById(R.id.signUpBtn);
        mSignInBtn = findViewById(R.id.signInBtn);

        mMailLayout = findViewById(R.id.emailFormLayout);
        mPassLayout = findViewById(R.id.passFormLayout);

        mMailField = findViewById(R.id.mailForm);
        mPassField = findViewById(R.id.passForm);

        registerDlg = new Dialog(this);
        registerDlg.setContentView(R.layout.registerpopup);

        mMailRegisterLayout = (TextInputLayout) registerDlg.findViewById(R.id.mailRegisterLayout);
        mPassRegisterLayout = (TextInputLayout) registerDlg.findViewById(R.id.passRegisterLayout);
        mNameRegisterLayout = (TextInputLayout) registerDlg.findViewById(R.id.nameRegisterLayout);

        registerMail = (TextInputEditText) registerDlg.findViewById(R.id.mailRegister);
        registerPass = (TextInputEditText) registerDlg.findViewById(R.id.passRegister);
        registerName = (TextInputEditText) registerDlg.findViewById(R.id.nameRegister);
        registerBtn = (Button)registerDlg.findViewById(R.id.registerBtn);

        // Instantiate client for interacting with the credentials API. For this demo
        // application we forcibly enable the SmartLock save dialog, which is sometimes
        // disabled when it would conflict with the Android autofill API.
        CredentialsOptions options = new CredentialsOptions.Builder()
                .forceEnableSaveDialog()
                .build();
        mCredentialsClient = Credentials.getClient(this, options);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("ShareBooks")
                .content("Cargando...")
                .progress(true, 0);
        dialog = builder.build();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mFacebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpWithFacebookAcount();
            }
        });
//prueba
        mGoogleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpWithGoogleAcount();
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    registerDlg.show();
            }
        });

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    signInWithMail();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateRegisterForm()) {
                    signUpWithMail();
                }
            }
        });
    }

    private void signInWithMail() {
        String email = mMailField.getText().toString();
        String password = mPassField.getText().toString();

        dialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    dialog.dismiss();
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    updateUI(null);
                }

            }
        });
    }

    private void signUpWithMail() {
        String email = registerMail.getText().toString();
        String password = registerPass.getText().toString();
        final String name = registerName.getText().toString();

        dialog.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    AddToDatabase(user,name);
                    updateUI(user);
                } else {
                    dialog.dismiss();
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

            }
        });
    }

    private void AddToDatabase(FirebaseUser user, String name) {

        final FirebaseUser firebase_user = user;
        final String name_profile;

        if(firebase_user.getDisplayName() == null){
            name_profile = name;
        }else{
            name_profile = firebase_user.getDisplayName();
        }
        DocumentReference docRef = mDb.collection("users").document(firebase_user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(!document.exists()) {
                        final Map<String, Object> dbUser = new HashMap<>();
                        dbUser.put("id", firebase_user.getUid());
                        dbUser.put("name", name_profile);
                        dbUser.put("mail", firebase_user.getEmail());
                        dbUser.put("mail_verified", firebase_user.isEmailVerified());
                        dbUser.put("provider_id", firebase_user.getProviders().get(0));

                        mDb.collection("users").document(firebase_user.getUid()).set(dbUser);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Esta cuenta de correo ya se encuentra creada en el sistema mediante " + firebase_user.getProviders().get(0), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signUpWithGoogleAcount() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        dialog.show();
        startActivityForResult(signInIntent, Google_SIGN_IN);
    }

    private void signUpWithFacebookAcount() {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
        dialog.show();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(Facebook_TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                dialog.dismiss();
                Log.d(Facebook_TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                dialog.dismiss();
                Log.d(Facebook_TAG, "facebook:onError", error);
                // ...
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        if (requestCode == Google_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                dialog.dismiss();
                updateUI(null);
            }
        } else if (requestCode == Facebook_SIGN_IN) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(Facebook_TAG, "handleFacebookAccessToken:" + token);

        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Facebook_TAG, "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    AddToDatabase(user,null);
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Facebook_TAG, "signInWithCredential:failure", task.getException());
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(Google_TAG, "firebaseAuthWithGoogle:" + acct.getIdToken());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Google_TAG, "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    AddToDatabase(user,null);
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Google_TAG, "signInWithCredential:failure", task.getException());
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mMailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mMailLayout.setError("Obligatorio");
            valid = false;
        } else {
            mMailField.setError(null);
        }

        String password = mPassField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassLayout.setError("Obligatorio");
            valid = false;
        } else {
            mPassField.setError(null);
        }

        return valid;
    }

    private boolean validateRegisterForm() {
        boolean valid = true;

        String email = registerMail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mMailRegisterLayout.setError("Obligatorio");
            valid = false;
        } else {
            mMailRegisterLayout.setError(null);
        }

        String password = registerPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassRegisterLayout.setError("Obligatorio");
            valid = false;
        } else {
            mPassRegisterLayout.setError(null);
        }

        String userName = registerName.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            mNameRegisterLayout.setError("Obligatorio");
            valid = false;
        } else {
            mNameRegisterLayout.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent startIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(startIntent);
            finish();
        }
    }

}