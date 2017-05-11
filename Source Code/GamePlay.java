import java.awt.Frame;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GamePlay {
	
	public enum SPENDING_TYPE{
		ATTACKING_PLAYER, DEFENDING_PLAYER, INTERFERING_PLAYER;
	}
	
	//Attributes - Attacking
	public static int attackModifier;
	public static StructureCard attackingGroup;
	public static GroupCard defendingGroup;
	public static ArrayList<StructureCard> aidingGroups = new ArrayList<StructureCard>();
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static Global.Privilege privilege;
	public static Player attackingPlayer, defendingPlayer;
	public static Global.AttackType attackType;
	
	//Player whose turn it currently is
	public static Player activePlayer;
	
	//Actions remaining for player
	public static int remainingActions;

	private static ArrayList<GUI> windows = new ArrayList<GUI>();
	
	public static Deck deck = new Deck();
	
	public static ArrayList<GroupCard> uncontrolledGroups = new ArrayList<GroupCard>();
	public static ArrayList<GroupCard> deadGroups = new ArrayList<GroupCard>();
	
	private static ArrayList<Integer> playerOrder = new ArrayList<Integer>();
	
	//Lists who wins
	private static ArrayList<Player> winners = new ArrayList<Player>();
	
	public static void main(String[] args) {
		initializeGame();
		
		//Game loop
		while(winners.size() == 0){
			for(int i = 0; i < playerOrder.size(); i++){
				playerTurn(players.get(playerOrder.get(i).intValue()));
				if(winners.size() > 0){
					break;
				}
			}
		}
		
		String winnerString = new String();
		for(int i = 0; i < winners.size(); i++){
			winnerString = winnerString + winners.get(i) + ", ";
		}
		
		Frame frame = new Frame();
		
		JOptionPane.showMessageDialog(frame, "The following player(s) won: " + winnerString.substring(0, winnerString.length() - 2) + "!");
	}
	
	//-------------------------------------Game Initialization-------------------------------------
	private static void initializeGame(){
		int numPlayers = getNumberOfPlayers();
		
		for(int i = 0; i < numPlayers; i++){
			createPlayer(i);
			//TODO - Change player order to dice roll?
			playerOrder.add(new Integer(i));
		}
		
		for(int i = 0; i < numPlayers; i++){
			players.get(i).GUI().initialize();
			players.get(i).GUI().Frame().setVisible(true);
		}
		
		GUI.createUncontrolledGUI();
		
		drawUncontrolledGroups(4);
		
		Collections.shuffle(playerOrder);
	}
	
	private static int getNumberOfPlayers(){
		Frame frame = new Frame();
		int returnVal = 0;
		
		while(returnVal < 2 || returnVal > 8){
			String input = (String) JOptionPane.showInputDialog("Enter the number of players (2-8)");
			try{
				returnVal = Integer.parseInt(input);
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(frame, "Invalid input!");
			}
			
			if(returnVal < 2 || returnVal > 8){
				JOptionPane.showMessageDialog(frame, "Invalid input!");
			}
		}
		
		return returnVal;
	}
	
	private static void createPlayer(int i){
		windows.add(i, new GUI());
		
		players.add(new Player("Player " + (i + 1), windows.get(i), i));
	}
	
	private static void drawUncontrolledGroups(int max){
		int i = uncontrolledGroups.size();
		MainDeckCard card;
		
		while(i < max){
			card = deck.drawCard();
			if(card instanceof SpecialCard){
				deck.returnCard(card);
				deck.shuffleDeck();
			}else{
				uncontrolledGroups.add((GroupCard) card);
				i++;
			}
		}
		
		GUI.uncontrolledGUI.update();
	}
	
	//--------------------------------------Player Turn---------------------------------------------
	
	public static void playerTurn(Player player){
		activePlayer = player;
		
		setAttackCounter(player);
		
		addIncome(player);
		drawPhase(player);
		
		actionPhase(player);
		
		drawUncontrolledGroups(2);
	}
	
	public static void setAttackCounter(Player player){
		if(player.Illuminati().Name().equals("The UFOs")){
			player.Illuminati().attackCounter = 2;
		}else{
			player.Illuminati().attackCounter = 1;
		}
		
		for(GroupCard card: player.GroupCards()){
			card.attackCounter = 1;
		}
	}
	
	public static void addIncome(Player player){
		player.Illuminati().addMoney(player.Illuminati().Income());
		player.GUI().GamePanel().illuminati.updateMoney();
		
		for(GroupCard card: player.GroupCards()){
			card.addMoney(card.Income());
			for(CardGUI c: player.GUI().GamePanel().cards.keySet()){
				c.updateMoney();
			}
		}
	}
	
	public static void drawPhase(Player player){
		int drawCounter = 1;
		
		if(player.Illuminati().Name().equals("The Network")){
			drawCounter = 2;
		}
		
		System.out.println(activePlayer.Name());
		while(drawCounter > 0){
			player.GUI().createPrompt("Draw a card");
			Global.selectionPhase = Global.SelectionPhase.DRAW_CARD;
			
			while(Global.selectionPhase == Global.SelectionPhase.DRAW_CARD){
				try{
					Thread.sleep(200);
				}catch(Exception e){}
			}
			
			drawCounter--;
			player.GUI().removePrompt();
		}
	}
	
	public static void actionPhase(Player player){
		remainingActions = 2;
		
		while(remainingActions > 0){
			player.GUI().removePrompt();
			player.GUI().createPrompt(remainingActions + " actions remaining");
			Global.isMainPhase = true;
			
			while(Global.isMainPhase){
				try{
					Thread.sleep(200);
				}catch(Exception e){}
			}
		}
	}
	
	
	//-----------------------------------------Attacking--------------------------------------------
	
	//Call this method to start the attack
	public static void attack(Player attackPlayer){
		attackModifier = 0;
		Global.isAttackPhase = true;
		
		attackingPlayer = attackPlayer;
		if(Global.isAttackPhase){
			attackType = attackingPlayer.GUI().AttackTypePopup();
		}
		if(Global.isAttackPhase){
			PromptAttackingGroup(attackingPlayer.GUI());
		}
		
		privilege = Global.Privilege.NOT_PRIVILEGED;

		if(Global.isAttackPhase){
			PromptDefendingGroup(attackingPlayer.GUI());
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
			}	
			
			//Uses BFS to calculate attack modifier based on power structure position
			if(defendingPlayer != null){
				attackModifier += ((GroupCard) defendingGroup).CalculateStructureResistance();
			}
			
			//Calculate "Any Attempt" Abilities
			attackModifier += CalculateAnyAttempt(attackingPlayer, defendingGroup, attackType);
			
			//Check for Chinese Campaign Donors special ability
			attackModifier += CheckChineseCampaignDonors(defendingGroup);
			
			//Calculates "Survivalists" ability
			if(defendingPlayer != null){
				attackModifier += CheckSurvivalistsAbility(defendingGroup.Owner());
			}
			GUI.outputAttackModifier(players);
		}
		
		//Prompt to add groups to aid attack.
		if(Global.isAttackPhase){
			PromptAidingGroups(attackingPlayer.GUI());
		}
		
		//Calculates the effect of transferable power
		attackModifier += CalculateAidingGroups();
		GUI.outputAttackModifier(players);

		//Bavarian Illuminati Special Attack
		if(Global.isAttackPhase){
			if(attackingPlayer.Illuminati().Name() == "The Bavarian Illuminati" && !attackingPlayer.Illuminati().SpecialUsed() && attackingPlayer.Illuminati().CurrentMoney() >= 5){
				privilege = BavarianIlluminatiSpecial();
			}
		}
		
		//Discard Special Card for Privilege
		if(Global.isAttackPhase){
			if(privilege != Global.Privilege.PRIVILEGED && attackingPlayer.SpecialCards().size() > 0){
				DiscardForPrivilege(attackingPlayer.GUI());
			}
		}
		
		//TODO - Deep Agent
		if(Global.isAttackPhase){
			if(privilege == Global.Privilege.PRIVILEGED){
				//Add code for Deep Agent
			}
		}
		
		if(Global.isAttackPhase){
			if(privilege == Global.Privilege.PRIVILEGED){
				DiscardForAbolish();
			}
		}
		
		//Spending phase of attack
		attackingPlayer.GUI().removeEndAttackButton();
		
		if(Global.isAttackPhase){
			attackModifier += SpendingPhase();
			GUI.outputAttackModifier(players);
			for(CardGUI c : attackingPlayer.GUI().GamePanel().cards.keySet()){
				c.updateMoney();
			}
		}
		
		//Stores if the attack is successful
		if(Global.isAttackPhase){
			boolean attackSuccess = AttackRoll();
		
		
			if(attackSuccess){
				attackingPlayer.GUI().DialogBox("Attack was successful!");
				
				if(attackType == Global.AttackType.CONTROL){
					controlSuccess();
				}else if(attackType == Global.AttackType.NEUTRALIZE){
					neutralizeSuccess();
				}else{
					destroySuccess();
				}
			}else{
				attackingPlayer.GUI().DialogBox("Attack was unsuccessful...");
			}
			
		
			//Reduce attack counter by one for attacking and aiding groups
			attackingGroup.attackCounter--;
			for(StructureCard card: aidingGroups){
				card.attackCounter--;
			}
			
			remainingActions--;
		}
		
		GUI.removeAttackModifier(players);
		Global.isMainPhase = false;
		Global.isAttackPhase = false;
	}
	
	private static void PromptAttackingGroup(GUI gui){
		gui.createPrompt("Please select the attacking group:");
		gui.endAttackButton();
		
		Global.selectionPhase = Global.SelectionPhase.SELECT_ATTACKING_GROUP;
		
		while(Global.selectionPhase == Global.SelectionPhase.SELECT_ATTACKING_GROUP){
			try{
				Thread.sleep(200);
				if(Global.isAttackPhase == false){
					return;
				}
			}catch(Exception e){}
		}
		
		gui.removePrompt();
	}
	
	private static void PromptDefendingGroup(GUI gui){
		gui.createPrompt("Please select the defending group:");
		
		Global.selectionPhase = Global.SelectionPhase.SELECT_DEFENDING_GROUP;
		
		while(Global.selectionPhase == Global.SelectionPhase.SELECT_DEFENDING_GROUP){
			try{
				Thread.sleep(200);
				if(Global.isAttackPhase == false){
					return;
				}
			}catch(Exception e){}
		}
		
		gui.removePrompt();
	}
	
	private static int CalculateAnyAttempt(Player attackingPlayer, GroupCard defendingGroup, Global.AttackType attackType){
		int returnVal = 0;
		
		for(GroupCard card: attackingPlayer.GroupCards()){
			if(card.Abilities().size() > 0){
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
		}
		
		return returnVal;
	}
	
	private static int CheckSurvivalistsAbility(Player defendingPlayer){
		if(defendingPlayer.GroupCards().size() > 0){
			for(GroupCard card: defendingPlayer.GroupCards()){
				if(card.Name().equals("Survivalists")){
					return -2;
				}
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
				if(Global.isAttackPhase == false){
					return;
				}
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
		
		aidingGroups = new ArrayList<StructureCard>();
		
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
					if(Global.isAttackPhase == false){
						return;
					}
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
	
	private static void DiscardForAbolish(){
		int playerResponse;
		
		for(int i = 0; i < players.size(); i++){
			if(players.get(i) != attackingPlayer && players.get(i).SpecialCards().size() >= 2){
				playerResponse = JOptionPane.showConfirmDialog(null, "Would you like to discard a special card to make this attack privileged?", null, JOptionPane.YES_NO_OPTION);
				
				if(playerResponse == JOptionPane.YES_OPTION){
					Global.selectionPhase = Global.SelectionPhase.PRIVILEGE_DISCARD;
					
					players.get(i).GUI().createPrompt("Please select a special card to discard:");
					
					players.get(i).GUI().createButtonEndPhase("Done");
					
					players.get(i).GUI().Frame().repaint();
					
					while(Global.selectionPhase == Global.SelectionPhase.PRIVILEGE_DISCARD){
						try{
							Thread.sleep(200);
							if(Global.isAttackPhase == false){
								return;
							}
						}catch(Exception e){}
					}
					
					if(players.get(i).GUI().SelectedCards().size() == 2){
						for(CardGUI card: players.get(i).GUI().SelectedCards()){
							attackingPlayer.RemoveSpecialCard((SpecialCard) card.Card()); 
						}
						
						players.get(i).GUI().refreshAll();
						privilege = Global.Privilege.ABOLISHED;
					}
					
					players.get(i).GUI().removePrompt();
				}
			}
		}
		privilege = Global.Privilege.PRIVILEGED;
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
			
			for(CardGUI c : attackingPlayer.GUI().GamePanel().cards.keySet()){
				c.updateMoney();
			}
			GUI.outputAttackModifier(players, attackModifier);
			
			if(defendingPlayer != null){
				//Defender spends MB
				phaseModifier += defendingPlayer.GUI().DefendingSpendingPopup(defendingGroup, attackingPlayer.Illuminati());
				if(phaseModifier != 0){
					spentThisPhase = true;
					attackModifier += phaseModifier;
					phaseModifier = 0;
				}
			}
			GUI.outputAttackModifier(players, attackModifier);
			
			//Other players interfere
			for(Player p: players){
				if(p != attackingPlayer && p != defendingPlayer && privilege != Global.Privilege.PRIVILEGED){
					phaseModifier += p.GUI().InterferenceSpendingPopup(p.Illuminati());
					if(phaseModifier != 0){
						spentThisPhase = true;
						attackModifier += phaseModifier;
						phaseModifier = 0;
					}
				}
			}
			GUI.outputAttackModifier(players, attackModifier);
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
	
	private static void controlSuccess(){
		attackingPlayer.AddGroupCard(defendingGroup);
		defendingGroup.owner = attackingPlayer;
		if(uncontrolledGroups.contains(defendingGroup)){
			uncontrolledGroups.remove(defendingGroup);
			GUI.uncontrolledGUI.cards.remove(defendingGroup.cardGUI());
			GUI.uncontrolledGUI.remove(defendingGroup.cardGUI());
		}else if(defendingPlayer.GroupCards().contains(defendingGroup)){
			defendingPlayer.GroupCards().remove(defendingGroup);
			defendingPlayer.GUI().GamePanel().cards.remove(defendingGroup.cardGUI());
			defendingPlayer.GUI().GamePanel().remove(defendingGroup.cardGUI());
		}
		
		defendingGroup.removeMoney(defendingGroup.currentMoney / 2);
		
		if(defendingGroup.Parent() != null){
			defendingGroup.Parent().RemoveChild(defendingGroup);
		}
		
		Global.selectionPhase = Global.SelectionPhase.ADD_CHILD;
		addChildToParent(attackingPlayer, attackingGroup, defendingGroup);
		
		while(Global.selectionPhase == Global.SelectionPhase.ADD_CHILD){
			try{
				Thread.sleep(200);
			}catch(Exception e){}
		}
		
		String input = null;
		int intInput;
		
		while(input == null){
			input = (String) JOptionPane.showInputDialog(attackingPlayer.GUI().Frame(), "Enter the amount of MB to transfer from the attacking group to the new group: (" + attackingGroup.CurrentMoney() + " MB max)", "Spend Money", JOptionPane.PLAIN_MESSAGE, null, null, "0");

			try{
				intInput = Integer.parseInt(input);
				if(intInput > attackingGroup.CurrentMoney() || intInput < 0){
					attackingPlayer.GUI().DialogBox("Invalid input!");
					input = null;
				}else{
					attackingGroup.removeMoney(intInput);
					defendingGroup.addMoney(intInput);
				}
			}catch(NumberFormatException e){
				attackingPlayer.GUI().DialogBox("Invalid input!");
				input = null;
			}
		}
		
		for(GroupCard card: attackingPlayer.GroupCards()){
			card.cardGUI().updateMoney();
		}
		attackingPlayer.Illuminati().cardGUI().updateMoney();
		
		
		GUI.uncontrolledGUI.update();
		attackingPlayer.GUI().Frame().repaint();
		if(defendingPlayer != null){
			defendingPlayer.GUI().Frame().repaint();
		}
	}
	
	private static void neutralizeSuccess(){
		if(attackingPlayer.GroupCards().contains(defendingGroup)){
			attackingPlayer.GroupCards().remove(defendingGroup);
			attackingPlayer.GUI().GamePanel().cards.remove(defendingGroup.cardGUI());
			attackingPlayer.GUI().GamePanel().remove(defendingGroup.cardGUI());
		}else{
			defendingPlayer.GroupCards().remove(defendingGroup);
			defendingPlayer.GUI().GamePanel().cards.remove(defendingGroup.cardGUI());
			defendingPlayer.GUI().GamePanel().remove(defendingGroup.cardGUI());
		}
		
		defendingGroup.rotation = StructureCard.Rotation.UP;
		uncontrolledGroups.add(defendingGroup);
		//GUI.uncontrolledGUI.cards.add(defendingGroup.cardGUI());
		GUI.uncontrolledGUI.update();
		
		defendingGroup.removeMoney(defendingGroup.currentMoney);
		
		if(defendingGroup.Parent() != null){
			defendingGroup.Parent().RemoveChild(defendingGroup);
		}
		
		GUI.uncontrolledGUI.update();
		attackingPlayer.GUI().Frame().repaint();
		if(defendingPlayer != null){
			defendingPlayer.GUI().Frame().repaint();
		}
	}
	
	private static void destroySuccess(){
		if(attackingPlayer.GroupCards().contains(defendingGroup)){
			attackingPlayer.GroupCards().remove(defendingGroup);
			attackingPlayer.GUI().GamePanel().cards.remove(defendingGroup.cardGUI());
			attackingPlayer.GUI().GamePanel().remove(defendingGroup.cardGUI());
		}else if(uncontrolledGroups.contains(defendingGroup)){
			uncontrolledGroups.remove(defendingGroup);
			GUI.uncontrolledGUI.cards.remove(defendingGroup.cardGUI());
			GUI.uncontrolledGUI.remove(defendingGroup.cardGUI());
		}else{
			defendingPlayer.GroupCards().remove(defendingGroup);
			defendingPlayer.GUI().GamePanel().cards.remove(defendingGroup.cardGUI());
			defendingPlayer.GUI().GamePanel().remove(defendingGroup.cardGUI());
		}
		
		deadGroups.add(defendingGroup);
		
		defendingGroup.removeMoney(defendingGroup.currentMoney);
		
		if(defendingGroup.Parent() != null){
			defendingGroup.Parent().RemoveChild(defendingGroup);
		}
		
		attackingPlayer.GUI().Frame().repaint();
		if(defendingPlayer != null){
			defendingPlayer.GUI().Frame().repaint();
		}
	}
	
	private static void addChildToParent(Player player, StructureCard parent, GroupCard child){
		player.GUI().parentControlArrowPopup(parent, child);
	}

}
