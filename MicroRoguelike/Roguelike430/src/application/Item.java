package application;

public class Item extends GridEntity{

	public boolean PickedUp;
	public Item(int X, int Y, char Char) {
		super(X, Y, Char);
		PickedUp = false;
	}
	
	public void Interact(int X, int Y) {
		if(PickedUp) return;
		if(X == GetX() && Y == GetY()) {
			//Obtain points
			GameManager.GetInstance().addScore(250);
			//Make this entity invisible and uninteractable
			GridChar = ' ';
			PickedUp = true;
		}
	}
}