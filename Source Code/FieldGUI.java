import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class FieldGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private final int PANEL_X = 6;
	private final int PANEL_Y = 6;
	private final int PANEL_WIDTH = 1043;
	private final int PANEL_HEIGHT = 577;
	
	//Grid variables
	private final int GRID_MAX_HEIGHT = 19;
	private final int GRID_MAX_WIDTH = 35;
	private final double WIDTH_UNIT = PANEL_WIDTH / GRID_MAX_WIDTH;
	private final double HEIGHT_UNIT = PANEL_HEIGHT / GRID_MAX_HEIGHT;
	//private int[][] grid;
	protected HashMap<CardGUI, Rectangle> cards;

	public FieldGUI(){
		super();
		
		this.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		this.setBackground(new Color(255, 255, 240));
		this.setBounds(PANEL_X, PANEL_Y, PANEL_X + PANEL_WIDTH, PANEL_Y + PANEL_HEIGHT);
		this.setLayout(null);
		
		cards = new HashMap<CardGUI, Rectangle>();
		
		//grid = new int[GRID_MAX_WIDTH][GRID_MAX_HEIGHT];
	}
	
	//Displays all cards on the field
	public void displayCards(){
		for(CardGUI card: cards.keySet()){
			this.add(card);
			card.setLocation((int) (cards.get(card).x * WIDTH_UNIT), (int) (cards.get(card).y * HEIGHT_UNIT));
		}
	}
	
	//Checks if there is a collision with another card with top-left point p
	//If not, places the card at top-left point p
	public void addCard(CardGUI card, Point p){
		if(p.x <= GRID_MAX_WIDTH - 6 && p.y <= GRID_MAX_HEIGHT - 4){
			if(!checkCardCollision(p)){
				cards.put(card, new Rectangle(p.x, p.y, 5, 3));
			}
		}
	}
	
	//Checks if a card overlaps another card, with point P as the top-left
	//This means a card cannot be placed there
	//Returns true if there is a collision, false otherwise
	private boolean checkCardCollision(Point p){
		if(checkPointCollision(new Point(p.x, p.y)) || checkPointCollision(new Point(p.x, p.y + 3)) ||
		   checkPointCollision(new Point(p.x + 5, p.y)) || checkPointCollision(new Point(p.x + 5, p.y + 3))){
			return true;
		}
		
		return false;
	}
	
	//Checks if a card already occupies the point P
	//Returns true if there is a collision, false otherwise
	private boolean checkPointCollision(Point p){
		boolean returnVal = false;
		
		for(Rectangle rect: cards.values()){
			returnVal = rect.contains(p);
			if(returnVal){
				break;
			}
		}
		
		return returnVal;
	}

}
