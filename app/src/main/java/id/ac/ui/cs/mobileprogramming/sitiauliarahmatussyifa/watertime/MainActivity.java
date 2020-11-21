package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.adapter.UserAdapter;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.adapter.WaterConsumptionAdapter;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.User;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.WaterConsumption;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.viewmodel.UserViewModel;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.viewmodel.WaterConsumptionViewModel;

public class MainActivity extends AppCompatActivity {
    public static final int NEW_LOG_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_LOG_REQUEST = 2;
    public static final int ADD_PROFILE_REQUEST = 3;
    public static final int EDIT_PROFILE_REQUEST = 4;
    private WaterConsumptionViewModel mLogViewModel;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById
                (R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar calendar = Calendar.getInstance();
        TextView tv_date = findViewById(R.id.date_textView);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        tv_date.setText(currentDate);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_water_consumption);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final WaterConsumptionAdapter adapter = new WaterConsumptionAdapter(this);
        recyclerView.setAdapter(adapter);

        mLogViewModel = ViewModelProviders.of(this).get(WaterConsumptionViewModel.class);
        mLogViewModel.getAllLog().observe(this, new Observer<List<WaterConsumption>>() {
            @Override
            public void onChanged(List<WaterConsumption> waterConsumptions) {
                adapter.setLogs(waterConsumptions);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mLogViewModel.deleteLog(adapter.getLogAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, R.string.toast_log_deleted, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new WaterConsumptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WaterConsumption waterConsumption) {
                Intent intent = new Intent(MainActivity.this, NewLogActivity.class);
                intent.putExtra(NewLogActivity.EXTRA_ID, waterConsumption.getWid());
                intent.putExtra(NewLogActivity.EXTRA_AMOUNT, waterConsumption.getWater_drunk());
                intent.putExtra(NewLogActivity.EXTRA_DATE, waterConsumption.getDate());
                intent.putExtra(NewLogActivity.EXTRA_TIME, waterConsumption.getTime());
                startActivityForResult(intent, EDIT_LOG_REQUEST);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewLogActivity.class);
                startActivityForResult(intent, NEW_LOG_ACTIVITY_REQUEST_CODE);
            }
        });

        final TextView tv_intake = findViewById(R.id.progress_textView);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView tv_max = findViewById(R.id.goal_textView);
        tv_max.setText("/ 2000");

        mLogViewModel.getTotalConsumption().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == null) {
                    int total = 0;
                    tv_intake.setText("0");
                } else {
                    int total = integer;
                    tv_intake.setText(String.valueOf(total));
                    progressBar.setMax(2000);
                    progressBar.setProgress(total, false);
                }
            }
        });

        RecyclerView recyclerView2 = findViewById(R.id.recycler_view_profile);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setHasFixedSize(true);
        final UserAdapter uAdapter = new UserAdapter(this);
        recyclerView2.setAdapter(uAdapter);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getmAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                uAdapter.setUsers(users);
            }
        });

        uAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User viewProfile) {
                Intent intentProfile = new Intent(MainActivity.this, ProfileActivity.class);
                intentProfile.putExtra(ProfileActivity.EXTRA_UID, viewProfile.getId());
                intentProfile.putExtra(ProfileActivity.EXTRA_NAME, viewProfile.getName());
                intentProfile.putExtra(ProfileActivity.EXTRA_AGE, viewProfile.getAge());
                intentProfile.putExtra(ProfileActivity.EXTRA_WEIGHT, viewProfile.getWeight());
                intentProfile.putExtra(ProfileActivity.EXTRA_HEIGHT, viewProfile.getHeight());
                startActivityForResult(intentProfile, EDIT_PROFILE_REQUEST);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(Settings.ACTION_LOCALE_SETTINGS), 0);
            return true;
        } else if (id == R.id.reminder_menu) {
            Intent intenReminder = new Intent(MainActivity.this, ReminderActivity.class);
            startActivity(intenReminder);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_LOG_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int amount = data.getIntExtra(NewLogActivity.EXTRA_AMOUNT, 100);
            String date = data.getStringExtra(NewLogActivity.EXTRA_DATE);
            String time =  data.getStringExtra(NewLogActivity.EXTRA_TIME);
            WaterConsumption log = new WaterConsumption(amount, date, time);
            mLogViewModel.addLog(log);
            Toast.makeText(this, R.string.toast_log_saved, Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_LOG_REQUEST && resultCode == RESULT_OK) {
            long id = data.getLongExtra(NewLogActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, R.string.log_not_updated, Toast.LENGTH_SHORT).show();
                return;
            }
            int amount = data.getIntExtra(NewLogActivity.EXTRA_AMOUNT, 100);
            String date = data.getStringExtra(NewLogActivity.EXTRA_DATE);
            String time =  data.getStringExtra(NewLogActivity.EXTRA_TIME);
            WaterConsumption log = new WaterConsumption(amount, date, time);
            log.setWid(id);
            mLogViewModel.updateLog(log);
            Toast.makeText(this, R.string.toast_log_updated, Toast.LENGTH_SHORT).show();

        } else if (requestCode == ADD_PROFILE_REQUEST && resultCode == RESULT_OK) {
            int height = data.getIntExtra(ProfileActivity.EXTRA_HEIGHT, 160);
            int weight = data.getIntExtra(ProfileActivity.EXTRA_WEIGHT, 55);
            String name = data.getStringExtra(ProfileActivity.EXTRA_NAME);
            String age =  data.getStringExtra(ProfileActivity.EXTRA_AGE);
            User usr = new User(name, age, height, weight);
            userViewModel.addUser(usr);
            Toast.makeText(this, R.string.toast_log_saved, Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_PROFILE_REQUEST && resultCode == RESULT_OK) {
            long id = data.getLongExtra(ProfileActivity.EXTRA_UID, -1);
            if (id == -1) {
                Toast.makeText(this, R.string.profile_not_updated, Toast.LENGTH_SHORT).show();
                return;
            }
            int height = data.getIntExtra(ProfileActivity.EXTRA_HEIGHT, 160);
            int weight = data.getIntExtra(ProfileActivity.EXTRA_WEIGHT, 55);
            String name = data.getStringExtra(ProfileActivity.EXTRA_NAME);
            String age =  data.getStringExtra(ProfileActivity.EXTRA_AGE);
            User usr = new User(name, age, height, weight);
            usr.setId(id);
            userViewModel.updateUser(usr);
            Toast.makeText(this, R.string.toast_profile_updated, Toast.LENGTH_SHORT).show();
        }
    }

}