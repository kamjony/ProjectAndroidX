package com.example.cartrader;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class AccountCreationPagerAdapter extends FragmentPagerAdapter {
    public AccountCreationPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CreateAccountFragment();

            case 1:
                return new SignInAccountFragment();


            default:
                return null;
        }
    }

    //Number of fragments you have. For us we have 2 frags
    @Override
    public int getCount() {
        return 2;
    }

    //TO CHANGE THE NAmE OF THE TABS
    public CharSequence getPageTitle(int position){

        switch (position){
            case 0:
                return "Create Account";

            case 1:
                return "Sign In";


            default:
                return null;
        }
    }

}


