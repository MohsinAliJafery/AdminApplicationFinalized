package com.bhjbestkalyangame.adminapplication.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.bhjbestkalyangame.adminapplication.Model.User;
import com.bhjbestkalyangame.adminapplication.R;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {


    CircleImageView ProfileImage;
    TextView Username;

    DatabaseReference mDatabaseReference;
    FirebaseUser mFirebaseUser;

    StorageReference mStorageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri ImageUri;
    private StorageTask UploadTask;
    View view;
    RelativeLayout mlayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        ProfileImage = view.findViewById(R.id.profile_image);
        Username = view.findViewById(R.id.username);

        mStorageReference = FirebaseStorage.getInstance().getReference("Uploads");

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mFirebaseUser.getUid());


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Username.setText(user.getUsername());

                if(user.getImageUrl().equals("default")){
                    ProfileImage.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Glide.with(getActivity()).load(user.getImageUrl()).into(ProfileImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }

          
        });


        return view;

    }

    private void openImage() {

        Intent mIntent = new Intent();
        mIntent.setType("image/*");
        mIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(mIntent, IMAGE_REQUEST);
    }

    private String getFileExtention(Uri mUri){
        ContentResolver mContentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(mContentResolver.getType(mUri));
    }

    private void imageUpload(View view){
        mlayout = view.findViewById(R.id.ProfileFragment);
        final ProgressBar progressBar;
        progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mlayout.addView(progressBar, params);
        progressBar.setVisibility(View.VISIBLE);

        if(ImageUri != null){
            final StorageReference mStorageRef = mStorageReference.child(System.currentTimeMillis()+"."+getFileExtention(ImageUri));

            UploadTask = mStorageRef.putFile(ImageUri);
            UploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return mStorageRef.getDownloadUrl();
                }

            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = (Uri) task.getResult();
                        String sUri = downloadUri.toString();

                        Toast.makeText(getContext(), ""+sUri, Toast.LENGTH_SHORT).show();

                        mDatabaseReference  = FirebaseDatabase.getInstance().getReference("Users").child(mFirebaseUser.getUid());
                        HashMap<String, Object> mHashmap = new HashMap<>();
                        mHashmap.put("ImageUrl", sUri);
                        mDatabaseReference.updateChildren(mHashmap);


                        progressBar.setVisibility(View.GONE);
                    }else{

                        Toast.makeText(getContext(), "Failed to upload!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });


        }else{
            Toast.makeText(getContext(), "No image selected!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null){
            ImageUri = data.getData();

            if(UploadTask != null && UploadTask.isInProgress()){
                Toast.makeText(getContext(), "Upload in Progress", Toast.LENGTH_SHORT).show();
            }else{
                imageUpload(view);
            }


        }


    }
}
