package repository;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import dao.DevDao;
import dao.PubDao;
import db.LibGameRoomDatabase;
import entity.Developer;
import entity.Publisher;

/**
 * Created by Dodskamp on 23.04.2018.
 */

public class DeveloperRepository {

    private DevDao devDao;
    private List<Developer> allDeveloper;

    public DeveloperRepository(Application application) {
        LibGameRoomDatabase db = LibGameRoomDatabase.getDatabase(application);
        devDao = db.devDao();
        allDeveloper = devDao.getAllDeveloper();
    }

    public List<Developer> getAllPublisher() {
        return allDeveloper;
    }

    public void insert(Developer developer) {
        new insertAsyncTask(devDao).execute(developer);
    }

    public static class insertAsyncTask extends AsyncTask<Developer, Void, Void> {

        private DevDao mAsyncTaskDao;

        insertAsyncTask(DevDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Developer... developers) {
            return null;
        }
    }

}
