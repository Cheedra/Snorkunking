import javafx.scene.image.ImageView;

import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Diver {

	private int treasures = 0, depth = -1, id, stash = 0;
    private ImageView img ;
    
    int height = 690, playerHeight = 70, chestHeight = 20;
	
	public Diver(int id){		
		this.id = id;	
	}
	
	public void setTreasures(int t) {
		this.treasures=t;
	}

	public int getTreasures() {
		return this.treasures;
	}
	
	public int getDepth(){
        return this.depth;
    }
       
    public void setDepth(int d){
        this.depth=d;
    }
        
    public void setImage(ImageView img ){
        this.img=img;  
    }
        
    public ImageView getImage(){
        return this.img;
    }
        
    public void setId(int i) {
    	this.id = i;
    }
    
    public int getId() {
    	return this.id;
    }
    
	public int getStash() {
		return stash;
	}

	public void setStash(int stash) {
		this.stash = stash;
	}

	public int getAmount(VBox[] chests) {
		int padding = ((height - playerHeight - 40) - (chests.length * chestHeight))/(chests.length - 1);
		int amount = (playerHeight + padding + chestHeight) - playerHeight;
		return amount;
	}
	
	public void moveUp(HBox[] list, int turn, VBox[] chests, int distance) {
		int amount = distance * getAmount(chests);
		/*Insets current = list[turn].getPadding();
		Insets next = new Insets(current.getTop()-amount, current.getRight(), current.getBottom(), current.getLeft());
		list[turn].setPadding(next);*/
		TranslateTransition anim = new TranslateTransition();
		anim.setDuration(Duration.millis(100)); 
		anim.setNode(list[turn]);
		anim.setByY(-amount);	
		anim.play();
	}
	
	public void moveDown(HBox[] list, int turn, VBox[] chests, int distance) {
		int amount = distance * getAmount(chests);
		/*Insets current = list[turn].getPadding();
		//redo insets
		Insets next = new Insets(current.getTop()+amount, current.getRight(), current.getBottom(), current.getLeft());
		list[turn].setPadding(next);*/
		TranslateTransition anim = new TranslateTransition();
		anim.setDuration(Duration.millis(100)); 
		anim.setNode(list[turn]);
		anim.setByY(amount);	
		anim.play();
	}

	public void moveFirst(HBox[] list, VBox[] chests, int distance) {
		int first = 20 + chestHeight;
		int amount = first;
		if(distance > 1) {
			amount = first + ((distance - 1) * getAmount(chests));
		}
		/*Insets current = list[turn].getPadding();
		//redo insets
		Insets next = new Insets(current.getTop()+amount, current.getRight(), current.getBottom(), current.getLeft());
		list[turn].setPadding(next);*/
		TranslateTransition anim = new TranslateTransition();
		anim.setDuration(Duration.millis(100)); 
		anim.setNode(list[id]);
		anim.setByY(amount);	
		anim.play();
	}
	
	public void moveLast(HBox[] list, VBox[] chests, int distance) {
		int first = 20 + chestHeight;
		int amount = first;
		if(distance > 1) {
			amount = first + ((distance - 1) * getAmount(chests));
		}
		/*Insets current = list[turn].getPadding();
		//redo insets
		Insets next = new Insets(current.getTop()-amount, current.getRight(), current.getBottom(), current.getLeft());
		list[turn].setPadding(next);*/
		TranslateTransition anim = new TranslateTransition();
		anim.setDuration(Duration.millis(100)); 
		anim.setNode(list[id]);
		anim.setByY(-amount);	
		anim.play();
	}
	
	public void resetPos(HBox[] list, VBox[] chests, ArrayList<Diver> players) {
		for(int i = 0; i < list.length; i++) {
			/*Insets ini = new Insets(0,80,0,0);
			list[i].setPadding(ini);*/
			int depth = players.get(i).getDepth();
			int lastAmount = 20 + chestHeight;
			int amount = depth * getAmount(chests) ;
			if(depth == -1) {
				amount = 0;
				lastAmount = 0;
			}
			TranslateTransition anim = new TranslateTransition();
			anim.setDuration(Duration.millis(100)); 
			anim.setNode(list[i]);
			anim.setByY(- amount - lastAmount);	
			anim.play();
		}
	}
}