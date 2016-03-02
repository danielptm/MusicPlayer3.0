package application.musicPlayer;

import java.util.concurrent.TimeUnit;

import javafx.concurrent.Task;
import javafx.scene.media.MediaPlayer;

public class TaskGetMusicLength extends Task{
	MediaPlayer mp;
	PlayerFunctions pf;
	String rv;
	
	public TaskGetMusicLength(PlayerFunctions pf){
		this.pf = pf;
	}
	@Override
	protected String call() throws Exception {
		String min, sec;
		Thread.sleep(1000);
		Double totalSeconds= this.pf.getMp().getStopTime().toSeconds();
		return Double.toString(totalSeconds);
	}
}