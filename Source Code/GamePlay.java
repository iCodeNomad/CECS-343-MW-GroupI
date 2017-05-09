import java.util.ArrayList;

import javax.swing.JOptionPane;

public class GamePlay {
	
	public enum SPENDING_TYPE{
		ATTACKING_PLAYER, DEFENDING_PLAYER, INTERFERING_PLAYER;
	}
	
	//Attributes - Attacking
	public static int attackModifier;
	public static StructureCard attackingGroup, defendingGroup;
	public static ArrayList<StructureCard> aidingGroups;
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static Global.Privilege privilege;
	public static Player attackingPlayer, defendingPlayer;

	private static GUI window;
	private static GUI window2;
	private static GUI window3;
	
	public static void main(String[] args) {
		Deck deck = new Deck();
		aidingGroups = new ArrayList<StructureCard>();
		
		window = new GUI();
		window2 = new GUI();
		window3 = new GUI();
		
		players.add(new Player("Tester", window));
		players.add(new Player("Tester2", window2));
		players.add(new Player("Tester3", window3));
		players.get(0).Illuminati().attackCounter = 1;
		players.get(1).Illuminati().attackCounter = 1;
		players.get(2).Illuminati().attackCounter = 1;
		
		System.out.println(players.get(0).Illuminati().toString());
		System.out.println(players.get(1).Illuminati().toString());
		System.out.println(players.get(2).Illuminati().toString());
	}
	
	
	
	//-----------------------------------------Attacking--------------------------------------------
	
