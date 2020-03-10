package com.example.cartrader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class AccountCreation extends AppCompatActivity {
    private ViewPager viewPager;
    private AccountCreationPagerAdapter accountCreationPagerAdapter;
    private static TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        //Adding toolbar and editing toolbar looks
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Sign in");

        //For tabs and swiping left in fragments
        viewPager = (ViewPager) findViewById(R.id.accountCreation_ViewPager);
        accountCreationPagerAdapter = new AccountCreationPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(accountCreationPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        //set which item to start as current, 1 means sign in here
        viewPager.setCurrentItem(1);

        mTabLayout = (TabLayout) findViewById(R.id.accountCreation_tabs);
        mTabLayout.setupWithViewPager(viewPager);
        
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        Intent startMain = new Intent(AccountCreation.this, MainActivity.class);
        startActivity(startMain);
        finish();
    }
}
