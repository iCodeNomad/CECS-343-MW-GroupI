import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CardGUI extends JLabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Card card;
	
	public Card Card(){
		return this.card;
	}

	public CardGUI(Card card) {
		this.card = card;
	}

}
