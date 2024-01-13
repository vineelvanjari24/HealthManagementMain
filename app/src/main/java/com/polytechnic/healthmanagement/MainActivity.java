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

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.polytechnic.healthmanagement.TrackYourHealth.Fragment.TrackYourHealthFragment;
import com.polytechnic.healthmanagement.Expenses.Fragment.ExpensesFragment;
import com.polytechnic.healthmanagement.MedicalList.Fragment.MedicalListFragment;
import com.polytechnic.healthmanagement.DoctorList.Fragment.DoctorListFragment;
import com.polytechnic.healthmanagement.UserHealth.Fragment.UserHealthFragment;
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

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawable,R.string.close_drawable);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        loadFragment(new TrackYourHealthFragment(),true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id== R.id.userHealth){
                    loadFragment(new UserHealthFragment(MainActivity.this),false);
                } else if (id==R.id.trackYourHealth) {
                    loadFragment(new TrackYourHealthFragment(),false);
                }
                else if (id==R.id.doctorList) {
                    loadFragment(new DoctorListFragment(),false);
                }
                else if (id==R.id.medicalList) {
                    loadFragment(new MedicalListFragment(),false);
                }
                else if (id==R.id.expenses) {
                    loadFragment(new ExpensesFragment(),false);
                }
                else if (id==R.id.visitExperience) {
                    loadFragment(new VisitExperienceFragment(),false);
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }

    private void loadFragment(Fragment fragment,Boolean flag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(flag)
            fragmentTransaction.add(R.id.frameLayoutDrawable,fragment);
        else
            fragmentTransaction.replace(R.id.frameLayoutDrawable,fragment);
        fragmentTransaction.commit();
    }
}