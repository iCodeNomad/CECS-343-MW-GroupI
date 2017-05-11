import java.util.ArrayList;

public class Player {
	//Class Attributes
	private String name;
	private int playerNum;
	private IlluminatiCard illuminati;
	private ArrayList<GroupCard> groupCards;
	private ArrayList<SpecialCard> specialCards;
	private GUI gui;
	
	//Accessor Methods
	public String Name(){
		return this.name;
	}
	
	public IlluminatiCard Illuminati(){
		return this.illuminati;
	}
	
	public ArrayList<GroupCard> GroupCards(){
		return this.groupCards;
	}
	
	public ArrayList<SpecialCard> SpecialCards(){
		return this.specialCards;
	}
	
	public int NumberOfSpecials(){
		return specialCards.size();
	}
	
	public GUI GUI(){
		return this.gui;
	}
	
	public int PlayerNum(){
		return this.playerNum;
	}
	
	//Constructor Method
	public Player(String name, GUI gui, int playerNum){
		this.name = name;
		this.gui = gui;
		this.playerNum = playerNum;
		
		SetIlluminati(GamePlay.deck.drawIlluminati());
		
		gui.player = this;
		
		groupCards = new ArrayList<GroupCard>();
		specialCards = new ArrayList<SpecialCard>();
	}
	
	//Adds Illuminati card to variable, sets Illuminati's owner to this player
	private void SetIlluminati(IlluminatiCard illuminatiCard){
		if(this.illuminati == null){
			this.illuminati = illuminatiCard;
			illuminatiCard.owner = this;
		}
	}
	
	//Adds group card to array list, sets group card's owner to this player
	public void AddGroupCard(GroupCard groupCard){
		groupCards.add(groupCard);
		groupCard.owner = this;
	}
	
	public void RemoveGroupCard(GroupCard groupCard){
		groupCards.remove(groupCard);
	}
	
	public void RemoveSpecialCard(SpecialCard specialCard){
		specialCards.remove(specialCard);
	}
	
	//Player draws a card from the deck
	//Card is added to player's hand
	public MainDeckCard DrawCard(Deck deck){
		MainDeckCard card = deck.drawCard();
		
		if(card instanceof GroupCard){
			//TODO - Change this so it doesn't add a group to the player's hand
			GamePlay.uncontrolledGroups.add((GroupCard) card);
			GUI.uncontrolledGUI.update();
		}else{
			specialCards.add((SpecialCard) card);
		}
		
		return card;
	}
		
		
}
