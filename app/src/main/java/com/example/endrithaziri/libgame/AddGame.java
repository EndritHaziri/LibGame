package com.example.endrithaziri.libgame;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import entity.Developer;
import entity.Game;
import entity.Publisher;
import view_model.DeveloperViewModel;
import view_model.GameViewModel;
import view_model.PublisherViewModel;

public class AddGame extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private static final int PICK_IMAGE_REQUEST = 100;
    private Button buttonImg;
    private Button buttonAddDev;
    private Button buttonAddPub;
    private Button buttonEditDev, buttonEditPub;
    private BottomNavigationItemView buttonAddGame;
    private ImageView image;
    private String imgData;
    private String name, description, id_publisher, id_developer;
    private List<Publisher> publishers;
    private List<Developer> developers;
    private GameViewModel gameViewModel;
    private DeveloperViewModel developerViewModel;
    private PublisherViewModel publisherViewModel;
    private Spinner spinnerDev, spinnerPub;
    private List<String> publishersName = new ArrayList<>();
    private List<String> developersName = new ArrayList<>();
    private ArrayAdapter aaDev, aaPub;
    private EditText etName, etDescription;
    private InputStream stream;
    private Bitmap realImage;

    /**
     * ON CREATE
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        /**
         *  PREPARE VARIABLES
         */
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        developerViewModel = ViewModelProviders.of(this).get(DeveloperViewModel.class);
        publisherViewModel = ViewModelProviders.of(this).get(PublisherViewModel.class);
        spinnerDev = findViewById(R.id.spinnerDev);
        spinnerPub = findViewById(R.id.spinnerPub);
        buttonImg = findViewById(R.id.buttonAddImageGame);
        buttonAddDev = findViewById(R.id.buttonAddDev);
        buttonAddPub = findViewById(R.id.buttonAddPub);
        buttonEditDev = findViewById(R.id.buttonEditDev);
        buttonEditPub = findViewById(R.id.buttonEditPub);
        buttonAddGame = findViewById(R.id.navigation_add);
        image = findViewById(R.id.imageViewAddGame);
        etName = findViewById(R.id.addGameTitle);
        etDescription = findViewById(R.id.addGameDescription);
        spinnerDev = findViewById(R.id.spinnerDev);
        spinnerPub = findViewById(R.id.spinnerPub);

        /**
         * GET ALL THE PUBLISHER AND DEVELOPER
         */
        publishers = publisherViewModel.getAllPublisher();
        developers = developerViewModel.getAllDeveloper();

        for (Publisher p: publishers)
            publishersName.add(p.getName());

        for (Developer d: developers)
            developersName.add(d.getName());

        /**
         * SET DATA IN CORRESPONDING FIELDS
         */
        aaDev = new ArrayAdapter(this,android.R.layout.simple_spinner_item, developersName);
        aaDev.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerDev.setAdapter(aaDev);


        aaPub = new ArrayAdapter(this,android.R.layout.simple_spinner_item, publishersName);
        aaPub.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerPub.setAdapter(aaPub);

        /**
         * ADD BUTTON LISTENER
         */
        buttonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(view);
            }
        });
        buttonAddGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        buttonAddDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addDev = new Intent (AddGame.this, AddDeveloper.class);
                AddGame.this.startActivity(addDev);
            }
        });
        buttonAddPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPub = new Intent (AddGame.this, AddPublisher.class);
                AddGame.this.startActivity(addPub);
            }
        });
        buttonEditDev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent editDev = new Intent (AddGame.this, EditDeveloper.class);
                editDev.putExtra("id", developerViewModel.getIdDev(spinnerDev.getSelectedItem().toString()));
                AddGame.this.startActivity(editDev);
            }
        });
        buttonEditPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editPub = new Intent(AddGame.this, EditPublisher.class);
                editPub.putExtra("id", publisherViewModel.getPubId(spinnerPub.getSelectedItem().toString()));
                AddGame.this.startActivity(editPub);
            }
        });
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.navigation_add:
                Intent homepage = new Intent (AddGame.this,Home.class);
                AddGame.this.startActivity(homepage);
                return true;
        }
        return false;
    }

    /**
     * METHOD TO SAVE A NEW GAME
     */
    private void saveData() {

        /**
         * GET THE NEW TEXT
         */
        name = etName.getText().toString();
        description = etDescription.getText().toString();
        id_developer = spinnerDev.getSelectedItem().toString();
        id_publisher = spinnerPub.getSelectedItem().toString();

        /**
         * INSERT THE NEW GAME
         */
        gameViewModel.insert(new Game(name, description, imgData, developerViewModel.getIdDev(id_developer), publisherViewModel.getPubId(id_publisher)));

        /**
         * SHOW INFORMATIONS AND CLOSE
         */
        Toast.makeText(AddGame.this, "Game saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * METHOD TO OPEN THE GALLERY TO CHOOSE AN IMAGE
     * @param v
     */
    public void openGallery(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /**
     * METHOD GET THE SELECTED IMAGE
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                Toast.makeText(AddGame.this, "Image saved", Toast.LENGTH_SHORT).show();
                stream = getContentResolver().openInputStream(data.getData());
                realImage = BitmapFactory.decodeStream(stream);
                image.setImageBitmap(realImage);
                imgData = encodeToBase64(realImage);
            }
            catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * METHOD TO ENCODE THE SELECTED IMAGE
     * @param image
     * @return
     */
    public static String encodeToBase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    /**
     * METHOD TO DECODE THE INPUT STRING TO AN IMAGE
     * @param input
     * @return
     */
    public static Bitmap decodeToBase64(String input) {
       byte[] decodedByte = Base64.decode(input, 0);
       return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


}
