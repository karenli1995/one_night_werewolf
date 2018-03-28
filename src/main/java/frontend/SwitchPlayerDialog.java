package main.java.frontend;

import javafx.util.Pair;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SwitchPlayerDialog extends Dialog<Pair<String, String>> {
	private TextField myTf1;
	private TextField myTf2;

	public SwitchPlayerDialog() {
		this.setTitle("Switch Cards");
		this.setHeaderText("Enter a player name or a middle card index number:");
		
		ButtonType doneBtn = new ButtonType("Done", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(doneBtn, ButtonType.CANCEL);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		myTf1 = new TextField();
		myTf2 = new TextField();
		
		grid.add(new Label("Card 1:"), 0, 0);
		grid.add(myTf1, 1, 0);
		grid.add(new Label("Card 2:"), 0, 1);
		grid.add(myTf2, 1, 1);

		this.getDialogPane().setContent(grid);
		
		this.setResultConverter(dialogButton -> {
		    if (dialogButton == doneBtn) {
		        return new Pair<>(myTf1.getText(), myTf2.getText());
		    }
		    return null;
		});
	}
}