	//Call this method to start the attack
	public static void attack(Player attackPlayer){
		attackingPlayer = attackPlayer;
		Global.AttackType attackType = attackingPlayer.GUI().AttackTypePopup();
		PromptAttackingGroup(attackingPlayer.GUI());
		
		System.out.println(attackingGroup);
		
		privilege = Global.Privilege.NOT_PRIVILEGED;
		
		//Select defending group, with checks based on attack type
		/*if(attackType == Global.AttackType.CONTROL){
			defendingGroup = PromptDefendingGroupControl();
		}else if(attackType == Global.AttackType.NEUTRALIZE){
			defendingGroup = PromptDefendingGroupNeutralize();
		}else{		//Global.AttackType.DESTROY
			defendingGroup = PromptDefendingGroupDestroy();
		}
		
		defendingPlayer = defendingGroup.Owner();
		
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
			
			//Uses BFS to calculate attack modifier based on power structure position
			attackModifier += ((GroupCard) defendingGroup).CalculateStructureResistance();
		}		
		
		//Calculate "Any Attempt" Abilities
		attackModifier += CalculateAnyAttempt(attackingPlayer, defendingGroup, attackType);
		
		//Check for Chinese Campaign Donors special ability
		attackModifier += CheckChineseCampaignDonors(defendingGroup);
		
		//Calculates "Survivalists" ability
		attackModifier += CheckSurvivalistsAbility(defendingGroup.Owner());
		GUI.outputAttackModifier(players);
		
		//Prompt to add groups to aid attack.
		PromptAidingGroups(attackingPlayer.GUI());
		
		//Calculates the effect of transferable power
		attackModifier += CalculateAidingGroups();*/
		GUI.outputAttackModifier(players);

		//Bavarian Illuminati Special Attack
		if(attackingPlayer.Illuminati().Name() == "The Bavarian Illuminati" && !attackingPlayer.Illuminati().SpecialUsed() && attackingPlayer.Illuminati().CurrentMoney() >= 5){
			privilege = BavarianIlluminatiSpecial();
		}
		
		//Discard Special Card for Privilege
		if(privilege != Global.Privilege.PRIVILEGED && attackingPlayer.SpecialCards().size() > 0){
			DiscardForPrivilege(attackingPlayer.GUI());
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
		attackModifier += SpendingPhase();
		
		//Stores if the attack is successful
		boolean attackSuccess = AttackRoll();
		
		if(attackSuccess){
			attackingPlayer.GUI().DialogBox("Attack was successful!");
			
			if(attackType == Global.AttackType.CONTROL){
				//TODO - Add logic for control (add group to power structure)
			}else if(attackType == Global.AttackType.NEUTRALIZE){
				//TODO - Add logic for neutralize
			}else{
				//TODO - Add logic for destroy
			}
		}else{
			attackingPlayer.GUI().DialogBox("Attack was unsuccessful...");
		}
	}
	
	private static void PromptAttackingGroup(GUI gui){
		gui.createPrompt("Please select an attacking group:");
		gui.endAttackButton();
		
		Global.selectionPhase = Global.SelectionPhase.SELECT_ATTACKING_GROUP;
		
		while(Global.selectionPhase == Global.SelectionPhase.SELECT_ATTACKING_GROUP){
			try{
				Thread.sleep(200);
			}catch(Exception e){}
		}
		
		gui.removePrompt();
	}
	
	private static GroupCard PromptDefendingGroupControl(){
		//TODO - Add logic to select a defending group based on cards in play for an attack to control
		//REMEMBER TO ADD CHECKS FOR DISCORDIAN SOCIETY
		return null;
	}
	
	private static GroupCard PromptDefendingGroupNeutralize(){
		//TODO - Add logic to select a defending group based on cards in play for an attack to neutralize
		//REMEMBER TO ADD CHECKS FOR DISCORDIAN SOCIETY
		return null;
	}
	
	private static GroupCard PromptDefendingGroupDestroy(){
		//TODO - Add logic to select a defending group based on cards in play for an attack to destroy
		//REMEMBER TO ADD CHECKS FOR DISCORDIAN SOCIETY
		return null;
	}
	
	private static int CalculateAnyAttempt(Player attackingPlayer, GroupCard defendingGroup, Global.AttackType attackType){
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
	
	private static int CheckSurvivalistsAbility(Player defendingPlayer){
		for(GroupCard card: defendingPlayer.GroupCards()){
			if(card.Name().equals("Survivalists")){
				return -2;
			}
		}
		
		return 0;
	}
	
	private static int CheckChineseCampaignDonors(GroupCard defendingGroup){
		//TODO - REMEMBER TO ADD CHECKS FOR DISCORDIAN SOCIETY
		if(defendingGroup.hasAlignment(GroupCard.Alignment.GOVERNMENT)){
			return 4;
		}
		return 0;
	}
	
	private static void PromptAidingGroups(GUI gui){
		gui.createPrompt("Please select groups to aid in the attack:");
		
		gui.createButtonEndPhase("Done");
		
		Global.selectionPhase = Global.SelectionPhase.SELECT_AIDING_GROUPS;
		
		gui.Frame().repaint();
		
		while(Global.selectionPhase == Global.SelectionPhase.SELECT_AIDING_GROUPS){
			try{
				Thread.sleep(200);
			}catch(Exception e){}
		}
		
		gui.removePrompt();
	}
	
	private static int CalculateAidingGroups(){
		int aidingPower = 0;
		
		if(aidingGroups.isEmpty()){
			return 0;
		}
		for(StructureCard card:aidingGroups){
			aidingPower += card.transferablePower;
		}
		
		return aidingPower;
	}
	
	private static Global.Privilege BavarianIlluminatiSpecial(){
		int playerResponse = JOptionPane.showConfirmDialog(null, "Would you like to use your special ability to spend 5 MB to make this attack privileged?");
		if(playerResponse == JOptionPane.YES_OPTION){
			attackingGroup.Owner().Illuminati().removeMoney(5);
			return Global.Privilege.PRIVILEGED;
		}
		
		return Global.Privilege.NOT_PRIVILEGED;
	}
	
	private static void DiscardForPrivilege(GUI gui){
		int playerResponse = JOptionPane.showConfirmDialog(null, "Would you like to discard a special card to make this attack privileged?", null, JOptionPane.YES_NO_OPTION);
		
		if(playerResponse == JOptionPane.YES_OPTION){
			Global.selectionPhase = Global.SelectionPhase.PRIVILEGE_DISCARD;
			
			gui.createPrompt("Please select a special card to discard:");
			
			gui.createButtonEndPhase("Done");
			
			gui.Frame().repaint();
			
			while(Global.selectionPhase == Global.SelectionPhase.PRIVILEGE_DISCARD){
				try{
					Thread.sleep(200);
				}catch(Exception e){}
			}
			
			if(gui.SelectedCards().size() > 0){
				for(CardGUI card: gui.SelectedCards()){
					attackingPlayer.RemoveSpecialCard((SpecialCard) card.Card()); 
				}
				
				gui.refreshAll();
				privilege = Global.Privilege.PRIVILEGED;
			}
			
			gui.removePrompt();
		}
	}
	
	private static Global.Privilege DiscardForAbolish(){
		//TODO - Add logic to prompt player to discard specials to abolish privilege
		return Global.Privilege.NOT_PRIVILEGED;
	}
	
	private static int SpendingPhase(){
		int attackModifier = 0;
		int phaseModifier = 0;
		boolean spentThisPhase = true;
		
		while(spentThisPhase){
			spentThisPhase = false;
			phaseModifier = 0;
			
			//Attacker spends MB
			phaseModifier += attackingPlayer.GUI().AttackingSpendingPopup(attackingGroup, attackingPlayer.Illuminati());
			if(phaseModifier != 0){
				spentThisPhase = true;
				attackModifier += phaseModifier;
				phaseModifier = 0;
			}
			
			//Defender spends MB
			phaseModifier += defendingPlayer.GUI().DefendingSpendingPopup(defendingGroup, attackingPlayer.Illuminati());
			if(phaseModifier != 0){
				spentThisPhase = true;
				attackModifier += phaseModifier;
				phaseModifier = 0;
			}
			
			//Other players interfere
			for(Player p: players){
				if(p != attackingPlayer && p != defendingPlayer){
					phaseModifier += p.GUI().InterferenceSpendingPopup(p.Illuminati());
					if(phaseModifier != 0){
						spentThisPhase = true;
						attackModifier += phaseModifier;
						phaseModifier = 0;
					}
				}
			}
		}
		
		return attackModifier;
	}
	
	//Rolls the dice to see if an attack is successful
	private static boolean AttackRoll(){
		int roll = Die.roll();
		attackingPlayer.GUI().DialogBox("You rolled a " + roll + "!");
		
		if(roll >= 11 || roll > attackModifier){
			return false;
		}else{
			return true;
		}
	}

}
