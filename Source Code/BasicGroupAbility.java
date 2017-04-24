import java.util.ArrayList;

//This will allow us to input most of the group card abilities.
//This includes "any attempt" and "direct control" abilities.
//The rest can be implemented outside of this class.

public class BasicGroupAbility{
	
	public Global.AbilityType abilityType;
	public ArrayList<GroupCard> groups;
	public Global.AttackType attackType;
	public GroupCard.Alignment alignment;
	public int amount;
	
	
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
