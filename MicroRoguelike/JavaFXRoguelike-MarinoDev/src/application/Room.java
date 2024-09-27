package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
	
	private char[][] Layout;
	
	List<GridEntity> Entities;
	List<Cell> Spawns;
	
	//private Room NeighbourUp;
	//private Room NeighbourRight;
	//private Room NeighbourDown;
	//private Room NeighbourLeft;
	
	private int PosX;
	private int PosY;
	private boolean HasBeenEntered;
	
	private int Mid1;
	private int Mid2;
	
	/*
	 * Constructor for a room, automatically generates a default layout
	 */
	public Room(int X, int Y) {
		HasBeenEntered = false;
		
		PosX = X;
		PosY = Y;
		
		Mid1 = (GUIManager.getInstance().getGridSize() / 2);
		Mid2 = (GUIManager.getInstance().getGridSize() / 2) - 1;
		
		Layout = new char[GUIManager.getInstance().getGridSize()][GUIManager.getInstance().getGridSize()];
		Entities = new ArrayList<GridEntity>();
		Spawns = new ArrayList<Cell>();
		
		//Default Room Layout
		for(int i = 0; i < GUIManager.getInstance().getGridSize(); i++) {
			for(int j = 0; j < GUIManager.getInstance().getGridSize(); j++) {
				Layout[i][j] = 'F'; //F == Walkable Floor
			}
		}
		for(int i = 0; i < GUIManager.getInstance().getGridSize(); i++) {
			Layout[GUIManager.getInstance().getGridSize() - 1][i] = 'W';//W == Wall
			Layout[0][i] = 'W';//W == Wall
			
			Layout[i][GUIManager.getInstance().getGridSize() - 1] = 'W';//W == Wall
			Layout[i][0] = 'W';//W == Wall
		}
		Spawns.add(new Cell(2,2));
		Spawns.add(new Cell(2,GUIManager.getInstance().getGridSize() - 3));
		Spawns.add(new Cell(GUIManager.getInstance().getGridSize() - 3,2));
		Spawns.add(new Cell(GUIManager.getInstance().getGridSize() - 3,GUIManager.getInstance().getGridSize() - 3));
		//room variation
		int roomVariation = new Random().nextInt(5);
		if(roomVariation == 2){
			Layout[Mid1][Mid2] = 'W';
			Layout[Mid2][Mid1] = 'W';
			Layout[Mid1][Mid1] = 'W';
			Layout[Mid2][Mid2] = 'W';
		}
		if(roomVariation == 3){
			for(int i = 2; i < GUIManager.getInstance().getGridSize() - 2; i++) {
				Layout[Mid1 + 1][i] = 'W';//W == Wall
				Layout[Mid2 - 1][i] = 'W';//W == Wall
			}
			Spawns.clear();
			Spawns.add(new Cell(2,Mid1 + 2));
			Spawns.add(new Cell(2,Mid2 - 2));
			Spawns.add(new Cell(GUIManager.getInstance().getGridSize() - 3,Mid1 + 2));
			Spawns.add(new Cell(GUIManager.getInstance().getGridSize() - 3,Mid2 - 2));
		}
		if(roomVariation == 4){
			for(int i = 2; i < GUIManager.getInstance().getGridSize() - 2; i++) {
				Layout[i][Mid1 + 1] = 'W';//W == Wall
				Layout[i][Mid2 - 1] = 'W';//W == Wall
			}
			Spawns.clear();
			Spawns.add(new Cell(Mid1 + 2,2));
			Spawns.add(new Cell(Mid2 - 2,2));
			Spawns.add(new Cell(Mid1 + 2,GUIManager.getInstance().getGridSize() - 3));
			Spawns.add(new Cell(Mid2 - 2,GUIManager.getInstance().getGridSize() - 3));
		}
	}
	
	public void SetNeighbours(Level l) {
		if(l.containsRoomAt(PosX, PosY + 1)) {
			//NeighbourUp = l.getRoomAt(PosX, PosY  + 1);
			Layout[Mid2][0] = 'F';
			Layout[Mid1][0] = 'F';
		}
		if(l.containsRoomAt(PosX, PosY - 1)) {
			//NeighbourDown = l.getRoomAt(PosX, PosY  - 1);
			Layout[Mid1][GUIManager.getInstance().getGridSize() - 1] = 'F';
			Layout[Mid2][GUIManager.getInstance().getGridSize() - 1] = 'F';
		}
		if(l.containsRoomAt(PosX - 1, PosY)) {
			//NeighbourLeft = l.getRoomAt(PosX - 1, PosY);
			Layout[0][Mid1] = 'F';
			Layout[0][Mid2] = 'F';
		}
		if(l.containsRoomAt(PosX + 1, PosY)) {
			//NeighbourRight = l.getRoomAt(PosX +1, PosY);
			Layout[GUIManager.getInstance().getGridSize() - 1][Mid1] = 'F';
			Layout[GUIManager.getInstance().getGridSize() - 1][Mid2] = 'F';
		}
	}
	
	public char[][] getLayout(){
		return Layout;
	}
	public char getTileAt(int getX, int getY) {
		return Layout[getX][getY];
	}
	
	public int getX() {
		return PosX;
	}
	public int getY() {
		return PosY;
	}
	
	public List<GridEntity> GetEntities(){
		return Entities;
	}
	
	public void SpawnEntity(int type) {
		//return if there are no possible spawns
		Cell c = Spawns.get(new Random().nextInt(Spawns.size()));
		
		switch(type){
			case 0://Spawn a Key
				Key k = new Key(c.getX(),c.getY(),'*');
				Entities.add(k);				
				break;
			case 1://Spawn a Sword
				Sword s = new Sword(c.getX(),c.getY(),'/');
				Entities.add(s);				
				break;
			case 2://Spawn an Item
				Item i = new Item(c.getX(),c.getY(),'#');
				Entities.add(i);				
				break;
			case 3://Spawn an Enemy		
				Enemy e = new Enemy(c.getX(),c.getY(),'!');
				Entities.add(e);				
				break;
		}
		
		Spawns.remove(c);
	}
	public void MarkEnd() {
		Layout[Mid1][Mid2] = 'E';
		Layout[Mid2][Mid1] = 'E';
		Layout[Mid1][Mid1] = 'E';
		Layout[Mid2][Mid2] = 'E';
	}
	public void CheckForEntered() {
		if(!HasBeenEntered) {
			GameManager.GetInstance().addScore(50);
			HasBeenEntered = true;
		}
	}
}
