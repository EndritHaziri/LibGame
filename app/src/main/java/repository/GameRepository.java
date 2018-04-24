package repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import dao.GameDao;
import db.LibGameRoomDatabase;
import entity.Game;

public class GameRepository {

    private GameDao gameDao;
    private List<Game> allGames;

    public GameRepository(Application application) {
        LibGameRoomDatabase db = LibGameRoomDatabase.getDatabase(application);
        gameDao = db.gameDao();
        allGames = gameDao.getAllGames();
    }

    public List<Game> getAllGames() {
        return allGames;
    }

    public Game getGameById(int id) {
        return gameDao.getGameById(id);
    }

    public void insert(Game game) {
        new insertAsyncTask(gameDao).execute(game);
    }

    public void update(int id, String name, String description) {
        gameDao.update(id, name, description);
    }

    public void deleteGame(int id) {
        gameDao.deleteGame(id);
    }

    private static class insertAsyncTask extends AsyncTask<Game, Void, Void> {

        private GameDao mAsyncTaskDao;

        insertAsyncTask(GameDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Game... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
