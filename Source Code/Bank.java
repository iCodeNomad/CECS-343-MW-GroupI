
public final class Bank {

	//Static Attributes
	private static int money = 1156;
	
	public static int Money(){
		return money;
	}
	
	private Bank() {
		
	}
	
	//Removes the given amount from money
	//Removes the remaining money if amount > money
	public static void removeMoney(int amount){
		if(amount > money){
			amount = money;
		}
		money -= amount;
	}
	
	//Adds the given amount to money
	public static void addMoney(int amount){
		money += amount;
	}

}
