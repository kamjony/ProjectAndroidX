package com.example.cartrader;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SellFragment extends Fragment {

    private View mView;
    Button btnSelectPhoto;


    public SellFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_sell, container, false);

        btnSelectPhoto = mView.findViewById(R.id.btnSelectPhotos);

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SellAddImageActivity.class);
                startActivity(i);
            }
        });

        // Inflate the layout for this fragment
        return mView;
    }

}
