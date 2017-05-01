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
	protected HashMap<CardGUI, Rectangle> cards;
	private CardGUI illuminati;

	public FieldGUI(CardGUI illuminati){
		super();
		
		this.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		this.setBackground(new Color(255, 255, 240));
		this.setBounds(PANEL_X, PANEL_Y, PANEL_X + PANEL_WIDTH, PANEL_Y + PANEL_HEIGHT);
		this.setLayout(null);
		
		cards = new HashMap<CardGUI, Rectangle>();
		this.illuminati = illuminati;
		addIlluminati();
	}
	
	//Displays all cards on the field
	public void displayCards(){
		for(CardGUI card: cards.keySet()){
			this.add(card);
			card.setLocation((int) (cards.get(card).x * WIDTH_UNIT), (int) (cards.get(card).y * HEIGHT_UNIT));
		}
	}
	
	private void addIlluminati(){
		cards.put(illuminati,  new Rectangle(15, 8, 5, 3));
	}
	
	//public boolean addCard(CardGUI card, Point p){
		/*if(p.x <= GRID_MAX_WIDTH - 5 && p.y <= GRID_MAX_HEIGHT - 3){
			if(!checkCardCollision(p)){
				cards.put(card, new Rectangle(p.x, p.y, 5, 3));
			}
		}*/
	
	//Checks if there is a collision with another card with top-left point p
	//If not, places the card at top-left point p
	//Returns true if successful, false otherwise
	public boolean addCard(CardGUI child, CardGUI parent){
		GroupCard sChild = (GroupCard) child.Card();
		StructureCard sParent = (StructureCard) parent.Card();
		
		int angle = 0;
		
		parent = containsCard(parent.Card());
		System.out.println(parent.Card().Name());
		
		if(parent != null){
			if(sParent.Rotation() == StructureCard.Rotation.RIGHT){
				angle += 90;
			}else if(sParent.Rotation() == StructureCard.Rotation.DOWN){
				angle += 180;
			}else if(sParent.Rotation() == StructureCard.Rotation.LEFT){
				angle += 270;
			}
			
			if(sChild.ParentArrow() == StructureCard.Arrow.LEFT){
				angle += 90;
			}else if(sChild.ParentArrow() == StructureCard.Arrow.TOP){
				angle += 180;
			}else if(sChild.ParentArrow() == StructureCard.Arrow.RIGHT){
				angle += 270;
			}
			
			int x = cards.get(parent).x;
			int y = cards.get(parent).y;
			angle = angle % 360;
			
			if(sChild.Rotation() == StructureCard.Rotation.UP || sChild.Rotation() == StructureCard.Rotation.DOWN){
				if(sParent.Rotation() == StructureCard.Rotation.UP || sParent.Rotation() == StructureCard.Rotation.DOWN){
					if(angle == 0){
						cards.put(child, new Rectangle(x, y + 3, 5, 3));
					}else if (angle == 90){
						cards.put(child, new Rectangle(x - 5, y, 5, 3));
					}else if (angle == 180){
						cards.put(child, new Rectangle(x, y - 3, 5, 3));
					}else{
						cards.put(child, new Rectangle(x + 5, y, 5, 3));
					}
				}else{
					if(angle == 0){
						cards.put(child, new Rectangle(x - 1, y + 5, 5, 3));
					}else if (angle == 90){
						cards.put(child, new Rectangle(x - 5, y - 1, 5, 3));
					}else if (angle == 180){
						cards.put(child, new Rectangle(x - 1, y - 3, 5, 3));
					}else{
						cards.put(child, new Rectangle(x + 3, y + 1, 5, 3));
					}
				}
			}else{
				if(sParent.Rotation() == StructureCard.Rotation.UP || sParent.Rotation() == StructureCard.Rotation.DOWN){
					if(angle == 0){
						cards.put(child, new Rectangle(x + 1, y + 3, 3, 5));
					}else if (angle == 90){
						cards.put(child, new Rectangle(x - 3, y - 1, 5, 3));
					}else if (angle == 180){
						cards.put(child, new Rectangle(x + 1, y - 5, 5, 3));
					}else{
						cards.put(child, new Rectangle(x + 5, y - 1, 5, 3));
					}
				}else{
					if(angle == 0){
						cards.put(child, new Rectangle(x, y + 5, 3, 5));
					}else if (angle == 90){
						cards.put(child, new Rectangle(x - 3, y, 5, 3));
					}else if (angle == 180){
						cards.put(child, new Rectangle(x, y - 5, 5, 3));
					}else{
						cards.put(child, new Rectangle(x + 3, y, 5, 3));
					}
				}
			}
			
			System.out.println(cards.get(child));
		}
		
		displayCards();
		
		return false;
	}
	
	//Checks if a given card is in the field
	private CardGUI containsCard(Card card){
		CardGUI returnVal = null;
		
		for(CardGUI c: cards.keySet()){
			if (card.Name() == c.Card().Name()){
				returnVal = c;
			}
		}
		
		return returnVal;
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
