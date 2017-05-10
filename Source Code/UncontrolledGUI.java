import java.awt.Color;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class UncontrolledGUI extends JPanel {
	
	private final int Y_ADJ = 90;
	private final int X_ADJ = 150;
	private final int PANEL_WIDTH = 1049;
	private final int PANEL_HEIGHT = 583;
	
	public ArrayList<CardGUI> cards = new ArrayList<CardGUI>();

	public UncontrolledGUI() {
		super();
		
		this.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		this.setBackground(new Color(255, 255, 240));
		this.setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		this.setLayout(null);
	}
	
	public void update(){
		int xOffset = 0;
		int yOffset = 10;
		cards = new ArrayList<CardGUI>();
		CardGUI newCard;
		
		this.removeAll();
		
		for(GroupCard card: GamePlay.uncontrolledGroups){
			newCard = new CardGUI(card, this, 200 + xOffset, yOffset);
			card.cardGUI = newCard;
			cards.add(newCard);
			this.add(newCard);
			
			yOffset += Y_ADJ;
			if(yOffset > 460){
				yOffset = 10;
				xOffset += X_ADJ;
			}
			
			System.out.println(card.Name());
		}
	}
}
