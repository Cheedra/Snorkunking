import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Stash {
	
	private int amount, id;
	private boolean closed = true;
	
	public Stash(int a, int i) {
		this.amount = a;
		this.id = i;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public boolean isClosed() {
		return closed;
	}
	
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void openStash(VBox[] list, int i) {
		Image img = new Image(this.getClass().getResourceAsStream("/images/stashchest"+i+"_open.png"));
		ImageView iv = new ImageView(img);
		iv.setFitWidth(30);
		iv.setPreserveRatio(true);
		list[i].getChildren().clear();
		list[i].getChildren().add(iv);
		Insets current = list[i].getPadding();
		Insets next = new Insets(current.getTop() - 18, current.getRight(), current.getBottom(), current.getLeft());
		list[i].setPadding(next);
	}
	
	public void closeStash(VBox[] list, int i) {
		Image img = new Image(this.getClass().getResourceAsStream("/images/stashchest"+i+".png"));
		ImageView iv = new ImageView(img);
		iv.setFitWidth(30);
		iv.setPreserveRatio(true);
		list[i].getChildren().clear();
		list[i].getChildren().add(iv);
		Insets current = list[i].getPadding();
		Insets next = new Insets(current.getTop() + 18, current.getRight(), current.getBottom(), current.getLeft());
		list[i].setPadding(next);
	}
}