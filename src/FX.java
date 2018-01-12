import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FX extends Application{
    
	static int c0 = ThreadLocalRandom.current().nextInt(9, 13);
	static int c1 = ThreadLocalRandom.current().nextInt(6, 10);
	static int c2 = ThreadLocalRandom.current().nextInt(3, 7);
	static int playerNum = 4, cave0Num = c0, cave1Num = c1, cave2Num = c2;
    int turn = 0 ;
    static int chestNum = cave0Num + cave1Num + cave2Num;
    private Stage stage;
    
    //Start scene lists
    private ArrayList<Button> buttonsList = new ArrayList<>();
    
    //Game scene lists
    private ArrayList<Diver> diversList = new ArrayList<>();
    private static ArrayList<Chest> chestsList = new ArrayList<>();
    private ArrayList<Stash> stashesList = new ArrayList<>();
    private ArrayList<Score> scoresList = new ArrayList<>();
    
    int height = 690;
    int playerHeight = 70;
    int playerWidth = 50;
    int chestHeight = 20;
    int stashHeight = 26;
    
  public void start(Stage stage){
    stage.setTitle("Snorkunking");
    this.stage=stage;
    welcomeScene();
  }
  
  //WELCOME SCENE
  
  private void welcomeScene() {
	  //Set background	  
	  HBox background = setBackground("waterback");
	  
	  //Set title
	  Text title = new Text("SNORKUNKING");
	  title.setFill(Color.DARKBLUE);
	  title.setStroke(Color.BLACK);
	  title.setStrokeWidth(7);
	  title.setScaleY(1.2);
	  title.setStrokeType(null);
	  title.setFont(Font.font("Riffic Free Medium", 80));
	  title.setX(75);
	  title.setY(150);
	  
	  //Set start button
	  
	  Image img = new Image(this.getClass().getResourceAsStream("/images/start.png"));
	  ImageView start = new ImageView(img);
	  start.setFitWidth(100);
	  start.setPreserveRatio(true);
	  start.setX(300);
	  start.setY(600);
	  start.setOnMouseEntered(ke -> {
		  //Hover image
		  Image startImg = new Image(this.getClass().getResourceAsStream("/images/start_selected.png"));
		  start.setImage(startImg);
		  });
	  start.setOnMouseExited(ke -> {
		  Image startImg2 = new Image(this.getClass().getResourceAsStream("/images/start.png"));
		  start.setImage(startImg2);
		  });
	  start.setOnMousePressed(ke -> {
		  //Return
		  startScene();
		  });
	   
	  //Set message
	  Text message = new Text("PRESS ENTER TO PLAY");
	  message.setFill(Color.SKYBLUE);
	  message.setStroke(Color.DARKBLUE);
	  message.setStrokeWidth(5);
	  message.setStrokeType(null);
	  message.setFont(Font.font("Riffic Free Medium",20));
	  message.setX(250);
	  message.setY(500);
	  Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> message.setVisible(false)),
              new KeyFrame(Duration.seconds( 0.2), evt -> message.setVisible(true)));
	  timeline.setCycleCount(Animation.INDEFINITE);
	  timeline.play();

	  
	  //Set scene
	  Group root = new Group();
	  root.getChildren().addAll(background,title,start,message);
	  Scene scene = new Scene(root, 700, height);	  
	  scene.setOnKeyPressed(ke -> {
		  //START
		  if(ke.getCode() == KeyCode.ENTER) {
			  startScene();
		  }
	  });
	  this.stage.setScene(scene);
	  this.stage.show();
  }
  
  //START SCENE
  
  private void startScene() {
	  
	  //Set background
	  
	  HBox background = setBackground("waterbacknames");
	  
	  //Set players
	  
	  VBox players = new VBox();
	  VBox[] playersList = new VBox[4];
	  for(int i = 0; i < 4; i++) {
		  if(i == 0) {
			  VBox player = new VBox();
			  Image img = new Image(this.getClass().getResourceAsStream("/images/diver"+i+"_selected.png"));
			  ImageView iv = new ImageView(img);
			  iv.setFitWidth(playerWidth);
			  iv.setPreserveRatio(true);
			  player.getChildren().clear();
			  player.getChildren().add(iv);
			  player.setPadding(new Insets(0,0,20,0));
			  playersList[i] = player;
		  }else {
			  VBox player = new VBox();
			  Image img = new Image(this.getClass().getResourceAsStream("/images/diver"+i+".png"));
			  ImageView iv = new ImageView(img);
			  iv.setFitWidth(playerWidth);
			  iv.setPreserveRatio(true);
			  player.getChildren().clear();
			  player.getChildren().add(iv);
			  player.setPadding(new Insets(0,0,20,0));
			  playersList[i] = player;  
		  }
	  }
	  players.getChildren().clear();
	  players.getChildren().addAll(playersList);
	  players.setPadding(new Insets(100,0,0,350 - playerWidth/2));
	  
	  //Set start button
	  
	  HBox start = new HBox();
	  Image img = new Image(this.getClass().getResourceAsStream("/images/start.png"));
	  ImageView iv = new ImageView(img);
	  iv.setFitWidth(100);
	  iv.setPreserveRatio(true);
	  start.getChildren().clear();
	  start.getChildren().add(iv);
	  start.setPadding(new Insets(600,0,0,350 - 50));
	  
	  //Set button list
	  
	  setButtons();
	  
	  //Set scene
	  Group root = new Group();
	  root.getChildren().addAll(background, players, start);
	  Scene scene = new Scene(root, 700, height);
	  
	  scene.setOnKeyPressed(ke -> {
		  //UP
		  if(ke.getCode() == KeyCode.UP) {
			  int id = getIdSelected();
			  Button button = buttonsList.get(id);
			  if(id > 0) {
				  Button prevButton = buttonsList.get(id - 1);
				  //update selected
				  button.setSelected(false);
				  prevButton.setSelected(true);
				  //update image
				  button.unlight(playersList, id, start); 
				  prevButton.light(playersList, id - 1, start);
			  }
		  }
		  //DOWN
		  if(ke.getCode() == KeyCode.DOWN) {
			  int id = getIdSelected();
			  Button button = buttonsList.get(id);
			  if(id < 4) {
				  Button nextButton = buttonsList.get(id + 1);
				  //update selected
				  button.setSelected(false);
				  nextButton.setSelected(true);
				  //update image
				  button.unlight(playersList, id, start);
				  nextButton.light(playersList, id + 1, start);
			  }
		  }
		  //LEFT
		  if((ke.getCode() == KeyCode.LEFT)){
			  int id = getIdSelected();
			  Button button = buttonsList.get(id);
			  if(id < 4) {
				  if(button.getPos() == 0) {
					  button.moveLeft(playersList, id);
					  button.setPos(1);
				  }
				  if(button.getPos() == 2) {
					  button.moveLeft(playersList, id);
					  button.setPos(0);
				  }  
			  }
		  }
		  //RIGHT
		  if(ke.getCode() == KeyCode.RIGHT) {
			  int id = getIdSelected();
			  Button button = buttonsList.get(id);
			  if(id < 4) {
				  if(button.getPos() == 0) {
					  button.moveRight(playersList, id);
					  button.setPos(2);
				  }
				  if(button.getPos() == 1) {
					  button.moveRight(playersList, id);
					  button.setPos(0);
				  }  
			  }
		  }
		  //ENTER
		  if(ke.getCode() == KeyCode.ENTER) {
			  int id = getIdSelected();
			  Button button = buttonsList.get(id);
			  if(id<4) {
					  Button nextButton = buttonsList.get(id + 1);
					  //update selected
					  button.setSelected(false);
					  nextButton.setSelected(true);
					  //update image
					  button.unlight(playersList, id, start);
					  nextButton.light(playersList, id + 1, start); 
			  }else if(id == 4) {
				  playerNum = getPlayerNum();
				  if(playerNum != 0) {
					  gameScene();  
				  }
			  }
		  }
	  });
	  this.stage.setScene(scene);
	  this.stage.show();
  }
  
  //GAME SCENE
  
  private void gameScene(){
	  //Set background
	  HBox background = setBackground("fullbacko2");
	  
	  //Set players
	  HBox[] playersList = new HBox[playerNum];
	  HBox players = new HBox();
	  for(int i = 0; i < playerNum; i++) {
		  int n = getNumList(i);
		  String name = "diver" + n;
		  HBox diver = newImageH(name, playerHeight);
		  diver.setPadding(new Insets(0,80,0,0));
		  playersList[i]=diver;
	  }
	  setDiverList();
	  players.getChildren().addAll(playersList);
	  players.setPadding(new Insets(0,0,0,160));

	  //Set chests    
	  VBox chests = new VBox();	  
	  VBox[] chestsBoxList = new VBox[chestNum];
	  int n = 0;
	  int padding = ((height - playerHeight - 40) - (chestNum * chestHeight)) / (chestNum - 1);
	  for(int i = 0; i < chestNum; i++) {
		  if((cave0Num  <= i) && (i < cave0Num + cave1Num)) {
			  n = 1;
		  }
		  if((cave0Num + cave1Num <= i) && (i < chestNum)) {
			  n = 2;
		  }
		  VBox chest = new VBox();
		  Image img = new Image(this.getClass().getResourceAsStream("/images/chest"+n+".png"));
		  ImageView iv = new ImageView(img);
		  iv.setFitHeight(chestHeight);
		  iv.setPreserveRatio(true);
		  chest.getChildren().clear();
		  chest.getChildren().add(iv);
		  chest.setPadding(new Insets(0.0, 0.0, padding, 0.0));
		  chestsBoxList[i]=chest;
	  }
	  setChestList();
	  chests.getChildren().addAll(chestsBoxList);
	  chests.setPadding(new Insets(playerHeight+20,0,0,30));
    
    //Set bottom chests
    
    HBox bottom = new HBox();
    HBox[] bottomList = new HBox[chestNum];
    for(int i = 0; i < chestNum; i++) {
		 HBox empty = new HBox();
		  Image img = new Image(this.getClass().getResourceAsStream("/images/void.png"));
		  ImageView iv = new ImageView(img);
		  iv.setFitHeight(20);
		  iv.setPreserveRatio(true);
		  empty.getChildren().clear();
		  empty.getChildren().add(iv);
		  empty.setPadding(new Insets(0, 10, 0, 0));
		  bottomList[i] = empty;
	}
    bottom.getChildren().clear();
    bottom.getChildren().addAll(bottomList);
    bottom.setPadding(new Insets(650, 0, 0, 60));
	    
    //Set stashes
    
    VBox[] stashesBoxList = new VBox[playerNum];
    HBox stashes = new HBox();
    for(int i = 0; i < playerNum; i++) {
    	int n1 = getNumList(i);
    	VBox stash = new VBox();
    	Image stashImg = new Image(this.getClass().getResourceAsStream("/images/stashchest"+n1+".png"));
    	ImageView stashIv = new ImageView(stashImg);    	
    	stashIv.setFitWidth(30);
		stashIv.setPreserveRatio(true);
		stash.getChildren().clear();
		stash.getChildren().addAll(stashIv);
		stash.setPadding(new Insets(0,90,0,0));
    	stashesBoxList[i]=stash;
    }
    setStashList();
    stashes.getChildren().addAll(stashesBoxList);
    stashes.setPadding(new Insets(playerHeight - stashHeight, 0, 0, 200));
    
    //Set oxygen
    
    	//Stock
    Oxygen ox = new Oxygen();
    setOxygen(ox);
    HBox oxygen = new HBox();
    Image oxygenImg = new Image(this.getClass().getResourceAsStream("/images/oxygen" + ox.getStock() + ".png"));
    ImageView oxygenIv = new ImageView(oxygenImg);    
    oxygen.getChildren().add(oxygenIv);
    oxygen.setPadding(new Insets(0, 0, 0, 600));
    
    	//Oxygen frame
    HBox oxFrame = new HBox();
    Image oxFrameImg = new Image(this.getClass().getResourceAsStream("/images/jauge.png"));
    ImageView oxFrameIv = new ImageView(oxFrameImg);
    oxFrame.getChildren().add(oxFrameIv);
    oxFrame.setPadding(new Insets(35, 0, 0, 610));
    
    	//Oxygen bar
    VBox bar = new VBox();
    int oxNum = 2 * chestNum;
    VBox[] barList = new VBox[oxNum];
    int barHeight = 510;
    int oxPad = 1;
    int oxHeight = (barHeight - (oxPad * (oxNum - 1))) / oxNum;
    for(int i = 0; i < oxNum; i++) {
    	VBox bubble = new VBox();    		
    	Rectangle r = new Rectangle();
    	r.setX(665);
    	r.setY((i * oxHeight) + (i * oxPad));
    	r.setWidth(25);
    	r.setHeight(oxHeight);
    	r.setFill(Color.CORNFLOWERBLUE);
    	bubble.getChildren().clear();
    	bubble.getChildren().add(r);
    	bubble.setPadding(new Insets(0, 0, oxPad, 0));
    	barList[i]=bubble;
    }
    bar.getChildren().addAll(barList);
    bar.setPadding(new Insets(110, 0, 0, 665));
   
    //Set scores
    
    HBox[] scoresBoxList = new HBox[playerNum];
    HBox scores = new HBox();
    for(int i = 0; i < playerNum; i++) {
    	int p1 = getNumList(i);
    	HBox score = new HBox();
    	Text txt = new Text("0");
    	txt.setFill(getColor(p1));
    	txt.setStroke(Color.BLACK);
    	txt.setStrokeWidth(0.5);
    	txt.setFont(Font.font(18));
    	score.getChildren().clear();
    	score.getChildren().add(txt);
    	score.setPadding(new Insets(-100,110,0,0));
    	scoresBoxList[i] = score;
    }
    scores.getChildren().addAll(scoresBoxList);
    scores.setPadding(new Insets(0,0,0,210));
    setScoreList();
    
    //Set return
    
    Image crossImg = new Image(this.getClass().getResourceAsStream("/images/cross.png"));
    ImageView cross = new ImageView(crossImg);
    cross.setFitWidth(45);
    cross.setPreserveRatio(true);
    cross.setX(10);
    cross.setY(10);
    cross.setOnMouseEntered(ke -> {
    	//Hover image
    	Image crossImg2 = new Image(this.getClass().getResourceAsStream("/images/cross_selected.png"));
    	cross.setImage(crossImg2);
    });
    cross.setOnMouseExited(ke -> {
    	Image crossImg2 = new Image(this.getClass().getResourceAsStream("/images/cross.png"));
    	cross.setImage(crossImg2);
    });
    cross.setOnMousePressed(ke -> {
    	//Return
    	resetCross();
    	startScene();
    });
    
    //Set scene
    
    Group root = new Group();
    root.getChildren().addAll(background, bottom, players, oxygen, bar, oxFrame, chests, stashes, scores, cross);
    Scene scene = new Scene(root, 700, height);
    
    //Key bindings
    setPlaying(playersList, diversList.get(turn));

    scene.setOnKeyPressed(ke -> {
    	//MOVE DOWN
        if(ke.getCode() == KeyCode.DOWN){
        	//Set variables
        	Diver currentDiver = diversList.get(turn);
        	int id = currentDiver.getId();
        	int initialDepth = currentDiver.getDepth();
        	int initialOxygen = ox.getLevel();
        	Stash currentStash = stashesList.get(id);
        	Score currentScore = scoresList.get(id);
        	//Check oxygen available
			if (checkOxygenMove(ox, currentDiver) == false){
				//Reset
        		resetAll(currentDiver, playersList, ox, barList, oxygen, chestsBoxList, bottomList);
        	}else{	
        		//CASE 1 : SURFACE
        		if(currentDiver.getDepth() == -1) {
        			//Close stash
        			if(currentStash.isClosed() == false) {
        				closeStash(currentStash, stashesBoxList);
        				hideScore(currentScore, scoresBoxList);
        			}
        			//Get distance
        			int distance = getDistanceDown(currentDiver);
        			//Move
        			firstMoveDown(currentDiver, playersList, chestsBoxList, distance);
        			//Remove oxygen
        			removeOxygenMove(currentDiver, ox, barList);
        			//Check empty
    				if(ox.isEmpty()) {
    					resetAll(currentDiver, playersList, ox, barList, oxygen, chestsBoxList, bottomList);
    				}
        		//CASE 2 : BOTTOM
        		}else if(currentDiver.getDepth() == chestNum - 1) {
        			//Do nothing
        			turn--;
        		//CASE 3 : WATER
        		}else {
        			//Get distance
        			int distance = getDistanceDown(currentDiver);
        			//Move
        			moveDown(currentDiver, playersList, chestsBoxList, distance);
        			//Remove oxygen
        			removeOxygenMove(currentDiver, ox, barList);
        			//Check empty
    				if(ox.isEmpty()) {
    					resetAll(currentDiver, playersList, ox, barList, oxygen, chestsBoxList, bottomList);
    				}
        		}
        		//Set current player image
        		setCurrentPlaying(playersList);
        		System.out.println("Player "+(id+1));
        		System.out.println("Action: Move Down");
        		System.out.println("Initial depth: "+initialDepth+" / Final depth: "+currentDiver.getDepth()+" / Disance: "+(currentDiver.getDepth() - initialDepth));
        		System.out.println("Inventory: " + currentDiver.getInventory());
        		System.out.println("Treasures: "+currentDiver.getTreasures()+" / Chests: "+currentDiver.getStash());
        		System.out.println("Oxygen consumed: " + (initialOxygen - ox.getLevel()) + " / Current Oxygen: "+ox.getLevel());
        		System.out.println("------------------------------------------------------------------------------------------");
        	}
        }
        //MOVE UP
        if(ke.getCode() == KeyCode.UP) {
        	//Set variables
        	Diver currentDiver = diversList.get(turn);
        	int id = currentDiver.getId(); 
        	int initialDepth = currentDiver.getDepth();
        	int initialOxygen = ox.getLevel();
        	Stash currentStash = stashesList.get(id);
        	Score currentScore = scoresList.get(id);  
        	//Check oxygen available
			if (checkOxygenMove(ox, currentDiver) == false){
				//Reset
        		resetAll(currentDiver, playersList, ox, barList, oxygen, chestsBoxList, bottomList);
        	}else{	
        		//CASE 1 : SURFACE
        		if(currentDiver.getDepth() == -1) {
        			//Do nothing
        			turn--;  
        		//CASE 2 : UNDER SURFACE
        		}else if(currentDiver.getDepth() == 0){
        			int distance = getDistanceUp(currentDiver);
        			//Move
        			lastMoveUp(currentDiver, playersList, chestsBoxList, distance);
        			//Remove oxygen
        			removeOxygenMove(currentDiver, ox, barList);
        			//Open stash
        			setStash(currentDiver, currentStash, stashesBoxList);        		
        			setScore(currentDiver, currentScore, scoresBoxList);  
        			//Check empty
    				if(ox.isEmpty()) {
    					resetAll(currentDiver, playersList, ox, barList, oxygen, chestsBoxList, bottomList);
    				}
        		//CASE 3 : WATER
        		}else {
        			//Get distance
        			int distance = getDistanceUp(currentDiver);
        			if((distance != 1) && (distance == currentDiver.getDepth() + 1)) {
        				lastMoveUp(currentDiver, playersList, chestsBoxList, distance);
        				//Open stash
            			setStash(currentDiver, currentStash, stashesBoxList);        		
            			setScore(currentDiver, currentScore, scoresBoxList);
        			}
        			else {
        				moveUp(currentDiver, playersList, chestsBoxList, distance);
        			}
        			//Remove oxygen
        			removeOxygenMove(currentDiver, ox, barList);
        			//Check empty
    				if(ox.isEmpty()) {
    					resetAll(currentDiver, playersList, ox, barList, oxygen, chestsBoxList, bottomList);
    				}
        		}
        		//Set current player image
        		setCurrentPlaying(playersList);
        		System.out.println("Player "+(id+1));
        		System.out.println("Action: Move Up");
        		System.out.println("Initial depth: "+initialDepth+" / Final depth: "+currentDiver.getDepth()+" / Distance: "+(initialDepth - currentDiver.getDepth()));
        		System.out.println("Inventory: " + currentDiver.getInventory());
        		System.out.println("Treasures: "+currentDiver.getTreasures()+" / Chests: "+currentDiver.getStash());
        		System.out.println("Oxygen consumed: " + (initialOxygen - ox.getLevel()) + " / Current Oxygen: "+ox.getLevel());
        		System.out.println("------------------------------------------------------------------------------------------");
        	}
        }
        //OPEN CHEST
        if((ke.getCode() == KeyCode.LEFT) || (ke.getCode() == KeyCode.RIGHT)) {
        	//Set variables
        	Diver currentDiver = diversList.get(turn);
        	int depth = currentDiver.getDepth();
        	int initialOxygen = ox.getLevel();
        	int initialTreasures = currentDiver.getTreasures(), initialStash = currentDiver.getStash();
        	//Check oxygen available
			if (checkOxygenMove(ox, currentDiver) == false){
				//Reset
        		resetAll(currentDiver, playersList, ox, barList, oxygen, chestsBoxList, bottomList);
        	}else{	
        		//CASE 1 : SURFACE
        		if(depth == -1) {
        			//Do nothing
        			turn --;
        		//CASE 2 : BOTTOM
        		}else if(currentDiver.getDepth() == chestNum - 1){
                	Chest currentChest = chestsList.get(depth);
        			//If chest is closed
        			if(currentChest.isClosed()) {
        				//Open all bottom chests
        				openChest(currentChest, currentDiver, chestsBoxList);
        				openAllChest(currentDiver, bottomList);
        				//Remove oxygen
        				removeOxygenChest(ox, barList);
        				//Check empty
        				if(ox.isEmpty()) {
        					resetAll(currentDiver, playersList, ox, barList, oxygen, chestsBoxList, bottomList);
        				}
        			//If chest is open
        			}else {
        				//Do nothing
        				turn--;
        			}
        		//CASE 3 : WATER	
        		}else {
                	Chest currentChest = chestsList.get(depth);
        			//If chest is closed
        			if(currentChest.isClosed()) {
        				//Open chest
        				openChest(currentChest, currentDiver, chestsBoxList);
        				//Remove oxygen
        				removeOxygenChest(ox, barList);
        				//Check empty
        				if(ox.isEmpty()) {
        					resetAll(currentDiver, playersList, ox, barList, oxygen, chestsBoxList, bottomList);
        				}
        			//If chest is open
        			}else {
        				//Do nothing
        				turn--;
        			}
        		}        		
        		//Set current player image
        		setCurrentPlaying(playersList);
        		System.out.println("Player "+(currentDiver.getId()+1));
        		System.out.println("Action: Open Chest");
        		System.out.println("Depth: "+depth);
        		System.out.println("Inventory: " + currentDiver.getInventory());
        		System.out.println("Initial Treasures: "+initialTreasures+" / Current Treasures: "+currentDiver.getTreasures()+" / Initial Chests: "+initialStash+" / Current Chests: "+currentDiver.getStash());
        		System.out.println("Oxygen consumed: " + (initialOxygen - ox.getLevel()) + " / Current Oxygen: "+ox.getLevel());
        		System.out.println("------------------------------------------------------------------------------------------");
        	}
        }
    });
    this.stage.setScene(scene);
    this.stage.show();
  }
  
  //END SCENE
  
  private void endScene() {
	  //Set background	  
	  HBox background = setBackground("waterback");
	  
	//Set victory text
	  Text txt = new Text();
	  txt.setText(getVictory());
	  txt.setFill(Color.CRIMSON);
	  txt.setStroke(Color.BLACK);
	  txt.setStrokeWidth(7);
	  txt.setStrokeType(null);
	  txt.setFont(Font.font("Riffic Free Medium", 40));
	  txt.setX(150);
	  txt.setY(300);
	  
	  //Set restart button
	  
	  Image restartImg = new Image(this.getClass().getResourceAsStream("/images/restart.png"));
	    ImageView restart = new ImageView(restartImg);
	    restart.setFitWidth(100);
	    restart.setPreserveRatio(true);
	    restart.setX(300);
	    restart.setY(600);
	    restart.setOnMouseEntered(ke -> {
	    	//Hover image
	    	Image crossImg2 = new Image(this.getClass().getResourceAsStream("/images/restart_selected.png"));
	    	restart.setImage(crossImg2);
	    });
	    restart.setOnMouseExited(ke -> {
	    	Image crossImg2 = new Image(this.getClass().getResourceAsStream("/images/restart.png"));
	    	restart.setImage(crossImg2);
	    });
	    restart.setOnMousePressed(ke -> {
	    	resetCross();
	    	startScene();
	    });
	  
	  //Set scene
	  Group root = new Group();
	  root.getChildren().addAll(background, txt, restart);
	  Scene scene = new Scene(root, 700, height);
	  this.stage.setScene(scene);
	  this.stage.show();
  }
  
  //METHODS
  //Set scene
  
  public HBox setBackground(String name) {
	  HBox background = newImageW(name, 700);
	  background.setPadding(new Insets(0,0,0,0));
	  return background;
  }
  
  //Set start
  
  public void setButtons() {
	  for(int i = 0; i < 5; i++) {
		  buttonsList.add(new Button(i));
	  }
  }
  
  public int getIdSelected() {
	  for(int i = 0; i < buttonsList.size(); i++) {
		  Button button = buttonsList.get(i); 
		  if(button.isSelected()) {
			  return button.getId();
		  }
	  }
	  return 0;
  }
  
  public int getPlayerNum() {
	  int playerNum = 0;
	  for(int i = 0; i < buttonsList.size() - 1; i++) {
		  if((buttonsList.get(i).getPos() == 1) || (buttonsList.get(i).getPos() == 2)) {			  
			  playerNum++;
		  }
	  }
	  return playerNum;
  }
  
  public int getNumList(int n) {
	  int[] list = new int[playerNum];
	  int c = 0;
	  for(int i = 0; i < buttonsList.size() - 1; i++) {
		  if((buttonsList.get(i).getPos() == 1) || (buttonsList.get(i).getPos() == 2)) {
			  list[c] = i;
			  c++;
		  }
	  }
	  return list[n];
  }
  
  //Set game
  
  public void setDiverList() {
	  ArrayList<Integer>  list = new ArrayList<>();
	  for(int i = 0; i < buttonsList.size() - 1; i++) {
		  if(buttonsList.get(i).getPos() == 2) {
			  list.add(i);
		  }
	  }
	  for(int i = 0; i < playerNum; i++) {
		  if(list.contains(i)) {
			  diversList.add(new Diver(i, true));  
		  }else {
			  diversList.add(new Diver(i, false));
		  }
	  }
  }
  
  public void setCurrentPlaying(HBox[] playersList) {
	  if(turn < playerNum -1) {
  		setPlaying(playersList, diversList.get(turn+1));
  		turn++;
  	}
  	else{
  		turn=0;  		
		sortList();
		setPlaying(playersList, diversList.get(turn));
  	}
  }
  
  public void setPlaying(HBox[] list, Diver D) {
	  for(int i = 0; i < list.length; i++) {
		  int n = getNumList(i);
		  Image img = new Image(this.getClass().getResourceAsStream("/images/diver"+n+".png"));
			ImageView iv = new ImageView(img);
			iv.setFitHeight(playerHeight);
			iv.setPreserveRatio(true);
			HBox diver = new HBox();
			diver.getChildren().clear();
			diver.getChildren().add(iv);
			list[i].getChildren().clear();
			list[i].getChildren().add(diver);
	  }
		int id = D.getId();
		int n = getNumList(id);
		Image img = new Image(this.getClass().getResourceAsStream("/images/diver"+n+"_selected.png"));
		ImageView iv = new ImageView(img);
		iv.setFitHeight(playerHeight);
		iv.setPreserveRatio(true);
		list[id].getChildren().clear();
		HBox diver = new HBox();
		diver.getChildren().clear();
		diver.getChildren().add(iv);
		list[id].getChildren().add(diver);
	}
  
  public void sortList() {
		  Collections.sort(diversList, new Comparator<Diver>(){
			   @Override
			   public int compare(Diver d1,Diver d2) {
				   if (d2.getDepth() > d1.getDepth() ){
					   return 1;
				   }
				   else if (d1.getDepth()  > d2.getDepth() ){
					   return -1;
				   }
				   else{
					   if ((int) (0.5 + Math.random()) == 1) {
						   return -1;
					   } else {
						   return 1;
					   }
				   }
			     }
			 }); 
	  }

  public void setChestList() {
	  for(int i = 0; i < chestNum; i++) {
		  int t0 = ThreadLocalRandom.current().nextInt(1, 4);
		  int t1 = ThreadLocalRandom.current().nextInt(5, 9);
		  int t2 = ThreadLocalRandom.current().nextInt(10, 13);
		  int c = 0;		  
		  if(i < cave0Num) {
			  chestsList.add(new Chest(c, t0, i+1));
		  }
		  if((cave0Num <= i) && (i < cave0Num + cave1Num)) {
			  c = 1;
			  chestsList.add(new Chest(c, t1, i+1));			  
		  }
		  if((cave0Num + cave1Num <= i) && (i < chestNum)) {
			  c = 2;
			  chestsList.add(new Chest(c, t2, i+1));			  
		  }
	  }
  }
  
  public void setStashList() {
	  for(int i = 0; i < playerNum; i++) {
		  stashesList.add(new Stash(0, i));
	  }
  }
  
  public void setScoreList() {
	  for(int i = 0; i < playerNum; i++) {
		  scoresList.add(new Score(i));
	  }
  }
  
  public void setOxygen(Oxygen ox) {
	  int total = getOxygen();
	  ox.setLevel(total);
	  ox.setTotal(total);
  }
  
  public int getOxygen() {
	  int n = 0;
	  for(int i = 0; i < chestsList.size(); i++) {
		  if(chestsList.get(i).isClosed()) {
			  n++;
		  }
	  }
	  return 2 * n;
  }
  
  public Paint getColor(int n) {
	  Paint[] list = new Paint[4];
	  switch(n) {
	  case 0:list[n] = Color.BLUE;break;
	  case 1:list[n] = Color.CRIMSON;break;
	  case 2:list[n] = Color.GREEN;break;
	  case 3:list[n] = Color.YELLOW;break;
	  }
	  return list[n];
  }
  
  public HBox newImageW(String name, int width) {
	  HBox box = new HBox();
	  Image img = new Image(this.getClass().getResourceAsStream("/images/"+name+".png"));
	  ImageView iv = new ImageView(img);
	  iv.setPreserveRatio(true);
	  iv.setFitWidth(width);
	  box.getChildren().clear();
	  box.getChildren().add(iv);
	  return box;
  }
  
  public HBox newImageH(String name, int height) {
	  HBox box = new HBox();
	  Image img = new Image(this.getClass().getResourceAsStream("/images/"+name+".png"));
	  ImageView iv = new ImageView(img);
	  iv.setPreserveRatio(true);
	  iv.setFitHeight(height);
	  box.getChildren().clear();
	  box.getChildren().add(iv);
	  return box;
  }
  
  public String getVictory() {
	  String s = "Victoire ";
	  ArrayList<Integer> list = new ArrayList<>();
	  for(int i = 0; i < diversList.size(); i++) {
		  list.add(diversList.get(i).getStash());
	  }
	  int max = list.get(0), maxNum = 1;
	  for(int i = 1; i < list.size(); i++) {
		  if(list.get(i) > max) {
			  max = list.get(i);
		  }
	  }
	  for(int i = 0; i < list.size(); i++) {
		  if(list.get(i) == max) {
			  maxNum++;
		  }
	  }
	  
	  /*int max = 0, num = 1;
	  int maxNum = 1;
	  int[] id = new int[4];
	  for(int i = 0; i < diversList.size(); i++) {
		  if(diversList.get(i).getStash() > max) {
			  max = diversList.get(i).getStash();
			  id[0] = diversList.get(i).getId()+1;
		  }
	  }
	  for(int i = 0; i < diversList.size(); i++) {
		  if((diversList.get(i).getStash() == max) && (diversList.get(i).getId() != id[0]-1)) {
			  maxNum ++;
			  id[num] = diversList.get(i).getId()+1;
			  num++;
		  }
	  }
	  if(maxNum == 1) {
		  s = s+"du joueur "+id[0];  
	  }else {
		  s = s+"des joueurs ";
		  for(int i = 0; i < num; i++) {
			  s = s + id[i]+" ";
		  }
	  }*/
	  return s;
  }
  
  //Actions
  
  public void openChest(Chest c, Diver d, VBox[] l) {
	  c.setClosed(false);
	  c.openChest(l, d.getDepth());
		//set treasures
	  d.setTreasures(d.getTreasures() + c.getTreasures());
	  d.setStash(d.getStash() + 1);
	  d.getInventory().add(c.getId());
  }
  
  public void openAllChest(Diver d, HBox[] l) {
	  int cave = 0;
	  int n = 0;
	  for(int i = 0; i < l.length; i++){
	  	for(int j = 0; j < chestsList.size(); j++){
	  		if(chestsList.get(j).getBottomId() == i){		
	  			chestsList.get(j).setClosed(false);
	  			chestsList.get(j).setBottom(false);
				d.setTreasures(d.getTreasures() + chestsList.get(j).getTreasures());
				d.setStash(d.getStash() + 1);
				d.getInventory().add(chestsList.get(j).getId());
				cave = chestsList.get(j).getCave();
				System.out.println(cave);
				n = 1;
				break;
	  		}	  		
	  	}
	  	if(n == 0) {
	  		break;
	  	}
	  	Image img = new Image(this.getClass().getResourceAsStream("/images/chest"+cave+"_open.png"));
		ImageView iv = new ImageView(img);
		iv.setFitHeight(20);
		iv.setPreserveRatio(true);
		l[i].getChildren().clear();
		l[i].getChildren().add(iv);
		System.out.println("Image changed");
		n = 0;
	  }
  }
  
  public void closeStash(Stash s, VBox[] list) {
	  int i = s.getId();
	  int n = getNumList(i);
	  s.setClosed(true);
	  Image img = new Image(this.getClass().getResourceAsStream("/images/stashchest"+n+".png"));
		ImageView iv = new ImageView(img);
		iv.setFitWidth(30);
		iv.setPreserveRatio(true);
		list[i].getChildren().clear();
		list[i].getChildren().add(iv);
		Insets current = list[i].getPadding();
		Insets next = new Insets(current.getTop() + 18, current.getRight(), current.getBottom(), current.getLeft());
		list[i].setPadding(next);
  }
  
  public void setStash(Diver d, Stash s, VBox[] l) {
	  s.setAmount(s.getAmount() + d.getTreasures());
	  d.getInventory().removeAll(d.getInventory());
	  openStash(l, s.getId());
	  s.setClosed(false);
  }
  
  public void openStash(VBox[] list, int i) {
	  int n = getNumList(i);
		Image img = new Image(this.getClass().getResourceAsStream("/images/stashchest"+n+"_open.png"));
		ImageView iv = new ImageView(img);
		iv.setFitWidth(30);
		iv.setPreserveRatio(true);
		list[i].getChildren().clear();
		list[i].getChildren().add(iv);
		Insets current = list[i].getPadding();
		Insets next = new Insets(current.getTop() - 18, current.getRight(), current.getBottom(), current.getLeft());
		list[i].setPadding(next);
  }
  
  public static void hideScore(Score s, HBox[] b) {
	  s.hideText(b, s.getId());
	  s.setHidden(true);
  }
  
  public void setScore(Diver d, Score s, HBox[] l) {
	  s.showText(l, s.getId());
	  s.setValue(d.getTreasures() + s.getValue());
	  s.setHidden(false);
	  d.setTreasures(0);
	  d.setStash(0);
	  setText(l, s.getId());
  }
  
  public void setText(HBox[] l, int i) {
		  int n = getNumList(i);
		  Text txt = new Text("" + scoresList.get(i).getValue() + "");
			txt.setFill(getColor(n));
			txt.setStroke(Color.BLACK);
	    	txt.setStrokeWidth(0.5);
	    	txt.setFont(Font.font(18));
			l[i].getChildren().clear();
			l[i].getChildren().add(txt);
  }
  
  public static int getDistanceDown(Diver d) {
	  int distance = 1, i = d.getDepth();
	  while((chestsList.get(i+1).isHidden()) && (i < chestNum-1)) {
		  distance++;
		  i++;
	  }
	  return distance;
  }
  
  public static int getDistanceUp(Diver d) {
	  int distance = 1, i = d.getDepth();
	  if(i > 0) {
		  while((i > 0) && (chestsList.get(i-1).isHidden())) {
			  i--;
			  distance++;
		  }
		  if((distance != 1) && (chestsList.get(0).isHidden())) {
			  if(d.getDepth() == distance) {
				  distance++;
			  }
		  }
	  }
	  return distance;
  }
  
  public static void moveDown(Diver d, HBox[] p, VBox[] c, int i) {
	  d.moveDown(p, d.getId(), c, i);
	  d.setDepth(d.getDepth()+i);
}
  
  public static void moveUp(Diver d, HBox[] p, VBox[] c, int i) {
	  d.moveUp(p, d.getId(), c, i);
	  d.setDepth(d.getDepth()-i);
  }
  
  public static void firstMoveDown(Diver d, HBox[] l, VBox[] c, int distance) {
	  d.moveFirst(l, c, distance);
		d.setDepth(d.getDepth()+distance);
  }
  
  public void lastMoveUp(Diver d, HBox[] l, VBox[] c, int distance) {
	  d.moveLast(l, c, distance);
	  d.setDepth(-1);
	  d.setStash(0);
  }
  
  public boolean checkOxygenMove(Oxygen o, Diver d) {
	  int level = o.getLevel();
	  int stash = d.getStash();
	  int amount = 1 + stash;
	  if(amount < level) {
		  return true;
	  }else {
		  return false;
	  }
  }
  
  public boolean checkOxygenChest(Oxygen o) {
	  int level = o.getLevel();
	  int amount = 1;
	  if(amount < level) {
		  return true;
	  }else {
		  return false;
	  }
  }
  
  public static void removeOxygenMove(Diver d, Oxygen o, VBox[] r) {
	  o.setLevel(o.getLevel() - (1 + d.getStash()));
	  o.removeOxygen(r,  (1 + d.getStash()));	  
  }
  
  public static void removeOxygenChest(Oxygen o, VBox[] r) {
	  o.setLevel(o.getLevel() - 1);
	  o.removeOxygen(r, 1);
  }
  
  //Reset scene

  public void resetAll(Diver currentDiver, HBox[] playersList, Oxygen ox, VBox[] bar, HBox oxygen, VBox[] chestsBoxList, HBox[] bottomList) {
	  if(ox.getStock() == 0) {
		  endScene();
	  }else {		  
		  resetPlayersList();
		  //reset players	
		  resetPlayers(currentDiver, playersList, chestsBoxList);    			
		  //reset oxygen        			
		  resetOxygen(ox, bar, oxygen);        			
		  //reset chests        			
		  resetChests(chestsBoxList, bottomList);		  
	  }	  	
  }
  
  public void resetPlayersList() {
	  ArrayList<Diver> newList = new ArrayList<Diver>();
	  for(int j = 0; j < diversList.size(); j++) {
		  for(int i = 0; i < diversList.size(); i++) {
			  Diver d = diversList.get(i);
			  if(d.getId() == j) {
				  newList.add(d);
			  }
		  }
	  }
	  diversList = newList;
  }
  
  public void resetPlayers(Diver currentDiver, HBox[] playersList, VBox[] chestsList) {
	  currentDiver.resetPos(playersList, chestsList, diversList);
		for(int i = 0; i < diversList.size(); i++) {
			diversList.get(i).setDepth(-1);
			diversList.get(i).setStash(0);
			diversList.get(i).setTreasures(0);
			
		}
		turn = playerNum -1; 
		setCurrentPlaying(playersList);
  }
  
  public static void resetOxygen(Oxygen o, VBox[] r, HBox b) {
	  o.resetBar(r);
	  o.setEmpty(false);
	  o.setLevel(o.getTotal());
	  o.setStock(o.getStock() - 1);
	  o.resetStock(b);
  }
  
  public void resetChests(VBox[] c, HBox[] bottomList) {
	  int n = 0;
	  int cave = 0;
	  int bottom = 0;
	  for(int i = 0; i < chestsList.size(); i ++) {
		  if(chestsList.get(i).isBottom()) {
			  bottom++;
		  }
	  }
	  n = bottom;
	  for(int i = 0; i < diversList.size(); i++) {
		  ArrayList<Integer> list = diversList.get(i).getInventory();	  
		  for(int j = 0; j < list.size(); j++) {	
			  for(int k = 0; k < chestsList.size(); k++) {				
				  if(chestsList.get(k).getId() == list.get(j)) {
					  cave = chestsList.get(k).getCave();
					  chestsList.get(k).setBottom(true);
					  chestsList.get(k).setBottomId(n);
				  }
			  }
			  Image img = new Image(this.getClass().getResourceAsStream("/images/chest"+cave+".png"));
			  ImageView iv = new ImageView(img);
			  iv.setFitHeight(20);
			  iv.setPreserveRatio(true);
			  bottomList[n].getChildren().clear();
			  bottomList[n].getChildren().add(iv);
			  n++;
		  }
	  }
	  for(int i = 0; i < chestsList.size(); i++) { 
		  if(chestsList.get(i).isClosed() == false) {
			  Insets current = c[i].getInsets();
			  Insets next = new Insets(current.getTop(), current.getRight(), current.getBottom(), current.getLeft() - 100);
			  c[i].setPadding(next);
			  chestsList.get(i).setHidden(true);
		  }  
	  }
	  for(int i = 0; i < diversList.size(); i++) {
		  diversList.get(i).setInventory(new ArrayList<Integer>());  
	  }
  }
  
  public void resetCross() {
	  //Reset buttons
	  buttonsList.clear();
	  //Reset chests
	  chestsList.clear();
	  //Reset divers
	  diversList.clear();
	  //Reset turn order
	  turn = 0;
	  sortList();
	  //Reset stashes
	  stashesList.clear();
	  //Reset scores	
	  scoresList.clear();
  }
  
  //Main
  
  public static void main(String[] args) {
        launch(args);
    }
}