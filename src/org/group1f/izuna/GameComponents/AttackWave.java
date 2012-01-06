package org.group1f.izuna.GameComponents;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for handling attack wave operaitons.
 * @author Mustafa
 */
public class AttackWave {

    private List<Enemy> enemies = new ArrayList<Enemy>();

    /**
     * This function adds enemy to game.
     * @param enemy: the creation of the game which is enemy
     */
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    /**
     * This function handles enemies empty or not.
     * @return enemies.isEmpty() : if there is enemy it is false, if there is no enemy 
     * it is true.
     */
    public boolean isFinished() {
        return enemies.isEmpty();
    }

    /**
     * This function removes enemy.
     * @param e
     */
    public void removeEnemy(Enemy e) {
        synchronized (enemies) {
            enemies.remove(e);
        }
    }

    /**
     * This function starts the attack wave.
     * @param time
     * @return enemies
     */
    public List<Enemy> startWave(long time) {
        for (Enemy enemy : enemies) {
            enemy.setVisible(true);
        }
        return enemies;
    }

    @Override
    public String toString() {
        return "Enemy Size: " + enemies.size();
    }
}