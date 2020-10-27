package com.example.grzegorzmrozek;

import android.content.Context;
import android.media.SoundPool;
import android.os.Vibrator;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class MyGLBall {
    private int points=720;
    private float vertices[];
    private FloatBuffer vertBuff;
    private float aspectRatio;

    private static final float LEFT_BOUNDARY = -0.9f;
    private static final float RIGHT_BOUNDARY = 0.9f;
    private static final float BOUNCE_FACTOR = 0.55f;
    private float currentXpos = 0.0f;
    private float currentAngle = 0;
    private static final float MAX_STEP_SPEED = 0.015f;
    private static final float MAX_SPEED = 0.3f;
    private float speed = 0;

    private SoundPool player;
    private int soundId;
    private Vibrator vibrator;

    public MyGLBall(SoundPool player, Context context){
        this.player = player;
        this.soundId = this.player.load(context, R.raw.pilka, 1);
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        vertices=new float[(points+1)*3];
        for(int i=3;i<(points+1)*3;i+=3){
            double rad=(i*360/points*3)*(3.14/180);
            vertices[i]=(float)Math.cos(rad);
            vertices[i+1]=(float) Math.sin(rad);
            vertices[i+2]=0;
        }
        ByteBuffer bBuff=ByteBuffer.allocateDirect(vertices.length*4);
        bBuff.order(ByteOrder.nativeOrder());
        vertBuff=bBuff.asFloatBuffer();
        vertBuff.put(vertices);
        vertBuff.position(0);
    }

    public void draw(GL10 gl){
        speed = Math.max(Math.min(speed + MAX_STEP_SPEED * currentAngle, MAX_SPEED), -MAX_SPEED);
        float previousXpos = currentXpos;
        currentXpos = currentXpos + speed;

        if(currentXpos < LEFT_BOUNDARY || currentXpos > RIGHT_BOUNDARY) {
            speed = -speed * BOUNCE_FACTOR;
            currentXpos = Math.max(Math.min(currentXpos, RIGHT_BOUNDARY), LEFT_BOUNDARY);
            if (currentXpos != previousXpos) {
                this.player.play(soundId, 1, 1, 10, 0, 1);
                vibrator.vibrate(32);
            }
        }

        if(currentXpos == previousXpos) {
            speed = 0.0f;
        }

        gl.glPushMatrix();
        gl.glTranslatef(currentXpos, 0, 0);
        gl.glScalef(0.1f, 0.1f * aspectRatio, 1.0f);
        gl.glColor4f(1.0f,1.0f,1.0f, 0.2f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertBuff);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, points/2);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glPopMatrix();
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public void setCurrentAngle(float currentAngle) {
        this.currentAngle = -1 * (Math.max(Math.min(currentAngle, 180), 0) - 90) / 90.0f;
    }
}