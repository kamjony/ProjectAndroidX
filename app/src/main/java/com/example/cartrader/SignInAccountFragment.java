package com.example.cartrader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

public class SignInAccountFragment extends Fragment {

    private View mView;
    TextView btnForgotPassword;

    private static final String TAG = "CreateAccount";
    private FirebaseAuth mAuth;

    private EditText etEmailSignIn, etPasswordSignIn;
    private Button btnSignIn;

    int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;

    public SignInAccountFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_signin_account, container, false);

        btnForgotPassword = mView.findViewById(R.id.btnForgotPass);
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ForgotPasswordActivity.class);
                startActivity(i);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        LoginButton loginButton = (LoginButton) mView.findViewById(R.id.btnFacebookSignIn);

        etEmailSignIn = mView.findViewById(R.id.etEmailSignIn);

        etPasswordSignIn = mView.findViewById(R.id.etPasswordSignIn);

        btnSignIn = mView.findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmailSignIn.getText().toString();
                String password = etPasswordSignIn.getText().toString();
                signInUser(email,password);
            }
        });

        GoogleSignInButton signInButton = mView.findViewById(R.id.btnGoogleSignIn);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient((Activity)getContext(), gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(i,RC_SIGN_IN);
            }
        });

        return mView;
    }

    private void signInUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            startActivity(new Intent(getActivity(),MainActivity.class));

        } catch (ApiException e){
            Log.w("Google Sign In Error","signInResult:failed code= " + e.getStatusCode());
            Toast.makeText(getContext(),"Failed Google SignIn", Toast.LENGTH_LONG).show();
        }
    }
}
