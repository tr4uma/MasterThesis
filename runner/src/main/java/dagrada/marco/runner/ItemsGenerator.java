package dagrada.marco.runner;

import android.opengl.Matrix;
import android.os.Handler;

import dagrada.marco.runner.interactables.MusicalNote;

/**
 * Created by Marco on 08/09/2015.
 */
public class ItemsGenerator implements Runnable {

    private static long minDelay = 200;

    private static int notes_number = 4;

    private static float start_X = 9.0f;
    private static float start_Y = 0.20f;
    private static float start_Z = 0;
    private static float z_step = 0.60f;

    private Handler handler;
    private long timeDelay;
    private InteractablesCollector interactablesCollector;
    private UpdatablesCollector updatablesCollector;
    private boolean stopped = true;

    public ItemsGenerator(Handler handler, long timeDelay, UpdatablesCollector updatablesCollector, InteractablesCollector interactablesCollector){

        this.handler = handler;
        this.timeDelay = timeDelay;
        this.interactablesCollector = interactablesCollector;
        this.updatablesCollector = updatablesCollector;
    }

    public ItemsGenerator(Handler handler, UpdatablesCollector updatablesCollector, InteractablesCollector interactablesCollector){

        this.handler = handler;
        this.timeDelay = -1;
        this.interactablesCollector = interactablesCollector;
        this.updatablesCollector = updatablesCollector;
    }

    public void start(){
        stopped = false;
        setNextLaunch();
    }

    public void stop(){
        stopped = true;
        handler.removeCallbacks(this);
    }


    @Override
    public void run() {

        int notetype = (int) Math.random() * notes_number;

        int binary = (int) Math.random() * 3;

        float z = start_Z;

        if(binary == 0){
            z=z-z_step;
        }
        if(binary == 1){

        }
        if (binary == 2){
            z+=z_step;
        }

        MusicalNote note = new MusicalNote(notetype, start_X, start_Y, z, 50);

        updatablesCollector.addUpdatable(note);
        interactablesCollector.addInteractable(note);

    }

    private void setNextLaunch(){
        if (timeDelay < 0 ){

            long delay = minDelay + ((long)Math.random() * 2000);

            handler.postDelayed(this, delay);
        }
        else {
            handler.postDelayed(this, timeDelay);
        }
    }
}