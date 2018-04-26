package repository;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import dao.PubDao;
import db.LibGameRoomDatabase;
import entity.Publisher;

/**
 * Created by Dodskamp on 23.04.2018.
 */

public class PublisherRepository {

    private PubDao pubDao;
    private List<Publisher> allPublisher;

    public PublisherRepository(Application application) {
        LibGameRoomDatabase db = LibGameRoomDatabase.getDatabase(application);
        pubDao = db.pubDao();
        allPublisher = pubDao.getAllPublisher();
    }

    public List<Publisher> getAllPublisher() {
        return allPublisher;
    }

    public Publisher getPubById(int id) {
        return pubDao.getPubById(id);
    }

    public int getPubId (String name) {
        return pubDao.getPubId(name);
    }

    public void deletePublisher(int id) {
        pubDao.deletePublisher(id);
    }

    public void insert(Publisher publisher) {
        new insertAsyncTask(pubDao).execute(publisher);
    }

    public void update(int id, String name) {
        pubDao.update(id, name);
    }

    public static class insertAsyncTask extends AsyncTask<Publisher, Void, Void> {

        private PubDao mAsyncTaskDao;

        insertAsyncTask(PubDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Publisher... publishers) {
            mAsyncTaskDao.insert(publishers[0]);
            return null;
        }
    }

}
