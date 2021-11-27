package com.codenipun.task_chatapp;

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


    }
}