package activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.ArrayList;


import dagrada.marco.runner.GameController;
import dagrada.marco.runner.GameEngine;
import dagrada.marco.runner.InteractablesCollector;
import dagrada.marco.runner.ItemsGenerator;
import dagrada.marco.runner.UpdatablesCollector;
import dagrada.marco.runner.interactables.Guitar;
import thesis.touch.GestureFilter;


public class MainActivity extends Activity {

    private GestureDetector detector;

    private long updateDelay = 1000;
    private long generationDelay = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting up levels for the game
        GraphicsRenderer renderer = new GraphicsRenderer(this);

        Handler handler = new Handler(Looper.getMainLooper());

        InteractablesCollector interactablesCollector = new InteractablesCollector();
        UpdatablesCollector updatablesCollector = new UpdatablesCollector();
        Guitar guitar = new Guitar();

        GameEngine engine = new GameEngine(handler, updateDelay, updatablesCollector, interactablesCollector, guitar, renderer);
        ItemsGenerator itemsGenerator = new ItemsGenerator(handler, updateDelay, updatablesCollector, interactablesCollector);

        GameController controller = new GameController(engine, itemsGenerator);




        GraphicsView view = new GraphicsView(this, controller, renderer);
        detector = new GestureDetector(this, new GestureFilter(view));
        setContentView(view);




    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}