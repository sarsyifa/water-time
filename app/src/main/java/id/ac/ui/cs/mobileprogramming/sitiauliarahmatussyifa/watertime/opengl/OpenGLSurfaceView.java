package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class OpenGLSurfaceView extends GLSurfaceView {
    private OpenGLRenderer renderer;

    public OpenGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(3);
        renderer = new OpenGLRenderer(context);
        setRenderer(renderer);
    }
}

