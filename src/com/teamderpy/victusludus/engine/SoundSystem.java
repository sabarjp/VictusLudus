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

	public static final String MUSIC_TRACK_TENSE = "res/sounds/mystery.wav";

	/** The sound store. */
	private SoundStore soundStore;

	private Audio currentMusicTrack;

	/**
	 * Instantiates a new sound system.
	 */
	public SoundSystem(){
		this.soundStore = SoundStore.get();

		this.soundStore.init();
	}

	/**
	 * Close.
	 */
	public void close(){
		this.soundStore.clear();
	}

	/**
	 * Load wav.
	 *
	 * @param path the path
	 * @return the audio
	 */
	public Audio loadWAV(final String path){
		try {
			return this.soundStore.getWAV(path);
		} catch (IOException e) {
			return null;
		}
	}

	public Audio getCurrentMusicTrack() {
		return this.currentMusicTrack;
	}

	public void setCurrentMusicTrack(final String track) {
		this.currentMusicTrack = this.loadWAV(track);
		this.currentMusicTrack.playAsMusic(1.0F, 1.0F, true);
	}
}
