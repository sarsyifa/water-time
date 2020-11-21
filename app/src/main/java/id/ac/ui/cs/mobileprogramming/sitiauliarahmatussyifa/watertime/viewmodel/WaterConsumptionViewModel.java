package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.WaterConsumption;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.repositories.WaterConsumptionRepository;

public class WaterConsumptionViewModel extends AndroidViewModel {

    private WaterConsumptionRepository mRepository;

    private LiveData<List<WaterConsumption>> mAllLog;

    private LiveData<Integer> mTotal;

    public WaterConsumptionViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WaterConsumptionRepository(application);
        mAllLog = mRepository.getAllLog();
        mTotal = mRepository.getTotal();
    }

    public LiveData<Integer> getTotalConsumption() {
        return mTotal;
    }

    public LiveData<List<WaterConsumption>> getAllLog() {
        return mAllLog;
    }

    public void addLog(WaterConsumption log) {
        mRepository.addLog(log);
    }

    public void updateLog(WaterConsumption log) {
        mRepository.updateLog(log);
    }

    public void deleteLog(WaterConsumption log) {
        mRepository.deleteLog(log);
    }
}