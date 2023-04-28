package application;

public class Enemy extends GridEntity{

	private boolean isAlive;
	public Enemy(int X, int Y, char Char) {
		super(X, Y, Char);
		isAlive = true;
	}

	@Override
	public void Interact(int X, int Y) {
		// TODO Auto-generated method stub
		
		if(!isAlive) return;//do nothing if this enemy is dead
		/*
		 * A Star Pathfinding Algorithm
		 * 
		 * Every time the player moves, this enemy will use A* to approach the player
		 */
		
		//Check for what do to
		if(PosX == X && PosY == Y) {
			//The enemy is at ontop of the player!
			LifeOrDeath();
		}
	}
	public void LifeOrDeath() {
		if(GameManager.GetInstance().isSwordHeld()) {
			//die, you have been smited!
			isAlive = false;
			GameManager.GetInstance().removeSword();
			GameManager.GetInstance().addScore(250);
			GridChar = ' ';
			GUIManager.getInstance().DrawEntity(PosX, PosY, '?');//put the player's icon back on the grid because they survived.
		}
		else {
			//the player dies instead
			//simply load the gameover screen
			GUIManager.getInstance().setScene(GUIManager.getInstance().getGameOverScene());
		}
	}
}
