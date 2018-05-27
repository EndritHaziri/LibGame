package com.example.endrithaziri.libgame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Developer;
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
    private String pathImage;
    private StorageReference filepath;
    private Uri uri;

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

                publishers.clear();
                publishersName.clear();

                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    Publisher publisher = d.getValue(Publisher.class);
                    publisher.setId(d.getKey());
                    publishers.add(publisher);
                }

                for (Publisher p: publishers)
                    publishersName.add(p.getName());

                aaPub = new ArrayAdapter(AddGame.this, android.R.layout.simple_spinner_item, publishersName);
                aaPub.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerPub.setAdapter(aaPub);
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

                developers.clear();
                developersName.clear();

                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    Developer developer = d.getValue(Developer.class);
                    developer.setId(d.getKey());
                    developers.add(developer);
                }

                for (Developer d: developers)
                    developersName.add(d.getName());

                aaDev = new ArrayAdapter(AddGame.this, android.R.layout.simple_spinner_item, developersName);
                aaDev.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinnerDev.setAdapter(aaDev);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("failed");
            }
        });

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
                editDev.putExtra("dev", developers.get(spinnerDev.getSelectedItemPosition()));
                AddGame.this.startActivity(editDev);
            }
        });
        buttonEditPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editPub = new Intent(AddGame.this, EditPublisher.class);
                editPub.putExtra("pub", publishers.get(spinnerPub.getSelectedItemPosition()));
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
        game.put("url_image", pathImage);

        Log.d(TAG, "onClick: Attempting to add object to database.");
        if(name.trim().equals("") || description.trim().equals("")) {
            Toast.makeText(AddGame.this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
        } else {
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddGame.this, "Upload done", Toast.LENGTH_LONG).show();
                }
            });
            myRef.child(name).updateChildren(game);
            toastMessage("Game saved");
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
                uri = data.getData();

                filepath = mStorageRef.child("GameImage").child(uri.getLastPathSegment());
                pathImage = "gs://libgame-20422.appspot.com" + filepath.getPath();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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
