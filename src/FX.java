
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

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
    
	static int playerNum = 4, cave0Num = 12, cave1Num = 6, cave2Num = 3;
    int turn = 0 ;
    static int chestNum = cave0Num + cave1Num + cave2Num;
	
    private Stage stage;
    private ArrayList<Diver> diversList = new ArrayList<>();
    private ArrayList<Chest> chestsList = new ArrayList<>();
    private ArrayList<Stash> stashesList = new ArrayList<>();
    private ArrayList<Score> scoresList = new ArrayList<>();
    
    int height = 690;
    int playerHeight = 70;
    int chestHeight = 20;
    int stashHeight = 26;
    
  public void start(Stage stage){
    stage.setTitle("Snorkunking");
    this.stage=stage;
    gameScene();
  }
  
  //GAME SCENE
  
  private void gameScene(){
	  
	//Set background
	
	HBox background = setBackground();
    
    //Set players
    
	HBox[] playersList = new HBox[playerNum];
	HBox players = new HBox();

    for(int i = 0; i < playerNum; i++) {
	    HBox diver = new HBox();
	    Image img = new Image(this.getClass().getResourceAsStream("/images/diver"+i+".png"));
		ImageView iv = new ImageView(img);
		iv.setFitHeight(playerHeight);
		iv.setPreserveRatio(true);
		diver.getChildren().clear();
		diver.getChildren().add(iv);
		diver.setPadding(new Insets(0,80,0,0));
	    playersList[i]=diver;
	  }
	  setDiverList();
	  //setPlaying(playersList, diversList, turn);
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
		iv.setFitHeight(20);
		iv.setPreserveRatio(true);
		chest.getChildren().clear();
		chest.getChildren().add(iv);
		chest.setPadding(new Insets(0, 0, padding, 0));
    	chestsBoxList[i]=chest;
    }
    setChestList();
    chests.getChildren().addAll(chestsBoxList);
    chests.setPadding(new Insets(playerHeight+20,0,0,60));
    
    //Set stashes
    
    VBox[] stashesBoxList = new VBox[playerNum];
    HBox stashes = new HBox();
    for(int i = 0; i < playerNum; i++) {
    	VBox stash = new VBox();
    	Image stashImg = new Image(this.getClass().getResourceAsStream("/images/stashchest"+i+".png"));
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
    
    //Chests frame
    
    HBox chestFrame = new HBox();
    Image chestFrameImg = new Image(this.getClass().getResourceAsStream("/images/frame2.png"));
    ImageView chestFrameIv = new ImageView(chestFrameImg);    
    chestFrame.getChildren().add(chestFrameIv);
    chestFrame.setPadding(new Insets(45, 0, 0, 0));
    
    //Set oxygen
    
    	//Stock
    Oxygen ox = new Oxygen();
    setOxygen(ox);
    HBox oxygen = new HBox();
    Image oxygenImg = new Image(this.getClass().getResourceAsStream("/images/oxygen" + ox.getStock() + ".png"));
    ImageView oxygenIv = new ImageView(oxygenImg);    
    oxygen.getChildren().add(oxygenIv);
    oxygen.setPadding(new Insets(0, 0, 0, 50));
    
    	//Oxygen frame
    HBox oxFrame = new HBox();
    Image oxFrameImg = new Image(this.getClass().getResourceAsStream("/images/jauge.png"));
    ImageView oxFrameIv = new ImageView(oxFrameImg);
    oxFrame.getChildren().add(oxFrameIv);
    oxFrame.setPadding(new Insets(20, 0, 0, 610));
    
    	//Oxygen bar
    Rectangle bar = new Rectangle();
    bar.setX(665);
    bar.setY(95);
    bar.setWidth(25);
    bar.setHeight(500);
    bar.setFill(Color.CORNFLOWERBLUE);
   
    //Set scores
    
    HBox[] scoresBoxList = new HBox[playerNum];
    HBox scores = new HBox();
    for(int i = 0; i < playerNum; i++) {
    	HBox score = new HBox();
    	Text txt = new Text("0");
    	txt.setFill(getColor(i));
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
    
    //Set scene
    
    Group root = new Group();
    root.getChildren().addAll(background, players, oxygen, bar, oxFrame, chestFrame, chests, stashes, scores);
    Scene scene = new Scene(root, 700, height);
    
    //Key bindings
    setPlaying(playersList, diversList, diversList.get(turn));
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
        	//CASE 1 : SURFACE
        	if(currentDiver.getDepth() == -1) {
        		//Close stash
        		if(currentStash.isClosed() == false) {
        			closeStash(currentStash, stashesBoxList);
        			hideScore(currentScore, scoresBoxList);
        		}
        		//Get distance
        		int distance = getDistanceDown(currentDiver, chestsList);
        		//Check oxygen available
    			//If available
    				//*Move
    			//Else
    				//Reset
        		//*Move
        		for(int i = 0; i < distance; i++) {
        			if(i == 0) {
        				firstMoveDown(currentDiver, playersList);
            		}else {
            			moveDown(currentDiver, playersList, chestsBoxList);
            		}    				
    			}
        		//Remove oxygen
        		removeOxygenMove(currentDiver, ox, bar);
        		//Check oxygen reserve
        		if (ox.isEmpty()){        		
        			resetAll(currentDiver, playersList, ox, bar, oxygen, chestsBoxList);
        		}
        		
        	//CASE 2 : BOTTOM
        	}else if(currentDiver.getDepth() == chestNum - 1) {
        		//Do nothing
        		turn--;
        	//CASE 3 : WATER
        	}else {
        		//Get distance
        		int distance = getDistanceDown(currentDiver, chestsList);
        		//Check oxygen available
    			//If available
    				//*Move
    			//Else
    				//Reset
        		//*Move
        		for(int i = 0; i < distance; i++) {
        			moveDown(currentDiver, playersList, chestsBoxList);
        		}
        		//Remove oxygen
        		removeOxygenMove(currentDiver, ox, bar);
        		//Check oxygen reserves
        		if (ox.isEmpty()){        		
        			resetAll(currentDiver, playersList, ox, bar, oxygen, chestsBoxList);
        		}
        	}
        	//Set current player image
        	setCurrentPlaying(playersList);
        	System.out.println("Player "+(id+1));
        	System.out.println("Action: Move Down");
        	System.out.println("Initial depth: "+initialDepth+" / Final depth: "+currentDiver.getDepth()+" / Disance: "+(currentDiver.getDepth() - initialDepth));
        	System.out.println("Treasures: "+currentDiver.getTreasures()+" / Chests: "+currentDiver.getStash());
        	System.out.println("Oxygen consumed: " + (initialOxygen - ox.getLevel()) + " / Current Oxygen: "+ox.getLevel());
        	System.out.println("------------------------------------------------------------------------------------------");
        	
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
        	//CASE 1 : SURFACE
        	if(currentDiver.getDepth() == -1) {
        		//Do nothing
        		turn--;  
        	//CASE 2 : UNDER SURFACE
        	}else if(currentDiver.getDepth() == 0){
        		//Check oxygen available
    			//If available
    				//*Move
    			//Else
    				//Reset
        		//*Move
        		lastMoveUp(currentDiver, playersList);
        		//Remove oxygen
        		removeOxygenMove(currentDiver, ox, bar);
        		//Check oxygen reserves
        		if (ox.isEmpty()){        		
        			resetAll(currentDiver, playersList, ox, bar, oxygen, chestsBoxList);
        		}
        		//Open stash
        		setStash(currentDiver, currentStash, stashesBoxList);        		
        		setScore(currentDiver, currentScore, scoresBoxList);    
        	//CASE 3 : WATER
        	}else {
        		//Get distance
        		int distance = getDistanceUp(currentDiver, chestsList);
        		//Check oxygen available
        			//If available
        				//*Move
        			//Else
        				//Reset
        		//*Move
        		for(int i = 0; i < distance; i++) {
        			if(currentDiver.getDepth() == 0) {
        				lastMoveUp(currentDiver, playersList);
        				//Open stash
                		setStash(currentDiver, currentStash, stashesBoxList);        		
                		setScore(currentDiver, currentScore, scoresBoxList);
        			}else {
        				moveUp(currentDiver, playersList, chestsBoxList);
        			}	
        		}
        		//Remove oxygen
        		removeOxygenMove(currentDiver, ox, bar);
        		//Check oxygen reserves
        		if (ox.isEmpty()){        		
        			resetAll(currentDiver, playersList, ox, bar, oxygen, chestsBoxList);
        		}
        	}
        	//Set current player image
        	setCurrentPlaying(playersList);
        	System.out.println("Player "+(id+1));
        	System.out.println("Action: Move Up");
        	System.out.println("Initial depth: "+initialDepth+" / Final depth: "+currentDiver.getDepth()+" / Disance: "+(initialDepth - currentDiver.getDepth()));
        	System.out.println("Treasures: "+currentDiver.getTreasures()+" / Chests: "+currentDiver.getStash());
        	System.out.println("Oxygen consumed: " + (initialOxygen - ox.getLevel()) + " / Current Oxygen: "+ox.getLevel());
        	System.out.println("------------------------------------------------------------------------------------------");
        }
        //OPEN CHEST
        if((ke.getCode() == KeyCode.LEFT) || (ke.getCode() == KeyCode.RIGHT)) {
        	//Set variables
        	Diver currentDiver = diversList.get(turn);
        	int depth = currentDiver.getDepth();
        	int initialOxygen = ox.getLevel();
        	int initialTreasures = currentDiver.getTreasures(), initialStash = currentDiver.getStash();
        	//CASE 1 : SURFACE
        	if(depth == -1) {
        		//Do nothing
        		turn --;
        	//CASE 2 : WATER
        	}else {
        		Chest currentChest = chestsList.get(depth);
        		//If chest is closed
        		if(currentChest.isClosed()) {
        			//Check oxygen available
        			//If available
        				//*Open chest
        			//Else
        				//Reset
        			//*Open chest
        			openChest(currentChest, currentDiver, chestsBoxList, bar ,ox);
        			//Remove oxygen
        			removeOxygenChest(ox, bar);
        			//Check oxygen reserves
        			if (ox.isEmpty()){
        				resetAll(currentDiver, playersList, ox, bar, oxygen, chestsBoxList);
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
        	System.out.println("Initial Treasures: "+initialTreasures+" / Current Treasures: "+currentDiver.getTreasures()+" / Initial Chests: "+initialStash+" / Current Chests: "+currentDiver.getStash());
        	System.out.println("Oxygen consumed: " + (initialOxygen - ox.getLevel()) + " / Current Oxygen: "+ox.getLevel());
        	System.out.println("------------------------------------------------------------------------------------------");
        }  
    });
    this.stage.setScene(scene);
    this.stage.show();
  }
  
  //Set scene
  
  public HBox setBackground() {
	  HBox background = new HBox();
	  Image backImg = new Image(this.getClass().getResourceAsStream("/images/fullbacko2.png"));
	  ImageView backIv = new ImageView(backImg);
	  int width = 700;
	  backIv.setFitWidth(width);
	  backIv.setPreserveRatio(true);
	  background.getChildren().clear();
	  background.getChildren().add(backIv);
	  background.setPadding(new Insets(0,0,0,0));
	  return background;
  }
  
  public void setDiverList() {
	  for(int i = 0; i < playerNum; i++) {
		  diversList.add(new Diver(i));		  
	  }
  }
  
  public void setCurrentPlaying(HBox[] playersList) {
	  if(turn < playerNum -1) {
  		setPlaying(playersList, diversList, diversList.get(turn+1));
  		turn++;
  	}
  	else{
  		turn=0;
			sortList();
			setPlaying(playersList, diversList, diversList.get(turn));
  	}
  }
  
  public void setPlaying(HBox[] list, ArrayList<Diver> d, Diver D) {
	  for(int i = 0; i < list.length; i++) {
		  Image img = new Image(this.getClass().getResourceAsStream("/images/diver"+i+".png"));
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
		Image img = new Image(this.getClass().getResourceAsStream("/images/diver"+id+"_selected.png"));
		ImageView iv = new ImageView(img);
		iv.setFitHeight(playerHeight);
		iv.setPreserveRatio(true);
		list[id].getChildren().clear();
		HBox diver = new HBox();
		diver.getChildren().clear();
		diver.getChildren().add(iv);
		//diver.setPadding(new Insets(0,80,0,0));
		list[id].getChildren().add(diver);
	}
  
  public void sortList() {
	  //if (turn == playerNum -1){
		  //turn = 0;
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
					   return 0;
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
	  //Set stock
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
	  Paint[] list = new Paint[playerNum];
	  switch(n) {
	  case 1:list[n] = Color.BLUE;
	  case 2:list[n] = Color.CRIMSON;
	  case 3:list[n] = Color.GREEN;
	  case 4:list[n] = Color.YELLOW;
	  }
	  return list[n];
  }
  
  //Actions
  
  public void openChest(Chest c, Diver d, VBox[] l, Rectangle r, Oxygen o) {
	  c.setClosed(false);
	  c.openChest(l, d.getDepth());
		//set treasures
	  d.setTreasures(d.getTreasures() + c.getTreasures());
	  d.setStash(d.getStash() + 1);
  }
  
  public static void closeStash(Stash s, VBox[] b) {
	  s.closeStash(b, s.getId());
	  s.setClosed(true);
  }
  
  public static void setStash(Diver d, Stash s, VBox[] l) {
	  s.setAmount(s.getAmount() + d.getTreasures());
	  s.openStash(l, s.getId());
	  s.setClosed(false);
  }
  
  public static void hideScore(Score s, HBox[] b) {
	  s.hideText(b, s.getId());
	  s.setHidden(true);
  }
  
  public static void setScore(Diver d, Score s, HBox[] l) {
	  s.showText(l, s.getId());
	  s.setValue(d.getTreasures() + s.getValue());
	  s.setText(l, s.getId());
	  s.setHidden(false);
	  d.setTreasures(0);
	  d.setStash(0);
  }
  
  public static int getDistanceDown(Diver d, ArrayList<Chest> c) {
	  int distance = 1, i = d.getDepth();
	  while((c.get(i+1).isHidden()) && (i < chestNum-1)) {
		  distance++;
		  i++;
	  }
	  return distance;
  }
  
  public static int getDistanceUp(Diver d, ArrayList<Chest> c) {
	  int distance = 1, i = d.getDepth();
	  while((c.get(i-1).isHidden()) && (i > 1)) {
		  i--;
		  distance++;
	  }
	  if(d.getDepth() == distance) {
		  distance++;
	  }
	  return distance;
  }
  
  public static void moveDown(Diver d, HBox[] p, VBox[] c) {
	  d.moveDown(p, d.getId(), c);
	  d.setDepth(d.getDepth()+1);
}
  
  public static void moveUp(Diver d, HBox[] p, VBox[] c) {
	  d.moveUp(p, d.getId(), c);
	  d.setDepth(d.getDepth()-1);
  }
  
  public static void firstMoveDown(Diver d, HBox[] l) {
	  d.moveFirst(l, d.getId());
		d.setDepth(d.getDepth()+1);
  }
  
  public static void lastMoveUp(Diver d, HBox[] b) {
	  d.moveLast(b,d.getId());
	  d.setDepth(d.getDepth()-1);
	  d.setStash(0);
  }
  
  public static void removeOxygenMove(Diver d, Oxygen o, Rectangle r) {
	  o.setLevel(o.getLevel() - (1 + d.getStash()));
	  o.removeOxygen(r, d.getStash());	  
  }
  
  public static void removeOxygenChest(Oxygen o, Rectangle r) {
	  o.setLevel(o.getLevel() - 1);
	  o.removeOxygen(r, 1);
  }
  
  //Reset scene

  public void resetAll(Diver currentDiver, HBox[] playersList, Oxygen ox, Rectangle bar, HBox oxygen, VBox[] chestsBoxList) {
	//reset players	
		resetPlayers(currentDiver, playersList);    			
		//reset oxygen        			
		resetOxygen(ox, bar, oxygen);        			
		//reset chests        			
		resetChests(chestsBoxList, chestsList);
  }
  
  public void resetPlayers(Diver currentDiver, HBox[] playersList) {
	  currentDiver.resetPos(playersList);
		for(int i = 0; i < diversList.size(); i++) {
			diversList.get(i).setDepth(-1);
			diversList.get(i).setStash(0);
			diversList.get(i).setTreasures(0);
		}
		turn = playerNum - 1;  
  }
  
  public static void resetOxygen(Oxygen o, Rectangle r, HBox b) {
	  o.resetBar(r);
	  o.setEmpty(false);
	  o.setLevel(o.getTotal());
	  o.setStock(o.getStock() - 1);
	  o.resetStock(b);
  }
  
  public void resetChests(VBox[] c, ArrayList<Chest> C) {
	  for(int i = 0; i < C.size(); i++) {
		  if(C.get(i).isClosed() == false) {
			  Insets current = c[i].getInsets();
			  Insets next = new Insets(current.getTop(), current.getRight(), current.getBottom(), current.getLeft() - 100);
			  c[i].setPadding(next);
			  C.get(i).setHidden(true);
		  }  
	  }
  }

  //Main
  
  public static void main(String[] args) {
        launch(args);
    }
}