import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Chest {

	private int cave, treasures, id, bottomId;
	private boolean closed = true, hidden = false, bottom = false;
	
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


	public int getBottomId() {
		return bottomId;
	}

	public void setBottomId(int bottomId) {
		this.bottomId = bottomId;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isBottom() {
		return bottom;
	}

	public void setBottom(boolean bottom) {
		this.bottom = bottom;
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