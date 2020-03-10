package com.example.cartrader;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuyFragment extends Fragment {

    private TabLayout mTabLayout;
    private LinearLayout container;
    private View mView;
    Button btnSearch;


    public BuyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_buy, container, false);

        mTabLayout = (TabLayout) mView.findViewById(R.id.buy_vehicleType_tabs);
        container = (LinearLayout) mView.findViewById(R.id.buy_fragment_container);
        btnSearch = mView.findViewById(R.id.btnSearch);

        //create tabs title
        mTabLayout.addTab(mTabLayout.newTab().setText("Cars"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Bikes"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Vans"));

        replaceFragment(new CarsFragment());

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(new CarsFragment());
                        btnSearch.setText("Search 400 cars");
                        break;

                    case 1:
                        replaceFragment(new BikesFragment());
                        btnSearch.setText("Search 400 bikes");
                        break;

                    case 2:
                        replaceFragment(new VansFragment());
                        btnSearch.setText("Search 400 vans");
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
        transaction.replace(R.id.buy_fragment_container, fragment);

        transaction.commit();
    }

}
