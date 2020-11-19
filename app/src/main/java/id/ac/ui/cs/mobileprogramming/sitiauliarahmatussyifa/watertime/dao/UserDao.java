package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.User;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.WaterConsumption;


@Dao
public interface UserDao {
    @Insert
    public long addUser(User user);

    @Update
    public void updateUser(User user);

    @Delete
    public void deleteUser(User user);

    @Query("SELECT * FROM users where user_id == :uid")
    public User getUser(long uid);

    @Query("SELECT * FROM users ORDER BY user_id ASC")
    public LiveData<List<User>> getAllUsers();

    @Query("DELETE FROM users")
    public void deleteAllUsers();
}
