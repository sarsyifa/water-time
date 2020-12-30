package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.opengl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.CalculatorActivity;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.R;

public class CubeActivity extends AppCompatActivity {
    private OpenGLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.cube_text);
        glSurfaceView = new OpenGLSurfaceView(this);
        setContentView(glSurfaceView);
        getSupportActionBar().hide();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), CalculatorActivity.class));
                finish();
            }
        }, 1500);
    }
}