package application;
	
import java.net.URL;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GUIManager {
	
	private static GUIManager instance;
	
	private Stage theStage;
    /*
     * Title Scene Variables
     */
    private Scene TitleScene;
    
    //private Label HighscoreLabel;
    
    /*
     * Gameplay Scene Variables
     */
    private Scene GameplayScene;
    
    private Label ScoreLabel;
    private Label LevelLabel;
    
    private static int GridSize = 8;
    private ImageView[][] TileGrid;
    private ImageView[][] EntityGrid;
    private ImageView[][] MapGrid;
    
    private ImageView SwordIcon;
    private ImageView KeyIcon;
    
    /*
     * Continue Scene
     * 
     */
    private Scene ContinueScene;
    
    /*
     * GameOver Scene
     * 
     */
    private Scene GameoverScene;
    private Label FinalscoreLabel;
    
    private URL ImageDirectory; //Temporary: this is a quick work around.
    //Ideally the image should be able to be located without having to deal with the directory.
    
    /*
     * Constructor for GUI Manager
     * 
     * This constructor is LARGE!
     * It will generate all the neccessary scenes needed for the software to function!
     */
	public GUIManager() {
		
		instance = this;
		/* 
		 * TITLE SCREEN
		 * 
		 */
		ImageDirectory = getClass().getResource("/application/Graphics/");
		// Create title label
        Label titleLabel = new Label("Alex's Dungeon Wanderer");
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));

        // Create start button
        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            theStage = (Stage) startButton.getScene().getWindow();
            GameManager.GetInstance().ResetGame();
            setScene(GameplayScene); 
            // Example functionality, adapt as needed
        });

        // Create a label for highscore
        //HighscoreLabel = new Label("High Score: 1000");
        
        // Add highscore label, title label, and start button to a vertical box
        VBox titleScreen = new VBox(20);
        titleScreen.setAlignment(Pos.CENTER);
        titleScreen.getChildren().addAll(titleLabel, startButton);
        
        // Load and create an ImageView for the logo
        ImageView logoView = new ImageView();
        logoView.setImage(new Image(ImageDirectory + "logo.png"));
        logoView.setFitWidth(100);  // Set the desired width
        logoView.setPreserveRatio(true);  // Preserve the aspect ratio of the image
        
        // Position the logo in a horizontal box aligned to the bottom-right
        HBox logoContainer = new HBox();
        logoContainer.setAlignment(Pos.BOTTOM_RIGHT);
        logoContainer.getChildren().add(logoView);
        logoContainer.setPadding(new javafx.geometry.Insets(0, 20, 20, 0));  // Add padding to move the logo away from edges

        // Use StackPane to layer titleScreen and the logo
        StackPane root = new StackPane();
        root.getChildren().addAll(logoContainer, titleScreen);
        StackPane.setAlignment(logoContainer, Pos.BOTTOM_RIGHT);  // Align the logo to the bottom-right corner

        // Create a scene and add the root stack pane to it
        TitleScene = new Scene(root, 640, 480);
        TitleScene.setFill(Color.LIGHTGRAY);
        TitleScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
         
        
        
        /* 
		 * GAMEPLAY SCREEN
		 * 
		 */
        
     // Create score label
        ScoreLabel = new Label("Score: 0");
        ScoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));

        // Create grid for the current room's tile layout
        GridPane tileGrid = createTileGrid();
        tileGrid.setAlignment(Pos.CENTER);
        fillGridPane(tileGrid, ImageDirectory + "phTile.png");
        
        // Create grid for the current room's entity positions
        GridPane entityGrid = createEntityGrid();
        entityGrid.setAlignment(Pos.CENTER);
        fillGridPane(entityGrid, ImageDirectory + "empty.png");
        
        // Create a stack pane to stack the entities on top of the tiles
        StackPane gameplayGrid = new StackPane();
        gameplayGrid.getChildren().addAll(tileGrid,entityGrid);

        // Create minimap grid
        GridPane minimapGrid = createMinimapGrid();
        minimapGrid.setStyle("-fx-border-color: #c6baac;");
        fillGridPane(minimapGrid, ImageDirectory + "empty.png");

        // Create level label
        LevelLabel = new Label("Level 1");
        LevelLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        // Create top HBox with score label
        HBox topBox = new HBox(10);
        topBox.setAlignment(Pos.TOP_CENTER);
        topBox.getChildren().add(ScoreLabel);

        // Create bottom VBox with minimap grid and level label
        VBox bottomBox = new VBox(10);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.getChildren().addAll(LevelLabel, minimapGrid);
        
        VBox gameplayBox = new VBox(10);
        gameplayBox.setAlignment(Pos.CENTER);
        gameplayBox.getChildren().add(gameplayGrid);
        
        //VBox gameplayBox = new VBox(10);
        //bottomBox.setAlignment(Pos.CENTER);
        //bottomBox.getChildren().addAll(gameplayGrid);
        
        Label itemsLabel = new Label("Items Collected");
        itemsLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        // create the image views
        SwordIcon = new ImageView(ImageDirectory + "empty.png");
        KeyIcon = new ImageView(ImageDirectory + "empty.png");
        
        SwordIcon.setFitWidth(48);
        SwordIcon.setFitHeight(48);
        
        KeyIcon.setFitWidth(48);
        KeyIcon.setFitHeight(48);

        // encapsulate the image views in a VBox
        HBox itemsHBox = new HBox(10);
     	itemsHBox.getChildren().addAll(SwordIcon,KeyIcon);
     	itemsHBox.setAlignment(Pos.CENTER);

     	// encapsulate the label and VBox in another VBox
     	VBox itemsCollectedVBox = new VBox(10);
     	itemsCollectedVBox.getChildren().addAll(itemsLabel, itemsHBox);
     	itemsCollectedVBox.setAlignment(Pos.CENTER);

        // Create BorderPane with gameplay grid on the right and top and bottom boxes on the left
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gameplayBox);
        borderPane.setTop(topBox);
        borderPane.setLeft(bottomBox);
        borderPane.setRight(itemsCollectedVBox);
        BorderPane.setMargin(gameplayGrid, new Insets(10, 10, 10, 10));
        BorderPane.setMargin(topBox, new Insets(10, 10, 10, 10));
        BorderPane.setMargin(bottomBox, new Insets(10, 10, 10, 10));
        BorderPane.setMargin(itemsCollectedVBox, new Insets(10, 10, 10, 10));
        borderPane.autosize();
        

        // Create scene
        GameplayScene = new Scene(borderPane, 720, 515);
        GameplayScene.setFill(Color.LIGHTGRAY);
        GameplayScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        
        
        
        /* 
		 * LEVEL COMPLETE SCREEN
		 * 
		 */
        Label completeLabel = new Label("Level Complete!");
        completeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        completeLabel.setTextAlignment(TextAlignment.CENTER); // Center the text

        // Create a button with the label "Play Next Level"
        Button continueButton = new Button("Play Next Level");

        // Create a VBox to hold the label and button
        VBox continueVbox = new VBox(20); // Set the spacing between the components
        continueVbox.getChildren().addAll(completeLabel, continueButton); // Add the label and button to the VBox
        continueVbox.setAlignment(Pos.CENTER);
        continueButton.setOnAction(e -> {
        	GameManager.GetInstance().GenerateNewLevel();
        	setScene(GameplayScene);
        });

        // Create a scene with the VBox as the root node
        ContinueScene = new Scene(continueVbox, 640, 480); // Set the width and height of the scene
        ContinueScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        
        
        
        /* 
		 * GAME OVER SCREEN
		 * 
		 */
        Label gameoverLabel = new Label("GAME OVER!\n >YOU DIED<");
        gameoverLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        gameoverLabel.setTextAlignment(TextAlignment.CENTER); // Center the text

        // Create a button with the label "Play Next Level"
        Button gameoverButton = new Button("Return To Title");
        
        // Create a label that displays your score after you die
        FinalscoreLabel = new Label("Final Score: 0");
        FinalscoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        // Create a VBox to hold the label and button
        VBox gameoverVbox = new VBox(20); // Set the spacing between the components
        gameoverVbox.getChildren().addAll(gameoverLabel, FinalscoreLabel, gameoverButton); // Add the two labels and button to the VBox
        gameoverVbox.setAlignment(Pos.CENTER);
        gameoverButton.setOnAction(e -> {
        	setScene(TitleScene);
        });

        // Create a scene with the VBox as the root node
        GameoverScene = new Scene(gameoverVbox, 640, 480); // Set the width and height of the scene
        GameoverScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
        
    }//
	
	/*
	 * Returns the current instance of GUIManager
	 */
	public static GUIManager getInstance() {
		return instance;
	}
	

	public void DrawRoomEntities(List<GridEntity> objs) {
		for(int i = 0; i < GridSize; i++){
			for(int j = 0; j < GridSize; j++) {
				 EntityGrid[i][j].setImage(new Image(ImageDirectory + "empty.png"));
			}
		}
		for(GridEntity e : objs)
			DrawEntity(e.GetX(),e.GetY(),e.GetChar());
	}
	public void DrawEntity(int x, int y, char c) {
		if(x > GridSize - 1 || x < 0 || y > GridSize - 1 || y < 0)return;
	         switch(c) {
	         case (' ')://empty
	         	 EntityGrid[x][y].setImage(new Image(ImageDirectory + "empty.png"));
	         break;
	         case ('?')://player
	        	 EntityGrid[x][y].setImage(new Image(ImageDirectory + "phEntity.png"));
	         break;
	         case ('*')://key
	        	 EntityGrid[x][y].setImage(new Image(ImageDirectory + "key.png"));
	         break;
	         case ('#')://item
	        	 EntityGrid[x][y].setImage(new Image(ImageDirectory + "item.png"));
	         break;
	         case ('/')://sword
	        	 EntityGrid[x][y].setImage(new Image(ImageDirectory + "sword.png"));
	         break;
	         case ('!')://enemy
	        	 EntityGrid[x][y].setImage(new Image(ImageDirectory + "enemy.png"));
	         break;

	       }
	    }
	/*
	 * This function takes in a char 2d array and draws the game grid accordingly
	 * 
	 * @param layout This should be coming from a Room object (Room.getLayout();)
	 */
	public void DrawRoom(char[][] layout) {
  	  for (int x = 0; x < GridSize; x++) {
            for (int y = 0; y < GridSize; y++) {
          	  switch(layout[x][y]) {
          	  case ('W'):
          		  TileGrid[x][y].setImage(new Image(ImageDirectory + "wall.png"));
          		  break;
          	  case ('E'):
        		  TileGrid[x][y].setImage(new Image(ImageDirectory + "end.png"));
        		  break;
          	  case ('F'):
          		  TileGrid[x][y].setImage(new Image(ImageDirectory + "floor.png"));
      		  	break;
          	  }
          }
  	  }
 }
  	  
  	public void DrawMiniMap(Level lvl, int X, int Y) {
   for (int i = -2; i < 3; i++) {
    	for (int j = -2; j < 3; j++) {
    		if(i == 0 && j == 0)
    			MapGrid[i + 2][j + 2].setImage(new Image(ImageDirectory + "curMap.png"));
    		else {
    			if(lvl.containsRoomAt(X + i, Y - j))
					  MapGrid[i + 2][j + 2].setImage(new Image(ImageDirectory + "phMap.png"));
    			else
					MapGrid[i + 2][j + 2].setImage(new Image(ImageDirectory + "empty.png"));
    			}
    		}
        }
  	}
	
	/*
	 * Helper functions for ConstructingGUIs
	 */
	
	private GridPane createTileGrid() {
		TileGrid = new ImageView[GridSize][GridSize];
        GridPane gridPane = new GridPane();
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        for (int x = 0; x < GridSize; x++) {
            for (int y = 0; y < GridSize; y++) {
                ImageView imageView = new ImageView();
                imageView.setFitWidth(48);
                imageView.setFitHeight(48);
                gridPane.add(imageView, x, y);
                TileGrid[x][y] = imageView;
            }
        }
        return gridPane;
    }
	
	private GridPane createEntityGrid() {
		EntityGrid = new ImageView[GridSize][GridSize];
        GridPane gridPane = new GridPane();
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        for (int x = 0; x < GridSize; x++) {
            for (int y = 0; y < GridSize; y++) {
                ImageView imageView = new ImageView();
                imageView.setFitWidth(48);
                imageView.setFitHeight(48);
                gridPane.add(imageView, x, y);
                EntityGrid[x][y] = imageView;
            }
        }
        return gridPane;
    }
	
	private GridPane createMinimapGrid() {
		MapGrid = new ImageView[5][5];
        GridPane gridPane = new GridPane();
        gridPane.setHgap(2);
        gridPane.setVgap(2);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                ImageView imageView = new ImageView();
                imageView.setFitWidth(24);
                imageView.setFitHeight(24);
                gridPane.add(imageView, x, y);
                MapGrid[x][y] = imageView;
            }
        }
        return gridPane;
    }

    private void fillGridPane(GridPane gridPane, String tileImageFile) {
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            ImageView imageView = (ImageView) gridPane.getChildren().get(i);
            imageView.setImage(new Image(tileImageFile));
        }
    }   
    
    public void UpdateGUIStats() {
    	GameManager GM = GameManager.GetInstance();
    	//Key Icon
    	if(GM.isKeyHeld())
    		KeyIcon.setImage(new Image(ImageDirectory + "key.png"));
    	else
    		KeyIcon.setImage(new Image(ImageDirectory + "empty.png"));
    	
    	//Sword Icon
    	if(GM.isSwordHeld())
    		SwordIcon.setImage(new Image(ImageDirectory + "sword.png"));
    	else
    		SwordIcon.setImage(new Image(ImageDirectory + "empty.png"));
    	//Score
    	ScoreLabel.setText("Score: " + GM.getScore());
    	//HighscoreLabel.setText("Score: " + GM.getHighScore());
    	FinalscoreLabel.setText("Final Score: " + GM.getScore());
    	LevelLabel.setText("Level " + GM.getLevel());
    }
    
    /*
     * Getters and Setters
     */
    public int getGridSize() {
    	return GridSize;
    }
    public void setScene(Scene s ) {
    	theStage.hide();
    	theStage.setScene(s);
    	theStage.show();
    }
    public Scene getGameplayScene() {
    	return GameplayScene;
    }
    
    public Scene getTitleScene() {
    	return TitleScene;
    }
    
    public Scene getLevelCompleteScene() {
    	return ContinueScene;
    }
    public Scene getGameOverScene() {
    	return GameoverScene;
    }
}
