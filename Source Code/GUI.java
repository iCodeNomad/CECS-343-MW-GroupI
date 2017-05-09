import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class GUI {

	private JFrame frame;
	private JTextField chatTextField;
	private JTextField textField;
	//private JTextField textField_1;
	
	private JPanel displayPanel;
	private FieldGUI gamePanel;
	//private CardLayout cardLayout;
	
	//Currently shows FieldGUI
	private FieldGUI currentPanel;
	
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
	
	public ArrayList<CardGUI> SelectedCards(){
		return this.selectedCards;
	}
	
	public JFrame Frame(){
		return this.frame;
	}
	
	/**
	 * Create the application.
	 */
	public GUI() {;
		
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		gamePanelCards = new ArrayList<CardGUI>();
		cardsPanelCards = new ArrayList<CardGUI>();
		
		frame = new JFrame();
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

		Deck deck = new Deck();
		player.SetIlluminati(deck.drawIlluminati());
		
		CardGUI illuminatiGUI = new CardGUI(player.Illuminati(), 5, 10);
		addEventHandler(illuminatiGUI);
		
		//Initialize Game Panel
		displayPanel = new JPanel();
		displayPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		displayPanel.setBackground(new Color(255, 255, 240));
		displayPanel.setBounds(6, 6, 1049, 583);
		panel.add(displayPanel);
		displayPanel.setLayout(null);
		
		gamePanel = new FieldGUI(illuminatiGUI);
		displayPanel.add(gamePanel);
		
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
				player.DrawCard(deck);
				GenerateHand(player, cardsPanel);
				frame.repaint();
			}
		});
		
		/*textField_1 = new JTextField();
		textField_1.setText("$20");
		textField_1.setBackground(UIManager.getColor("Button.select"));
		textField_1.setBounds(470, 402, 35, 26);
		gamePanel.add(textField_1);
		textField_1.setColumns(10);*/
		
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
		
		String[] fieldOptions = {"Player 1", "Player 2", "Player 3", "Uncontrolled Area"};
		JComboBox fieldSelection = new JComboBox(fieldOptions);
		fieldSelection.setBounds(6, 36, 222, 25);
		rightSidePanel.add(fieldSelection);
		fieldSelection.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String response = (String) fieldSelection.getSelectedItem();
				response = response.substring(response.length() - 1);
				int playerNum = Integer.parseInt(response) - 1;
				displayPanel.removeAll();
				
				displayPanel.add(GamePlay.players.get(playerNum).GUI().gamePanel);
				frame.revalidate();
				frame.repaint();
				displayPanel.revalidate();
				displayPanel.repaint();
				currentPanel = GamePlay.players.get(playerNum).GUI().gamePanel;
				System.out.println(playerNum);
				//TODO - Finish switching game panels (uncontrolled area)
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
		
		JTextPane chatPane = new JTextPane();
		chatPane.setText("Player 1: Hi there.\nPlayer 1: I wish you the best, but i will win.\nPlayer 2: I dont think so.\nPlayer 1: haha will see.\nPlayer 2: kk\nPlayer 3: can you please hurry up and play");
		chatPane.setBounds(6, 280, 220, 423);
		rightSidePanel.add(chatPane);
		chatPane.setForeground(Color.BLACK);
		chatPane.setBackground(Color.WHITE);
		
		chatTextField = new JTextField();
		chatTextField.setBounds(6, 710, 220, 26);
		rightSidePanel.add(chatTextField);
		chatTextField.setColumns(10);
		
		JButton clearButton = new JButton("Clear");
		clearButton.setBounds(133, 731, 93, 29);
		rightSidePanel.add(clearButton);
		
		JButton sendButton = new JButton("Send");
		sendButton.setBounds(6, 731, 130, 29);
		rightSidePanel.add(sendButton);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
			}
		});
		
		JPanel playerActionsPanel = new JPanel();
		playerActionsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		playerActionsPanel.setBounds(760, 601, 295, 171);
		panel.add(playerActionsPanel);
		playerActionsPanel.setLayout(null);
		
		JButton btnNext = new JButton("Transfer Money");
		btnNext.setBounds(153, 38, 130, 29);
		playerActionsPanel.add(btnNext);
		
		JLabel lblCardActionsJ = new JLabel("Player Actions ");
		lblCardActionsJ.setBounds(6, 6, 121, 20);
		playerActionsPanel.add(lblCardActionsJ);
		lblCardActionsJ.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		JButton btnAttack = new JButton("Move Money");
		btnAttack.setBounds(10, 67, 130, 29);
		playerActionsPanel.add(btnAttack);
		
		JButton btnGiveAGroup = new JButton("Give a Group");
		btnGiveAGroup.setBounds(153, 67, 130, 29);
		playerActionsPanel.add(btnGiveAGroup);
		
		JButton btnDropAGroup = new JButton("Give a Card");
		btnDropAGroup.setBounds(153, 96, 130, 29);
		playerActionsPanel.add(btnDropAGroup);
		
		JButton btnAttack_1 = new JButton("Attack");
		btnAttack_1.setBounds(11, 38, 130, 29);
		playerActionsPanel.add(btnAttack_1);
		btnAttack_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
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
		});
		
		JButton btnUseASpecial = new JButton("Use a Specialty ");
		btnUseASpecial.setBounds(10, 96, 130, 29);
		playerActionsPanel.add(btnUseASpecial);
		
		JButton button = new JButton("Drop a Group");
		button.setBounds(10, 136, 130, 29);
		playerActionsPanel.add(button);
		
		JButton btnNextTurn = new JButton("Done");
		btnNextTurn.setBounds(153, 136, 130, 29);
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
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
	
	public void GenerateHand(Player player, JPanel panel){
		panel.removeAll();
		panel.updateUI();
		ArrayList<GroupCard> groups = player.GroupCards();
		ArrayList<SpecialCard> specials = player.SpecialCards();
		
		cardsPanelCards = new ArrayList<CardGUI>();
		selectedCards = new ArrayList<CardGUI>();
		
		int xOffset = 4;
		
		for(Card card: groups){
			CardGUI cardGUI = new CardGUI(card, panel, xOffset, 10);
			cardGUI.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					if(!selectedCards.contains(cardGUI)){
						selectedCards.add(cardGUI);
					}else{
						selectedCards.remove(cardGUI);
					}
				}
			});
			panel.add(cardGUI);
			cardsPanelCards.add(cardGUI);
			
			xOffset += 154;
		}
		for(Card card: specials){
			CardGUI cardGUI = new CardGUI(card, panel, xOffset, 10);
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
		gamePanel.addCard(childGUI, new CardGUI(parent, gamePanel, 0, 0));
		gamePanelCards.add(childGUI);
		
		return childGUI;
	}
	
	private void addEventHandler(CardGUI card){
		card.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				if(Global.selectionPhase == Global.SelectionPhase.ADD_CHILD){
					parentControlArrowPopup((StructureCard) card.Card());
				}else if(Global.selectionPhase == Global.SelectionPhase.SELECT_ATTACKING_GROUP){
					designateAttackingGroup((StructureCard) card.Card());
				}else if(Global.selectionPhase == Global.SelectionPhase.SELECT_AIDING_GROUPS){
					designateAidingGroup((StructureCard) card.Card());
				}
			}
		});
	}
	
	//-------------------------------------Card Selection Effects-------------------------------
	
	private void designateAttackingGroup(StructureCard card){
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
		cornerPrompt = new JLabel(s);
		cornerPrompt.setBounds(6, 6, 500, 25);
		cornerPrompt.setFont(cornerPrompt.getFont().deriveFont(20.0f));
		cornerPrompt.setForeground(new Color(255, 50, 50));
		gamePanel.add(cornerPrompt);
	}
	
	//Removes the label in the top left corner
	public void removePrompt(){
		cornerPrompt.setVisible(false);
		cornerPrompt = null;
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
	
	public void parentControlArrowPopup(StructureCard parent){
		if(selectedCards.size() > 0){
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
				
				buttons.get(i).addActionListener(new ArrowButtonListener(parent, actionArrow, popup));
				
				yOffset += 40;
			}
			
			yOffset += 50;
			
			JButton btnCancel = CreateCancelButton(popup);
			popupPanel.add(btnCancel);
			
			frame.repaint();
		}
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
			input = (String) JOptionPane.showInputDialog(frame, "How much money will the attacking group spend on the attack? (Amount of MB in treasury: " + attackingGroup.CurrentMoney() + ")", "Spend Money", JOptionPane.PLAIN_MESSAGE, null, null, "0");
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
		
		attackingGroup.removeMoney(groupVal);
		
		int illuminatiVal = 0;
		
		if(illuminatiCard != attackingGroup){
			illuminatiVal = Integer.MAX_VALUE;
			
			while(illuminatiVal == Integer.MAX_VALUE){
				input = (String) JOptionPane.showInputDialog(frame, "How much money will the Illuminati spend on the attack? (Amount of MB in treasury: " + illuminatiCard.CurrentMoney() + ")", "Spend Money", JOptionPane.PLAIN_MESSAGE, null, null, "0");
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
			input = (String) JOptionPane.showInputDialog(frame, "How much money will the defennding group spend on the attack? (Amount of MB in treasury: " + defendingGroup.CurrentMoney() + ")", "Spend Money", JOptionPane.PLAIN_MESSAGE, null, null, "0");
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
				input = (String) JOptionPane.showInputDialog(frame, "How much money will the Illuminati spend on the attack? (Amount of MB in treasury: " + illuminatiCard.CurrentMoney() + ")", "Spend Money", JOptionPane.PLAIN_MESSAGE, null, null, "0");
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
				input = (String) JOptionPane.showInputDialog(frame, "How much money will your Illuminati spend on assisting the attack? (Amount of MB in treasury: " + illuminatiCard.CurrentMoney() + ")", "Spend Money", JOptionPane.PLAIN_MESSAGE, null, null, "0");
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
					input = (String) JOptionPane.showInputDialog(frame, "How much money will your Illuminati spend on interfering in the attack? (Amount of MB in treasury: " + illuminatiCard.CurrentMoney() + ")", "Spend Money", JOptionPane.PLAIN_MESSAGE, null, null, "0");
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
		
		public ArrowButtonListener(StructureCard parent, StructureCard.Arrow arrow, JFrame popup){
			this.arrow = arrow;
			this.parent = parent;
			this.popup = popup;
		}		
		
		public void actionPerformed(ActionEvent e){
			System.out.println(arrow);
			CardGUI child = addChild(parent, (GroupCard) selectedCards.get(0).Card(), arrow);
			
			//Will be unnecessary, simply for basic playability
			player.RemoveGroupCard((GroupCard) selectedCards.get(0).Card());
			GenerateHand(player, cardsPanel);
			//End unnecessary parts
			
			child.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					if(Global.selectionPhase == Global.SelectionPhase.ADD_CHILD){
						parentControlArrowPopup((StructureCard) child.Card());
					}else if(Global.selectionPhase == Global.SelectionPhase.SELECT_ATTACKING_GROUP){
						designateAttackingGroup((StructureCard) child.Card());
					}else if(Global.selectionPhase == Global.SelectionPhase.SELECT_AIDING_GROUPS){
						designateAidingGroup((StructureCard) child.Card());
					}
					//selectedCard = null;
				}
			});
			
			((GroupCard) child.Card()).attackCounter = 1;
			
			popup.setVisible(false);
			popup.dispose();
		}
	}
	
	//-----------------------------------------Other UI Elements--------------------------------------------
	private JButton btnEndAttack;
	
	//Create a label to show the attack modifier as the players' actions update it FOR ALL PLAYERS
	public static void outputAttackModifier(ArrayList<Player> players){
		
		for(Player p: players){
			p.GUI().modifierPrompt = new JLabel("Attack modifier: " + GamePlay.attackModifier);
			p.GUI().modifierPrompt.setBounds(850, 6, 500, 25);
			p.GUI().modifierPrompt.setFont(p.GUI().modifierPrompt.getFont().deriveFont(20.0f));
			p.GUI().modifierPrompt.setForeground(new Color(255, 50, 50));
			p.GUI().gamePanel.add(p.GUI().modifierPrompt);
			
			p.GUI().frame.repaint();
		}
	}
	
	public void endAttackButton(){
		btnEndAttack = new JButton("End Attack");
		btnEndAttack.setBounds(new Rectangle(910, 540, 120, 30));
		
		btnEndAttack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				btnEndAttack.setVisible(false);
				btnEndAttack = null;
				
				//TODO - Actually end the attack
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
			btnEndAttack.setVisible(false);
			btnEndAttack = null;
		}
	}
	
	public void refreshAll(){
		GenerateHand(player, cardsPanel);
	}
}
