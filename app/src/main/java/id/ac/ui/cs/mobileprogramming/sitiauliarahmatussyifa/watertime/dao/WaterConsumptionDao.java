package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.WaterConsumption;


@Dao
public interface WaterConsumptionDao {
    @Insert
    public long addLog(WaterConsumption consumption);

    @Update
    public void updateLog(WaterConsumption consumption);

    @Delete
    public void deleteLog(WaterConsumption consumption);

    @Query("SELECT * FROM water_consumption where wid == :wid")
    public WaterConsumption getLog(long wid);

    @Query("SELECT * FROM water_consumption ORDER BY wid ASC")
    public LiveData<List<WaterConsumption>> getAllLog();
}
