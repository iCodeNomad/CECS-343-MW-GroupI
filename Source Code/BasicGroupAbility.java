import java.util.ArrayList;

//This will allow us to input most of the group card abilities.
//This includes "any attempt" and "direct control" abilities.
//The rest can be implemented outside of this class.

public class BasicGroupAbility{
	
	private Global.AbilityType abilityType;
	private ArrayList<GroupCard> groups;
	private Global.AttackType attackType;
	private GroupCard.Alignment alignment;
	private int amount;
	
	
	public Global.AbilityType AbilityType() {
		return abilityType;
	}

	public ArrayList<GroupCard> Groups() {
		return groups;
	}

	public Global.AttackType AttackType() {
		return attackType;
	}

	public GroupCard.Alignment Alignment() {
		return alignment;
	}

	public int Amount() {
		return amount;
	}

	//Constructors
	public BasicGroupAbility(Global.AbilityType abilityType, Global.AttackType attackType, int amount){
		this.abilityType = abilityType;
		this.attackType = attackType;
		this.amount = amount;
	}
	
	public BasicGroupAbility(Global.AbilityType abilityType, ArrayList<GroupCard> groups, Global.AttackType attackType, int amount){
		this(abilityType, attackType, amount);
		
		this.groups = groups;
	}
	
	public BasicGroupAbility(Global.AbilityType abilityType, GroupCard group, Global.AttackType attackType, int amount){
		this(abilityType, attackType, amount);
		
		this.groups = new ArrayList<GroupCard>();
		this.groups.add(group);
	}
	
	public BasicGroupAbility(Global.AbilityType abilityType, GroupCard.Alignment alignment, Global.AttackType attackType, int amount){
		this(abilityType, attackType, amount);
		
		this.alignment = alignment;
	}

	@Override
	public String toString() {
		return "BasicGroupAbility [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + ", abilityType=" + abilityType + ", groups=" + groups + ", attackType="
				+ attackType + ", alignment=" + alignment + ", amount=" + amount + "]";
	}
	
	
}
