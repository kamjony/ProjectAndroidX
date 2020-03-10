package com.example.cartrader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout container;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        container = (LinearLayout) findViewById(R.id.main_fragment_container);
        mBottomNavigationView = findViewById(R.id.navigation);



        //replace default fragment
        replaceFragment(new BuyFragment());

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_buy:
                        replaceFragment(new BuyFragment());
                        break;

                    case R.id.action_sell:
                        replaceFragment(new SellFragment());
                        break;

                    case R.id.action_saved:
                        replaceFragment(new SavedFragment());
                        break;

                    case R.id.action_account:
                        replaceFragment(new AccountFragment());
                        break;

                }
                return true;

            }
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment);

        transaction.commit();
    }

}
