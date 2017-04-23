import java.util.ArrayList;

public class GroupCard extends StructureCard implements MainDeckCard{
	
	public enum Alignment{
		GOVERNMENT, COMMUNIST, LIBERAL, CONSERVATIVE, PEACEFUL, VIOLENT, STRAIGHT, WEIRD, CRIMINAL, FANATIC;
	}
	
	//Class Attributes
	private ArrayList<Alignment> alignments;
	protected StructureCard parent;
	
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

	//Constructor Methods
	public GroupCard(String name, int power, int transferablePower, int resistance, int income, 
					 String imagePath, Arrow inwardArrow, ArrayList<Arrow> outwardArrows) {
		
		super(name, power, transferablePower, income, imagePath, inwardArrow);
		
		this.outwardArrows = outwardArrows;
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
}
