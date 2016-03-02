package application.musicPlayer;
import java.nio.file.Paths;

import LoadMusic.SongBean;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class PlayerFunctions{

	private Media hit;
	private MediaPlayer mp;
	SongBean sb;
	private boolean running=false;
	private boolean paused=false;
	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	Double sliderMax;
	String lengthOfSong;
	String songName;
	
	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getLengthOfSong() {
		return lengthOfSong;
	}

	public void setLengthOfSong(String length) {
		this.lengthOfSong = length;
	}

	public PlayerFunctions(SongBean sb){
		this.sb = sb;
		hit = new Media(Paths.get(this.sb.getPath()).toUri().toString());
		mp = new MediaPlayer(hit);

	}		
		
	public void startSong(){
		mp.play();
		setRunning(true);

	}
	
	
	public void stopSong(){
		setRunning(false);
		mp.stop();
		
	}
	public void pauseSong(){
		mp.pause();

	}
	public Duration getSongLength(){
		return mp.getStopTime();
	}
	
	public MediaPlayer getMp() {
		return mp;
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean trueOrFalse) {
		running = trueOrFalse;
	}
	public static void getData(){
		
		
	}

}