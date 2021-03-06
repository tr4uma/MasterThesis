package dagrada.marco.aquarium.controllers;

import android.content.Context;
import android.util.Log;

import activities.renderers.ProxyRenderer;
import dagrada.marco.aquarium.GameModeHandler;
import dagrada.marco.aquarium.MyResourceManager;
import dagrada.marco.aquarium.communicationpackets.ProxyItemMovePacket;
import dagrada.marco.aquarium.communicationpackets.ProxyItemPacket;
import dagrada.marco.aquarium.exceptions.TouchedItemNotFoundException;
import dagrada.marco.aquarium.resources.ItemsHolder;
import thesis.Graphics.GameRenderer;
import thesis.touch.TouchActivity;

/**
 * Created by Marco on 07/10/2015.
 */
public class EditController implements TouchActivity{

    private Context context;

    private ProxyRenderer renderer;
    //private GameController controller;
    private MyResourceManager manager;
    private float previousx, previousy;
    private boolean moving;
    int[] selected;
    private GameModeHandler gameModeHandler;


    private final float MULTIPLIER = 0.0101f;

    public EditController(Context context, ProxyRenderer renderer, MyResourceManager manager, GameModeHandler gameModeHandler) {

        this.context=context;

        this.renderer = renderer;
        this.gameModeHandler = gameModeHandler;
        //this.controller = controller;
        this.manager = manager;
    }

    @Override
    public void onRightSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "RIGHT SWIPE");
        Log.d("START POINT", "("+String.valueOf(startX)+","+String.valueOf(startY)+")");
        Log.d("END POINT", "(" + String.valueOf(endX) + "," + String.valueOf(endY) + ")");
        //controller.moveRight();


    }

    @Override
    public void onLeftSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "LEFT SWIPE");
        Log.d("START POINT", "(" + String.valueOf(startX) + "," + String.valueOf(startY) + ")");
        Log.d("END POINT", "(" + String.valueOf(endX) + "," + String.valueOf(endY) + ")");
        //controller.moveLeft();


    }

    @Override
    public void onUpSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "UP SWIPE");
        Log.d("START POINT", "(" + String.valueOf(startX) + "," + String.valueOf(startY) + ")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");


    }

    @Override
    public void onDownSwipe(float startX, float startY, float endX, float endY) {
        Log.d("TOUCH", "DOWN SWIPE");
        Log.d("START POINT", "(" + String.valueOf(startX) + "," + String.valueOf(startY) + ")");
        Log.d("END POINT", "("+String.valueOf(endX)+","+String.valueOf(endY)+")");


    }

    @Override
    public void onDoubleTap(float x, float y) {

        //renderer.toggleRenderer();

        manager.loadAllResources();

        Log.d("TOUCH", "DOUBLE TAP");

    }

    @Override
    public void onLongPress(float x, float y) {
        Log.d("TOUCH", "LONG PRESS");
        //controller.toggleGame();

    }

    @Override
    public void onSingleTapUp(float x, float y) {
        Log.d("TOUCH", "SINGLE TAP on (" + String.valueOf(x) + "," + String.valueOf(y) + ")");
        ;
    }

    @Override
    public void onDown(float x, float y) {
        //Log.d("DOWN", "<---");

            try {
                selected = renderer.detectTouchedItem(x, y);
                if(selected[0] == -1 ) {
                    switch (selected[1]){
                        case 0:case 1:case 2:
                            ProxyItemPacket p = new ProxyItemPacket(selected[1] + 1);
                            renderer.updateModel(p);
                            previousx = x;
                            previousy = y;
                            break;

                        default:break;

                    }

                }

            } catch (TouchedItemNotFoundException e) {

            }

    }

    @Override
    public void onMove(float x, float y) {
        moving = true;

        float dx = x - previousx;
        float dy = y - previousy;
        //Log.d("DISTANCE", String.valueOf(dx*MULTIPLIER)+" "+String.valueOf(dy*MULTIPLIER));
        ProxyItemMovePacket p = new ProxyItemMovePacket(dx*MULTIPLIER, dy*MULTIPLIER);
        renderer.updateModel(p);
        this.previousx = x;
        this.previousy = y;
    }

    @Override
    public void onUp(float x, float y) {

        int[] res;

        ProxyItemPacket pk = new ProxyItemPacket(0);
        renderer.updateModel(pk);

        if(selected!=null) {
            try {
                res = renderer.detectTouchedItem(x, y);

                if (res[0] >= 0 && selected[0] == res[0] && selected[1] == res[1]) {
                    ItemsHolder holder = (ItemsHolder) manager.getResource(MyResourceManager.ITEMS);
                    holder.setItem(res[0], res[1], ItemsHolder.NO_ITEM);
                    holder.updateGraphics();


                }
                if (res[0] == -1 && selected[0] == res[0] && selected[1] == res[1]){
                    switch (res[1]){
                        case 3:
                            manager.loadAllResources();
                            break;
                        case 4:
                            manager.storeAllResources();
                            break;
                        case 5:
                            gameModeHandler.setGamemode(GameModeHandler.GAMEMODE_PANORAMIC);
                            break;
                        default:break;
                    }

                }

                if (moving) {

                    if (res[0] >= 0) {
                        ItemsHolder holder = (ItemsHolder) manager.getResource(MyResourceManager.ITEMS);
                        holder.setItem(res[0], res[1], selected[1] + 1);
                        holder.updateGraphics();
                        ProxyItemPacket p = new ProxyItemPacket(ProxyItemPacket.UNSELECTED);
                        renderer.updateModel(p);
                    }


                }
                moving = false;
                selected = null;


            } catch (TouchedItemNotFoundException e) {
                //e.printStackTrace();
            }
        }


    }

    public GameRenderer getRenderer() {
        return this.renderer;
    }
}
