package com.polytechnic.healthmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polytechnic.healthmanagement.UserLogin.LoginPage;

public class OnBoardingTutorial extends AppCompatActivity {
    ViewPager vp;
    LinearLayout ll;
    Button prev,next,skip;
    TextView[] dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_tutorial);
        vp=findViewById(R.id.vp);
        ll=findViewById(R.id.ll);
        next=findViewById(R.id.next);
        prev=findViewById(R.id.prev);
        skip=findViewById(R.id.skip);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0)<5){
                    vp.setCurrentItem(getItem(1),true);
                }
                else{
                    Intent i=new Intent(OnBoardingTutorial.this, LoginPage.class);
                    startActivity(i);
                    finish();
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getItem(0)>0){
                    vp.setCurrentItem(getItem(-1),true);
                }

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(OnBoardingTutorial.this,LoginPage.class);
                startActivity(i);
                finish();
            }
        });

        VPAdapter vpa=new VPAdapter(OnBoardingTutorial.this);
        vp.setAdapter(vpa);
        pageIndicators(0);
        vp.addOnPageChangeListener(viewListener);

    }

    public void pageIndicators(int posiiton){
        dots=new TextView[6];
        ll.removeAllViews();

        for (int i=0;i<dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226",Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,getApplicationContext().getTheme()));
            ll.addView(dots[i]);
        }
        dots[posiiton].setTextColor(getResources().getColor(R.color.active,getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            pageIndicators(position);

            if(position>0){
                prev.setVisibility(View.VISIBLE);
            }
            else{
                prev.setVisibility(View.INVISIBLE);
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public int getItem(int i){
        return vp.getCurrentItem()+i;
    }


}