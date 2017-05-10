import java.util.ArrayList;
import java.util.HashMap;

public abstract class StructureCard extends Card{
	public static enum Arrow{
		TOP, BOTTOM, LEFT, RIGHT;
	}
	
	public static enum Rotation{
		UP, RIGHT, DOWN, LEFT;
	}
	
	//Class Attributes
	protected int power;
	protected int transferablePower;
	protected int income;
	protected Arrow inwardArrow;
	protected ArrayList<Arrow> outwardArrows;
	protected HashMap<Arrow, GroupCard> children = new HashMap<Arrow, GroupCard>();
	protected Player owner;
	protected int attackCounter;
	protected int currentMoney;
	protected Rotation rotation;
	protected int distanceFromIlluminati;
	
	//Accessor Methods
	
	public int Power(){
		return this.power;
	}
	
	public int TransferablePower(){
		return this.transferablePower;
	}
	
	public int Income(){
		return this.income;
	}
	
	public String ImagePath(){
		return this.imagePath;
	}
	
	public Arrow InwardArrow() {
		return this.inwardArrow;
	}

	public ArrayList<Arrow> OutwardArrows() {
		return this.outwardArrows;
	}
	
	public HashMap<Arrow, GroupCard> Children(){
		return this.children;
	}
	
	public Player Owner(){
		return this.owner;
	}
	
	public int AttackCounter(){
		return this.attackCounter;
	}
	
	public int CurrentMoney(){
		return this.currentMoney;
	}
	
	public Rotation Rotation(){
		return this.rotation;
	}
	
	public int DistanceFromIlluminati() {
		return distanceFromIlluminati;
	}

	public void setDistanceFromIlluminati(int distanceFromIlluminati) {
		this.distanceFromIlluminati = distanceFromIlluminati;
	}
	
	public void addMoney(int amount){
		currentMoney += amount;
		Bank.removeMoney(amount);
	}
	
	public void removeMoney(int amount){
		if((currentMoney - amount) >= 0){
			currentMoney -= amount;
			Bank.addMoney(amount);
		}
	}

	//Constructor Method
	public StructureCard(String name, int power, int transferablePower, int income, String imagePath,
						  Arrow inwardArrow){
		super(name, imagePath);
		
		this.power = power;
		this.transferablePower = transferablePower;
		this.income = income;
		this.inwardArrow = inwardArrow;
		
		distanceFromIlluminati = 0;
	}
	
	
	//Add child group card
	//Checks if the control arrow exists and is available, then adds the new child card to children.
	//Also sets the child's parent to this card.
	//Returns true if the arrow exists and is available, false otherwise.
	public boolean AddChild(GroupCard card, Arrow arrow){
		if(outwardArrows.indexOf(arrow) >= 0){
			if(children.containsKey(arrow)){
				return false;
			}
			else{
				children.put(arrow, card);
				card.parent = this;
				card.parentArrow = arrow;
				
				card.rotation = card.calculateRotation();
				
				return true;
			}
		}
		return false;
	}
	
	//Remove child group card
	//If a group is at a given control arrow, it is removed from children and true is returned.
	//Also removes this as the child's parent.
	//Otherwise, returns false.
	public boolean RemoveChild(Arrow arrow){
		if(children.containsKey(arrow)){
			GroupCard child = children.remove(arrow);
			child.parent = null;
			
			return true;
		}
		return false;
	}
	
	public boolean RemoveChild(GroupCard card){
		if(children.containsValue(card)){
			for(Arrow arrow: outwardArrows){
				if(children.get(arrow) == card){
					children.remove(arrow);
					return true;
				}
			}
		}
		
		return false;
	}
	
	//Calculates the modifier based on attacker power and defender resistance (or defender power if attack to destroy)
	public int CalculatePowerModifier(GroupCard defendingGroup, Global.AttackType attackType){
		//Modifier for Society of Assassins and Servants of Cthulhu
		int modifier = 0;
		
		if(attackType == Global.AttackType.DESTROY){
			if(this.owner.Illuminati().Name() == "The Servants of Cthulhu"){
				modifier = 2;
			}
			
			//TODO - This occurs when Whispering Campaign is active. Make sure this is checked somewhere!
			if(defendingGroup.Power() == 0){
				return this.Power() + modifier - defendingGroup.Resistance();
			}else{
				return this.Power() + modifier - defendingGroup.Power();
			}
		}else if(attackType == Global.AttackType.NEUTRALIZE){
			if(this.owner.Illuminati().Name() == "The Society of Assassins"){
				modifier = 4;
			}
			return this.Power() + 6 + modifier - defendingGroup.Resistance();
		}else{				//ATTACK TO CONTROL
			return this.Power() - defendingGroup.Resistance();
		}
	}

	@Override
	public String toString(){
		return this.Name();
	}
	
	
}
