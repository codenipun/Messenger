package com.codenipun.task_chatapp.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codenipun.task_chatapp.Models.messageModel;
import com.codenipun.task_chatapp.R;
import com.codenipun.task_chatapp.databinding.ItemReceiveBinding;
import com.codenipun.task_chatapp.databinding.ItemSendBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class messagesAdapter extends RecyclerView.Adapter{

    ArrayList<messageModel> messagesList;
    Context context;

    final int ITEM_SEND = 1;
    final int ITEM_RECEIVE = 2;

    public messagesAdapter(ArrayList<messageModel> messagesList, Context context) {
        this.messagesList = messagesList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SEND){
            View view = LayoutInflater.from(context).inflate(R.layout.item_send, parent, false);
            return new sentViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_receive, parent, false);
            return new receiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        messageModel message = messagesList.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(message.getSenderId())){
            return ITEM_SEND;
        }else{
            return ITEM_RECEIVE;
        }
//        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        messageModel message = messagesList.get(position);
        if(holder.getClass() == sentViewHolder.class){
            sentViewHolder viewHolder = (sentViewHolder) holder;
            viewHolder.binding.sendersMessage.setText(message.getMessage());

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            viewHolder.binding.sendersTime.setText(dateFormat.format(new Date(message.getTimeStamp())));
        }else{
            receiverViewHolder viewHolder = (receiverViewHolder) holder;
            viewHolder.binding.recieversMessage.setText(message.getMessage());
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            viewHolder.binding.receivertime.setText(dateFormat.format(new Date(message.getTimeStamp())));
        }
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    // We have created two sample layout so we also have to make two view holder for that

    public class sentViewHolder extends RecyclerView.ViewHolder{

        ItemSendBinding binding;
        public sentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSendBinding.bind(itemView);
        }
    }
    public class receiverViewHolder extends RecyclerView.ViewHolder{

        ItemReceiveBinding binding;
        public receiverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemReceiveBinding.bind(itemView);
        }
    }
}
