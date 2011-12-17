package org.group1f.izuna;

import java.util.ArrayList;
import org.group1f.izuna.GameComponents.*;

public class GameState {

    Player p1;
    Player p2;
    ArrayList<Enemy> enemies;
    ArrayList<Bonus> bonuses;
    ArrayList<Weapon> userWeapons;
    ArrayList<Weapon> enemyWeapons;
    int score;
    long startTime;
    Difficulty difficulty;
    
    enum Difficulty {
        Easy, Medium, Hard
    }

    public GameState() {
        enemies = new ArrayList<Enemy>();
        bonuses = new ArrayList<Bonus>();
        userWeapons = new ArrayList<Weapon>();
        enemyWeapons = new ArrayList<Weapon>();
        score = 0;
        startTime = System.currentTimeMillis();
        difficulty = Difficulty.Easy;
    }        
}
