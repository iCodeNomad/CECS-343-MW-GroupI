import java.util.Random;

public final class Die {
	private static final int MIN_ROLL = 2;
	private static final int MAX_ROLL = 12;
	
	private static Random random = new Random();
	
	private Die(){

	}
	
	//Returns a random integer between minRoll and maxRoll (inclusively)
	public static int roll(){
		return random.nextInt(MAX_ROLL - MIN_ROLL + 1) + MIN_ROLL;
	}
}
