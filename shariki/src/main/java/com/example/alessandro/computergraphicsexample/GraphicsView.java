package com.example.alessandro.computergraphicsexample;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import dagrada.marco.shariki.GameStatusHandler;
import dagrada.marco.shariki.MatrixChecker;
import dagrada.marco.shariki.controllers.MatrixController;
import dagrada.marco.shariki.exceptions.TouchedItemNotFoundException;
import sfogl.integration.Node;
import sfogl2.SFOGLSystemState;
import shadow.math.SFMatrix3f;
import shadow.math.SFTransform3f;
import thesis.Graphics.NodesKeeper;
import thesis.Graphics.ShadersKeeper;
import thesis.touch.TouchActivity;


public class GraphicsView extends GLSurfaceView implements TouchActivity{

    private Context context;

    private GraphicsRenderer renderer;
    private MatrixController controller;

    public GraphicsView(Context context, MatrixController controller) {
        super(context);
        setEGLContextClientVersion(2);
        this.context=context;
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        renderer = new GraphicsRenderer(context, controller.getHandler());
        setRenderer(renderer);
        this.controller = controller;
    }

    @Override
    public void onRightSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "RIGHT SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");
        int[] indices = new int[0];
        try {
            indices = renderer.detectTouchedItem(startX, startY);
            int x = indices[0];
            int y = indices[1];
            controller.switchPosition(y ,x, y, x+1 );
            renderer.update();
        } catch (TouchedItemNotFoundException e) {}

    }

    @Override
    public void onLeftSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "LEFT SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");
        int[] indices = new int[0];
        try {
            indices = renderer.detectTouchedItem(startX, startY);
            int x = indices[0];
            int y = indices[1];
            controller.switchPosition(y, x, y, x - 1);
            renderer.update();
        } catch (TouchedItemNotFoundException e) {

        }

    }

    @Override
    public void onUpSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "UP SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");
        int[] indices = new int[0];
        try {
            indices = renderer.detectTouchedItem(startX, startY);
            int x = indices[0];
            int y = indices[1];
            controller.switchPosition(y, x, y-1, x);
            renderer.update();
        } catch (TouchedItemNotFoundException e) {

        }

    }

    @Override
    public void onDownSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "DOWN SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");
        int[] indices = new int[0];
        try {
            indices = renderer.detectTouchedItem(startX, startY);
            int x = indices[0];
            int y = indices[1];
            controller.switchPosition(y, x, y+1, x);
            renderer.update();
        } catch (TouchedItemNotFoundException e) {

        }

    }

    @Override
    public void onDoubleTap(float x, float y) {
        Log.d("TOUCH", "DOUBLE TAP");

    }

    @Override
    public void onLongPress(float x, float y) {
        Log.d("TOUCH", "LONG PRESS");
        renderer.update();
    }

    @Override
    public void onSingleTapUp(float x, float y) {
        Log.d("TOUCH", "SINGLE TAP on (" + String.valueOf(x) + "," + String.valueOf(y) + ")");
        int[] indices = new int[0];
        try {
            indices = renderer.detectTouchedItem(x, y);
            int xx = indices[0];
            int yy = indices[1];
            Log.d("TOUCH", "INDICES "+ String.valueOf(xx)+","+String.valueOf(yy));
        } catch (TouchedItemNotFoundException e) {

        }
;
    }
}
