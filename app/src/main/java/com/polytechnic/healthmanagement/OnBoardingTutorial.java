package com.polytechnic.healthmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polytechnic.healthmanagement.Admin.AdminMainActivity;
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
        prev.setVisibility(View.GONE);
        skip=findViewById(R.id.skip);
        SharedPreferences login = getSharedPreferences("login",MODE_PRIVATE);
        if(login.getBoolean("onBoardingTutorial",false)) {
            Intent intent;
            if(!login.getBoolean("user",false) && !login.getBoolean("admin",false)){
                intent = new Intent(OnBoardingTutorial.this, LoginPage.class);
            }
            else if(login.getBoolean("user",false)){
                intent = new Intent(OnBoardingTutorial.this, MainActivity.class);
            }
            else{
                intent = new Intent(OnBoardingTutorial.this, AdminMainActivity.class);
            }
            startActivity(intent);
            finish();
        }
        else {


            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getItem(0) < 5) {
                        prev.setVisibility(View.GONE);
                        vp.setCurrentItem(getItem(1), true);
                    } else {
                        intentmethod();
                    }
                }


            });

            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getItem(0) > 0) {
                        vp.setCurrentItem(getItem(-1), true);
                    }

                }
            });

            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentmethod();
                }
            });

            VPAdapter vpa = new VPAdapter(OnBoardingTutorial.this);
            vp.setAdapter(vpa);
            pageIndicators(0);
            vp.addOnPageChangeListener(viewListener);
        }
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
    private void intentmethod() {
        Intent i=new Intent(OnBoardingTutorial.this, LoginPage.class);
        SharedPreferences login = getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = login.edit();
        editor.putBoolean("onBoardingTutorial",true);
        editor.apply();
        startActivity(i);
        finish();
    }
   
   

}