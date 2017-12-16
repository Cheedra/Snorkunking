import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

	public void moveUp(HBox[] list, int turn, VBox[] chests) {
		int padding = ((height - playerHeight - 40) - (chests.length * chestHeight))/(chests.length - 1);
		int amount = (playerHeight + padding + chestHeight) - playerHeight;
		Insets current = list[turn].getPadding();
		Insets next = new Insets(current.getTop()-amount, current.getRight(), current.getBottom(), current.getLeft());
		list[turn].setPadding(next);
	}
	
	public void moveDown(HBox[] list, int turn, VBox[] chests) {
		int padding = ((height - playerHeight - 40) - (chests.length * chestHeight))/(chests.length - 1);
		int amount = padding + chestHeight;
		Insets current = list[turn].getPadding();
		//redo insets
		Insets next = new Insets(current.getTop()+amount, current.getRight(), current.getBottom(), current.getLeft());
		list[turn].setPadding(next);		
	}

	public void moveFirst(HBox[] list, int turn) {
		int amount = 20 + chestHeight;
		Insets current = list[turn].getPadding();
		//redo insets
		Insets next = new Insets(current.getTop()+amount, current.getRight(), current.getBottom(), current.getLeft());
		list[turn].setPadding(next);
	}
	
	public void moveLast(HBox[] list, int turn) {
		int amount = 20 + chestHeight;
		Insets current = list[turn].getPadding();
		//redo insets
		Insets next = new Insets(current.getTop()-amount, current.getRight(), current.getBottom(), current.getLeft());
		list[turn].setPadding(next);
	}
	
	public void resetPos(HBox[] list) {
		for(int i = 0; i < list.length; i++) {
			Insets ini = new Insets(0,80,0,0);
			list[i].setPadding(ini);
		}
	}
}