package application.view;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import LoadMusic.LoadMusic;
import LoadMusic.SongBean;

import application.musicPlayer.LabelTimeRemaining;
import application.musicPlayer.PlayerFunctions;
import application.musicPlayer.SpectrumListener;

import application.musicPlayer.TaskGetCurrentTime;
import application.musicPlayer.TaskGetMusicLength;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;

public class Main extends Application{
	String dNote = "//Users/daniel/Documents/javayh/kurs4/kurs4WorkspaceNew/MPFinal/src/application/resources/dNote.png";
	@FXML Label lengthLabel;
	@FXML Label songName;
	@FXML Label timeRemaining;
	@FXML TableView<SongBean> table;
	@FXML TableColumn<SongBean, String> artistCol;
	@FXML TableColumn<SongBean, String> songCol;
	@FXML TableColumn<SongBean, String> albumCol;
	@FXML ListView<String> lv;
	@FXML Slider slide = new Slider();
	@FXML ProgressBar pb = new ProgressBar();
	@FXML TextField tf;
	@FXML ListView<String> plv;
	@FXML ObservableList<String> oplv = FXCollections.observableArrayList();
	@FXML TextField newpl;
	@FXML Label bar1;
	@FXML Label bar2;
	@FXML Label bar3;
	@FXML Label bar4; 
	@FXML Label bar5; 
	@FXML Label bar6; 
	@FXML Label bar7;
	@FXML Canvas cv;
	
	String nameOfPlayList;
	String playListFilePath;
	Scene scene;
	
	
	//Used for displaying ListView contents
	@FXML
	ObservableList<String> olLv = FXCollections.observableArrayList();
	
	//Used for displaying table contents
	HashSet<String> hs = new HashSet<String>();
	
	@FXML ObservableList<SongBean> ol = FXCollections.observableArrayList();
	
	PlayerFunctions pf;
	SongBean sb;
	
	//Used as a switch to determine whether or not to sort by album or artists.
	boolean inArtistView=false;
	boolean justStarted=false;
	
	Task<String> t;
	Task<Double> gct;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
<<<<<<< HEAD
	 * Creates a row factory so, that when clicked, the SongBean is returned.
	 * Creats a setOnMouseClicked function that is for the list view that displays music by album and artist.
	 * Adds a listener to slide so that values can be obtained from sliding the bar.
=======
	 * Initializes d-Tunes. Sets a rowFactory which enables the object soundBean to be retrieved.
	 * Sets an onMouseClicked function to the list view which displays albums or artists.
	 * Sets a listener to slide,(Not active yet.)
	 * Loads all of the music into the table.
>>>>>>> 27a270ab9d11452aa17aea886294e3ee73fc638b
	 * @author daniel
	 */
	
