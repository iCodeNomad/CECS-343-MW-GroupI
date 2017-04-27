import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
	
	//CHANGE ME BACK LATER
	//public static final String IMAGE_PATH = "img/";
	public static final String IMAGE_PATH = "C:/Users/Ryan/eclipse/workspace/Illuminati2/src/img/";
	
	
	//Class Attributes
	private ArrayList<MainDeckCard> deck;
	private ArrayList<IlluminatiCard> illuminatiDeck;
	
	private Random random;
	
	//Constructor
	public Deck() {
		deck = new ArrayList<MainDeckCard>();
		illuminatiDeck = new ArrayList<IlluminatiCard>();
		
		createDeck();
	}
	
	//Creates the cards and places them in the deck
	//Called once in the constructor
	private void createDeck(){
		
		//******************Ryan will add the following:****************************
				//DONE ALREADY - NOT IN PAGE ORDER: Nuclear Power Companies, The Mafia
				//NEED TO ADD Orbital Mind Control Lasers, Punk Rockers, Post Office
		
		//---------------------------------CREATE GROUP CARDS-----------------------------------------------
		BasicGroupAbility ability;
		ArrayList<GroupCard.Arrow> outwardArrows;
		GroupCard groupCard;
		ArrayList<GroupCard.Alignment> alignments;
		
		//American Autoduel Association
		outwardArrows = new ArrayList<GroupCard.Arrow>();
		outwardArrows.add(GroupCard.Arrow.RIGHT);
		alignments = new ArrayList<GroupCard.Alignment>();
		alignments.add(GroupCard.Alignment.VIOLENT);
		alignments.add(GroupCard.Alignment.WEIRD);
		groupCard = new GroupCard("American Autoduel Association", 1, 0, 5, 1, IMAGE_PATH + "AmericanAutoduelAssociation.png", GroupCard.Arrow.LEFT, outwardArrows, alignments);				
		deck.add(groupCard);
		
		//Nuclear Power Companies
		outwardArrows = new ArrayList<GroupCard.Arrow>();
		outwardArrows.add(GroupCard.Arrow.LEFT);
		groupCard = new GroupCard("Nuclear Power Companies", 4, 0, 4, 3, IMAGE_PATH + "NuclearPowerCompanies.png", GroupCard.Arrow.RIGHT, outwardArrows, GroupCard.Alignment.CONSERVATIVE);				
		deck.add(groupCard);
		GroupCard nuclearPowerCompanies = groupCard;
		
		//Anti-Nuclear Activists
		ability = new BasicGroupAbility(Global.AbilityType.ANY_ATTEMPT, nuclearPowerCompanies, Global.AttackType.DESTROY, 2);
		outwardArrows = new ArrayList<GroupCard.Arrow>();
		outwardArrows.add(GroupCard.Arrow.TOP);
		groupCard = new GroupCard("Anti-Nuclear Activists", 2, 0, 5, 1, IMAGE_PATH + "Anti-NuclearActivists.png", GroupCard.Arrow.LEFT, outwardArrows, GroupCard.Alignment.LIBERAL, ability);				
		deck.add(groupCard);
		
		//Antiwar Activists
		alignments = new ArrayList<GroupCard.Alignment>();
		alignments.add(GroupCard.Alignment.PEACEFUL);
		alignments.add(GroupCard.Alignment.LIBERAL);
		groupCard = new GroupCard("Antiwar Activists", 0, 0, 3, 1, IMAGE_PATH + "AntiwarActivists.png", GroupCard.Arrow.LEFT, null, alignments);				
		deck.add(groupCard);
		
		//Big Media
		outwardArrows = new ArrayList<GroupCard.Arrow>();
		outwardArrows.add(GroupCard.Arrow.TOP);
		outwardArrows.add(GroupCard.Arrow.LEFT);
		outwardArrows.add(GroupCard.Arrow.BOTTOM);
		alignments = new ArrayList<GroupCard.Alignment>();
		alignments.add(GroupCard.Alignment.LIBERAL);
		alignments.add(GroupCard.Alignment.STRAIGHT);
		groupCard = new GroupCard("Big Media", 4, 3, 6, 3, IMAGE_PATH + "BigMedia.png", GroupCard.Arrow.RIGHT, outwardArrows, alignments);				
		deck.add(groupCard);
		
		//Boy Sprouts
		alignments = new ArrayList<GroupCard.Alignment>();
		alignments.add(GroupCard.Alignment.STRAIGHT);
		alignments.add(GroupCard.Alignment.PEACEFUL);
		groupCard = new GroupCard("Boy Sprouts", 0, 0, 3, 1, IMAGE_PATH + "BoySprouts.png", GroupCard.Arrow.LEFT, null, alignments);				
		deck.add(groupCard);
		
		//California
		outwardArrows = new ArrayList<GroupCard.Arrow>();
		outwardArrows.add(GroupCard.Arrow.TOP);
		outwardArrows.add(GroupCard.Arrow.BOTTOM);
		alignments = new ArrayList<GroupCard.Alignment>();
		alignments.add(GroupCard.Alignment.LIBERAL);
		alignments.add(GroupCard.Alignment.WEIRD);
		alignments.add(GroupCard.Alignment.GOVERNMENT);
		groupCard = new GroupCard("California", 5, 0, 4, 5, IMAGE_PATH + "California.png", GroupCard.Arrow.LEFT, outwardArrows, alignments);				
		deck.add(groupCard);
		
		//CFL-AIO
		outwardArrows = new ArrayList<GroupCard.Arrow>();
		outwardArrows.add(GroupCard.Arrow.TOP);
		outwardArrows.add(GroupCard.Arrow.BOTTOM);
		outwardArrows.add(GroupCard.Arrow.LEFT);
		groupCard = new GroupCard("CFL-AIO", 6, 0, 5, 3, IMAGE_PATH + "CFL-AIO.png", GroupCard.Arrow.RIGHT, outwardArrows, GroupCard.Alignment.LIBERAL);				
		deck.add(groupCard);
		
		//Chinese Campaign Donors
		outwardArrows = new ArrayList<GroupCard.Arrow>();
		outwardArrows.add(GroupCard.Arrow.TOP);
		outwardArrows.add(GroupCard.Arrow.BOTTOM);
		groupCard = new GroupCard("Chinese Campaign Donors", 3, 0, 2, 3, IMAGE_PATH + "ChineseCampaignDonors.png", GroupCard.Arrow.RIGHT, outwardArrows, GroupCard.Alignment.COMMUNIST);				
		deck.add(groupCard);
		
		//C.I.A.
		outwardArrows = new ArrayList<GroupCard.Arrow>();
		outwardArrows.add(GroupCard.Arrow.TOP);
		outwardArrows.add(GroupCard.Arrow.BOTTOM);
		outwardArrows.add(GroupCard.Arrow.LEFT);
		alignments = new ArrayList<GroupCard.Alignment>();
		alignments.add(GroupCard.Alignment.GOVERNMENT);
		alignments.add(GroupCard.Alignment.VIOLENT);
		groupCard = new GroupCard("C.I.A.", 6, 4, 5, 0, IMAGE_PATH + "CIA.png", GroupCard.Arrow.RIGHT, outwardArrows, alignments);				
		deck.add(groupCard);

		//Clone Arrangers
		outwardArrows = new ArrayList<GroupCard.Arrow>();
		outwardArrows.add(GroupCard.Arrow.RIGHT);
		outwardArrows.add(GroupCard.Arrow.BOTTOM);
		alignments = new ArrayList<GroupCard.Alignment>();
		alignments.add(GroupCard.Alignment.VIOLENT);
		alignments.add(GroupCard.Alignment.COMMUNIST);
		alignments.add(GroupCard.Alignment.CRIMINAL);
		groupCard = new GroupCard("Clone Arrangers", 6, 2, 6, 1, IMAGE_PATH + "CloneArrangers.png", GroupCard.Arrow.LEFT, outwardArrows, alignments);				
		deck.add(groupCard);
		
		
		
		
		
		//The Mafia
		ability = new BasicGroupAbility(Global.AbilityType.DIRECT_CONTROL, GroupCard.Alignment.CRIMINAL, Global.AttackType.CONTROL, 3);
		outwardArrows = new ArrayList<GroupCard.Arrow>();
		outwardArrows.add(GroupCard.Arrow.TOP);
		outwardArrows.add(GroupCard.Arrow.LEFT);
		outwardArrows.add(GroupCard.Arrow.BOTTOM);
		groupCard = new GroupCard("The Mafia", 7, 0, 7, 6, IMAGE_PATH + "TheMafia.png", GroupCard.Arrow.RIGHT, outwardArrows, GroupCard.Alignment.CRIMINAL, ability);				
		deck.add(groupCard);
		
		//------------------------------CREATE ILLUMINATI CARDS----------------------------------------------
		
		IlluminatiCard illuminatiCard;
		
		//The Society of Assassins
		illuminatiCard = new IlluminatiCard("The Society of Assassins", 8, 8, 8, IMAGE_PATH + "TheSocietyOfAssassins.png");
		illuminatiDeck.add(illuminatiCard);
		
		//The Network
		illuminatiCard = new IlluminatiCard("The Network", 7, 7, 9, IMAGE_PATH + "TheNetwork.png");
		illuminatiDeck.add(illuminatiCard);
		
		//The Discordian Society
		illuminatiCard = new IlluminatiCard("The Discordian Society", 8, 8, 8, IMAGE_PATH + "TheDiscordianSociety.png");
		illuminatiDeck.add(illuminatiCard);
		
		//The UFOs
		illuminatiCard = new IlluminatiCard("The UFOs", 6, 6, 8, IMAGE_PATH + "TheUFOs.png");
		illuminatiDeck.add(illuminatiCard);
		
		//The Servants of Cthulhu
		illuminatiCard = new IlluminatiCard("The Servants of Cthulhu", 9, 9, 7, IMAGE_PATH + "TheServantsOfCthulhu.png");
		illuminatiDeck.add(illuminatiCard);
		
		//The Gnomes of Zurich
		illuminatiCard = new IlluminatiCard("The Gnomes of Zurich", 7, 7, 12, IMAGE_PATH + "TheGnomesOfZurich.png");
		illuminatiDeck.add(illuminatiCard);
		
		//The Bavarian Illuminati
		illuminatiCard = new IlluminatiCard("The Bavarian Illuminati", 10, 10, 8, IMAGE_PATH + "TheBavarianIlluminati.png");
		illuminatiDeck.add(illuminatiCard);
		
		//The Bermuda Triangle
		illuminatiCard = new IlluminatiCard("The Bermuda Triangle", 8, 8, 9, IMAGE_PATH + "TheBermudaTriangle.png");
		illuminatiDeck.add(illuminatiCard);

		//String name, int power, int transferablePower, int income, String imagePath,
		//  Arrow inwardArrow
		
		//-------------------------------CREATE SPECIAL CARDS------------------------------------------------
		
		SpecialCard specialCard;
		
		//Assassination
		specialCard = new SpecialCard("Assassination", IMAGE_PATH + "Assassination.png");
		deck.add(specialCard);
		
		//Bribery
		specialCard = new SpecialCard("Bribery", IMAGE_PATH + "Bribery.png");
		deck.add(specialCard);
		
		//Computer Espionage
		specialCard = new SpecialCard("Computer Espionage", IMAGE_PATH + "ComputerEspionage.png");
		deck.add(specialCard);
	}
	
	//Randomly returns an IlluminatiCard contained in illuminatiDeck
	//Returns null if illuminatiDeck is empty
	public IlluminatiCard drawIlluminati(){
		if(illuminatiDeck.size() > 0){
			int index = random.nextInt(illuminatiDeck.size());
			return illuminatiDeck.remove(index);
		}
		return null;
	}
	
	//Randomizes the order of the deck
	public void shuffleDeck(){
		Collections.shuffle(deck);
	}
	
	//Removes and returns the top (first) card in the deck.
	//Returns null if deck is empty
	public MainDeckCard drawCard(){
		if(deck.size() > 0){
			return deck.remove(0);
		}
		return null;
	}

	@Override
	public String toString() {
		return "Deck [deck=" + deck + ", illuminatiDeck=" + illuminatiDeck + "]";
	}
}
