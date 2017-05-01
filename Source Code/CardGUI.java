import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CardGUI extends JLabel {

	public enum Rotation{
		UP, RIGHT, DOWN, LEFT;
	}
	
	private static final long serialVersionUID = 1L;
	private Card card;
	private String imgPath;
	private Rectangle rectField;
	
	public Card Card(){
		return this.card;
	}
	
	public Rectangle RectField(){
		return this.rectField;
	}

	public CardGUI(Card card, Rectangle rect) {
		this.card = card;
		this.setBounds(rect);
		this.imgPath = imgPath;
		
		setImage(card.ImagePath());
	}
	
	public void setImage(String newImgPath){
		/*ImageIcon imageIcon = new ImageIcon(newImgPath);
		Image image = imageIcon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
		this.setIcon(new ImageIcon(image));*/
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File(newImgPath));
		}catch(Exception e){
			System.out.println("Error loading image");
		}
		if(image != null){
			image = rotate(image, 180);
			ImageIcon imageIcon = new ImageIcon((Image) image);
			Image scaledImage = imageIcon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
			this.setIcon(new ImageIcon(scaledImage));
		}
	}
	
	public BufferedImage rotate(BufferedImage image, double angle) {
		angle = Math.toRadians(angle);
	    double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
	    int w = image.getWidth(), h = image.getHeight();
	    int newW = (int)Math.floor(w*cos+h*sin), newH = (int) Math.floor(h * cos + w * sin);
	    GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	    BufferedImage result = gc.createCompatibleImage(newW, newH, Transparency.TRANSLUCENT);
	    Graphics2D g = result.createGraphics();
	    g.translate((newW - w) / 2, (newH - h) / 2);
	    g.rotate(angle, w / 2, h / 2);
	    g.drawRenderedImage(image, null);
	    g.dispose();
	    return result;
	}
	
	//Places the card on FieldGUI, based on center point P
	/*public void setOnField(Point p){
		//Add checks so it remains in field
		rectField = new Rectangle(p.x - 2, p.y - 1, 5, 3);
	}*/

}
