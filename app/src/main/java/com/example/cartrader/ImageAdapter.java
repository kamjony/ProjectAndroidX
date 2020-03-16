package com.example.cartrader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    ArrayList<String> imgList = new ArrayList<>();


    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
//        return imageArray.length;
        return imgList.size();
    }

    @Override
    public Object getItem(int position) {
//        return imageArray[position];
        return imgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    void addItem(String url){
        imgList.add(url);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ImageView imgView = new ImageView(mContext);
//        imgView.setImageResource(imgList.get(position));
        imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imgView.setLayoutParams(new GridView.LayoutParams(340, 350));

        String url = (String) getItem(position);

        Picasso.get().load(url).placeholder(R.drawable.addphoto).fit().into(imgView);

        return imgView;
    }


}
