package application;

public class AStarCell extends Cell{

	private int GCost;
	private int HCost;
	private int FCost;
	
	private AStarCell cameFrom;
	
	public AStarCell(int x, int y) {
		super(x,y);
	}
	
	public void SetCosts(int StartX, int StartY, int EndX, int  EndY) {
		GCost = Math.abs(StartX - x) + Math.abs(StartY - y);
		HCost = Math.abs(EndX - x) + Math.abs(EndY - y);
		FCost = GCost + HCost;
	}
	public void SetPrevCell(AStarCell c) {
		cameFrom = c;
	}
	public AStarCell GetPrevCell() {
		return cameFrom;
	}
	public int GetFCost() {
		return FCost;
	}
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    
	    AStarCell other = (AStarCell) obj;
	    
	    return this.x == other.x && this.y == other.y;  // Compare based on coordinates
	}
}
