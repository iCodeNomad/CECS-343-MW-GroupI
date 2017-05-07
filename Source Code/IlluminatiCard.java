import java.util.ArrayList;

public class IlluminatiCard extends StructureCard {
	
	private boolean specialUsed;
	
	public boolean SpecialUsed(){
		return this.specialUsed;
	}
	
	public void setSpecialUsed(boolean specialUsed){
		this.specialUsed = specialUsed;
	}
	
	//Constructor Method
	public IlluminatiCard(String name, int power, int transferablePower, int income, String imagePath) {
		
		super(name, power, transferablePower, income, imagePath, null);
		
		ArrayList<Arrow> allArrows = new ArrayList<Arrow>();
		
		//Illuminati have outward arrows in every direction
		allArrows.add(Arrow.TOP);
		allArrows.add(Arrow.BOTTOM);
		allArrows.add(Arrow.LEFT);
		allArrows.add(Arrow.RIGHT);
		
		this.outwardArrows = allArrows;
		this.rotation = Rotation.UP;
		specialUsed = false;
	}

	/*@Override
	public String toString() {
		return "IlluminatiCard [power=" + power + ", transferablePower=" + transferablePower + ", income=" + income
				+ ", inwardArrow=" + inwardArrow + ", outwardArrows=" + outwardArrows + ", children=" + children
				+ ", owner=" + owner + ", attackCounter=" + attackCounter + ", currentMoney=" + currentMoney
				+ ", rotation=" + rotation + ", distanceFromIlluminati=" + distanceFromIlluminati + ", name=" + name
				+ ", imagePath=" + imagePath + "]";
	}*/
	
	
}
