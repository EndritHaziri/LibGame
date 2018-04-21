package db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import dao.DevDao;
import dao.GameDao;
import dao.PubDao;
import entity.Developer;
import entity.Game;
import entity.Publisher;

@Database(entities = {Game.class, Developer.class, Publisher.class}, version = 1)
public abstract class LibGameRoomDatabase extends RoomDatabase{

    private static LibGameRoomDatabase INSTANCE;

    public abstract GameDao gameDao();
    public abstract DevDao devDao();
    public abstract PubDao pubDao();

    public static LibGameRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LibGameRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LibGameRoomDatabase.class, "libGame_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
