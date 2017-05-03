package com.ppm.imagine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class FirebaseConnection extends GoogleApiActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

        //ProgressDialogClass pdialog= new ProgressDialogClass();
        private final int RC_SIGN_IN = 9001;
        static ProgressDialog mProgressDialog;

        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_google);

            findViewById(R.id.sign_in_button).setOnClickListener(this);

            mAuth = FirebaseAuth.getInstance();

            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    updateUI(firebaseAuth.getCurrentUser());
                }
            };
        }

        @Override
        public void onStart() {
            super.onStart();

            // Check if user is signed in (non-null) and update UI accordingly.

            mAuth.addAuthStateListener(mAuthListener);

            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
            if (opr.isDone()) {
                // User's cached credentials are valid
                GoogleSignInResult result = opr.get();
                handleGoogleSignInResult(result);
            } else {
                // Asynchronous branch will attempt to sign in the user silently
                showProgressDialog();
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
                        hideProgressDialog();
                        handleGoogleSignInResult(googleSignInResult);
                    }
                });
            }
        }

        @Override
        public void onStop() {
            super.onStop();

            if (mAuthListener != null) {
                mAuth.removeAuthStateListener(mAuthListener);
            }
        }

        private void handleGoogleSignInResult(GoogleSignInResult result) {
            if (result.isSuccess()) {
                firebaseAuthWithGoogle(result.getSignInAccount());
            }
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleGoogleSignInResult(result);
            }
        }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("SIGNIN", "firebaseAuthWithGoogle:" + acct.getId());

        showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(FirebaseConnection.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        hideProgressDialog();
                    }
                });
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            //
            startActivity(new Intent(getApplicationContext(), StartMenuActivity.class));

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("SIGNIN", "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        }
    }
}
