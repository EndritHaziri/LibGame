package db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import dao.DevDao;
import dao.GameDao;
import dao.PubDao;
import entity.Developer;
import entity.Game;
import entity.Publisher;

@Database(entities = {Game.class, Developer.class, Publisher.class}, version = 2)
public abstract class LibGameRoomDatabase extends RoomDatabase{

    private static LibGameRoomDatabase INSTANCE;

    public abstract GameDao gameDao();
    public abstract DevDao devDao();
    public abstract PubDao pubDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    //new PopulateDbAsync(INSTANCE).execute();
                }
            };

    public static LibGameRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LibGameRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        LibGameRoomDatabase.class, "libGame_database")
                        //.addCallback(sRoomDatabaseCallback)
                        .allowMainThreadQueries()
                        .build();
                }
            }
        }
        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final GameDao gameDao;
        private final DevDao devDao;
        private final PubDao pubDao;

        PopulateDbAsync(LibGameRoomDatabase db) {
            gameDao = db.gameDao();
            devDao = db.devDao();
            pubDao = db.pubDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            gameDao.deleteAll();
            gameDao.insert(new Game("Pokemon", "Attrapez les tous", "pokemon.png", 1, 2));
            gameDao.insert(new Game("Rocket League", "Le sel est présent", "rl.png", 1, 2));

            /*devDao.deleteAll();*/

            devDao.deleteAll();

            devDao.insert(new Developer("2K Games"));
            devDao.insert(new Developer("Activision"));
            devDao.insert(new Developer("Blizzard Entertainment"));

            pubDao.deleteAll();
            pubDao.insert(new Publisher("2K Games"));
            pubDao.insert(new Publisher("Bethesda Softworks"));
            pubDao.insert(new Publisher("Capcom"));

            gameDao.deleteAll();
            gameDao.insert(new Game("Pokemon", "Attrapez les tous", "pokemon.png", devDao.getIdDev("Activision"), pubDao.getPubId("2K Games")));
            gameDao.insert(new Game("Rocket League", "Le sel est présent", "rl.png", devDao.getIdDev("Blizzard Entertainment"), pubDao.getPubId("Capcom")));
            return null;
        }
    }

}
