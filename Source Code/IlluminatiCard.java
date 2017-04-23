import java.util.ArrayList;

public class IlluminatiCard extends StructureCard {
	
	//Constructor Method
	public IlluminatiCard(String name, int power, int transferablePower, int income, String imagePath,
						  Arrow inwardArrow) {
		
		super(name, power, transferablePower, income, imagePath, inwardArrow);
		
		ArrayList<Arrow> allArrows = new ArrayList<Arrow>();
		
		//Illuminati have outward arrows in every direction
		allArrows.add(Arrow.TOP);
		allArrows.add(Arrow.BOTTOM);
		allArrows.add(Arrow.LEFT);
		allArrows.add(Arrow.RIGHT);
		
		this.outwardArrows = allArrows;
	}
}
