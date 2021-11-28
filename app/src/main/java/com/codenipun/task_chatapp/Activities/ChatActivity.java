package com.codenipun.task_chatapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import com.codenipun.task_chatapp.Adapters.messagesAdapter;
import com.codenipun.task_chatapp.Models.messageModel;
import com.codenipun.task_chatapp.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    messagesAdapter adapter;
    ArrayList<messageModel> messageList;

    String senderRoom, receiverRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String name = getIntent().getStringExtra("name");
        String receiverUid = getIntent().getStringExtra("id");
        String profilePic = getIntent().getStringExtra("profilePic");
        String senderUid = FirebaseAuth.getInstance().getUid();

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setSubtitle("Onilne");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.etMessage.setBackgroundResource(android.R.color.transparent);
//        getSupportActionBar().setIcon(Integer.parseInt(profilePic));

        messageList = new ArrayList<>();
        adapter = new messagesAdapter(messageList, this);
        binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.chatRecyclerView.setAdapter(adapter);

        senderRoom = senderUid + receiverUid;
        receiverRoom = receiverUid + senderUid;

//        messageList.add(new messageModel("1", "hello", 2121155475));
//        adapter.notifyDataSetChanged();

        database.getReference().child("chats").child(senderRoom).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    messageModel myMessage = dataSnapshot.getValue(messageModel.class);
                    messageList.add((myMessage));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageTxt = binding.etMessage.getText().toString();

                if(messageTxt.isEmpty()){return;}

                binding.etMessage.setText("");
                Date date = new Date();
                messageModel message = new messageModel(senderUid, messageTxt, date.getTime());

                HashMap<String, Object> lastMsgObject = new HashMap<>();
                lastMsgObject.put("lastMsg", message.getMessage());
                lastMsgObject.put("lastMsgTime", date.getTime());

                database.getReference().child("chats").child(senderRoom).updateChildren(lastMsgObject);
                database.getReference().child("chats").child(receiverRoom).updateChildren(lastMsgObject);

                database.getReference()
                        .child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .push()
                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference()
                                .child("chats")
                                .child(receiverRoom)
                                .child("messages")
                                .push()
                                .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });


                    }
                });
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}