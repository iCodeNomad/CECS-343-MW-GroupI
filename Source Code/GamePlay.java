import java.awt.EventQueue;
import java.util.ArrayList;

public class GamePlay {
	
	//Attributes - Attacking
	private int attackModifier;
	
	private static GUI window;
	
	public static void main(String[] args) {
		Deck deck = new Deck();
		
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new GUI();
					window.Frame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		
		window = new GUI();
		
		Player player = new Player("Tester", window);
	}
	
	
	
	//-----------------------------------------Attacking--------------------------------------------
	
	//Call this method to start the attack
	public void attack(Player attackingPlayer){
		Global.AttackType attackType = attackingPlayer.GUI().AttackTypePopup();
		StructureCard attackingGroup = PromptAttackingGroup();
		GroupCard defendingGroup;
		
		ArrayList<GroupCard> aidingGroups;
		
		Global.Privilege privilege = Global.Privilege.NOT_PRIVILEGED;
		
		//Select defending group, with checks based on attack type
		if(attackType == Global.AttackType.CONTROL){
			defendingGroup = PromptDefendingGroupControl();
		}else if(attackType == Global.AttackType.NEUTRALIZE){
			defendingGroup = PromptDefendingGroupNeutralize();
		}else{		//Global.AttackType.DESTROY
			defendingGroup = PromptDefendingGroupDestroy();
		}
		
		//Change attack modifier based on power and resistance
		attackModifier += attackingGroup.CalculatePowerModifier(defendingGroup, attackType);
		
		//For group cards only
		if(attackingGroup instanceof GroupCard){
			//Change attack modifier based on alignments of attacking and defending groups
			attackModifier += ((GroupCard) attackingGroup).CalculateAlignmentModifier(defendingGroup, attackType);
			
			//Check for Gun Lobby ability
			attackModifier += ((GroupCard) attackingGroup).GunLobbyCheck(defendingGroup);
					
			//Calculate attack modifier based on "direct control" ability
			attackModifier += ((GroupCard) attackingGroup).CalculateDirectControlAbility(defendingGroup, attackType);
			
			//TODO - Add BFS to calculate attack modifier based on power structure position
		}		
		
		//Calculate "Any Attempt" Abilities
		attackModifier += CalculateAnyAttempt(attackingPlayer, defendingGroup, attackType);
		
		//Check for Chinese Campaign Donors special ability
		attackModifier += CheckChineseCampaignDonors(defendingGroup);
		
		//Calculates "Survivalists" ability
		attackModifier += CheckSurvivalistsAbility(defendingGroup.Owner());
		
		//TODO - Add groups to aid attack.
		aidingGroups = PromptAidingGroups();
		
		//Maybe combine this with PromptAidingGroups().
		//Calculates the effect of transferable power
		attackModifier += CalculateAidingGroups(aidingGroups);
		
		//TODO - Bavarian Illuminati Special Attack
		privilege = BavarianIlluminatiSpecial();
		
		//TODO - Discard Special Card for Privilege
		if(privilege != Global.Privilege.PRIVILEGED){
			privilege = DiscardForPrivilege();
		}
		
		//TODO - Deep Agent
		if(privilege == Global.Privilege.PRIVILEGED){
			//Add code for Deep Agent
		}
		
		//TODO - Discard two cards to abolish privilege
		if(privilege == Global.Privilege.PRIVILEGED){
			privilege = DiscardForAbolish();
		}
		
		//Spending phase of attack
		
	}
	
	private StructureCard PromptAttackingGroup(){
		//TODO - Add logic to select an attacking group based on cards in structure
		return null;
	}
	
	private GroupCard PromptDefendingGroupControl(){
		//TODO - Add logic to select a defending group based on cards in play for an attack to control
		//REMEMBER TO ADD CHECKS FOR DISCORDIAN SOCIETY
		return null;
	}
	
	private GroupCard PromptDefendingGroupNeutralize(){
		//TODO - Add logic to select a defending group based on cards in play for an attack to neutralize
		//REMEMBER TO ADD CHECKS FOR DISCORDIAN SOCIETY
		return null;
	}
	
	private GroupCard PromptDefendingGroupDestroy(){
		//TODO - Add logic to select a defending group based on cards in play for an attack to destroy
		//REMEMBER TO ADD CHECKS FOR DISCORDIAN SOCIETY
		return null;
	}
	
	private int CalculateAnyAttempt(Player attackingPlayer, GroupCard defendingGroup, Global.AttackType attackType){
		int returnVal = 0;
		
		for(GroupCard card: attackingPlayer.GroupCards()){
			for(BasicGroupAbility ability: card.Abilities()){
				if(ability.AbilityType() == Global.AbilityType.ANY_ATTEMPT && (ability.AttackType() == attackType || ability.AttackType() == Global.AttackType.ALL)){
					for(GroupCard.Alignment alignment: defendingGroup.Alignments()){
						if(alignment == ability.Alignment()){
							returnVal += ability.Amount();
						}
					}
					
					for(GroupCard targetGroup: ability.Groups()){
						if(targetGroup == defendingGroup){
							returnVal += ability.Amount();
						}
					}
				}
			}
		}
		
		return returnVal;
	}
	
	private int CheckSurvivalistsAbility(Player defendingPlayer){
		for(GroupCard card: defendingPlayer.GroupCards()){
			if(card.Name().equals("Survivalists")){
				return -2;
			}
		}
		
		return 0;
	}
	
	private int CheckChineseCampaignDonors(GroupCard defendingGroup){
		//TODO - REMEMBER TO ADD CHECKS FOR DISCORDIAN SOCIETY
		if(defendingGroup.hasAlignment(GroupCard.Alignment.GOVERNMENT)){
			return 4;
		}
		return 0;
	}
	
	private ArrayList<GroupCard> PromptAidingGroups(){
		//TODO - Add logic to select aiding groups
		return null;
	}
	
	private int CalculateAidingGroups(ArrayList<GroupCard> aidingGroups){
		int aidingPower = 0;
		
		if(aidingGroups.isEmpty()){
			return 0;
		}
		for(GroupCard card:aidingGroups){
			aidingPower += card.transferablePower;
		}
		
		return aidingPower;
	}
	
	private Global.Privilege BavarianIlluminatiSpecial(){
		//TODO - Add logic to check if player is Bavarian Illuminati, prompt, response, 5MB, etc.
		return Global.Privilege.NOT_PRIVILEGED;
	}
	
	private Global.Privilege DiscardForPrivilege(){
		//TODO - Add logic to prompt player to discard special for privilege
		return Global.Privilege.NOT_PRIVILEGED;
	}
	
	private Global.Privilege DiscardForAbolish(){
		//TODO - Add logic to prompt player to discard specials to abolish privilege
		return Global.Privilege.NOT_PRIVILEGED;
	}

}
