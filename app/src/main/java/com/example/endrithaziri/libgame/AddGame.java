package com.example.endrithaziri.libgame;

import android.arch.lifecycle.ViewModelProviders;
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

import view_model.GameViewModel;

public class AddGame extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";



    Button buttonImg;
    BottomNavigationItemView buttonAddGame;
    ImageView imageView;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD

        setContentView(R.layout.activity_add_game);


        button = (Button) findViewById(R.id.buttonAddImageGame);
=======
        setContentView(R.layout.activity_add_game);



        buttonImg = (Button) findViewById(R.id.buttonAddImageGame);
        buttonAddGame = (BottomNavigationItemView) findViewById(R.id.navigation_add);
>>>>>>> e17598791eb5a0f7a2fa4e9f1f9f7ecbf909e46d
        imageView = (ImageView) findViewById(R.id.imageViewAddGame);

        buttonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
<<<<<<< HEAD

=======
>>>>>>> e17598791eb5a0f7a2fa4e9f1f9f7ecbf909e46d

        buttonAddGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveData();
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
