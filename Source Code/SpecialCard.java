
public class SpecialCard extends Card implements MainDeckCard {

	public SpecialCard(String name, String imagePath) {
		super(name, imagePath);
	}

	@Override
	public String toString() {
		return "SpecialCard [name=" + name + "]" + System.getProperty("line.separator");
	}

}
