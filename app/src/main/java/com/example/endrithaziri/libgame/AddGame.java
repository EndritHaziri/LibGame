package com.example.endrithaziri.libgame;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Developer;
import entity.Game;
import entity.Publisher;

public class AddGame extends AppCompatActivity {

    /**
     * VARIABLE DECLARATION
     */
    private static final int PICK_IMAGE_REQUEST = 100;
    private Button buttonImg;
    private Button buttonAddDev;
    private Button buttonAddPub;
    private Button buttonEditDev, buttonEditPub;
    private BottomNavigationItemView buttonAddGame, buttonCancel;
    private ImageView image;
    private String imgData = "";
    private String name, description, id_publisher, id_developer;
    private List<Publisher> publishers = new ArrayList<>();
    private List<Developer> developers = new ArrayList<>();
    private Spinner spinnerDev, spinnerPub;
    private List<String> publishersName = new ArrayList<>();
    private List<String> developersName = new ArrayList<>();
    private ArrayAdapter aaDev, aaPub;
    private EditText etName, etDescription;
    private InputStream stream;
    private Bitmap realImage;

    /**
     *  FIREBASE VARIABLES
     */
    private static final String TAG = "AddToDatabase";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef, myRefPub, myRefDev;
    private StorageReference mStorageRef;

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
        spinnerDev = findViewById(R.id.spinnerDev);
        spinnerPub = findViewById(R.id.spinnerPub);
        buttonImg = findViewById(R.id.buttonAddImageGame);
        buttonAddDev = findViewById(R.id.buttonAddDev);
        buttonAddPub = findViewById(R.id.buttonAddPub);
        buttonEditDev = findViewById(R.id.buttonEditDev);
        buttonEditPub = findViewById(R.id.buttonEditPub);
        buttonAddGame = findViewById(R.id.navigation_add);
        buttonCancel = findViewById(R.id.navigation_cancel);
        image = findViewById(R.id.imageViewAddGame);
        etName = findViewById(R.id.addGameTitle);
        etDescription = findViewById(R.id.addGameDescription);
        spinnerDev = findViewById(R.id.spinnerDev);
        spinnerPub = findViewById(R.id.spinnerPub);

        /**
         * FIREBASE
         */
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("games");
        myRefPub = mFirebaseDatabase.getReference("publishers");
        myRefDev = mFirebaseDatabase.getReference("developers");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        /**
         * GET ALL THE PUBLISHER AND DEVELOPER
         */
        myRefPub.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    Publisher publisher = d.getValue(Publisher.class);
                    publisher.setId(d.getKey());
                    System.out.println("===========pub===========" + publisher.getName());
                    publishers.add(publisher);
                }

                for (Publisher p: publishers)
                    publishersName.add(p.getName());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("failed");
            }
        });

        myRefDev.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    Developer developer = d.getValue(Developer.class);
                    developer.setId(d.getKey());
                    System.out.println("==========dev========" + developer.getName());
                    developers.add(developer);
                }

                for (Developer d: developers)
                    developersName.add(d.getName());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("failed");
            }
        });
        /**
         * SET DATA IN CORRESPONDING FIELDS
         */
        aaDev = new ArrayAdapter(this, android.R.layout.simple_spinner_item, developersName);
        aaDev.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerDev.setAdapter(aaDev);


        aaPub = new ArrayAdapter(this, android.R.layout.simple_spinner_item, publishersName);
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
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent home = new Intent (AddGame.this, Home.class);
                AddGame.this.startActivity(home);
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
                //editDev.putExtra("id", developerViewModel.getIdDev(spinnerDev.getSelectedItem().toString()));
                AddGame.this.startActivity(editDev);
            }
        });
        buttonEditPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editPub = new Intent(AddGame.this, EditPublisher.class);
                //editPub.putExtra("id", publisherViewModel.getPubId(spinnerPub.getSelectedItem().toString()));
                AddGame.this.startActivity(editPub);
            }
        });
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
         * INSERT THE NEW GAME - FIREBASE
         */
        Map<String,Object> game = new HashMap<>();
        game.put("title", name);
        game.put("description", description);
        game.put("developer_id", id_developer.toLowerCase().trim());
        game.put("publisher_id", id_publisher.toLowerCase().trim());

        Log.d(TAG, "onClick: Attempting to add object to database.");
        if(name.trim().equals("") || description.trim().equals("")) {
            Toast.makeText(AddGame.this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
        } else if(imgData.equals(""))
            Toast.makeText(AddGame.this, R.string.error_img_too_big, Toast.LENGTH_SHORT).show();
        else {
            myRef.child(name).updateChildren(game);
            toastMessage("ok");
        }
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
                stream = getContentResolver().openInputStream(data.getData());
                realImage = BitmapFactory.decodeStream(stream);
                image.setImageBitmap(realImage);
                imgData = encodeToBase64(realImage);
            }
            catch (FileNotFoundException e) {
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
        immage.compress(Bitmap.CompressFormat.PNG, 10, baos);
        immage.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] b = baos.toByteArray();
        if(b.length / 1024 >= 1024) {
            System.out.println(b.length/1024 + " Image too big");
            return "";
        } else {
            String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
            System.out.println(b.length/1024 + " ok");
            return imageEncoded;
        }

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

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }



    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
