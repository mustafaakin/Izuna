package org.group1f.izuna.GameComponents;

import java.util.ArrayDeque;
import java.util.Queue;

public class Level {

    Queue<AttackWave> waves;

    public Level() {
        waves = new ArrayDeque<AttackWave>();
    }

    public void addWave(AttackWave wave) {
        waves.add(wave);
    }

    public AttackWave getWave() {
        return waves.poll();
    }
}
