package com.polytechnic.healthmanagement;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.polytechnic.healthmanagement.TrackYourHealth.Fragment.TrackYourHealthFragment;
import com.polytechnic.healthmanagement.Expenses.Fragment.ExpensesFragment;
import com.polytechnic.healthmanagement.MedicalList.Fragment.MedicalListFragment;
import com.polytechnic.healthmanagement.DoctorList.Fragment.DoctorListFragment;
import com.polytechnic.healthmanagement.UserHealth.Fragment.UserHealthFragment;
import com.polytechnic.healthmanagement.UserLogin.Login;
import com.polytechnic.healthmanagement.VisitExperience.Fragment.VisitExperienceFragment;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigationView);
        toolbar =findViewById(R.id.toolbarDrawable);

        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawable,R.string.close_drawable);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        loadFragment(new TrackYourHealthFragment(),true,"TrackYourHealthFragmentTag");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id== R.id.userHealth){
                    loadFragment(new UserHealthFragment(MainActivity.this),false,"UserHealthFragmentTag");
                } else if (id==R.id.trackYourHealth) {
                    loadFragment(new TrackYourHealthFragment(),false,"TrackYourHealthFragmentTag");

                }
                else if (id==R.id.doctorList) {
                    loadFragment(new DoctorListFragment(MainActivity.this,"fromMainActivity"),false,"DoctorListFragmentTag");
                }
                else if (id==R.id.medicalList) {
                    loadFragment(new MedicalListFragment(),false,"MedicalListFragmentTag");
                }
                else if (id==R.id.expenses) {
                    loadFragment(new ExpensesFragment(),false,"ExpensesFragmentTag");
                }
                else if (id==R.id.visitExperience) {
                    loadFragment(new VisitExperienceFragment(),false,"VisitExperienceFragmentTag");
                }

                drawerLayout.closeDrawer(GravityCompat.START);


                return true;
            }
        });
        if(getIntent().getBooleanExtra("flag",false)){
            loadFragment(new DoctorListFragment(MainActivity.this,"fromUser"),false,"DoctorListFragmentTag");
        }

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }

    private void loadFragment(Fragment fragment,Boolean flag,String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(flag)
            fragmentTransaction.add(R.id.frameLayoutDrawable,fragment,tag);
        else
            fragmentTransaction.replace(R.id.frameLayoutDrawable,fragment,tag);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exit_option, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.exit){
            SharedPreferences login = getSharedPreferences("login",MODE_PRIVATE);
            SharedPreferences.Editor edit = login.edit();
            edit.putBoolean("user",false);
            edit.putBoolean("admin",false);
            edit.apply();
            Intent intent = new Intent(this, Login.class);
            intent.putExtra("flag",true);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}