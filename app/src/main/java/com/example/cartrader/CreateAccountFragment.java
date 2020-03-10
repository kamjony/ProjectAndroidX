package com.example.cartrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

public class CreateAccountFragment extends Fragment {

    private View mView;

    public CreateAccountFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_create_account, container, false);

        GoogleSignInButton signInButton = mView.findViewById(R.id.btnGoogleCreate);

        LoginButton loginButton = (LoginButton) mView.findViewById(R.id.btnFacebookCreate);

        return mView;
    }
}
