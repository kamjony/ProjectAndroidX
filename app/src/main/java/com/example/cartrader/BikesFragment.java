package com.example.cartrader;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class BikesFragment extends Fragment {

    private TabLayout mTabLayout;
    private FrameLayout layout;
    private View mView;
    private Button btnSearch;



    public BikesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_bikes, container, false);

        mTabLayout = (TabLayout) mView.findViewById(R.id.bikes_search_tabs);
        layout = (FrameLayout) mView.findViewById(R.id.bikes_fragment_container);
//        btnSearch = mView.findViewById(R.id.btnSearch);

        //create tabs title
        mTabLayout.addTab(mTabLayout.newTab().setText("Bikes"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Bike dealers"));

        //replace default fragment
        replaceFragment(new BikesSearchFragment());

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(new BikesSearchFragment());
//                        ((MainActivity)getActivity()).btnSearch.setText("Search 400 dealers");
                        break;

                    case 1:
                        replaceFragment(new BikeDealersSearchFragment());
//                        ((MainActivity)getActivity()).btnSearch.setText("Search 400 dealers");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // Inflate the layout for this fragment
        return mView;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.bikes_fragment_container, fragment);

        transaction.commit();
    }



}
