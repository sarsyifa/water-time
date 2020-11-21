package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.R;

public class NewLogActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.EXTRA_ID";
    public static final String EXTRA_AMOUNT = "id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.EXTRA_AMOUNT";
    public static final String EXTRA_DATE = "id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.EXTRA_DATE";
    public static final String EXTRA_TIME = "id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.EXTRA_TIME";

    private EditText mEditLogView;
    private EditText editTextAmount;
    private EditText editTextDate;
    private EditText editTextTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_log);
        editTextAmount = findViewById(R.id.amount);
        editTextDate = findViewById(R.id.date_picker);
        editTextTime = findViewById(R.id.weight);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle(R.string.edit_water_title);
            editTextAmount.setText(String.valueOf(intent.getIntExtra(EXTRA_AMOUNT, 100)));
            editTextDate.setText(intent.getStringExtra(EXTRA_DATE));
            editTextTime.setText(intent.getStringExtra(EXTRA_TIME));
        } else {
            setTitle(R.string.add_water_title);
        }

//        final Button button = findViewById(R.id.button_save);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent replyIntent = new Intent();
//                if (TextUtils.isEmpty(editTextAmount.getText())) {
//                    setResult(RESULT_CANCELED, replyIntent);
//                } else {
//                    String amount = editTextAmount.getText().toString();
//                    String date = editTextDate.getText().toString();
//                    String time = editTextTime.getText().toString();
//                    replyIntent.putExtra(EXTRA_AMOUNT, amount);
//                    replyIntent.putExtra(EXTRA_DATE, date);
//                    replyIntent.putExtra(EXTRA_TIME, time);
//                    setResult(RESULT_OK, replyIntent);
//                }
//                finish();
//            }
//        });
    }

    private void saveLog() {

        int amount = Integer.parseInt(editTextAmount.getText().toString());
        String date = editTextDate.getText().toString();
        String time = editTextTime.getText().toString();
        if ( date.trim().isEmpty() || time.trim().isEmpty() ) {
            Toast.makeText(this, "Insert amount", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_AMOUNT, amount);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_TIME, time);

        long id = getIntent().getLongExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_log, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_log:
                saveLog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}