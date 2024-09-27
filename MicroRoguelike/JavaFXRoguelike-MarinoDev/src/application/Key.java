package application;

public class Key extends GridEntity{

	public boolean PickedUp;
	public Key(int X, int Y, char Char) {
		super(X, Y, Char);
		PickedUp = false;
	}
	
	public void Interact(int X, int Y) {
		if(PickedUp) return;
		if(X == GetX() && Y == GetY()) {
			//Obtain the key
				GameManager.GetInstance().setKeyHeld();
				GameManager.GetInstance().addScore(25);
			//Make this entity invisible and uninteractable
			GridChar = ' ';
			PickedUp = true;
			//GameManager.GetInstance().GetCurrentLevel().GetCurrentRoom().GetEntities().remove(this);
		}
	}
}
