package application;

public class GameManager {

	private static GameManager instance;
	private int Level = 0;
	private int Score = 0;
	private int HighScore = 0;
	private boolean KeyHeld;
	private boolean SwordHeld;
	private Level CurrentLevel;
	private Player thePlayer;
	
	public GameManager() {
		instance = this;
		
		thePlayer = new Player(4,4,'?');
		ResetGame();
	}
	
	public static GameManager GetInstance() {
		return instance;
	}
	
	public Level GetCurrentLevel() {
		return CurrentLevel;
	}
	
	public void GenerateNewLevel() {
		
		KeyHeld = false;
		
		Level++;
		
		CurrentLevel = new Level(2+(Level * 2),Level,1+Level + Level/2);
		
		//draw grid
		if(GUIManager.getInstance() != null)
			GUIManager.getInstance().DrawRoom((CurrentLevel.GetCurrentRoom().getLayout()));
		
		addScore(200);
		//update Gui
		GUIManager.getInstance().UpdateGUIStats();
	}
	
	public void ResetGame() {
		Level = 0;
		Score = -250;//Entering the first room grants you 250 score
		KeyHeld = false;
		SwordHeld = false;
		
		GenerateNewLevel();
	}

	public Player getPlayer() {
		return thePlayer;
	}

	public boolean isKeyHeld() {
		return KeyHeld;
	}

	public void setKeyHeld() {
		KeyHeld = true;
		
		//update GUI
		GUIManager.getInstance().UpdateGUIStats();
	}

	public boolean isSwordHeld() {
		return SwordHeld;
	}

	public void setSwordHeld() {
		SwordHeld = true;
		
		//update GUI
		GUIManager.getInstance().UpdateGUIStats();
	}

	public void addScore(int score) {
		Score += score;
		
		//update GUI
		GUIManager.getInstance().UpdateGUIStats();
	}
	public int getScore() {
		//Run a SQL Query
		return Score;
	}
	public int getLevel() {
		//Run a SQL Query
		return Level;
	}
	public int getHighScore() {
		//Run a SQL Query
		return 0;
	}
}
