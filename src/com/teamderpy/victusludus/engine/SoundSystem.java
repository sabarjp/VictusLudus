package com.teamderpy.victusludus.engine;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.SoundStore;

// TODO: Auto-generated Javadoc
/**
 * The Class SoundSystem.
 */
public class SoundSystem {
	
	/** The Constant SOUND_SELECT_1. */
	public static final String SOUND_SELECT_1 = "res/sounds/select.wav";
	
	/** The Constant SOUND_TYPE_1. */
	public static final String SOUND_TYPE_1   = "res/sounds/type.wav"; 
	
	/** The sound store. */
	private SoundStore soundStore;
	
	/**
	 * Instantiates a new sound system.
	 */
	public SoundSystem(){
		soundStore = SoundStore.get();
		
		soundStore.init();
	}
	
	/**
	 * Close.
	 */
	public void close(){
		soundStore.clear();
	}
	
	/**
	 * Load wav.
	 *
	 * @param path the path
	 * @return the audio
	 */
	public Audio loadWAV(String path){
		try {
			return soundStore.getWAV(path);
		} catch (IOException e) {
			return null;
		}
	}
}
