package game;

import java.util.ArrayList;

import game.player.Player;

/**
 * Created by Andrea on 23/04/2015.
 */
public class Team {

    private String name;
    private int color;

    private ArrayList<Player> players = new ArrayList<>();

    public Team(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

}
