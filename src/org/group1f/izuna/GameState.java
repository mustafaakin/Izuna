package org.group1f.izuna;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import org.group1f.izuna.GameComponents.*;
import org.group1f.izuna.GameComponents.Drawing.Sprite;

/**
 * 
 * @author Mustafa
 */
public class GameState {

    SoundEffect backgroundMusic;
    Image[] backgroundLayers;
    Player p1;
    Player p2;
    volatile private ArrayList<Enemy> enemies;
    ArrayList<Bonus> bonuses;
    volatile private ArrayList<Weapon> userWeapons;
    volatile private ArrayList<Weapon> enemyWeapons;
    int score;
    long startTime;
    volatile private ArrayList<GameObject> explosions;

    /**
     * 
     * @return
     */
    public synchronized ArrayList<Weapon> getEnemyWeapons() {
        return enemyWeapons;
    }

    /**
     * 
     * @return
     */
    public synchronized ArrayList<GameObject> getExplosions() {
        return explosions;
    }

    /**
     * 
     * @return
     */
    public synchronized ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * 
     * @return
     */
    public synchronized ArrayList<Weapon> getUserWeapons() {
        return userWeapons;
    }

    /**
     * 
     */
    public GameState() {
        backgroundLayers = new Image[3];
        enemies = new ArrayList<Enemy>();
        bonuses = new ArrayList<Bonus>();
        userWeapons = new ArrayList<Weapon>();
        enemyWeapons = new ArrayList<Weapon>();
        score = 0;
        explosions = new ArrayList<GameObject>();
        startTime = System.currentTimeMillis();
    }
}
