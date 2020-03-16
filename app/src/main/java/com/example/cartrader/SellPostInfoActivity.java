package com.example.cartrader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class SellPostInfoActivity extends AppCompatActivity {

    private ArrayList<String> images;
    ViewPager viewPager;
    ImageButton next_btn, prev_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_post_info);

        Intent i = getIntent();
        images = i.getStringArrayListExtra("image_id");

        viewPager = findViewById(R.id.sellPost_ViewPager);
        SellPostImageAdapter adapter = new SellPostImageAdapter(this, images);
        viewPager.setAdapter(adapter);

        next_btn = findViewById(R.id.next_img_btn);
        prev_btn = findViewById(R.id.previous_img_btn);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1,true);
            }
        });
        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1,true);
            }
        });


    }
}
