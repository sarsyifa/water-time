package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "water_consumption")
public class WaterConsumption {
    @ColumnInfo(name = "wid")
    @PrimaryKey(autoGenerate = true)
    private long wid;

    @ColumnInfo(name = "log_water_drunk")
    private int water_drunk;

    @ColumnInfo(name = "log_date")
    private String date;

    @ColumnInfo(name = "log_time")
    private String time;

    public WaterConsumption(int water_drunk, String date, String time){
        this.water_drunk = water_drunk;
        this.date = date;
        this.time = time;
    }

    public long getWid() {
        return wid;
    }

    public void setWid(long wid) {
        this.wid = wid;
    }

    public int getWater_drunk() {
        return water_drunk;
    }

    public void setWater_drunk(int water_drunk) {
        this.water_drunk = water_drunk;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
