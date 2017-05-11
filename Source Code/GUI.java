import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class GUI {

	private JFrame frame;
	private JTextField textField;
	
	private JLayeredPane displayPanel;
	private FieldGUI gamePanel;
	
	//Uncontrolled groups area
	public static UncontrolledGUI uncontrolledGUI;
	
	//Currently shows FieldGUI
	private JPanel currentPanel;
	
	//Used to pass an arrow to the mouse handler in the popup
	private StructureCard.Arrow actionArrow;
	
	//Tracks the cards selected in the player's hand
	private ArrayList<CardGUI> selectedCards;
	
	//Tracks all CardGUI in gamePanel;
	private ArrayList<CardGUI> gamePanelCards;
	
	//Tracks all CardGUI in cardsPanel
	private ArrayList<CardGUI> cardsPanelCards;
	
	public Player player;
	private JPanel cardsPanel;
	
	//Text prompt in the upper-left corner
	private JLabel cornerPrompt;
	
	//Text prompt for attack modifier in upper-right corner
	private JLabel modifierPrompt;
	
	//ComboBox for selecting which field to look at
	private JComboBox fieldSelection;
	
	//Client-server variables
	private static int port = 81;
	private static BufferedReader streamIn;
    private static PrintStream streamOut;
    private JTextField chatTextField;
    private JTextArea textArea;
    private JButton sendButton;
    private JButton clearButton;
	
	public ArrayList<CardGUI> SelectedCards(){
		return this.selectedCards;
	}
	
	public JFrame Frame(){
		return this.frame;
	}
	
	public FieldGUI GamePanel(){
		return this.gamePanel;
	}
	
	/**
	 * Create the application.
	 */
	public GUI() {
		
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		gamePanelCards = new ArrayList<CardGUI>();
		cardsPanelCards = new ArrayList<CardGUI>();
		
		frame = new JFrame(player.Name());
		frame.setSize(1300, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		cardsPanel = new JPanel();
		cardsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		cardsPanel.setBackground(Color.LIGHT_GRAY);
		cardsPanel.setBounds(6, 601, 735, 111);
		panel.add(cardsPanel);
		cardsPanel.setLayout(null);
		
		CardGUI illuminatiGUI = new CardGUI(player.Illuminati(), 5, 10);
		player.Illuminati().cardGUI = illuminatiGUI;
		addEventHandler(illuminatiGUI);
		
		//Initialize Game Panel
		displayPanel = new JLayeredPane();
		displayPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		displayPanel.setBackground(new Color(255, 255, 240));
		displayPanel.setBounds(6, 6, 1049, 583);
		panel.add(displayPanel);
		displayPanel.setLayout(null);
		
		gamePanel = new FieldGUI(illuminatiGUI);
		displayPanel.add(gamePanel, 1);
		
		currentPanel = gamePanel;
		
		gamePanelCards.add(illuminatiGUI);
		
		//Add the deck to the field
		JLabel fieldDeck = new JLabel("");
		ImageIcon imageIcon = new ImageIcon("./src/img/Deck.png");
		Image scaledImage = imageIcon.getImage().getScaledInstance(150, 90, Image.SCALE_SMOOTH);
		fieldDeck.setIcon(new ImageIcon(scaledImage));
		fieldDeck.setBounds(10, 480, 150, 90);
		gamePanel.add(fieldDeck);
		fieldDeck.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				drawCard();
			}
		});
		
		JPanel rightSidePanel = new JPanel();
		rightSidePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		rightSidePanel.setBounds(1061, 6, 233, 766);
		panel.add(rightSidePanel);
		rightSidePanel.setLayout(null);
		
		//Create ComboBox for viewing other play fields
		JLabel lblOptions = new JLabel("Viewing Field");
		lblOptions.setBounds(6, 6, 222, 30);
		rightSidePanel.add(lblOptions);
		lblOptions.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		
		String[] fieldOptions = new String[GamePlay.players.size() + 1];
		for(int i = 0; i < GamePlay.players.size(); i++){
			fieldOptions[i] = "Player " + (i + 1);
			
		}
		fieldOptions[GamePlay.players.size()] = "Uncontrolled Area";
		
		fieldSelection = new JComboBox(fieldOptions);
		fieldSelection.setSelectedIndex(player.PlayerNum());
		fieldSelection.setBounds(6, 36, 222, 25);
		rightSidePanel.add(fieldSelection);
		fieldSelection.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String response = (String) fieldSelection.getSelectedItem();
				if(response != "Uncontrolled Area"){
					response = response.substring(response.length() - 1);
					int playerNum = Integer.parseInt(response) - 1;
					displayPanel.remove(currentPanel);
					
					displayPanel.add(GamePlay.players.get(playerNum).GUI().gamePanel, 1);
					frame.revalidate();
					frame.repaint();
					displayPanel.revalidate();
					displayPanel.repaint();
					currentPanel = GamePlay.players.get(playerNum).GUI().gamePanel;
				}else{
					displayPanel.remove(currentPanel);
					
					displayPanel.add(uncontrolledGUI, 1);
					frame.revalidate();
					frame.repaint();
					displayPanel.revalidate();
					displayPanel.repaint();
					currentPanel = uncontrolledGUI;
				}
			}
		});
		
		JLabel lblLogs = new JLabel("Logs");
		lblLogs.setBounds(6, 61, 220, 30);
		rightSidePanel.add(lblLogs);
		lblLogs.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		
		JEditorPane logsPane = new JEditorPane();
		logsPane.setText("player 1 logged in.\nplayer 2 logged in.\nplayer 3 logged in.\nGame Starts NOW...\nplayer 1 turn!\nplayer 1 added a card.\nplayer 1 end turn!\nplayer 2 turn!\nplayer 2 added a card.\nplayer 2 end turn!\nplayer 3 turn!\nplayer 3 added a card.");
		logsPane.setBounds(6, 91, 222, 140);
		rightSidePanel.add(logsPane);
		
		JLabel lblChat = new JLabel("Chat");
		lblChat.setBounds(6, 239, 222, 29);
		rightSidePanel.add(lblChat);
		lblChat.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setBounds(6, 280, 220, 423);
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(Color.WHITE);
		rightSidePanel.add(textArea);
		
		chatTextField = new JTextField();
		chatTextField.setBounds(6, 710, 220, 26);
		rightSidePanel.add(chatTextField);
		chatTextField.setColumns(10);
		
		JButton clearButton = new JButton("Clear");
		clearButton.setBounds(133, 731, 93, 29);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chatTextField.setText("");
			}
		});
		rightSidePanel.add(clearButton);
		
		JButton sendButton = new JButton("Send");
		sendButton.setBounds(6, 731, 130, 29);
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				streamOut.println(player.Name() + ": " + chatTextField.getText());
				chatTextField.setText("");
			}
		});
		rightSidePanel.add(sendButton);
		
		
		JPanel playerActionsPanel = new JPanel();
		playerActionsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerActionsPanel.setBounds(760, 601, 295, 171);
		panel.add(playerActionsPanel);
		playerActionsPanel.setLayout(null);
		
		JButton btnNext = new JButton("Transfer Money");
		btnNext.setBounds(153, 38, 130, 29);
		playerActionsPanel.add(btnNext);
		
		JLabel lblCardActionsJ = new JLabel("Player Actions");
		lblCardActionsJ.setBounds(6, 6, 121, 20);
		playerActionsPanel.add(lblCardActionsJ);
		lblCardActionsJ.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		JButton btnMoveMoney = new JButton("Move Money");
		btnMoveMoney.setBounds(10, 67, 130, 29);
		playerActionsPanel.add(btnMoveMoney);
		
		JButton btnGiveAGroup = new JButton("Give a Group");
		btnGiveAGroup.setBounds(153, 67, 130, 29);
		playerActionsPanel.add(btnGiveAGroup);
		
		JButton btnDropAGroup = new JButton("Give a Card");
		btnDropAGroup.setBounds(153, 96, 130, 29);
		playerActionsPanel.add(btnDropAGroup);
		
		JButton btnAttack = new JButton("Attack");
		btnAttack.setBounds(11, 38, 130, 29);
		playerActionsPanel.add(btnAttack);
		btnAttack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Global.isMainPhase && GamePlay.activePlayer == player){
					SwingUtilities.invokeLater(new Runnable(){
						public void run(){
							Thread qthread = new Thread(){
								public void run(){
									GamePlay.attack(player);
								}
							};
							
							qthread.start();
						}
					});
				}
			}
		});
		
		JButton btnUseASpecial = new JButton("Use a Specialty ");
		btnUseASpecial.setBounds(10, 96, 130, 29);
		playerActionsPanel.add(btnUseASpecial);
		
		JButton button = new JButton("Drop a Group");
		button.setBounds(10, 136, 130, 29);
		playerActionsPanel.add(button);
		
		JButton btnNextTurn = new JButton("Done");
		btnNextTurn.setBounds(153, 136, 130, 29);
		btnNextTurn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				if(Global.isMainPhase && GamePlay.activePlayer == player){
					if(GamePlay.remainingActions == 2){
						GamePlay.activePlayer.Illuminati().addMoney(5);
					}
					GamePlay.remainingActions = 0;
					Global.isMainPhase = false;
				}
			}
		});
		playerActionsPanel.add(btnNextTurn);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(6, 727, 735, 45);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Total balance:");
		lblNewLabel.setBounds(6, 6, 99, 16);
		panel_1.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setText("$200");
		textField.setBounds(99, 1, 130, 26);
		panel_1.add(textField);
		textField.setColumns(10);
		
		Thread chatThread = new Thread(){
			public void run(){
				try{
					connect();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		};
		
		chatThread.start();
	}
	
	public void GenerateHand(Player player, JPanel panel){
		panel.removeAll();
		panel.updateUI();
		ArrayList<SpecialCard> specials = player.SpecialCards();
		
		cardsPanelCards = new ArrayList<CardGUI>();
		selectedCards = new ArrayList<CardGUI>();
		
		int xOffset = 4;

		for(Card card: specials){
			CardGUI cardGUI = new CardGUI(card, panel, xOffset, 10);
			card.cardGUI = cardGUI;
			cardGUI.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					if(Global.selectionPhase == Global.SelectionPhase.PRIVILEGE_DISCARD){
						selectedCards = new ArrayList<CardGUI>();
						designateSpecialForPrivilege(cardGUI);
					}
				}
			});
			
			panel.add(cardGUI);
			cardsPanelCards.add(cardGUI);
			
			xOffset += 154;
		}
		
		frame.repaint();
	}
	
	private CardGUI addChild(StructureCard parent, StructureCard child, StructureCard.Arrow arrow){
		parent.AddChild((GroupCard) child, arrow);
		CardGUI childGUI = new CardGUI(child, gamePanel, 0, 0);
		child.cardGUI = childGUI;
		//gamePanel.addCard(childGUI, new CardGUI(parent, gamePanel, 0, 0));
		gamePanel.addCard(childGUI, parent.cardGUI());
		gamePanelCards.add(childGUI);
		
		return childGUI;
	}
	
	public void addEventHandler(CardGUI card){
		card.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				/*if(Global.selectionPhase == Global.SelectionPhase.ADD_CHILD){
					parentControlArrowPopup((StructureCard) card.Card());
				}else*/ if(Global.selectionPhase == Global.SelectionPhase.SELECT_ATTACKING_GROUP){
					designateAttackingGroup((StructureCard) card.Card());
				}else if(Global.selectionPhase == Global.SelectionPhase.SELECT_DEFENDING_GROUP){
					designateDefendingGroup((StructureCard) card.Card());
				}else if(Global.selectionPhase == Global.SelectionPhase.SELECT_AIDING_GROUPS){
					designateAidingGroup((StructureCard) card.Card());
				}
			}
		});
	}
	
	//-------------------------------------Card Selection Effects-------------------------------
	
	private void drawCard(){
		if(GamePlay.activePlayer == player && Global.selectionPhase == Global.SelectionPhase.DRAW_CARD){
			player.DrawCard(GamePlay.deck);
			GenerateHand(player, cardsPanel);
			frame.repaint();
			
			Global.selectionPhase = Global.SelectionPhase.NONE;
			
			for(CardGUI c: uncontrolledGUI.cards){
				addEventHandler(c);
			}
		}
	}
	
	private void designateAttackingGroup(StructureCard card){
		
		if (card == GamePlay.attackingPlayer.Illuminati() || GamePlay.attackingPlayer.GroupCards().contains(card)){
			if(card.AttackCounter() > 0){
				if(card.Power() == 0){
					DialogBox("This group has no power and cannot attack!");
				}else{
					GamePlay.attackingGroup = card;
					Global.selectionPhase = Global.SelectionPhase.NONE;
				}
			}else{
				DialogBox("This group has no attacks remaining!");
			}
		}else{
			DialogBox("You do not control this group!");
		}
	}
	
	private void designateDefendingGroup(StructureCard card){
		if(GamePlay.defendingPlayer != null && GamePlay.defendingPlayer.Illuminati().Name().equals("The Discordian Society") && checkDiscordian(card)){
			DialogBox("The Discordian Society is immune to this card.");
		}else{
			if(card == GamePlay.attackingGroup){
				DialogBox("A group cannot attack itself!");
			}else if(card instanceof IlluminatiCard){
				DialogBox("You cannot attack an Illuminati!");
			}else{
				if(GamePlay.attackType == Global.AttackType.CONTROL){
					if(GamePlay.attackingPlayer.GroupCards().contains(card)){
						DialogBox("You already control this group!");
					}else{
						GamePlay.defendingGroup = (GroupCard) card;
						Global.selectionPhase = Global.SelectionPhase.NONE;
						player.GUI().fieldSelection.setSelectedItem(GamePlay.attackingPlayer.Name());
					}
				}else if(GamePlay.attackType == Global.AttackType.NEUTRALIZE){
					if(card.Owner() == null){
						DialogBox("You cannot neutralize an uncontrolled group!");
					}else{
						GamePlay.defendingGroup = (GroupCard) card;
						Global.selectionPhase = Global.SelectionPhase.NONE;
						player.GUI().fieldSelection.setSelectedItem(GamePlay.attackingPlayer.Name());
					}
				}else{		//Destroy
					if(card.Power() == 0){
						DialogBox("You cannot destroy a group with no power!");
					}else{
						GamePlay.defendingGroup = (GroupCard) card;
						Global.selectionPhase = Global.SelectionPhase.NONE;
						player.GUI().fieldSelection.setSelectedItem(GamePlay.attackingPlayer.Name());
					}
				}
			}
		}
	}
	
	private boolean checkDiscordian(StructureCard card){
		if(card instanceof GroupCard){
			if(((GroupCard) card).hasAlignment(GroupCard.Alignment.GOVERNMENT) || ((GroupCard) card).hasAlignment(GroupCard.Alignment.STRAIGHT)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	private void designateAidingGroup(StructureCard card){
		if(card == GamePlay.attackingGroup){
			DialogBox("The attacking group cannot assist itself!");
			return;
		}
		
		if(card.AttackCounter() > 0){
			if(card.TransferablePower() == 0){
				DialogBox("This group has no transferable power and cannot aid in the attack!");
			}else{
				if(GamePlay.aidingGroups.contains(card)){
					GamePlay.aidingGroups.remove(card);
					System.out.println(GamePlay.aidingGroups);
				}else{
					GamePlay.aidingGroups.add(card);
					System.out.println(GamePlay.aidingGroups);
				}
			}
		}else{
			DialogBox("This group has no attacks remaining!");
		}
	}
	
	private void designateSpecialForPrivilege(CardGUI cardGUI){
		if(selectedCards.size() > 0){
			selectedCards.remove(0);
		}
			
		selectedCards.add(cardGUI);
	}
	
	//--------------------------------------Prompts/Popups--------------------------------------
	
	//Variables for Listeners
	private Global.AttackType listAttackType;
	
	//Creates a label in the top left corner of the game field, to prompt the user to do something
	public void createPrompt(String s){
		if(cornerPrompt != null){
			gamePanel.remove(cornerPrompt);
			cornerPrompt.setVisible(false);
			cornerPrompt = null;
		}
		
		cornerPrompt = new JLabel(s);
		cornerPrompt.setBounds(6, 6, 500, 25);
		cornerPrompt.setFont(cornerPrompt.getFont().deriveFont(20.0f));
		cornerPrompt.setForeground(new Color(255, 50, 50));
		gamePanel.add(cornerPrompt);
		
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	//Removes the label in the top left corner
	public void removePrompt(){
		if(cornerPrompt != null){
			gamePanel.remove(cornerPrompt);
			cornerPrompt.setVisible(false);
			cornerPrompt = null;
		}
	}
	
	private JFrame CreateGenericPopup(){
		return CreateGenericPopup(300, 500);
	}
	
	private JFrame CreateGenericPopup(int width, int height){
		JFrame popup = new JFrame();
		popup.setUndecorated(true);
		popup.setSize(width, height);
		popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		popup.getContentPane().setLayout(new BorderLayout(0, 0));
		popup.repaint();
		
		popup.setVisible(true);
		popup.setAlwaysOnTop(true);
		
		return popup;
	}
	
	private JPanel CreateGenericPanel(){
		return CreateGenericPanel(300, 500);
	}
	
	private JPanel CreateGenericPanel(int width, int height){
		JPanel popupPanel = new JPanel();
		popupPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		popupPanel.setBounds(0, 0, width, height);
		
		popupPanel.setLayout(null);
		
		return popupPanel;
	}
	
	private JButton CreateCancelButton(JFrame popup, int yOffset){
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(new Rectangle(200, yOffset, 80, 30));
		
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				popup.setVisible(false);
				popup.dispose();
			}
		});
		
		return btnCancel;
	}
	
	private JButton CreateCancelButton(JFrame popup){
		return CreateCancelButton(popup, 450);
	}
	
	//Creates a simple dialog box to display a string to the player.
	public void DialogBox(String s){
		JOptionPane.showMessageDialog(null, "<html><body style = 'width: 250 px'>" + s);
	}
	
	public void parentControlArrowPopup(StructureCard parent, GroupCard child){
		JFrame popup = CreateGenericPopup();
		
		JPanel popupPanel = CreateGenericPanel();
		popup.add(popupPanel);
		
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		
		JLabel title = new JLabel("Pick a Control Arrow:");
		title.setBounds(100, 30, 300, 16);
		popupPanel.add(title);
		
		for(StructureCard.Arrow arrow: parent.OutwardArrows()){
			buttons.add(buttons.size(), new JButton(arrow.toString()));
		}
		
		int yOffset = 60;
		
		for(int i = 0; i < buttons.size(); i++){
			buttons.get(i).setBounds(new Rectangle(10, yOffset, 280, 30));
			popupPanel.add(buttons.get(i));
			
			if(buttons.get(i).getText() == "TOP"){
				actionArrow = StructureCard.Arrow.TOP;
			}else if(buttons.get(i).getText() == "RIGHT"){
				actionArrow = StructureCard.Arrow.RIGHT;
			}else if(buttons.get(i).getText() == "LEFT"){
				actionArrow = StructureCard.Arrow.LEFT;
			}else{
				actionArrow = StructureCard.Arrow.BOTTOM;
			}
			
			buttons.get(i).addActionListener(new ArrowButtonListener(parent, actionArrow, popup, child));
			
			yOffset += 40;
		}
		
		yOffset += 50;
		
		/*JButton btnCancel = CreateCancelButton(popup);
		popupPanel.add(btnCancel);*/
		
		child.owner = parent.Owner();
		
		frame.repaint();
	}
	
	public Global.AttackType AttackTypePopup(){
		
		listAttackType = null;
		
		JFrame popup = CreateGenericPopup();
		
		JPanel popupPanel = CreateGenericPanel();
		popup.add(popupPanel);
		
		JLabel title = new JLabel("Pick an attack type:");
		title.setBounds(100, 30, 300, 16);
		popupPanel.add(title);
		
		JButton btnControl = new JButton("Control");
		btnControl.setBounds(new Rectangle(10, 60, 280, 30));
		popupPanel.add(btnControl);
		btnControl.setVisible(true);
		btnControl.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				listAttackType = Global.AttackType.CONTROL;
				popup.setVisible(false);
				popup.dispose();
			}
		});
		
		JButton btnNeutralize = new JButton("Neutralize");
		btnNeutralize.setBounds(new Rectangle(10, 100, 280, 30));
		popupPanel.add(btnNeutralize);
		btnNeutralize.setVisible(true);
		btnNeutralize.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				listAttackType = Global.AttackType.NEUTRALIZE;
				popup.setVisible(false);
				popup.dispose();
			}
		});
		
		JButton btnDestroy = new JButton("Destroy");
		btnDestroy.setBounds(new Rectangle(10, 140, 280, 30));
		popupPanel.add(btnDestroy);
		btnDestroy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				listAttackType = Global.AttackType.DESTROY;
				popup.setVisible(false);
				popup.dispose();
			}
		});
		
		JButton btnCancel = CreateCancelButton(popup);
		popupPanel.add(btnCancel);
		btnDestroy.setVisible(true);
		
		frame.repaint();
		popupPanel.repaint();
		
		
		while(popup.isVisible()){
			try{
				Thread.sleep(200);
			}catch (Exception e){}
		}
		
		if(listAttackType == null){
			return null;
		}else{
			return listAttackType;
		}
	}
	
	//Attacker Spending Popup
	public int AttackingSpendingPopup(StructureCard attackingGroup, IlluminatiCard illuminatiCard){
		String input;
		int groupVal = Integer.MAX_VALUE;
		
		while(groupVal == Integer.MAX_VALUE){
			input = (String) JOptionPane.showInputDialog(frame, GamePlay.attackingPlayer.Name() + ": How much money will the attacking group spend on the attack? (Amount of MB in treasury: " + attackingGroup.CurrentMoney() + ")", "Spend Money", JOptionPane.PLAIN_MESSAGE, null, null, "0");
			try{
				groupVal = Integer.parseInt(input);
				
				if(groupVal > attackingGroup.CurrentMoney()){
					DialogBox("The attacking group does not have enough MB.");
					groupVal = Integer.MAX_VALUE;
				}
				
				if(groupVal < 0){
					DialogBox("The number cannnot be negative!");
					groupVal = Integer.MAX_VALUE;
				}
				
			}catch (NumberFormatException e){
				DialogBox("Invalid input. Please try again");
			}
		}
		
		System.out.println(groupVal);
		attackingGroup.removeMoney(groupVal);
		
		int illuminatiVal = 0;
		
		if(illuminatiCard != attackingGroup){
			illuminatiVal = Integer.MAX_VALUE;
			
			while(illuminatiVal == Integer.MAX_VALUE){
				input = (String) JOptionPane.showInputDialog(frame, GamePlay.attackingPlayer.Name() + ": How much money will the Illuminati spend on the attack? (Amount of MB in treasury: " + illuminatiCard.CurrentMoney() + ")", "Spend Money", JOptionPane.PLAIN_MESSAGE, null, null, "0");
				try{
					illuminatiVal = Integer.parseInt(input);
					
					if(illuminatiVal > illuminatiCard.CurrentMoney()){
						DialogBox("The Illuminati does not have enough MB.");
						illuminatiVal = Integer.MAX_VALUE;
					}
					
					if(illuminatiVal < 0){
						DialogBox("The number cannnot be negative!");
						illuminatiVal = Integer.MAX_VALUE;
					}
					
				}catch (NumberFormatException e){
					DialogBox("Invalid input. Please try again");
				}
			}
		}
		
		illuminatiCard.removeMoney(illuminatiVal);
		
		return illuminatiVal + groupVal;
	}
	
	//Defender Spending Popup
	public int DefendingSpendingPopup(StructureCard defendingGroup, IlluminatiCard illuminatiCard){
		String input;
		int groupVal = Integer.MAX_VALUE;
		
		while(groupVal == Integer.MAX_VALUE){
			input = (String) JOptionPane.showInputDialog(frame, GamePlay.defendingPlayer.Name() + ": How much money will the defending group spend on the attack? (Amount of MB in treasury: " + defendingGroup.CurrentMoney() + ")", "Spend Money", JOptionPane.PLAIN_MESSAGE, null, null, "0");
			try{
				groupVal = Integer.parseInt(input);
				
				if(groupVal > defendingGroup.CurrentMoney()){
					DialogBox("The defending group does not have enough MB.");
					groupVal = Integer.MAX_VALUE;
				}
				
				if(groupVal < 0){
					DialogBox("The number cannnot be negative!");
					groupVal = Integer.MAX_VALUE;
				}
				
			}catch (NumberFormatException e){
				DialogBox("Invalid input. Please try again");
			}
		}
		
		defendingGroup.removeMoney(groupVal);
		
		int illuminatiVal = 0;
		
		if(illuminatiCard != defendingGroup){
			illuminatiVal = Integer.MAX_VALUE;
			
			while(illuminatiVal == Integer.MAX_VALUE){
				input = (String) JOptionPane.showInputDialog(frame, GamePlay.defendingPlayer.Name() + ": How much money will the Illuminati spend on the attack? (Amount of MB in treasury: " + illuminatiCard.CurrentMoney() + ")", "Spend Money", JOptionPane.PLAIN_MESSAGE, null, null, "0");
				try{
					illuminatiVal = Integer.parseInt(input);
					
					if(illuminatiVal > illuminatiCard.CurrentMoney()){
						DialogBox("The Illuminati does not have enough MB.");
						illuminatiVal = Integer.MAX_VALUE;
					}
					
					if(illuminatiVal < 0){
						DialogBox("The number cannnot be negative!");
						illuminatiVal = Integer.MAX_VALUE;
					}
					
				}catch (NumberFormatException e){
					DialogBox("Invalid input. Please try again");
				}
			}
		}
		
		illuminatiCard.removeMoney(illuminatiVal);
		
		return 0 - (illuminatiVal + groupVal * 2);
	}
	
	//Interference Spending Popup
	public int InterferenceSpendingPopup(IlluminatiCard illuminatiCard){
		String input;
		int assistingVal = Integer.MAX_VALUE;
		
		while(assistingVal == Integer.MAX_VALUE){
			input = (String) JOptionPane.showInputDialog(frame, player.Name() + ": How much money will your Illuminati spend on assisting the attack? (Amount of MB in treasury: " + illuminatiCard.CurrentMoney() + ")", "Spend Money", JOptionPane.PLAIN_MESSAGE, null, null, "0");
			try{
				assistingVal = Integer.parseInt(input);
				
				if(assistingVal > illuminatiCard.CurrentMoney()){
					DialogBox("The Illuminati does not have enough MB.");
					assistingVal = Integer.MAX_VALUE;
				}
				
				if(assistingVal < 0){
					DialogBox("The number cannnot be negative!");
					assistingVal = Integer.MAX_VALUE;
				}
				
			}catch (NumberFormatException e){
				DialogBox("Invalid input. Please try again");
			}
		}
		
		illuminatiCard.removeMoney(assistingVal);
		
		int interferingVal = 0;
		
		if(assistingVal == 0){
			
			interferingVal = Integer.MAX_VALUE;
			
			while(interferingVal == Integer.MAX_VALUE){
				input = (String) JOptionPane.showInputDialog(frame, player.Name() + ": How much money will your Illuminati spend on interfering in the attack? (Amount of MB in treasury: " + illuminatiCard.CurrentMoney() + ")", "Spend Money", JOptionPane.PLAIN_MESSAGE, null, null, "0");
				try{
					interferingVal = Integer.parseInt(input);
					
					if(interferingVal > illuminatiCard.CurrentMoney()){
						DialogBox("The Illuminati does not have enough MB.");
						interferingVal = Integer.MAX_VALUE;
					}
					
					if(interferingVal < 0){
						DialogBox("The number cannnot be negative!");
						interferingVal = Integer.MAX_VALUE;
					}
					
				}catch (NumberFormatException e){
					DialogBox("Invalid input. Please try again");
				}
			}
		}
		
		illuminatiCard.removeMoney(interferingVal);
		
		return assistingVal - interferingVal;
	}
	
	//------------------------------------------Action Listeners-------------------------------------
	
	private class ArrowButtonListener implements ActionListener{
		private StructureCard.Arrow arrow;
		private StructureCard parent;
		private JFrame popup;
		private GroupCard child;
		
		public ArrowButtonListener(StructureCard parent, StructureCard.Arrow arrow, JFrame popup, GroupCard child){
			this.arrow = arrow;
			this.parent = parent;
			this.popup = popup;
			this.child = child;
		}		
		
		public void actionPerformed(ActionEvent e){
			CardGUI childGUI = addChild(parent, child, arrow);
			
			childGUI.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					/*if(Global.selectionPhase == Global.SelectionPhase.ADD_CHILD){
						parentControlArrowPopup((StructureCard) child.Card());
					}else*/ if(Global.selectionPhase == Global.SelectionPhase.SELECT_ATTACKING_GROUP){
						designateAttackingGroup(child);
					}else if(Global.selectionPhase == Global.SelectionPhase.SELECT_DEFENDING_GROUP){
						designateDefendingGroup(child);
					}else if(Global.selectionPhase == Global.SelectionPhase.SELECT_AIDING_GROUPS){
						designateAidingGroup(child);
					}
				}
			});
			
			child.attackCounter = 1;
			
			Global.selectionPhase = Global.SelectionPhase.NONE;
			
			popup.setVisible(false);
			popup.dispose();
		}
	}
	
	//-----------------------------------------Other UI Elements--------------------------------------------
	private JButton btnEndAttack;
	
	//Create a label to show the attack modifier as the players' actions update it FOR ALL PLAYERS
	public static void outputAttackModifier(ArrayList<Player> players, int adjustment){
		
		for(Player p: players){
			if(p.GUI().modifierPrompt != null){
				p.GUI().modifierPrompt.setVisible(false);
				p.GUI().gamePanel.remove(p.GUI().modifierPrompt);
				p.GUI().modifierPrompt = null;
			}
			p.GUI().modifierPrompt = new JLabel("Attack modifier: " + (GamePlay.attackModifier + adjustment));
			p.GUI().modifierPrompt.setBounds(850, 6, 500, 25);
			p.GUI().modifierPrompt.setFont(p.GUI().modifierPrompt.getFont().deriveFont(20.0f));
			p.GUI().modifierPrompt.setForeground(new Color(255, 50, 50));
			p.GUI().gamePanel.add(p.GUI().modifierPrompt);
			
			p.GUI().displayPanel.revalidate();
			p.GUI().displayPanel.repaint();
			p.GUI().frame.repaint();
		}
	}
	
	public static void outputAttackModifier(ArrayList<Player> players){
		outputAttackModifier(players, 0);
	}
	
	public static void removeAttackModifier(ArrayList<Player> players){
		for(Player p: players){
			if(p.GUI().modifierPrompt != null){
				p.GUI().modifierPrompt.setVisible(false);
				p.GUI().gamePanel.remove(p.GUI().modifierPrompt);
				p.GUI().modifierPrompt = null;
			}
		}
	}
	
	public void endAttackButton(){
		btnEndAttack = new JButton("End Attack");
		btnEndAttack.setBounds(new Rectangle(910, 540, 120, 30));
		
		btnEndAttack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				btnEndAttack.setVisible(false);
				btnEndAttack = null;
				
				Global.isAttackPhase = false;
				
				removePrompt();
			}
		});
		
		gamePanel.add(btnEndAttack);
		gamePanel.repaint();
	}
	
	//Creates a button to return the phase to Global.SelectionPhase.NONE
	public void createButtonEndPhase(String s){
		JButton btn = new JButton(s);
		btn.setBounds(new Rectangle(10, 50, 120, 30));
		
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Global.selectionPhase = Global.SelectionPhase.NONE;
				btn.setVisible(false);
			}
		});
		
		gamePanel.add(btn);
		frame.repaint();
	}
	
	public void removeEndAttackButton(){
		if(btnEndAttack != null){
			gamePanel.remove(btnEndAttack);
			btnEndAttack.setVisible(false);
			btnEndAttack = null;
		}
	}
	
	public void refreshAll(){
		GenerateHand(player, cardsPanel);
	}
	
	public static void createUncontrolledGUI(){
		uncontrolledGUI = new UncontrolledGUI();
	}
	
	//---------------------------Server-client connection------------------------------------------
	private String getUsername(){
        return JOptionPane.showInputDialog(frame, "Name:", "Welcome to Chat", JOptionPane.QUESTION_MESSAGE);
    }
	
	private void connect() throws IOException{
        Socket clientSocket = new Socket(InetAddress.getByName(null), port);
        streamIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        streamOut = new PrintStream(clientSocket.getOutputStream(), true);
    
        while(true){
            String line = streamIn.readLine();
            if(line.startsWith("Username")){
                streamOut.println(getUsername());
            }else if(line.startsWith("Welcome")){
            	chatTextField.setEditable(true);
            }else if(line.startsWith("From")){
            	textArea.append(line.substring(6)+ "\n"); // output
            }
        }   
    }

}
