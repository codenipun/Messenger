package com.codenipun.task_chatapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.codenipun.task_chatapp.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name = getIntent().getStringExtra("name");
        String uid = getIntent().getStringExtra("id");
        String profilePic = getIntent().getStringExtra("profilePic");

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setSubtitle("Onilne");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setIcon(Integer.parseInt(profilePic));

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}