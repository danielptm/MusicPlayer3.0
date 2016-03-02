package application.musicPlayer;


import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.MediaPlayer.Status;

public class SpectrumListener extends Task implements AudioSpectrumListener{
	float bar1, bar2, bar3,bar4, bar5, bar6, bar7, bar8, bar9, bar10, bar11;
	float bar12, bar13, bar14, bar15, bar16, bar17, bar18, bar19, bar20;
	
	GraphicsContext gc;
	PlayerFunctions pf;
	Canvas cv;
	int xpoint=0, ypoint=0;
	
	public SpectrumListener(PlayerFunctions pf, GraphicsContext gc, Canvas cv){
		this.gc = gc;
		this.pf = pf;
		this.cv =cv;
	}


	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		bar1 = (float) 2.0*(-1*(magnitudes[0]));
		bar2 = (float) 2.0*(-1*(magnitudes[1]));
		bar3 = (float) 2.0*(-1*(magnitudes[2]));
		bar4 = (float) 2.0*(-1*(magnitudes[3]));
		bar5 = (float) 2.0*(-1*(magnitudes[4]));
		bar6 = (float) 2.0*(-1*(magnitudes[5]));
		bar7 = (float) 2.0*(-1*(magnitudes[6]));
		bar8 = (float) 2.0*(-1*(magnitudes[7]));
		bar9 = (float) 2.0*(-1*(magnitudes[8]));
		bar10 = (float) 2.0*(-1*(magnitudes[9]));
		bar11 = (float) 2.0*(-1*(magnitudes[10]));
		bar12 = (float) 2.0*(-1*(magnitudes[11]));
		bar13 = (float) 2.0*(-1*(magnitudes[12]));
		bar14 = (float) 2.0*(-1*(magnitudes[13]));
		bar15 = (float) 2.0*(-1*(magnitudes[14]));
		bar16 = (float) 2.0*(-1*(magnitudes[15]));
		bar17 = (float) 2.0*(-1*(magnitudes[16]));
		bar18 = (float) 2.0*(-1*(magnitudes[17]));
		bar19 = (float) 2.0*(-1*(magnitudes[18]));
		bar20 = (float) 2.0*(-1*(magnitudes[19]));
	}
	
	int barWidth=25;
	int barSpace=28;
	@Override
	protected Object call() throws Exception {
		while(pf.getMp().getStatus()==Status.PLAYING){
			Thread.sleep(10);
			this.gc.clearRect(0, 0, cv.getWidth(), cv.getHeight());
				this.gc.fillRect(0, 1, barWidth, bar1);
				this.gc.fillRect(barSpace*1, 1, barWidth, bar2);
				this.gc.fillRect(barSpace*2, 1, barWidth, bar3);
				this.gc.fillRect(barSpace*3, 1, barWidth, bar4);
				this.gc.fillRect(barSpace*4, 1, barWidth, bar5);
				this.gc.fillRect(barSpace*5, 1, barWidth, bar6);
				this.gc.fillRect(barSpace*6, 1, barWidth, bar7);
				this.gc.fillRect(barSpace*7, 1, barWidth, bar8);
				this.gc.fillRect(barSpace*8, 1, barWidth, bar9);
				this.gc.fillRect(barSpace*9, 1, barWidth, bar10);
				this.gc.fillRect(barSpace*10, 1, barWidth, bar11);
				this.gc.fillRect(barSpace*11, 1, barWidth, bar12);
				this.gc.fillRect(barSpace*12, 1, barWidth, bar13);
				this.gc.fillRect(barSpace*13, 1, barWidth, bar14);
				this.gc.fillRect(barSpace*14, 1, barWidth, bar15);
				this.gc.fillRect(barSpace*15, 1, barWidth, bar16);
				this.gc.fillRect(barSpace*16, 1, barWidth, bar17);
				this.gc.fillRect(barSpace*17, 1, barWidth, bar18);
				this.gc.fillRect(barSpace*18, 1, barWidth, bar19);
		}
		this.cancel();
		return null;
	}
}