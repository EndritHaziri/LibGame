package view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.webkit.ConsoleMessage;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import entity.Game;
import repository.GameRepository;

public class GameViewModel extends AndroidViewModel {

    private GameRepository gameRepository;
    private List<Game> allGames;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("libgame-20422");

    public GameViewModel(@NonNull Application application) {
        super(application);
        gameRepository = new GameRepository(application);
        allGames = gameRepository.getAllGames();
    }

    public List<Game> getAllGames() {
        return allGames;
    }

    public Game getGameById(int id) {
        return gameRepository.getGameById(id);
    }

    public void insert(Game game) {
        gameRepository.insert(game);
    }

    public void update(int id, String name, String description) {
        gameRepository.update(id, name, description);
    }

    public List<Game> getGame() {
        final List<Game> games = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                games.add(dataSnapshot.getValue(Game.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.printf("The read failed: " + databaseError.getCode());
            }
        });
        return games;
    }

    public void deleteGame(int id) {
        gameRepository.deleteGame(id);
    }
}
