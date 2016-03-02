package application.musicPlayer;

import javafx.concurrent.Task;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;

public class TaskGetCurrentTime extends Task{
	MediaPlayer mp;
	PlayerFunctions pf;
	Double currentTime;
	Double stop;
	Slider slide;
	
	public TaskGetCurrentTime(Slider slide, PlayerFunctions pf){
		this.slide=slide;
		this.pf = pf;
		stop = pf.getMp().getStopTime().toSeconds();
	}

	@Override
	protected Double call() throws Exception {
		currentTime= pf.getMp().getCurrentTime().toSeconds();
		while(currentTime<stop){
			slide.setValue(pf.getMp().getCurrentTime().toSeconds());
			this.updateProgress(currentTime, pf.getMp().getStopTime().toSeconds());
			Thread.sleep(1000);
		}
		return slide.getValue();
	}
}