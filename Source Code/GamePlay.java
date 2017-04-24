public class GamePlay {
	
	//Attributes - Attacking
	private int attackModifier;
	
	public static void main(String[] args) {
		Deck deck = new Deck();
		
		//Test Die.roll()
		for(int i = 0; i < 50; i++){
			System.out.println(Die.roll());
		}
		
		//System.out.println(deck.toString());
	}
	
	
	
	//-----------------------------------------Attacking--------------------------------------------
	
	//Call this method to start the attack
	public void attack(){
		Global.AttackType attackType = promptAttackType();
		StructureCard attackingGroup = promptAttackingGroup();
		GroupCard defendingGroup;
		
		
		//Select defending group, with checks based on attack type
		if(attackType == Global.AttackType.CONTROL){
			defendingGroup = promptDefendingGroupControl();
		}else if(attackType == Global.AttackType.NEUTRALIZE){
			defendingGroup = promptDefendingGroupNeutralize();
		}else{		//Global.AttackType.DESTROY
			defendingGroup = promptDefendingGroupDestroy();
		}
		
		//Change attack modifier based on alignments of attacking and defending groups
		if(attackingGroup instanceof GroupCard){
			attackModifier += ((GroupCard) attackingGroup).CalculateAlignmentModifier(defendingGroup, attackType);
		}
		
		//Change attack modifier based on power and resistance
		attackModifier += attackingGroup.CalculatePowerModifier(defendingGroup, attackType);
		
		//TODO - Add BFS to calculate attack modifier based on power structure position
		
		//TODO - Calculate power structure's "Any Attempt" abilities
		
		//Calculate attack modifier based on "direct control" ability
		attackModifier += ((GroupCard) attackingGroup).CalculateDirectControlAbility(defendingGroup, attackType);
		
		//NEXT - Chinese Campaign Donors? Or go back and look at extensions
	}
	
	private Global.AttackType promptAttackType(){
		//TODO - Add logic to display pop-up with the three attack options
		//Return based on player's response.
		return Global.AttackType.CONTROL;
	}
	
	private StructureCard promptAttackingGroup(){
		//TODO - Add logic to select an attacking group based on cards in structure
		return null;
	}
	
	private GroupCard promptDefendingGroupControl(){
		//TODO - Add logic to select a defending group based on cards in play for an attack to control
		return null;
	}
	
	private GroupCard promptDefendingGroupNeutralize(){
		//TODO - Add logic to select a defending group based on cards in play for an attack to neutralize
		return null;
	}
	
	private GroupCard promptDefendingGroupDestroy(){
		//TODO - Add logic to select a defending group based on cards in play for an attack to destroy
		return null;
	}

}
