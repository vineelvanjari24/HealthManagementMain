package com.polytechnic.healthmanagement.UserHealth.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.healthmanagement.DoctorList.Fragment.DoctorListFragment;
import com.polytechnic.healthmanagement.R;
import com.polytechnic.healthmanagement.UserHealth.AddUserHealthActivity;
import com.polytechnic.healthmanagement.UserHealth.DataBase.UserHealthDB;
import com.polytechnic.healthmanagement.UserHealth.Model.UserHealthModel;
import com.polytechnic.healthmanagement.UserHealth.RecycleView.UserHealthAdapter;

import java.util.ArrayList;


public class UserHealthFragment extends Fragment {
    private static final int ADD_ITEM_REQUEST = 1;
    private RecyclerView recyclerView;
    private UserHealthDB userHealthDB;
    private ArrayList<UserHealthModel> arrayList;
    private  UserHealthAdapter adapter;

    public Context context;
    public UserHealthFragment(Context context) {
    this.context = context;
    }
    public UserHealthFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_health, container, false);
         recyclerView = view.findViewById(R.id.userHealthRecycleView);
        recyclerView.setLayoutManager( new LinearLayoutManager(context));
         userHealthDB = new UserHealthDB(context);
       arrayList = userHealthDB.select();
        if(arrayList.size()>0){
             adapter = new UserHealthAdapter(context,arrayList);
            recyclerView.setAdapter(adapter);
        }
        view.findViewById(R.id.addUserHealth).setOnClickListener(v ->{
            Intent intent =new Intent(context, AddUserHealthActivity.class);
            intent.putExtra("flag",true);
            startActivityForResult(intent, ADD_ITEM_REQUEST);
        });

        return  view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_REQUEST && resultCode == RESULT_OK) {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayoutDrawable,
                    new DoctorListFragment(context,"fromUser"));
            transaction.addToBackStack(null);
            transaction.commit();
        }
        arrayList = userHealthDB.select();
        UserHealthAdapter adapter1= new UserHealthAdapter(context,arrayList);
        recyclerView.setAdapter(adapter1);
    }

}