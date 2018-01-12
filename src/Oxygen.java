import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Oxygen {

	private int level, total, stock = 2;
	private boolean empty = false;
	
	Oxygen(){
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getStock() {
		return this.stock;
	}
	
	public void setStock(int i) {
		this.stock=i;
	}
	
	public void removeOxygen(VBox[] r, int n) {
		for(int i = 0; i < r.length; i++) {
			if((i  >= this.level) && (i < this.level + n)){
				FadeTransition ft = new FadeTransition(Duration.millis(1000));
				ft.setFromValue(1.0);
				ft.setToValue(0.0);
				ft.setNode(r[i]);
				ft.play();
			}
		}
		if(this.level <= 0) {
			this.setEmpty(true);
		}
	}
	
	public void resetBar(VBox[] r) {
		for(int i = 1; i < r.length; i++) {			
			FadeTransition ft = new FadeTransition(Duration.millis(1000));
			ft.setFromValue(0.0);
			ft.setToValue(1.0);
			ft.setNode(r[i]);
			ft.play();
		}
	}
	
	public void resetStock(HBox b) {
		Image oxygenImg = new Image(this.getClass().getResourceAsStream("/images/oxygen" + this.getStock() + ".png"));
	    ImageView oxygenIv = new ImageView(oxygenImg);  
	    b.getChildren().clear();
	    b.getChildren().add(oxygenIv);
	}
}