package com.example.cartrader;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SignInAccountFragment extends Fragment {

    private View mView;
    TextView btnForgotPassword;

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

        return mView;
    }
}
