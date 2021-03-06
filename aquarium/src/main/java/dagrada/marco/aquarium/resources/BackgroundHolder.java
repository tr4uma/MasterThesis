package dagrada.marco.aquarium.resources;

import java.io.IOException;

import thesis.utils.FileHandler;
import thesis.utils.Resource;
import dagrada.marco.aquarium.communicationpackets.BackgroundPacket;
import thesis.Graphics.GraphicsEngine;

/**
 * Created by Marco on 06/10/2015.
 */
public class BackgroundHolder implements Resource {

    private int[][] grid;
    private GraphicsEngine engine;
    private FileHandler fileHandler;

    public BackgroundHolder(GraphicsEngine engine, FileHandler fileHandler){
        this.engine = engine;
        grid = new int[5][5];
        this.fileHandler = fileHandler;
    }

    @Override
    public void store() {
        try {
            fileHandler.writeToFile(this.grid);
            updateGraphics();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void load() {
        try {
            int[][] result = (int[][])fileHandler.readFromFile();
            this.grid = result;
            updateGraphics();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateGraphics(){
        BackgroundPacket packet = new BackgroundPacket(this.grid);
        engine.updateModel(packet);
        engine.update();
    }
}
