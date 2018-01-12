import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

public class Score {

	private int value = 0, id;
	private boolean hidden = true;
	
	public Score(int id) {
		this.id = id;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void hideText(HBox[] l, int i) {
		Insets current = l[i].getInsets();
		Insets next = new Insets(current.getTop() - 100, current.getRight(), current.getBottom(), current.getLeft());
		l[i].setPadding(next);
	}
	
	public void showText(HBox[] l, int i) {
		Insets current = l[i].getInsets();
		Insets next = new Insets(current.getTop() + 100, current.getRight(), current.getBottom(), current.getLeft());
		l[i].setPadding(next);
	}
}