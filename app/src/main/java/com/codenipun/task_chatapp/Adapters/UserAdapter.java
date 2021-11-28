package com.codenipun.task_chatapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codenipun.task_chatapp.Activities.ChatActivity;
import com.codenipun.task_chatapp.Models.UserModel;
import com.codenipun.task_chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    ArrayList<UserModel> list;
    Context context;

    public UserAdapter(ArrayList<UserModel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public UserAdapter(){}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_structure,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel users = list.get(position);

        String senderId = FirebaseAuth.getInstance().getUid();

        String senderRoom = senderId + users.getUid();

        FirebaseDatabase.getInstance().getReference().child("chats").child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            String lastMessage = snapshot.child("lastMsg").getValue(String.class);
                            long time = snapshot.child("lastMsgTime").getValue(Long.class);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
                              holder.lastMessage.setText(lastMessage);
                            holder.lastMessageTime.setText(simpleDateFormat.format(new Date(time)));
                        }else{
                            holder.lastMessage.setText("Tap to chat");
                            holder.lastMessageTime.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // Note : till the given image does  not load on the app we will some another image through place holder
        Picasso.get().load(users.getProfile()).placeholder(R.drawable.user).into(holder.image);
        holder.userName.setText(users.getUname());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("name", users.getUname());
                intent.putExtra("id", users.getUid());
                intent.putExtra("profilePic", users.getProfile());
                context.startActivity(intent);
            }
        });

        // work for last message
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView userName , lastMessage, lastMessageTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.User_Name);
            lastMessage = itemView.findViewById(R.id.last_message);
            lastMessageTime = itemView.findViewById(R.id.lastMessageTime);
        }
    }
}
