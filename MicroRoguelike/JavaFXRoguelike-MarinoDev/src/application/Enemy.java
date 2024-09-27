package application;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends GridEntity{

	private boolean isAlive;
	private boolean OffWalkCounter;
	public Enemy(int X, int Y, char Char) {
		super(X, Y, Char);
		isAlive = true;
		OffWalkCounter = false;
	}

	@Override
	public void Interact(int X, int Y) {
		if(!isAlive) return;//do nothing if this enemy is dead
		
		
		/*
		 * A Star PathFinding Algorithm
		 * 
		 * Every time the player moves, this enemy will use A* to approach the player
		 */
		int PlayerX = GameManager.GetInstance().getPlayer().PosX;
		int PlayerY = GameManager.GetInstance().getPlayer().PosY;
		
		//Initialize Open and Closed lists
		List<AStarCell> openList = new ArrayList<AStarCell>();
		List<AStarCell> closedList = new ArrayList<AStarCell>();
		
		Room room = GameManager.GetInstance().GetCurrentLevel().GetCurrentRoom();
		//Add starting cell to open list
		if(OffWalkCounter) {
			System.out.println("Starting A*");
			AStarCell start = new AStarCell(PosX,PosY);
			start.SetCosts(PosX, PosY, PlayerX, PlayerY);
			openList.add(new AStarCell(PosX,PosY));
			
			AStarCell Path = new AStarCell(PosX,PosY);
			boolean goalFound = false;
			
			while (!openList.isEmpty() && !goalFound) {
			    // Get the cell with the lowest FCost
			    AStarCell currentNode = openList.get(0);
			    for (int i = 0; i < openList.size(); i++) {
			        if (currentNode.GetFCost() > openList.get(i).GetFCost()) {
			            currentNode = openList.get(i);
			        }
			    }

			    System.out.println("Checking: (" + currentNode.x + "," + currentNode.y + ")");

			    // Move it to closed list
			    openList.remove(currentNode);
			    closedList.add(currentNode);

			    // Check if goal is reached
			    if (currentNode.getX() == PlayerX && currentNode.getY() == PlayerY) {
			        Path = currentNode;
			        goalFound = true;
			        openList.clear();
			        break;
			    }

			    // Generate children (neighbors)
			    AStarCell[] children = new AStarCell[4];
			    children[0] = new AStarCell(currentNode.getX() + 1, currentNode.getY());
			    children[1] = new AStarCell(currentNode.getX() - 1, currentNode.getY());
			    children[2] = new AStarCell(currentNode.getX(), currentNode.getY() + 1);
			    children[3] = new AStarCell(currentNode.getX(), currentNode.getY() - 1);

			    for (AStarCell child : children) {
			        if (child.getX() < 0 || child.getY() < 0 || 
			            child.getX() >= GUIManager.getInstance().getGridSize() || 
			            child.getY() >= GUIManager.getInstance().getGridSize() || 
			            closedList.contains(child) || 
			            room.getTileAt(child.getX(), child.getY()) == 'W') {
			            // Invalid child or wall; skip it
			            continue;
			        }

			        child.SetCosts(PosX, PosY, PlayerX, PlayerY);
			        child.SetPrevCell(currentNode);

			        if (!openList.contains(child)) {
			            openList.add(child);
			        }
			    }

			    // If the open list becomes empty and no path is found, terminate to avoid infinite loops
			    if (openList.isEmpty() && !goalFound) {
			        System.out.println("No path to goal, terminating algorithm.");
			        break;
			    }
			}
			//If a goal has been found, follow this process
			if(goalFound) {
				while(Path.GetPrevCell() != null && !Path.GetPrevCell().equals(start)) {
					Path = Path.GetPrevCell();
				}
			}
			System.out.println("Found Path!");
			//finally, set the position
			SetPosition(Path.getX(),Path.getY());
			//wait one turn to move
			OffWalkCounter = false;
		}
		else OffWalkCounter = true;
		
		//Check for what do to
		if(PosX == X && PosY == Y) {
			//The enemy is at ontop of the player!
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
}
