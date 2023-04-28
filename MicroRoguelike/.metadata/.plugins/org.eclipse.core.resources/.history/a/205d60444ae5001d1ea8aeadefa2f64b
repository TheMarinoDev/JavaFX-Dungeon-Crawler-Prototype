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
	
	/*
	 * Constructor for a room, automatically generates a default layout
	 */
	public Room(int X, int Y) {
		HasBeenEntered = false;
		
		PosX = X;
		PosY = Y;
		
		Layout = new char[8][8];
		Entities = new ArrayList<GridEntity>();
		Spawns = new ArrayList<Cell>();
		
		//Default Room Layout
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Layout[i][j] = 'F'; //F == Walkable Floor
			}
		}
		for(int i = 0; i < 8; i++) {
			Layout[7][i] = 'W';//W == Wall
			Layout[0][i] = 'W';//W == Wall
			
			Layout[i][7] = 'W';//W == Wall
			Layout[i][0] = 'W';//W == Wall
		}
		
		//Add possible entity spawns
		//Debug purposes
		Spawns.add(new Cell(2,2));
		Spawns.add(new Cell(2,5));
		Spawns.add(new Cell(5,2));
		Spawns.add(new Cell(5,5));
		
	}
	
	public void SetNeighbours(Level l) {
		if(l.containsRoomAt(PosX, PosY + 1)) {
			//NeighbourUp = l.getRoomAt(PosX, PosY  + 1);
			Layout[3][0] = 'F';
			Layout[4][0] = 'F';
		}
		if(l.containsRoomAt(PosX, PosY - 1)) {
			//NeighbourDown = l.getRoomAt(PosX, PosY  - 1);
			Layout[3][7] = 'F';
			Layout[4][7] = 'F';
		}
		if(l.containsRoomAt(PosX - 1, PosY)) {
			//NeighbourLeft = l.getRoomAt(PosX - 1, PosY);
			Layout[0][3] = 'F';
			Layout[0][4] = 'F';
		}
		if(l.containsRoomAt(PosX + 1, PosY)) {
			//NeighbourRight = l.getRoomAt(PosX +1, PosY);
			Layout[7][3] = 'F';
			Layout[7][4] = 'F';
		}
	}
	
	public char[][] getLayout(){
		return Layout;
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
				break;
		}
		
		Spawns.remove(c);
	}
	public void MarkEnd() {
		Layout[3][3] = 'E';
		Layout[4][3] = 'E';
		Layout[3][4] = 'E';
		Layout[4][4] = 'E';
	}
	public void CheckForEntered() {
		if(!HasBeenEntered) {
			GameManager.GetInstance().addScore(50);
			HasBeenEntered = true;
		}
	}
}
