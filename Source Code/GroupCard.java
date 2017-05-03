import java.util.ArrayList;

public class GroupCard extends StructureCard implements MainDeckCard{
	
	public enum Alignment{
		GOVERNMENT, COMMUNIST, LIBERAL, CONSERVATIVE, PEACEFUL, VIOLENT, STRAIGHT, WEIRD, CRIMINAL, FANATIC;
	}
	
	//Class Attributes
	private ArrayList<Alignment> alignments;
	protected StructureCard parent;
	protected StructureCard.Arrow parentArrow;
	protected int resistance;
	
	//BasicGroupAbility needs to be in an array list because one card has two different abilities with different amounts.
	protected ArrayList<BasicGroupAbility> abilities;
	
	//Accessor Methods
	public ArrayList<Alignment> Alignments(){
		return this.alignments;
	}
	
	public StructureCard Parent(){
		return this.parent;
	}
	
	public ArrayList<BasicGroupAbility> Abilities(){
		return this.abilities;
	}
	
	public int Resistance(){
		return this.resistance;
	}
	
	public StructureCard.Arrow ParentArrow(){
		return this.parentArrow;
	}

	//Constructor Methods
	public GroupCard(String name, int power, int transferablePower, int resistance, int income, 
					 String imagePath, Arrow inwardArrow, ArrayList<Arrow> outwardArrows) {
		
		super(name, power, transferablePower, income, imagePath, inwardArrow);
		
		this.outwardArrows = outwardArrows;
		this.resistance = resistance;
		this.rotation = Rotation.UP;
	}
	
	public GroupCard(String name, int power, int transferablePower, int resistance, int income, 
			 String imagePath, Arrow inwardArrow, ArrayList<Arrow> outwardArrows, ArrayList<Alignment> alignments) {
		
		this(name, power, transferablePower, resistance, income, imagePath, inwardArrow, outwardArrows);
		
		this.alignments = alignments;
	}
	
	public GroupCard(String name, int power, int transferablePower, int resistance, int income, 
			 String imagePath, Arrow inwardArrow, ArrayList<Arrow> outwardArrows, Alignment alignment) {
		
		this(name, power, transferablePower, resistance, income, imagePath, inwardArrow, outwardArrows);
		
		this.alignments = new ArrayList<Alignment>();
		this.alignments.add(alignment);
	}
	
	public GroupCard(String name, int power, int transferablePower, int resistance, int income, 
			 String imagePath, Arrow inwardArrow, ArrayList<Arrow> outwardArrows, Alignment alignment, ArrayList<BasicGroupAbility> abilities) {
		
		this(name, power, transferablePower, resistance, income, imagePath, inwardArrow, outwardArrows, alignment);
		
		this.abilities = abilities;
	}
	
	public GroupCard(String name, int power, int transferablePower, int resistance, int income, 
			 String imagePath, Arrow inwardArrow, ArrayList<Arrow> outwardArrows, Alignment alignment, BasicGroupAbility ability) {
		
		this(name, power, transferablePower, resistance, income, imagePath, inwardArrow, outwardArrows, alignment);
		
		this.abilities = new ArrayList<BasicGroupAbility>();
		this.abilities.add(ability);
	}
	
	public GroupCard(String name, int power, int transferablePower, int resistance, int income, 
			 String imagePath, Arrow inwardArrow, ArrayList<Arrow> outwardArrows, ArrayList<Alignment> alignments, BasicGroupAbility ability) {
		
		this(name, power, transferablePower, resistance, income, imagePath, inwardArrow, outwardArrows, alignments);
		
		this.abilities = new ArrayList<BasicGroupAbility>();
		this.abilities.add(ability);
	}
	
