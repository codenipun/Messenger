package com.codenipun.task_chatapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import com.codenipun.task_chatapp.Models.UserModel;
import com.codenipun.task_chatapp.R;
import com.codenipun.task_chatapp.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class profileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = binding.userStatus.getText().toString();
                String username = binding.UserName.getText().toString();

                HashMap<String, Object> obj = new HashMap<>();
                obj.put("uname", username);
                obj.put("Status", status);

                database.getReference().child("Profiles").child(FirebaseAuth.getInstance().getUid()).updateChildren(obj);

                Toast.makeText(profileActivity.this, "profile updated", Toast.LENGTH_SHORT).show();
            }
        });
        database.getReference().child("Profiles").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel users = snapshot.getValue(UserModel.class);
                Picasso.get().load(users.getProfile()).placeholder(R.drawable.user).into(binding.profileImage);

                binding.UserName.setText(users.getUname());
//                binding.userStatus.setText(users.getStatus());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData()!=null){
            Uri sFile = data.getData();
            binding.profileImage.setImageURI(sFile);

            final StorageReference reference = storage.getReference().child("profile_picture").child(FirebaseAuth.getInstance().getUid());

            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Profiles").child(FirebaseAuth.getInstance().getUid())
                                    .child("profile").setValue(uri.toString());
                        }
                    });
                }
            });
        }else{
            return;
        }
    }
}