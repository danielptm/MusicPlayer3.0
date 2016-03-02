package LoadMusic;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class LoadMusic {
	ArrayList<SongBean> sbList = new ArrayList<SongBean>();
	private String artist;
	private String album;
	private String song;
	private String skip = ".DS_Store";
	
	public ArrayList<SongBean> loadMusicInTable(){
		final String musicFolderPath = ("//Users/daniel/Documents/javayh/kurs4/musicPlayerMusic/");
		SongBean sb;
		//Gets the Artist
		File artistFolder = new File(musicFolderPath);
		//Gets the album
		
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
						if(song.equals(skip)){continue;}
						sb = new SongBean(artist, song, album, songsPath+"/"+songName);
						sbList.add(sb);
					}
				}
			}
		
		return sbList;
	}
}