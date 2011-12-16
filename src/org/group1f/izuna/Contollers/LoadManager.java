package org.group1f.izuna.Contollers;

import java.awt.Image;
import java.util.Hashtable;

import org.group1f.izuna.GameComponents.SoundEffect;
import org.group1f.izuna.GameComponents.Drawing.Animation;

public class LoadManager {
	private static Hashtable<String, Animation> animationBucket;
	private static Hashtable<String, SoundEffect> soundBucket;
	private static Hashtable<String, Image> imageBucket;

	private LoadManager() {
		// Making it singleton
	}
}
