package com.example.endrithaziri.libgame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AddGame extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    Button buttonImg;
    Button buttonAddDev;
    Button buttonAddPub;
    BottomNavigationItemView buttonAddGame;
    ImageView imageView;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        buttonImg = (Button) findViewById(R.id.buttonAddImageGame);
        buttonAddDev = (Button) findViewById(R.id.buttonAddDev);
        buttonAddPub = (Button) findViewById(R.id.buttonAddPub);
        buttonAddGame = (BottomNavigationItemView) findViewById(R.id.navigation_add);
        imageView = (ImageView) findViewById(R.id.imageViewAddGame);

        // Listener button image
        buttonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        // Listener menu button add
        buttonAddGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        // Listener button add developer
        buttonAddDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addDev = new Intent (AddGame.this, AddDeveloper.class);
                AddGame.this.startActivity(addDev);
            }
        });

        // Listener button add publisher
        buttonAddPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPub = new Intent (AddGame.this, AddPublisher.class);
                AddGame.this.startActivity(addPub);
            }
        });

    }

    private void saveData() {
        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty("test")) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String word = "Far Cry";
            replyIntent.putExtra(EXTRA_REPLY, word);
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

}
