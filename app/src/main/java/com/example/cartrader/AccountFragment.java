package com.example.cartrader;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private View mView;
    private Button btnSignIn;
    private TextView userEmailTxt, userNameTxt, signedInTxt;
    String fbName, fbEmail;

    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    AccessToken accessToken;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_account, container, false);

        btnSignIn = mView.findViewById(R.id.btnAccountSignIn);

        userEmailTxt = mView.findViewById(R.id.userEmailTxt);
        userNameTxt = mView.findViewById(R.id.userNameTxt);
        signedInTxt = mView.findViewById(R.id.signedInTxt);

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(),gso);
        account = GoogleSignIn.getLastSignedInAccount(getContext());

        accessToken = AccessToken.getCurrentAccessToken();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (account != null) {
                    mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(),"Successfully signed out",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        }
                    });
                } else if (currentUser != null) {
                    Toast.makeText(getActivity(),"Successfully signed out",Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                } else if (accessToken != null && !accessToken.isExpired()){
                    Toast.makeText(getActivity(),"Successfully signed out",Toast.LENGTH_SHORT).show();
                    LoginManager.getInstance().logOut();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }

                else {
                    Intent i = new Intent(getActivity(), AccountCreation.class);
                    startActivity(i);
                }
            }
        });

        // Inflate the layout for this fragment
        return mView;
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (currentUser != null){
            String userEmail = currentUser.getEmail();
            userEmailTxt.setVisibility(View.VISIBLE);
            signedInTxt.setVisibility(View.VISIBLE);
            userEmailTxt.setText(userEmail);
            btnSignIn.setText("SignOut");

        } else if (account != null){
            String userEmail = account.getEmail();
            String userName = account.getDisplayName();
            userNameTxt.setVisibility(View.VISIBLE);
            userEmailTxt.setVisibility(View.VISIBLE);
            signedInTxt.setVisibility(View.VISIBLE);
            userNameTxt.setText(userName);
            userEmailTxt.setText(userEmail);
            btnSignIn.setText("SignOut");

        } else if (accessToken != null && !accessToken.isExpired()){
            Bundle passedParameters = getActivity().getIntent().getExtras();
            if (passedParameters != null) {
                fbName = passedParameters.getString("name");
                fbEmail = passedParameters.getString("email");
                signedInTxt.setVisibility(View.VISIBLE);
                userNameTxt.setVisibility(View.VISIBLE);
                userEmailTxt.setVisibility(View.VISIBLE);
                userNameTxt.setText(fbName);
                userEmailTxt.setText(fbEmail);
                btnSignIn.setText("SignOut");
            }
        }

        else {
            userEmailTxt.setVisibility(View.GONE);
            userEmailTxt.setText("Not signed in");
            btnSignIn.setText("SignIn");
        }
    }

}
