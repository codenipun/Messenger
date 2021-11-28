package com.codenipun.task_chatapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.codenipun.task_chatapp.Models.UserModel;
import com.codenipun.task_chatapp.R;
import com.codenipun.task_chatapp.Adapters.UserAdapter;
import com.codenipun.task_chatapp.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    ArrayList<UserModel> model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model = new ArrayList<>();
        binding.progressBar.setVisibility(View.VISIBLE);
        database = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();

//        model.add(new UserModel("Nipun","1", "NoImage"));
//        model.add(new UserModel("Nipun","2", "NoImage"));
//        model.add(new UserModel("Nipun","3", "NoImage"));
//        model.add(new UserModel("Nipun","4", "NoImage"));

        UserAdapter adapter = new UserAdapter(model, this);

        binding.Profiles.setLayoutManager(new LinearLayoutManager(this));
        binding.Profiles.setAdapter(adapter);
        database.getReference().child("Profiles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                binding.progressBar.setVisibility(View.GONE);
                model.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    if(!user.getUid().equals(FirebaseAuth.getInstance().getUid())) {
                        model.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout :
                mAuth.signOut();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.setUpProfile:
                Intent intent2 = new Intent(this,profileActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}