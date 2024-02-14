package com.polytechnic.healthmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class VPAdapter extends PagerAdapter {
    Context ct;
    int images[]={R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img,R.drawable.img};

    public VPAdapter(Context c){
        ct=c;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater lf=(LayoutInflater) ct.getSystemService(ct.LAYOUT_INFLATER_SERVICE);
        View v=lf.inflate(R.layout.slider,container,false);
        ImageView sliderImage=v.findViewById(R.id.titleImage);
        sliderImage.setImageResource(images[position]);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
