package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.dao.EventDao;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.dao.UserDao;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.dao.WaterConsumptionDao;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.Event;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.User;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.WaterConsumption;

@Database(entities = {WaterConsumption.class, User.class, Event.class}, version = 1, exportSchema = false)
public abstract class WaterConsumptionDatabase extends RoomDatabase {
    public abstract WaterConsumptionDao getLogDAO();
    public abstract UserDao getUserDao();
    public abstract EventDao getEventDao();
    private static WaterConsumptionDatabase INSTANCE;

    public static WaterConsumptionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WaterConsumptionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WaterConsumptionDatabase.class, "waterConsumption_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onCreate (@NonNull SupportSQLiteDatabase db){
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private WaterConsumptionDao mDao;
        private UserDao uDao;
        private EventDao eDao;
        PopulateDbAsync(WaterConsumptionDatabase db) {
            mDao = db.getLogDAO();
            uDao = db.getUserDao();
            eDao = db.getEventDao();

        }


        @Override
        protected Void doInBackground(Void... voids) {

            User init_user = new User("Strangers", "19", 165, 55);
            uDao.addUser(init_user);

            WaterConsumption log = new WaterConsumption(200, "20/10/2020", "9:00");
            mDao.addLog(log);
            mDao.addLog(new WaterConsumption(800, "20/10/2020", "9:10"));
            return null;
        }
    }
}