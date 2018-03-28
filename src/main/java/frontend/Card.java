package main.java.frontend;

import main.java.characters.Characters;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;

public class Card extends ImageView {
	private static final String myHiddenImagePath = "mystery.jpg";
	private Image myHiddenImage;
	
	private Image myImageRevealed;
	private String myImagePathForReveal;
	
	private String currImagePath;
	
	private String myPlayer;
	private Characters myRole;
	
	public Card(String player, Characters role) {
		currImagePath = myHiddenImagePath;
		myImagePathForReveal = role.toString().toLowerCase() + ".jpg";
		myImageRevealed = new Image(myImagePathForReveal);
		myHiddenImage = new Image(myHiddenImagePath);
		this.setImage(myHiddenImage);
		
		this.setFitWidth(40);
		this.setFitHeight(40);
		this.preserveRatioProperty();
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				flipCard();
			}
		});
						
		myPlayer = player;
		myRole = role;
	}
	
	public void flipCard() {
		if (currImagePath.contains("mystery")) {
			this.setImage(myImageRevealed);
		} else {
			this.setImage(myHiddenImage);
		}
	}
	
	public void setPlayer(String name) {
		myPlayer = name;
	}
	
	public String getPlayer() {
		return myPlayer;
	}

	public Characters getMyRole() {
		return myRole;
	}

	public void setMyRole(Characters myRole) {
		this.myRole = myRole;
		myImagePathForReveal = myRole.toString().toLowerCase() + ".jpg";
	}
}
