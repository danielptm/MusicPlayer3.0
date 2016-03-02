package test;

import org.junit.Ignore;
import org.junit.Test;

import LoadMusic.LoadMusic;
import Util.DumpM4A;

public class MP3Tests {

	@Ignore
	public void testLoadMusic(){
		LoadMusic lm = new LoadMusic();
		lm.loadMusicInTable();
	}
	
	@Test 
	public void testKeepOnlymp3(){
		DumpM4A d = new DumpM4A();
		d.keepOnlymp3();
	}
}