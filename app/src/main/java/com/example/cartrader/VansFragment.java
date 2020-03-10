package com.example.cartrader;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class VansFragment extends Fragment {

    private TabLayout mTabLayout;
    private FrameLayout layout;
    private View mView;


    public VansFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_vans, container, false);

        mTabLayout = (TabLayout) mView.findViewById(R.id.vans_search_tabs);
        layout = (FrameLayout) mView.findViewById(R.id.vans_fragment_container);

        //create tabs title
        mTabLayout.addTab(mTabLayout.newTab().setText("Vans"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Van dealers"));

        //replace default fragment
        replaceFragment(new VansSearchFragment());

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(new VansSearchFragment());
                        break;

                    case 1:
                        replaceFragment(new VanDealersSearchFragment());
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
        transaction.replace(R.id.vans_fragment_container, fragment);

        transaction.commit();
    }

}
