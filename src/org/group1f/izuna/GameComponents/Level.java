package org.group1f.izuna.GameComponents;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *
 * @author Mustafa
 */
public class Level {

    private Queue<AttackWave> waves;
    private AttackWave currentWave;
    private String password;

    /**
     * This function gets current attack wave.
     * @return currentWave
     */
    public AttackWave getCurrentWave() {
        return currentWave;
    }

    /**
     * This function creates attack waves.
     */
    public Level() {
        waves = new ArrayDeque<AttackWave>();
    }

    /**
     * This function adds attack waves.
     * @param wave
     */
    public void addWave(AttackWave wave) {
        waves.add(wave);
    }

    /**
     * This function starts the level.
     * @return currentWave : current attack wave
     */
    public AttackWave startLevel() {
        currentWave = waves.poll();
        return currentWave;
    }

    /**
     * This function swaps the next attack wave.
     * @return currentWave : current attack wave.
     */
    public AttackWave swapNextWave() {
        if (currentWave.isFinished()) {
            AttackWave wave = waves.poll();
            if (wave != null) {
                currentWave = wave;
            }
            return currentWave;
        }
        currentWave = null;
        return currentWave;
    }

    /**
     * This function handles attack wave is finished or not.
     *
     * @return waves.isEmpty() && currentWave.isFinished() : if both attack
     * waves and current attack waves are finished, it is true.
     *
     */
    public boolean isFinished() {
        return waves.isEmpty() && currentWave.isFinished();
    }

    /**
     * This function removes enemy.
     *
     * @param e : enemy of the game
     */
    public void killEnemy(Enemy e) {
        currentWave.removeEnemy(e);
    }

    /**
     *
     * @return the password indicated for this level
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password password for this level
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
