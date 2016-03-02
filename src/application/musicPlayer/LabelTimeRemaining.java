package application.musicPlayer;

import javafx.concurrent.Task;

public class LabelTimeRemaining extends Task{
	PlayerFunctions pf;

	
	public LabelTimeRemaining(PlayerFunctions pf){
		this.pf = pf;
	}
	
	@Override
	protected String call() throws Exception {
		String x="";
		while(pf.getMp().getCurrentTime().toSeconds()<pf.getMp().getStopTime().toSeconds()){
			x = Double.toString(pf.getMp().getCurrentTime().toSeconds());
			Thread.sleep(1000);
			this.updateMessage(x);
		}
		return x;
	}
}