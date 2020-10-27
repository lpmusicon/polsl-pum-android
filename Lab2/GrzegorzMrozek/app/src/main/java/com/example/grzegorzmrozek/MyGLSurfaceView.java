package com.example.grzegorzmrozek;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView
{
    public MyGLSurfaceView(Context context)
    {
        super(context);
        setRenderer(new MyRenderer(context));
    }
}