	@FXML
	public void initialize(){
		table.setRowFactory(tv->{
			TableRow<SongBean> row = new TableRow<>();
			row.setOnMouseClicked(event->{
				stopSongThread();
				pf=null;
				sb = table.getSelectionModel().getSelectedItem();
			});
			
			row.setOnDragDetected((event)->{
				
				Image img = new Image("application/resources/dNote.png");
				sb = table.getSelectionModel().getSelectedItem();
				Dragboard db = row.startDragAndDrop(TransferMode.ANY);
				db.setDragView(img);
				ClipboardContent content = new ClipboardContent();
				content.putString(sb.getSongCol());
				db.setContent(content);
				event.consume();
			});
			
			plv.setOnDragOver(event->{
				if(event.getGestureSource() != plv && event.getDragboard().hasString()){
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
				plv.setOnDragDropped(eventPlv->{
					String song = eventPlv.getDragboard().getString();
					oplv.add(song);
					writeSongToPlayList(song);
					eventPlv.getDragboard().clear();
					eventPlv.consume();
					plv.setItems(oplv);
				});
			});
			
			return row;
		});
		
		plv.setOnMouseClicked(event->{
			nameOfPlayList = plv.getSelectionModel().getSelectedItem();
			showPlayListContents(nameOfPlayList);
		});
		
		
		lv.setOnMouseClicked(event->{
			String selection = lv.getSelectionModel().getSelectedItem();
			if(inArtistView==true){
				showArtistsInTable(selection);
			}else{showAlbumsInTable(selection);}
			
		});
		
		slide.valueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if( ((Math.abs(newValue.doubleValue()-oldValue.doubleValue()) )>5)&&
						(newValue.doubleValue()<pf.getMp().getStopTime().toSeconds() )){
					seekSong(newValue.doubleValue());
				}
			}	
		});
		loadAllMusicIntoTable();
		readInPlayLists();
	}
	
	/**
	 * Starts gui
	 * @author daniel
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(Main.class.getResource("Main.fxml"));
			scene = new Scene(root);
			scene.setOnKeyPressed((KeyEvent)->keyBoardHandler(KeyEvent) );
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("d-Tunes");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a keyboard handler, so that when a button is pressed, this function is fired.
	 * @author daniel
	 * @param ke
	 */
	
	@FXML
	public void keyBoardHandler(KeyEvent ke){
		if(ke.getCode()==ke.getCode().S){
			if(pf.isRunning()==false){
				pf.startSong();
			}else{pf.pauseSong();}
		}
	}
	
	/**
	 * Starts a song, and runs other functions required for presenting data.
	 * @throws InterruptedException
	 */
	
	String musicLength;
	String bar1Value;
	@FXML
	synchronized public void startSongThread() throws InterruptedException{
		//justStarted=true;
		if(pf == null){
			pf = new PlayerFunctions(sb);
			t = new TaskGetMusicLength(pf);
			new Thread(t).start();
			t.addEventHandler(
					WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>(){
				@Override
				public void handle(WorkerStateEvent event) {
					if(gct!=null){
						gct.cancel();
						gct=null;
					}
					getBar1();
					musicLength = t.getValue();
					slide.setMax(Double.parseDouble(musicLength));
					gct = new TaskGetCurrentTime(slide, pf);
					drawOnCanvas();
					new Thread(gct).start();
					
				}
				
			});
		}
		pf.setRunning(true);
		pf.startSong();
			
	}
	
	@FXML
	public void seekSong(Double sp){
		Double seekPoint = sp;
		System.out.println(seekPoint);
		pf.getMp().seek((Duration.seconds(seekPoint)));
	}
	
	
	@FXML
	public void pauseSongThread(){
		drawOnCanvas();
		pf.pauseSong();
	}
	
	@FXML
	public void stopSongThread(){
		//gct.cancel();
		if(pf!=null){
			if(pf.isRunning()==true){
				pf.stopSong();
			}
		}
	}

	
	//********************************************************************************************************************
	
	public void getBar1(){
		
		
	}
	
	
	int displayInfo=1;
	@FXML Label runningLabel;
	@FXML ImageView info;
	@FXML
	public void  displaySongInfo(){
		switch(displayInfo){
			case 1:
				lengthLabel.setText(formatLabelText(musicLength));
				info.setVisible(false);
				runningLabel.setVisible(false);
				lengthLabel.setVisible(true);
				displayInfo++;
				break;
			case 2:
				lengthLabel.setText(formatTitleText(sb.getSongCol()));
				displayInfo++;
				break;
			case 3:
				getCurrentTime();
				lengthLabel.setVisible(false);
				runningLabel.setVisible(true);
				displayInfo++;
				break;
			case 4:
				displayInfo=1;
				info.setVisible(true);
				lengthLabel.setVisible(false);
				runningLabel.setVisible(false);
				break;
		}
	}
	
	public String formatLabelText(String text){
		Double textNum = Double.valueOf(text);
		double hh = Math.floor(textNum/60);
		int ihh = (int) hh;
		double mm = textNum%60;
		int imm = (int) mm;
		return (Integer.toString(ihh)+":"+(Integer.toString(imm)));
	}
	
	public String formatTitleText(String text){
		Pattern p = Pattern.compile(".mp3");
		Matcher m = p.matcher(text);
		m.find();
		String noMp3 = text.substring(0, m.start());
		Pattern pp = Pattern.compile("\\d\\d");
		Matcher mm = pp.matcher(noMp3);
		mm.find();
		String noNum = noMp3.substring(mm.end(), noMp3.length());
		return noNum;
	}
	
	public void getCurrentTime(){
		Task<String> ltr = new LabelTimeRemaining(pf);
			new Thread(ltr).start();
			
			ltr.messageProperty().addListener(new ChangeListener<String>(){
	
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					runningLabel.setText(formatLabelText(newValue));
					
				}});
	}
	

	
	/**
	 * Show the artists in listview and the songs of selected artists in the table.
	 */
	@FXML
	public void showArtists(){
		inArtistView=true;
		hs.clear();
		olLv.clear();
		for(int i=0; i<ol.size(); i++){
			hs.add(ol.get(i).getArtistCol());
		}
		olLv.addAll(hs);
		lv.setItems(olLv);
	
	}
	/**
	 * Shows the albums in listView and the songs of the selected albums
	 */
	@FXML
	public void showAlbums(){
		inArtistView=false;
		hs.clear();
		olLv.clear();
		for(int i=0; i<ol.size(); i++){
			hs.add(ol.get(i).getAlbumCol());
		}
		olLv.addAll(hs);
		lv.setItems(olLv);
	}
	
	public void showArtistsInTable(String artist){
		FilteredList<SongBean> fl = new FilteredList<>(ol, p->true);
		fl.setPredicate( (SongBean)->{
			boolean trueOrFalse=false;
			if(SongBean.getArtistCol().equals(artist)){
				trueOrFalse=true;
				}
			return trueOrFalse;
		});
		SortedList<SongBean> sd = new SortedList<>(fl);
		table.setItems(sd);
	}
	
	public void showAlbumsInTable(String album){
		FilteredList<SongBean> fl = new FilteredList<>(ol, p->true);
		fl.setPredicate((SongBean)->{
			boolean trueOrFalse=false;
			if(SongBean.getAlbumCol().equals(album)){
				trueOrFalse=true;
			}
			return trueOrFalse;
		});
		SortedList<SongBean> sd = new SortedList<>(fl);
		table.setItems(sd);
	}
	
	

	@FXML
	public void showAllSongs(){
		hs.clear();
		olLv.clear();
		FilteredList<SongBean> fl = new FilteredList<>(ol, p->true);
		fl.setPredicate((SongBean)->{
			return true;
		});
		SortedList<SongBean> sd = new SortedList<>(fl);
		table.setItems(sd);
		
	}
	

	
	@FXML
	public void showSearchResults(){
		FilteredList<SongBean> fl = new FilteredList<>(ol, p->true);
		tf.textProperty().addListener((observable, oldValue, newValue)->{
			fl.setPredicate(SongBean->{
				String lowerCaseFilter = newValue.toLowerCase();
				
				if(newValue==null || newValue.isEmpty()){
					return true;
				}
				if(SongBean.getAlbumCol().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				if(SongBean.getArtistCol().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				if(SongBean.getSongCol().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else{return false;}
			});
		});
		SortedList<SongBean> sd = new SortedList<>(fl);
		table.setItems(sd);
	}
	
	/**
	 * Called in the initialize method. Prepares the tabless and columns.
	 */

	public void loadAllMusicIntoTable(){
		LoadMusic lm = new LoadMusic();
		artistCol.setCellValueFactory(new PropertyValueFactory<SongBean, String>("artistCol") );
		songCol.setCellValueFactory(new PropertyValueFactory<SongBean, String>("songCol") );
		albumCol.setCellValueFactory(new PropertyValueFactory<SongBean, String>("albumCol") );
		for(SongBean sb: lm.loadMusicInTable()){
			ol.add(sb);
		}
		table.setItems(ol);
	}
	
	@FXML
	public void createNewPlayList(){
		oplv.clear();
		String plName = newpl.getText();
		plv.setEditable(true);
		plv.setCellFactory(TextFieldListCell.forListView());
		plv.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>(){

			@Override
			public void handle(EditEvent<String> t) {
				String plName = t.getNewValue();
				plv.getItems().set(t.getIndex(), plName);
				if(plName.equals("")){
					oplv.remove(plv.getItems().get(t.getIndex()));
				}
				createPlayListTextFile(plName);
			}
		});
		createPlayListTextFile(plName);
	}

	public void createPlayListTextFile(String newList){
		oplv.add(0, "Drag songs here!");
		playListFilePath = "//Users/daniel/Documents/javayh/kurs4/kurs4WorkspaceNew/MPFinal/src/application/resources/playlists/"+newList+".txt";
		File f = new File(playListFilePath);
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a song name to the text file, this method is called when an item is dragged to the plv node.
	 * @param song
	 */
	
	public void writeSongToPlayList(String song){
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(playListFilePath, true));
			pw.println(song);
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@FXML
	public void readInPlayLists(){
		String playListFolder = "//Users/daniel/Documents/javayh/kurs4/kurs4WorkspaceNew/MPFinal/src/application/resources/playlists/";
		File f = new File(playListFolder);
		String[] lists = f.list();
		oplv.clear();
		for(String s: lists){
			if(s.equals(".DS_Store")){continue;}
			oplv.add(s.substring(0,s.length()-4));
			plv.setItems(oplv);
		}
	}
	
	@FXML
	public void showPlayListContents(String nameOfPlayList){
		ArrayList<String> songNames = getPlayListContents(nameOfPlayList);
		FilteredList<SongBean> fl = new FilteredList<>(ol, p->true);
		fl.setPredicate((SongBean)->{
			boolean trueOrNot=false;
			for(String s: songNames){
				if(SongBean.getSongCol().equals(s)){
					trueOrNot=true;
				}
			}
			return trueOrNot;
		});
		SortedList<SongBean> sd = new SortedList<>(fl);
		table.setItems(sd);
	}
	
	/**
	 * Returns a String array of the song titles within a particular playlist
	 */
	public ArrayList<String> getPlayListContents(String playList){
		ArrayList<String> al = new ArrayList<String>();
		String fileToOpen = "//Users/daniel/Documents/javayh/kurs4/kurs4WorkspaceNew/MPFinal/src/application/resources/playlists/"+playList+".txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileToOpen));
			String song="";
			while(true){
				if(song==null){break;}
				song = br.readLine();
				al.add(song);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}
	
	@FXML
	public void deletePlayList(){
		String fileToDelete = "//Users/daniel/Documents/javayh/kurs4/kurs4WorkspaceNew/MPFinal/src/application/resources/playlists/"+nameOfPlayList+".txt";
		File f = new File(fileToDelete);
		for(int i=0; i<oplv.size();i++){
			String s = oplv.get(i);
			if(s.equals(nameOfPlayList)){
				oplv.remove(i);
				}
			}
		f.delete();
	}
	
	public void drawOnCanvas(){
		GraphicsContext gc = cv.getGraphicsContext2D();
		SpectrumListener sl = new SpectrumListener(pf, gc, cv);
		pf.getMp().setAudioSpectrumListener(sl);
		new Thread(sl).start();
		
	}
}