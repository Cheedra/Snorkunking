import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
	
	public void setText(HBox[] l, int i) {
		Paint[] list = new Paint[4];
		  list[0] = Color.BLUE;
		  list[1] = Color.CRIMSON;
		  list[2] = Color.GREEN;
		  list[3] = Color.YELLOW;
		Text txt = new Text("" + this.getValue() + "");
		txt.setFill(list[this.getId()]);
		txt.setStroke(Color.BLACK);
    	txt.setStrokeWidth(0.5);
    	txt.setFont(Font.font(18));
		l[i].getChildren().clear();
		l[i].getChildren().add(txt);
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