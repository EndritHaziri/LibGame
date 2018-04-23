package view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.webkit.ConsoleMessage;

import java.io.Console;
import java.util.List;

import entity.Game;
import repository.GameRepository;

public class GameViewModel extends AndroidViewModel {

    private GameRepository gameRepository;
    private List<Game> allGames;

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
}
