package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.dao.WaterConsumptionDao;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.database.WaterConsumptionDatabase;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.WaterConsumption;

public class WaterConsumptionRepository {

    private WaterConsumptionDao mLogDao;
    private LiveData<List<WaterConsumption>> mAllLog;
    private LiveData<Integer> mTotal;

    public WaterConsumptionRepository(Application application) {
        WaterConsumptionDatabase db = WaterConsumptionDatabase.getDatabase(application);
        mLogDao = db.getLogDAO();
        mAllLog = mLogDao.getAllLog();
        mTotal = mLogDao.totalConsumption();
    }

    public LiveData<List<WaterConsumption>> getAllLog() {
        return mAllLog;
    }

    public LiveData<Integer> getTotal() {
        return mTotal;
    }

    public void addLog(WaterConsumption log) {

        new InsertLogAsyncTask(mLogDao).execute(log);
    }

    public void updateLog(WaterConsumption log) {

        new UpdateLogAsyncTask(mLogDao).execute(log);
    }

    public void deleteLog(WaterConsumption log) {

        new DeleteLogAsyncTask(mLogDao).execute(log);
    }

    private static class InsertLogAsyncTask extends AsyncTask<WaterConsumption, Void, Void> {

        private WaterConsumptionDao mAsyncTaskDao;

        InsertLogAsyncTask(WaterConsumptionDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WaterConsumption... params) {
            mAsyncTaskDao.addLog(params[0]);
            return null;
        }

    }

    private static class UpdateLogAsyncTask extends AsyncTask<WaterConsumption, Void, Void> {

        private WaterConsumptionDao mAsyncTaskDao;

        UpdateLogAsyncTask(WaterConsumptionDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WaterConsumption... params) {
            mAsyncTaskDao.updateLog(params[0]);
            return null;
        }

    }

    private static class DeleteLogAsyncTask extends AsyncTask<WaterConsumption, Void, Void> {

        private WaterConsumptionDao mAsyncTaskDao;

        DeleteLogAsyncTask(WaterConsumptionDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WaterConsumption... params) {
            mAsyncTaskDao.deleteLog(params[0]);
            return null;
        }

    }

}
