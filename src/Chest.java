import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Chest {

	private int cave, treasures, id;
	private boolean closed = true, hidden = false;
	
	Chest(int c, int t, int id){
		this.cave=c;
		this.treasures=t;
		this.setId(id);
	}
	
	public int getTreasures() {		
		return this.treasures;		
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public int getCave() {
		return cave;
	}

	public void setCave(int cave) {
		this.cave = cave;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void openChest(VBox[] list, int i) {
		Image img = new Image(this.getClass().getResourceAsStream("/images/chest"+this.cave+"_open.png"));
		ImageView iv = new ImageView(img);
		iv.setFitHeight(20);
		iv.setPreserveRatio(true);
		list[i].getChildren().clear();
		list[i].getChildren().add(iv);
	}
}