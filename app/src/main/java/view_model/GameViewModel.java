package view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import entity.Game;
import repository.GameRepository;

public class GameViewModel extends AndroidViewModel {

    private GameRepository gameRepository;
    private LiveData<List<Game>> allGames;

    public GameViewModel(@NonNull Application application) {
        super(application);
        gameRepository = new GameRepository(application);
        allGames = gameRepository.getAllGames();
    }

    public LiveData<List<Game>> getAllGames() {
        return allGames;
    }

    public void insert(Game game) {
        gameRepository.insert(game);
    }
}
