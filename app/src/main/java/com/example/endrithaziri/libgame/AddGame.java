package com.example.endrithaziri.libgame;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
    private BottomNavigationItemView buttonAddGame, buttonCancel;
    private ImageView image;
    private String imgData = "";
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

    private EditText imageName;
    private ProgressDialog mProgressDialog;
    private ArrayList<String> pathArray;
    private int array_position;

    private Intent dataImg;
    private Cursor cursor;

    /**
     *  FIREBASE VARIABLES
     */
    private static final String TAG = "AddToDatabase";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth Auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
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
        buttonCancel = findViewById(R.id.navigation_cancel);
        image = findViewById(R.id.imageViewAddGame);
        etName = findViewById(R.id.addGameTitle);
        etDescription = findViewById(R.id.addGameDescription);
        spinnerDev = findViewById(R.id.spinnerDev);
        spinnerPub = findViewById(R.id.spinnerPub);

        /**
         * FIREBASE
         */
        Auth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
            }
        };

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        buttonAddGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

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


    /**
     * METHOD TO SAVE A NEW GAME
     */
    private void saveData() {

        /**
         * GET THE NEW TEXT
         */
        name = etName.getText().toString();
        description = etDescription.getText().toString();
        id_developer = removeSpace(spinnerDev.getSelectedItem().toString());
        id_publisher = removeSpace( spinnerPub.getSelectedItem().toString());

        /**
         * INSERT THE NEW GAME - OLD
         */
        mProgressDialog.setMessage("Uploading Image...");
        mProgressDialog.show();

        //get the signed in user
        FirebaseUser user = Auth.getCurrentUser();
        String userID = user.getUid();

        String name = imageName.getText().toString();
        if(!name.equals("")){
            Uri uri = Uri.fromFile(new File(pathArray.get(array_position)));
            StorageReference storageReference = mStorageRef.child("images/users/" + userID + "/" + name + ".jpg");
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URL to the uploaded content
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    toastMessage("Upload Success");
                    mProgressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    toastMessage("Upload Failed");
                    mProgressDialog.dismiss();
                }
            })
            ;
        }



        /*if(name.trim().equals("") || description.trim().equals("")) {
            Toast.makeText(AddGame.this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
        } else if(imgData.equals(""))
            Toast.makeText(AddGame.this, R.string.error_img_too_big, Toast.LENGTH_SHORT).show();
        else {
            gameViewModel.insert(new Game(name, description, imgData, developerViewModel.getIdDev(id_developer), publisherViewModel.getPubId(id_publisher)));

            /**
             * SHOW INFORMATION AND CLOSE
             */
            /*Toast.makeText(AddGame.this, R.string.game_saved, Toast.LENGTH_SHORT).show();
            Intent homepage = new Intent (AddGame.this,Home.class);
            AddGame.this.startActivity(homepage);
        }*/
        /**
         * INSERT THE NEW GAME - FIREBASE
         */
        Uri downloadUrl;
        final Map<String,Object> game = new HashMap<>();
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
            Uri uri = Uri.fromFile(new File(dataImg.getData().getPath()));
            System.out.println("====================" + uri.getPath().toString() + "====================");
            StorageReference storageReference = mStorageRef.child("img/" + name + ".jpg");
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URL to the uploaded content
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    game.put("url_image", downloadUrl);
                    toastMessage("Upload Success");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    toastMessage("Upload Failed");
                }
            });
            myRef.child("games").child(name).updateChildren(game);
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
                dataImg = data;
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
        Auth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            Auth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private String removeSpace(String s) {
        String s2 = "";
        s2 = s.replaceAll("\\s", "");

        return s2;
    }
}
