package Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import LoadMusic.SongBean;

public class DumpM4A {
	public void keepOnlymp3(){
		String artist;
		String album;
		String song;
		String skip = ".DS_Store";
		final String musicFolderPath = "//Users/daniel/Documents/javayh/kurs4/musicPlayerMusic/";
		Pattern p = Pattern.compile("3.mp3");
		Matcher m; 
		File artistFolder = new File(musicFolderPath);
		
		String[] artists = artistFolder.list();
		for(String artistName: artists ){
			artist = artistName;
			if(artist.equals(skip)){continue;}
			File albumPath = new File(musicFolderPath+artist+"/");
			String[] albums = albumPath.list();
			for(String albumName: albums){
					album = albumName;
					if(album.equals(skip)){continue;}
					File songsPath = new File(musicFolderPath+artist+"/"+album+"/");
					
					String[] songs = songsPath.list();
					for(String songName: songs){
						song = songName;
						m = p.matcher(song);
						if(m.find()){
							//Move this file musicFolderPath+artist+"/"+album+"/"+song to this location Users/daniel/desktop/music/m4a.
							try {
								Files.delete(Paths.get(musicFolderPath+artist+"/"+album+"/"+song).toAbsolutePath());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						}
						if(song.equals(skip)){continue;}

					}
				}
			}
		
	}
}