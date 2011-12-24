package org.group1f.izuna;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import org.group1f.izuna.GameComponents.*;

public class GameState {
    SoundEffect backgroundMusic;
    Image[] backgroundLayers;
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
        backgroundLayers = new Image[3];
        enemies = new ArrayList<Enemy>();
        bonuses = new ArrayList<Bonus>();
        userWeapons = new ArrayList<Weapon>();
        enemyWeapons = new ArrayList<Weapon>();
        score = 0;
        startTime = System.currentTimeMillis();
        difficulty = Difficulty.Easy;
    }        
    
    public List<GameObject> getAll(){
        List<GameObject> gameObjs = new ArrayList<GameObject>();
        gameObjs.addAll(enemies);
        gameObjs.addAll(bonuses);
        gameObjs.addAll(userWeapons);
        gameObjs.add(p1);
        gameObjs.add(p2);        
        return gameObjs;
    }
}
