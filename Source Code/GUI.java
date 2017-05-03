import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
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
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class GUI {

	private JFrame frame;
	private JTextField chatTextField;
	private JTextField textField;
	//private JTextField textField_1;
	
	private FieldGUI gamePanel;
	
	//Used to pass an arrow to the mouse handler in the popup
	private StructureCard.Arrow actionArrow;
	
	//Tracks the card selected in the player's hand
	private CardGUI selectedCard;
	
	//Tracks all CardGUI in gamePanel;
	private ArrayList<CardGUI> gamePanelCards;
	
	//Tracks all CardGUI in cardsPanel
	private ArrayList<CardGUI> cardsPanelCards;
	
	public Player player;
	private JPanel cardsPanel;
	
	public CardGUI SelectedCard(){
		return this.selectedCard;
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
		//frame.getBackground(red);
		//frame.setBounds(200, 200, 450, 300);
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
		
		//player = new Player("Joe");
		Deck deck = new Deck();
		player.SetIlluminati(deck.drawIlluminati());
		
		CardGUI illuminatiGUI = new CardGUI(player.Illuminati(), 5, 10);
		addEventHandler(illuminatiGUI);
		gamePanel = new FieldGUI(illuminatiGUI);
		gamePanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		gamePanel.setBackground(new Color(255, 255, 240));
		gamePanel.setBounds(6, 6, 1049, 583);
		panel.add(gamePanel);
		gamePanel.setLayout(null);
		
		gamePanelCards.add(illuminatiGUI);
		
		//GenerateHand(player, cardsPanel);
		
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
		
		JEditorPane logsPane = new JEditorPane();
		logsPane.setText("player 1 logged in.\nplayer 2 logged in.\nplayer 3 logged in.\nGame Starts NOW...\nplayer 1 turn!\nplayer 1 added a card.\nplayer 1 end turn!\nplayer 2 turn!\nplayer 2 added a card.\nplayer 2 end turn!\nplayer 3 turn!\nplayer 3 added a card.");
		logsPane.setBounds(6, 39, 221, 193);
		rightSidePanel.add(logsPane);
		
		JLabel lblChat = new JLabel("Chat");
		lblChat.setBounds(6, 239, 222, 29);
		rightSidePanel.add(lblChat);
		lblChat.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		
		JLabel lblLogs = new JLabel("Logs");
		lblLogs.setBounds(6, 6, 220, 29);
		rightSidePanel.add(lblLogs);
		lblLogs.setFont(new Font("Arial", Font.PLAIN, 20));
		
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
	
	private void GenerateHand(Player player, JPanel panel){
		panel.removeAll();
		panel.updateUI();
		ArrayList<GroupCard> groups = player.GroupCards();
		ArrayList<SpecialCard> specials = player.SpecialCards();
		
		cardsPanelCards = new ArrayList<CardGUI>();
		
		int xOffset = 4;
		
		for(Card card: groups){
			CardGUI cardGUI = new CardGUI(card, panel, xOffset, 10);
			cardGUI.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					if(selectedCard != cardGUI){
						selectedCard = cardGUI;
					}else{
						selectedCard = null;
					}
				}
			});
			panel.add(cardGUI);
			cardsPanelCards.add(cardGUI);
			
			xOffset += 154;
		}
		for(Card card: specials){
			CardGUI cardGUI = new CardGUI(card, panel, xOffset, 10);
			panel.add(cardGUI);
			cardsPanelCards.add(cardGUI);
			
			xOffset += 154;
		}
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
				}
			}
		});
	}
	
	//--------------------------------------Prompts/Popups--------------------------------------
	
	//Variables for Listeners
	private Global.AttackType listAttackType;
	
	private JFrame CreateGenericPopup(){
		JFrame popup = new JFrame();
		popup.setUndecorated(true);
		popup.setSize(300, 500);
		popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		popup.getContentPane().setLayout(new BorderLayout(0, 0));
		popup.repaint();
		
		popup.setVisible(true);
		popup.setAlwaysOnTop(true);
		
		return popup;
	}
	
	private JPanel CreateGenericPanel(){
		JPanel popupPanel = new JPanel();
		popupPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		popupPanel.setBounds(0, 0, 300, 500);
		
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
	
	public void parentControlArrowPopup(StructureCard parent){
		if(selectedCard != null){
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
		
		while(popup.isVisible()){
			try{
				Thread.sleep(200);
			}catch (Exception e){}
		}
		
		frame.repaint();
		System.out.println("METHOD: " + listAttackType);
		if(listAttackType == null){
			return null;
		}else{
			return listAttackType;
		}
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
			CardGUI child = addChild(parent, (GroupCard) selectedCard.Card(), arrow);
			
			//Will be unnecessary, simply for basic playability
			player.RemoveGroupCard((GroupCard) selectedCard.Card());
			GenerateHand(player, cardsPanel);
			//End unnecessary parts
			
			child.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					if(Global.selectionPhase == Global.SelectionPhase.ADD_CHILD){
						parentControlArrowPopup((StructureCard) child.Card());
					}
					//selectedCard = null;
				}
			});
			
			popup.setVisible(false);
			popup.dispose();
		}
	}
}
