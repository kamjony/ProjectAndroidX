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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SignInAccountFragment extends Fragment {

    private View mView;
    TextView btnForgotPassword;

    private static final String TAG = "CreateAccount";
    private FirebaseAuth mAuth;

    private EditText etEmailSignIn, etPasswordSignIn;
    private Button btnSignIn;

    int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;

    LoginButton loginButton;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private AccessToken mAccessToken;

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
        callbackManager = CallbackManager.Factory.create();

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

        loginButton = (LoginButton) mView.findViewById(R.id.btnFacebookSignIn);
        loginButton.setPermissions(Arrays.asList(EMAIL, "public_profile"));
        loginButton.setFragment(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbLogin();
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
        callbackManager.onActivityResult(requestCode, resultCode, data);

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

    private void fbLogin(){
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mAccessToken = loginResult.getAccessToken();
                getFBuserProfile(mAccessToken);
                //go to Home page

                Log.d(TAG, "token: " +mAccessToken);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG, "error: " +error);
            }
        });
    }

    private void getFBuserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");

                    Log.d(TAG, "fbname: " +first_name);

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    Bundle passParameters = new Bundle();
                    passParameters.putString("name", first_name+" "+last_name);
                    passParameters.putString("email", email);
                    intent.putExtras(passParameters);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

        Toast.makeText(getContext(), "Login Success with facebook", Toast.LENGTH_SHORT).show();

    }
}
