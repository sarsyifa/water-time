package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.adapter.EventAdapter;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.database.WaterConsumptionDatabase;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.Event;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.repositories.EventRepository;

public class ReminderActivity extends AppCompatActivity {

    Button createEvent;

    private EventAdapter eventAdapter;
    private FloatingActionButton add;
    private List<Event> temp;
    private RecyclerView recyclerViewReminder;
    private TextView empty;
    public WaterConsumptionDatabase waterConsumptionDatabase;
    public EventRepository eventRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);
        setTitle(R.string.reminder_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        waterConsumptionDatabase = WaterConsumptionDatabase.getDatabase(getApplicationContext());

        add = findViewById(R.id.fab_alarm);
        recyclerViewReminder = findViewById(R.id.recycler_view_reminder);
        empty = findViewById(R.id.empty);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == add){
                    Intent intent = new Intent(getApplicationContext(), CreateEvent.class);
                    startActivity(intent);
                }
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
                waterConsumptionDatabase.getEventDao().delete(eventAdapter.getEventAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ReminderActivity.this, R.string.toast_deleted_alarm, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerViewReminder);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();

    }

    private void setAdapter() {
        List<Event> classList = waterConsumptionDatabase.getEventDao().getAll();
        eventAdapter = new EventAdapter(getApplicationContext(), classList);
        recyclerViewReminder.setAdapter(eventAdapter);
        if(classList.size()>0) {
            empty.setVisibility(View.INVISIBLE);
            recyclerViewReminder.setVisibility(View.VISIBLE);
        }
    }
}
