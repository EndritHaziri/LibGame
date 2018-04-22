package db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
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
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    public static LibGameRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LibGameRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LibGameRoomDatabase.class, "libGame_database")
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final GameDao mDao;

        PopulateDbAsync(LibGameRoomDatabase db) {
            mDao = db.gameDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Game game = new Game("Far Cry", "Jeu de tir", "farcry.png", 1,1);
            mDao.insert(game);
            game = new Game("WoW", "MEUPORG", "wow.png", 1,1);
            mDao.insert(game);
            return null;
        }
    }

}
