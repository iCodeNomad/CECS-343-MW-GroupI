import java.util.ArrayList;

public class Deck {

	private ArrayList<MainDeckCard> deck;
	
	public Deck() {
		deck = new ArrayList<MainDeckCard>();
		
		//The Mafia
		BasicGroupAbility ability = new BasicGroupAbility(Global.AbilityType.DIRECT_CONTROL, GroupCard.Alignment.CRIMINAL, Global.AttackType.CONTROL, 3);
		ArrayList<GroupCard.Arrow> outwardArrows = new ArrayList<GroupCard.Arrow>();
		outwardArrows.add(GroupCard.Arrow.TOP);
		outwardArrows.add(GroupCard.Arrow.LEFT);
		outwardArrows.add(GroupCard.Arrow.BOTTOM);
		GroupCard groupCard = new GroupCard("The Mafia", 7, 0, 7, 6, "img/TheMafia.png", GroupCard.Arrow.RIGHT, outwardArrows, GroupCard.Alignment.CRIMINAL, ability);				
				//public GroupCard(String name, int power, int transferablePower, int resistance, int income, 
				//		 String imagePath, Arrow inwardArrow, ArrayList<Arrow> outwardArrows, Alignment alignment, BasicGroupAbility ability)
	}

}
