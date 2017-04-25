import java.util.ArrayList;

public class Player {
	//Class Attributes
	private String name;
	private IlluminatiCard illuminati;
	private ArrayList<GroupCard> groupCards;
	private ArrayList<SpecialCard> specialCards;
	
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
	
	//Constructor Method
	public Player(String name){
		this.name = name;
		groupCards = new ArrayList<GroupCard>();
		specialCards = new ArrayList<SpecialCard>();
	}
	
	//Adds Illuminati card to variable, sets Illuminati's owner to this player
	public void SetIlluminati(IlluminatiCard illuminatiCard){
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
}
