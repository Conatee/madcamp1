package com.example.project1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PersonInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton abortButton, callButton;
    private String number;

    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        callButton = findViewById(R.id.callButton);
        abortButton = findViewById(R.id.abortButton);

        abortButton.setOnClickListener(this);
        callButton.setOnClickListener(this);

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("name") && getIntent().hasExtra("number")) {
            String name = getIntent().getStringExtra("name");
            String number = getIntent().getStringExtra("number");
            this.number = number;

            setNameNumber(name, number);
        }

        if (getIntent().hasExtra("email")) {
            String email = getIntent().getStringExtra("email");
            setEmail(email);
        } else {
            TextView emailText = findViewById(R.id.textView3);
            TextView emailTag = findViewById(R.id.textView5);
            emailText.setVisibility(View.INVISIBLE);
            emailTag.setVisibility(View.INVISIBLE);
        }

        if (getIntent().hasExtra("photo")) {
            Bitmap photo = (Bitmap) getIntent().getParcelableExtra("photo");
            ImageView photoImage = findViewById(R.id.imageView);
            Glide.with(this)
                    .asBitmap()
                    .load(photo)
                    .into(photoImage);
        } else {
            ImageView photoImage = findViewById(R.id.imageView);
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.no_user_logo)
                    .into(photoImage);
        }
    }

    private void setNameNumber(String name, String number) {
        TextView nameText = findViewById(R.id.textView);
        nameText.setText(name);

        TextView numberText = findViewById(R.id.textView2);
        numberText.setText(number);
    }

    private void setEmail(String email) {
        TextView emailText = findViewById(R.id.textView3);
        emailText.setText(email);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.abortButton: {
                finish();
                return;
            }

            case R.id.callButton: {
                Uri uri = Uri.parse("tel:" + number);
                Intent intent = new Intent(Intent.ACTION_CALL, uri);
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    return;
                }
                startActivity(intent);
            }
        }
    }
}
