package org.group1f.izuna.GameComponents.Drawing;

import java.awt.Image;
import java.util.ArrayList;
import org.group1f.izuna.GameCore;

/**
 * Animation class provides user to animate Image sequences. 
 * It automatically updates corresponding images according to their frame duration 
 * and elapsed time.
 * 
 * @author Nail AKINCI
 */
public class Animation {

    /**
     * Determines the type of the animation that is used, Smooth or REPEAT.
     */
    public enum AnimationType {

        /**
         * If animation type is REPEAT, it starts form the beginning after it is ended.
         */
        REPEAT,
        /**
         * If animation type is SMOOTH, it returns to initial state by rewinding frames, after animation lasts.
         */
        SMOOTH
    }
    private AnimationType animType = AnimationType.REPEAT;
    /**
     * It determines the duration of an Image in an animation.
     */
    public static final long FRAME_DURATION = 1000 / 24;
    /**
     * It is used only in smooth animation, if animation is ended, it starts the rewinding procedure.
     */
    public boolean ended = false;
    private boolean isFinished = false;
    private ArrayList<AnimationFrame> frames;
    /**
     * It shows which frame is active currently.
     */
    protected int currentFrameIndex;
    /**
     * Holds the total time of the animation.
     */
    protected long totalDuration;
    /**
     * Holds the elapsed time of the animation.
     */
    protected long elapsedTime;

    /**
     * 
     * @return Index of the current frame.
     */
    public int getCurrentFrameIndex() {
        return currentFrameIndex;
    }

    /**
     * Initialize a new animation.
     */
    public Animation() {
        this(new ArrayList<AnimationFrame>());
    }

    /**
     * 
     * @return Checks whether its last frame of the animation.
     */
    public boolean isLastFrame() {
        return currentFrameIndex == frames.size() - 1;
    }

    private Animation(ArrayList<AnimationFrame> frames) {
        this.frames = frames;
        startOver();
    }

    /**
     * Starts the animation from its beginning
     */
    public synchronized void startOver() {
        elapsedTime = 0;
        currentFrameIndex = 0;
    }

    /**
     * 
     * @return Image frames of the animation.
     */
    public ArrayList<AnimationFrame> getFrames() {
        return frames;
    }

    @Override
    public Animation clone() {
        Animation a = new Animation();
        for (AnimationFrame af : frames) {
            a.addFrame(af.frame, af.frame3D);
        }
        a.setAnimType(animType);
        return a;
    }

    /**
     * Updates the animation according to elapsed time.If time has come, put next or previous image onto the screen.
     * @param passedTime time in milliseconds.
     */
    public synchronized void update(long passedTime) {
        if (frames.size() > 1) {
            elapsedTime += passedTime;
        }
        if (animType == AnimationType.REPEAT) // repeat
        {
            if (elapsedTime >= totalDuration) {
                elapsedTime = elapsedTime % totalDuration;
                currentFrameIndex = 0;
            }
        } else { //smooth
            if (!ended && elapsedTime >= totalDuration) {
                currentFrameIndex = frames.size() - 1;
            } else if (ended) {
                elapsedTime = elapsedTime % totalDuration;
            }
        }

        while (!timeCorrespondImage(currentFrameIndex)) {
            currentFrameIndex++;
        }
        if (animType == AnimationType.SMOOTH) { //smooth
            while (ended && !timeCorrespondImageRewind(currentFrameIndex)) {
                currentFrameIndex--;
            }

            if (ended && currentFrameIndex == 0) {
                isFinished = true;
            }
        }
    }

    /**
     * If animation is smooth, it needs to be refined to initial position.
     * @return whether it is refining.
     */
    public synchronized boolean refine() {
        if (!ended) {
            ended = true;
        }
        if (isFinished) {
            ended = false;
            isFinished = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @return whether its refinement completed.
     */
    public synchronized boolean finished() {
        if (isFinished) {
            ended = false;
            isFinished = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether previous image needs to be displayed,
     * @param index current index
     * @return true if previous image needs to be displayed, false otherwise.
     */
    protected boolean timeCorrespondImageRewind(int index) {
        long timeChecked = 0;

        for (int i = 0; i <= index; i++) {
            timeChecked += FRAME_DURATION;
        }

        //if there is a mistake look at it first
        if ((totalDuration - timeChecked) < elapsedTime && !(timeChecked == FRAME_DURATION)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks whether next image needs to be displayed,
     * @param index current index
     * @return true if next image needs to be displayed, false otherwise.
     */
    protected boolean timeCorrespondImage(int index) {
        long timeChecked = 0;

        for (int i = 0; i <= index; i++) {
            timeChecked += FRAME_DURATION;
        }

        if (timeChecked < elapsedTime && !(elapsedTime >= totalDuration)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Sets the type of the animation SMOOTH or REPEAT
     * @param value AnimationType SMOOTH or REPEAT
     */
    public void setAnimType(AnimationType value) {
        animType = value;
    }

    /**
     * Gets the type of the animation SMOOTH or REPEAT
     * @return  AnimationType SMOOTH or REPEAT
     */
    public AnimationType getAnimType() {
        return animType;
    }

    /**
     * Adds new a new Frame to the Animation and extend the totalDuration accordingly.
     * @param img Image
     * @param img3D Image
     */
    public synchronized void addFrame(Image img, Image img3D) {
        totalDuration += FRAME_DURATION;
        frames.add(new AnimationFrame(img, img3D));
    }

    /**
     * Returns the image that needs to be currently displayed.
     * @return Image that corresponds to currentFrame Index.
     */
    public synchronized Image getImage() {
        if (frames.isEmpty()) {
            return null;
        } else {
            return getFrame(currentFrameIndex).getFrame();
        }
    }

    /**
     * Returns passed time from the beginning of the animation.
     * @return elapsed time
     */
    public long getElapsedTime() {
        return elapsedTime;
    }

    private AnimationFrame getFrame(int i) {
        return frames.get(i);
    }

    /*
     * Animation Frame class stores 2d and 3d images that will be used in animations, in the memory. It
     * stores both 2d and 3d images of the animation sequences but if an object has no 3d image, both
     * stores the same image. Because it makes images ready before animation and it will be only used
     * in Animation class so it is an inner class of animation.
     */
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
