package com.example.endrithaziri.libgame;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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

    private static final int PICK_IMAGE_REQUEST = 100;
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    Button buttonImg;
    Button buttonAddDev;
    Button buttonAddPub;
    Button buttonEditDev, buttonEditPub;
    BottomNavigationItemView buttonAddGame;
    ImageView image;
    Uri imageUri;
    String imgData;
    List<Publisher> publishers;
    List<Developer> developers;
    private GameViewModel gameViewModel;
    private DeveloperViewModel developerViewModel;
    private PublisherViewModel publisherViewModel;
    Spinner spinnerDev, spinnerPub;
    List<String> publishersName = new ArrayList<>();
    List<String> developersName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        developerViewModel = ViewModelProviders.of(this).get(DeveloperViewModel.class);
        publisherViewModel = ViewModelProviders.of(this).get(PublisherViewModel.class);

        publishers = publisherViewModel.getAllPublisher();
        developers = developerViewModel.getAllDeveloper();

        for (Publisher p: publishers
             ) {
            publishersName.add(p.getName());
        }

        for (Developer d: developers
                ) {
            developersName.add(d.getName());
        }

        spinnerDev = findViewById(R.id.spinnerDev);

        ArrayAdapter aaDev = new ArrayAdapter(this,android.R.layout.simple_spinner_item, developersName);
        aaDev.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerDev.setAdapter(aaDev);

        spinnerPub = findViewById(R.id.spinnerPub);
        ArrayAdapter aaPub = new ArrayAdapter(this,android.R.layout.simple_spinner_item, publishersName);

        aaPub.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerPub.setAdapter(aaPub);

        buttonImg = (Button) findViewById(R.id.buttonAddImageGame);
        buttonAddDev = (Button) findViewById(R.id.buttonAddDev);
        buttonAddPub = (Button) findViewById(R.id.buttonAddPub);
        buttonEditDev = (Button) findViewById(R.id.buttonEditDev);
        buttonEditPub = (Button) findViewById(R.id.buttonEditPub);
        buttonAddGame = (BottomNavigationItemView) findViewById(R.id.navigation_add);
        image = (ImageView) findViewById(R.id.imageViewAddGame);

      /*  SharedPreferences myPreference = getPreferences(MODE_PRIVATE);
        String imageS = myPreference.getString("imagePreference", "");
        Bitmap imageB;
        if(!imageS.equals("")) {
            imageB = decodeToBase64(imageS);
            image.setImageBitmap(imageB);
        }
*/
        /* ==== ADD LISTENERS ==== */

        // Listener button image
        buttonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(view);
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

        buttonEditDev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent editDev = new Intent (AddGame.this, EditDeveloper.class);
                editDev.putExtra("id", developerViewModel.getIdDev(spinnerDev.getSelectedItem().toString()));
                AddGame.this.startActivity(editDev);
            }
        });

    }

    private void saveData() {
        String name, description, id_publisher, id_developer;
        EditText etName, etDescription;

        etName = findViewById(R.id.addGameTitle);
        name = etName.getText().toString();

        etDescription = findViewById(R.id.addGameDescription);
        description = etDescription.getText().toString();

        spinnerDev = findViewById(R.id.spinnerDev);
        id_developer = spinnerDev.getSelectedItem().toString();

        spinnerPub = findViewById(R.id.spinnerPub);
        id_publisher = spinnerPub.getSelectedItem().toString();

        gameViewModel.insert(new Game(name, description, imgData, developerViewModel.getIdDev(id_developer), publisherViewModel.getPubId(id_publisher)));
        Toast.makeText(AddGame.this, "Game saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void openGallery(View v){
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            InputStream stream;
            try {
                Toast.makeText(AddGame.this, "Image saved", Toast.LENGTH_SHORT).show();
                stream = getContentResolver().openInputStream(data.getData());
                Bitmap realImage = BitmapFactory.decodeStream(stream);
                image.setImageBitmap(realImage);
                //imgData = encodeToBase64(realImage);

                /*SharedPreferences myPrefrence = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefrence.edit();
                editor.putString("imagePreferance", encodeToBase64(realImage));
                editor.commit();*/
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

    public static String encodeToBase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
       String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        //Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeToBase64(String input) {
       byte[] decodedByte = Base64.decode(input, 0);
       return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


}
