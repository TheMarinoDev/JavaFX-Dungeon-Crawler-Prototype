package application;
	
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	GUIManager GUIMan;
	GameManager GameMan;
	
    public void start(Stage primaryStage) {
		
    	GUIMan = new GUIManager();
    	GameMan = new GameManager();
    	
		
    	primaryStage.setResizable(false);
        primaryStage.setTitle("Alex's Dungeon Wander. by: MarinoDev");
        primaryStage.setScene(GUIMan.getTitleScene());
        primaryStage.show();
        
        
        GUIMan.DrawRoom(GameMan.GetCurrentLevel().GetCurrentRoom().getLayout());
    }

    public static void main(String[] args) {
        launch(args);
    }
}