package application;

public class Sword extends GridEntity{

	public boolean PickedUp;
	public Sword(int X, int Y, char Char) {
		super(X, Y, Char);
		PickedUp = false;
	}
	
	public void Interact(int X, int Y) {
		if(PickedUp) return;
		if(X == GetX() && Y == GetY()) {
			//Obtain the Sword
			if(!GameManager.GetInstance().isSwordHeld()) {
				GameManager.GetInstance().setSwordHeld();
				GameManager.GetInstance().addScore(25);
			}
			else
				GameManager.GetInstance().addScore(250);
			//Make this entity invisible and uninteractable
			GridChar = ' ';
			PickedUp = true;
			//GameManager.GetInstance().GetCurrentLevel().GetCurrentRoom().GetEntities().remove(this);
		}
	}
}