	//Calculates the rotation of the card based on its parent and the parent arrow it is connected to.
	public Rotation calculateRotation(){
		Rotation parentRotation = this.parent.Rotation();
		StructureCard.Arrow parentArrow = this.parentArrow;
		
		int rotationCalc = 0;
		
		if(parentRotation == Rotation.RIGHT){
			rotationCalc += 90;
		}else if(parentRotation == Rotation.DOWN){
			rotationCalc += 180;
		}else if(parentRotation == Rotation.LEFT){
			rotationCalc += 270;
		}
		
		if(parentArrow == Arrow.LEFT){
			rotationCalc += 90;
		}else if(parentArrow == Arrow.TOP){
			rotationCalc += 180;
		}else if(parentArrow == Arrow.RIGHT){
			rotationCalc += 270;
		}
		
		if(this.inwardArrow == Arrow.LEFT){
			rotationCalc += 90;
		}else if(this.inwardArrow == Arrow.BOTTOM){
			rotationCalc += 180;
		}else if(this.inwardArrow == Arrow.RIGHT){
			rotationCalc += 270;
		}
		
		if((rotationCalc % 360) == 0){
			return Rotation.UP;
		}else if((rotationCalc % 360) == 90){
			return Rotation.RIGHT;
		}else if((rotationCalc % 360) == 180){
			return Rotation.DOWN;
		}else{ //rotationCalc % 360 == 270
			return Rotation.LEFT;
		}
	}
	
	
	//Returns an attack modifier adjustment based on alignments of this card and the given card
	//This card is considered the attacking group
	public int CalculateAlignmentModifier(GroupCard defendingGroup, Global.AttackType attackType){
		int attackModifier = 0;
		
		for(Alignment i: this.alignments){
			for(Alignment j: defendingGroup.alignments){
				if(i == j){
					if(attackType == Global.AttackType.DESTROY){
						attackModifier -= 4;
					}else{
						attackModifier += 4;
					}
				}else if(isOppositeAlignment(i, j)){
					if(attackType == Global.AttackType.DESTROY){
						attackModifier += 4;
					}else{
						attackModifier -= 4;
					}
				}
			}
		}
		
		return attackModifier;
	}
	
	//Returns true if the two alignments are opposites, false otherwise
	public boolean isOppositeAlignment(Alignment a, Alignment b){
		if(a == Alignment.GOVERNMENT && b == Alignment.COMMUNIST){
			return true;
		}else if(a == Alignment.COMMUNIST && b == Alignment.GOVERNMENT){
			return true;
		}else if(a == Alignment.LIBERAL && b == Alignment.CONSERVATIVE){
			return true;
		}else if(a == Alignment.CONSERVATIVE && b == Alignment.LIBERAL){
			return true;
		}else if(a == Alignment.PEACEFUL && b == Alignment.VIOLENT){
			return true;
		}else if(a == Alignment.VIOLENT && b == Alignment.PEACEFUL){
			return true;
		}else if(a == Alignment.STRAIGHT && b == Alignment.WEIRD){
			return true;
		}else if(a == Alignment.WEIRD && b == Alignment.STRAIGHT){
			return true;
		}else if(a == Alignment.FANATIC && b == Alignment.FANATIC){
			return true;
		}else{
			return false;
		}
	}
	
	public int CalculateDirectControlAbility(GroupCard defendingGroup, Global.AttackType attackType){
		
		//CHANGE ME TO USE hasAlignment
		
		for(BasicGroupAbility i: this.abilities){
			if (i.AbilityType() == Global.AbilityType.DIRECT_CONTROL && attackType == Global.AttackType.CONTROL){
				for(GroupCard j: i.Groups()){
					if(j == defendingGroup){
						return i.Amount();
					}
				}
				
				if(defendingGroup.hasAlignment(i.Alignment())){
					return i.Amount();
				}
			}
		}
		
		return 0;
	}
	
	
	public int GunLobbyCheck(GroupCard defendingGroup){
		if(defendingGroup.name == "Gun Lobby"){
			if(this.hasAlignment(Alignment.COMMUNIST) || this.hasAlignment(Alignment.LIBERAL) || this.hasAlignment(Alignment.WEIRD)){
				return -7;
			}
		}
		return 0;
	}
	
	//Returns true if this card has alignment a
	public boolean hasAlignment(Alignment a){
		for(Alignment i:this.alignments){
			if(i == a){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "GroupCard [alignments=" + alignments + ", parent=" + parent + ", parentArrow=" + parentArrow
				+ ", resistance=" + resistance + ", abilities=" + abilities + ", power=" + power
				+ ", transferablePower=" + transferablePower + ", income=" + income + ", inwardArrow=" + inwardArrow
				+ ", outwardArrows=" + outwardArrows + ", children=" + children + ", owner=" + owner
				+ ", attackCounter=" + attackCounter + ", currentMoney=" + currentMoney + ", rotation=" + rotation
				+ ", name=" + name + ", imagePath=" + imagePath + "]";
	}
	
	
}
