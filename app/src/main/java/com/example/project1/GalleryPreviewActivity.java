package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoViewAttacher;

public class GalleryPreviewActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_preview);

        ImageButton abortButton = findViewById(R.id.abortButtonG);


        abortButton.setOnClickListener(this);

        Integer picture = (Integer) getIntent().getIntExtra("picture", 0);
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setImageResource(picture);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.abortButtonG) {
            finish();
            return;
        }
    }
}
