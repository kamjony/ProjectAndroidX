package com.example.cartrader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


public class SellAddImageActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 1;
    GridView gridView;
    Context c;
    ImageAdapter adapter;
    Button btnSave;
    Uri image_Uri;
    ArrayList<String> mImageThumbs = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_add_image);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }

        btnSave = findViewById(R.id.btnImageSave);

        gridView = findViewById(R.id.img_gridView);
        adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);

        //Adding toolbar and editing toolbar looks
        Toolbar myToolbar = (Toolbar) findViewById(R.id.sell_add_image_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Photos");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getCount() <1){
                    showToast("Please add more photos.");
                } else {
                    Intent intent = new Intent(SellAddImageActivity.this, SellPostInfoActivity.class);
                    intent.putStringArrayListExtra("image_id", mImageThumbs);
                    startActivity(intent);

                }

            }
        });


    }


    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Pic");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From Cam");
                    image_Uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_Uri);
                    startActivityForResult(takePhotoIntent,0);


                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK) {
                        if (adapter.getCount() <2) {
                            adapter.addItem(image_Uri.toString());
                            mImageThumbs.add(image_Uri.toString());
                        } else {
                            showToast("Maximum number of images reached.");
                        }
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        if (adapter.getCount() <2) {
                            Uri selectedImage = data.getData();
                            String uri = selectedImage.toString();
                            adapter.addItem(uri);
                            mImageThumbs.add(uri);
                        } else {
                            showToast("Maximum number of images reached.");
                        }


                    }
                    break;
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_photo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_photo_menu_btn){
            selectImage(SellAddImageActivity.this);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        Intent startMain = new Intent(SellAddImageActivity.this, MainActivity.class);
        startActivity(startMain);
        finish();
    }

    private void showToast(String msg){
        Toast.makeText(SellAddImageActivity.this, msg, Toast.LENGTH_LONG).show();
    }

}
