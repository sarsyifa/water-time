package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.database.WaterConsumptionDatabase;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.Event;

public class CreateEvent extends AppCompatActivity implements View.OnClickListener {
    Button btn_time, btn_date, btn_done;
    EditText editext_message;
    String timeTonotify;
    WaterConsumptionDatabase waterConsumptionDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reminder);
        btn_time = findViewById(R.id.btn_time);
        btn_date = findViewById(R.id.btn_date);
        btn_done = findViewById(R.id.btn_done);
        editext_message = findViewById(R.id.editext_message);
        btn_time.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        waterConsumptionDatabase = WaterConsumptionDatabase.getDatabase(getApplicationContext());

    }

    @Override
    public void onClick(View view) {
        if (view == btn_time) {
            selectTime();
        } else if (view == btn_date) {
            selectDate();
        } else {
            submit();
        }
    }

    private void submit() {
        String text = editext_message.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, R.string.toast_enter_text, Toast.LENGTH_SHORT).show();
        } else {
            if (btn_time.getText().toString().equals("Select Time") || btn_date.getText().toString().equals("Select Date") || btn_date.getText().toString().equals("Pilih Tanggal") || btn_time.getText().equals("Pilih Waktu")) {
                Toast.makeText(this, R.string.toast_select_dt, Toast.LENGTH_SHORT).show();
            } else {
                Event event = new Event();
                String value = (editext_message.getText().toString().trim());
                String date = (btn_date.getText().toString().trim());
                String time = (btn_time.getText().toString().trim());
                event.setRemindDate(date);
                event.setMessage(value);
                event.setRemindTime(time);
                waterConsumptionDatabase.getEventDao().insert(event);
                setAlarm(value, date, time);
            }
        }
    }

    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTonotify = i + ":" + i1;
                btn_time.setText(FormatTime(i, i1));
            }
        }, hour, minute, false);
        timePickerDialog.show();

    }

    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                btn_date.setText(day + "-" + (month + 1) + "-" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public String FormatTime(int hour, int minute) {

        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }

    private void setAlarm(String text, String date, String time) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), NotifierAlarm.class);
        intent.putExtra("event", text);
        intent.putExtra("time", date);
        intent.putExtra("date", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = date + " " + timeTonotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        finish();

    }
}
