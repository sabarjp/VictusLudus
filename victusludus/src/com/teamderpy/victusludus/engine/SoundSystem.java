package com.teamderpy.victusludus.engine;

import java.io.IOException;

import com.badlogic.gdx.audio.Music;
import com.teamderpy.victusludus.VictusLudusGame;


/**
 * The Class SoundSystem.
 */
public class SoundSystem {

	/** The Constant SOUND_SELECT_1. */
	public static final String SOUND_SELECT_1 = "./sounds/select.wav";

	/** The Constant SOUND_TYPE_1. */
	public static final String SOUND_TYPE_1   = "./sounds/type.wav";

	public static final String MUSIC_TRACK_TENSE = "./sounds/mystery.wav";

	private Music currentMusicTrack;

	/**
	 * Load wav.
	 *
	 * @param path the path
	 * @return the audio
	 */
	public Music loadWAV(final String path){
		return VictusLudusGame.engine.assetManager.get(path, Music.class);
	}

	public Music getCurrentMusicTrack() {
		return this.currentMusicTrack;
	}
	
	public void close(){
		if(this.currentMusicTrack != null){
			this.currentMusicTrack.stop();
			this.currentMusicTrack.dispose();
		}
	}

	public void setCurrentMusicTrack(final String track) {
		if(this.currentMusicTrack != null){
			this.currentMusicTrack.stop();
			this.currentMusicTrack.dispose();
		}
		
		this.currentMusicTrack = this.loadWAV(track);
		this.currentMusicTrack.setLooping(true);
		this.currentMusicTrack.play();
	}
}
