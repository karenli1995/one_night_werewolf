package main.java;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.java.frontend.StartGUI;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
		stage.setResizable(false);
		
		new StartGUI(stage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
