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

    /**
     * 
     * @return
     */
    public AttackWave getCurrentWave() {
        return currentWave;
    }

    /**
     * 
     */
    public Level() {
        waves = new ArrayDeque<AttackWave>();
    }

    /**
     * 
     * @param wave
     */
    public void addWave(AttackWave wave) {
        waves.add(wave);
    }

    /**
     * 
     * @return
     */
    public AttackWave startLevel() {
        currentWave = waves.poll();
        return currentWave;
    }

    /**
     * 
     * @return
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
     * 
     * @return
     */
    public boolean isFinished() {
        return waves.isEmpty() && currentWave.isFinished();
    }

    /**
     * 
     * @param e
     */
    public void killEnemy(Enemy e) {
        currentWave.removeEnemy(e);
    }    
}
