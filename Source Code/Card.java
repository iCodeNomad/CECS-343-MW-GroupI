
public abstract class Card extends Object {

	//Class Attributes
	protected String name;
	protected String imagePath;
	
	//Accessor Methods
	public String Name(){
		return this.name;
	}
	
	//Constructor Methods
	public Card(String name, String imagePath){
		this.name = name;
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "Card [name=" + name + "]";
	}
}
