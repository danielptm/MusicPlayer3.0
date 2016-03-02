package LoadMusic;

public class SongBean {
	String artistCol, songCol, albumCol, path;
	
	SongBean(String artist, String song, String album, String path){
		this.artistCol = artist;
		this.songCol=song;
		this.albumCol = album;
		this.path=path;
	}
	
	public String getAlbumCol() {
		return albumCol;
	}

	public void setAlbumCol(String album) {
		this.albumCol = album;
	}

	public String getArtistCol() {
		return artistCol;
	}

	public void setArtistCol(String artist) {
		this.artistCol = artist;
	}

	public String getSongCol() {
		return songCol;
	}

	public void setSongCol(String song) {
		this.songCol = song;
	} 
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}