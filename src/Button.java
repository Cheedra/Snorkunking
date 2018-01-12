import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Button {

	private int id, pos = 0;
	boolean selected = false;

	Button(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public void setSelected(boolean b) {
		this.selected = b;
	}
	
	public boolean isSelected() {
		return this.selected;
	}
	
	public void light(VBox[] playersList, int n, HBox start) {
		if(n < 4) {
			Image img = new Image(this.getClass().getResourceAsStream("/images/diver"+n+"_selected.png"));
			ImageView iv = new ImageView(img);
			iv.setFitWidth(50);
			iv.setPreserveRatio(true);
			playersList[n].getChildren().clear();
			playersList[n].getChildren().add(iv);
			playersList[n].setPadding(new Insets(0,0,20,0));
		}else {
			Image img = new Image(this.getClass().getResourceAsStream("/images/start_selected.png"));
			ImageView iv = new ImageView(img);
			iv.setFitWidth(100);
			iv.setPreserveRatio(true);
			start.getChildren().clear();
			start.getChildren().add(iv);
			start.setPadding(new Insets(600,0,0,350 - 50));
		}
	}
	
	public void unlight(VBox[] playersList, int n, HBox start) {
		if(n < 4) {
			Image img = new Image(this.getClass().getResourceAsStream("/images/diver"+n+".png"));
			ImageView iv = new ImageView(img);
			iv.setFitWidth(50);
			iv.setPreserveRatio(true);
			playersList[n].getChildren().clear();
			playersList[n].getChildren().add(iv);
			playersList[n].setPadding(new Insets(0,0,20,0));
		}else {
			Image img = new Image(this.getClass().getResourceAsStream("/images/start.png"));
			ImageView iv = new ImageView(img);
			iv.setFitWidth(100);
			iv.setPreserveRatio(true);
			start.getChildren().clear();
			start.getChildren().add(iv);
			start.setPadding(new Insets(600,0,0,350 - 50));
		}		
	}
	
	public void moveLeft(VBox[] list, int n) {
		TranslateTransition anim = new TranslateTransition();
		anim.setDuration(Duration.millis(100)); 
		anim.setNode(list[n]);
		anim.setByX(-100);	
		anim.play();
	}
	
	public void moveRight(VBox[] list, int n) {
		TranslateTransition anim = new TranslateTransition();
		anim.setDuration(Duration.millis(100)); 
		anim.setNode(list[n]);
		anim.setByX(100);	
		anim.play();
	}
}
