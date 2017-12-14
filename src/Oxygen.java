import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

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
	
	public void removeOxygen(Rectangle r, int i) {
		int height = 500;
		int amount = height / this.total;
		int remove = (i * amount) + amount;
		r.setHeight(r.getHeight() - remove);
		r.setY(r.getY() + remove);
		if(this.level <= 0) {
			this.setEmpty(true);
		}
	}
	
	public void resetBar(Rectangle r) {
		r.setHeight(500);
		r.setY(95);
	}
	
	public void resetStock(HBox b) {
		Image oxygenImg = new Image(this.getClass().getResourceAsStream("/images/oxygen" + this.getStock() + ".png"));
	    ImageView oxygenIv = new ImageView(oxygenImg);  
	    b.getChildren().clear();
	    b.getChildren().add(oxygenIv);
	}
}