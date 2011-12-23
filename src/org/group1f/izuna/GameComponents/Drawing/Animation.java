package org.group1f.izuna.GameComponents.Drawing;

import java.awt.*;
import java.util.ArrayList;

import org.group1f.izuna.GameCore;

public class Animation {

    public static final long FRAME_DURATION = 1000 / 24;
    protected ArrayList<AnimationFrame> frames;
    protected int currentFrameIndex;
    protected long totalDuration;
    protected long elapsedTime;

    public Animation() {
        this(new ArrayList<AnimationFrame>());
    }

    private Animation(ArrayList<AnimationFrame> frames) {
        this.frames = frames;
        startOver();
    }

    public synchronized void startOver() {
        elapsedTime = 0;
        currentFrameIndex = 0;
    }

    public Animation clone() {
        return new Animation(frames);
    }

    public synchronized void update(long passedTime) {
        if (frames.size() > 1) {
            elapsedTime += passedTime;
        }

        if (elapsedTime >= totalDuration) {
            elapsedTime = elapsedTime % totalDuration;
            currentFrameIndex = 0;
        }

        while (!timeCorrespondImage(currentFrameIndex)) {
            currentFrameIndex++;
        }
    }

    protected boolean timeCorrespondImage(int index) {
        long timeChecked = 0;

        for (int i = 0; i <= index; i++) {
            timeChecked += FRAME_DURATION;
        }

        if (timeChecked < elapsedTime) {
            return false;
        } else {
            return true;
        }
    }

    public synchronized void addFrame(Image img, Image img3D) {
        totalDuration += FRAME_DURATION;
        frames.add(new AnimationFrame(img, img3D));
    }
    
    public synchronized Image getImage() {
        if (frames.isEmpty()) {
            return null;
        } else {
            return getFrame(currentFrameIndex).getFrame();
        }
    }

    private AnimationFrame getFrame(int i) {
        return frames.get(i);
    }

    private class AnimationFrame {

        private Image frame;
        private Image frame3D;

        public AnimationFrame(Image img, Image img3D) {
            frame = img;
            setFrame3D(img3D);
        }

        public Image getFrame() {
            boolean is3D = GameCore.preferences().getBoolean("3D", false);
            if (is3D) {
                return frame3D;
            } else {
                return frame;
            }
        }

        public Image getFrame3D() {
            return frame3D;
        }

        public void setFrame3D(Image frame3D) {
            this.frame3D = frame3D;
        }
    }
}
