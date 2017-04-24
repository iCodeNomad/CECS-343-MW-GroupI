import java.util.ArrayList;

public class GroupCard extends StructureCard implements MainDeckCard{
	
	public enum Alignment{
		GOVERNMENT, COMMUNIST, LIBERAL, CONSERVATIVE, PEACEFUL, VIOLENT, STRAIGHT, WEIRD, CRIMINAL, FANATIC;
	}
	
	//Class Attributes
	private ArrayList<Alignment> alignments;
	protected StructureCard parent;
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

	//Constructor Methods
	public GroupCard(String name, int power, int transferablePower, int resistance, int income, 
					 String imagePath, Arrow inwardArrow, ArrayList<Arrow> outwardArrows) {
		
		super(name, power, transferablePower, income, imagePath, inwardArrow);
		
		this.outwardArrows = outwardArrows;
		this.resistance = resistance;
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
		for(BasicGroupAbility i: this.abilities){
			if (i.abilityType == Global.AbilityType.DIRECT_CONTROL && attackType == Global.AttackType.CONTROL){
				for(GroupCard j: i.groups){
					if(j == defendingGroup){
						return i.amount;
					}
				}
				
				for(Alignment j: defendingGroup.alignments){
					if(j == i.alignment){
						return i.amount;
					}
				}
			}
		}
		
		return 0;
	}

	@Override
	public String toString() {
		return "GroupCard [name=" + name + ", power=" + power + ", transferablePower=" + transferablePower + ", income=" + income
				+ ", imagePath=" + imagePath + ", inwardArrow=" + inwardArrow + ", outwardArrows=" + outwardArrows
				+ ", children=" + children + ", alignments=" + alignments + ", parent=" + parent
				+ ", abilities=" + abilities + "]"+ System.getProperty("line.separator");
	}
	
	
}
