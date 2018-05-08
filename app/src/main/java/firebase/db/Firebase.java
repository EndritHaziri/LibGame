package firebase.db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Dodskamp on 08.05.2018.
 */

public class Firebase {

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("title");

    public Firebase() {
        myRef.setValue("Hello, World!");
    }


}
