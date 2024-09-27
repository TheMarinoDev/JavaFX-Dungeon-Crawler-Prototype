package application;

public class Cell {
    protected int x;
    protected int y;

    public Cell() {
    }
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public boolean equals(Cell c) {
    	return c.getX() == x && c.getY() == y;
    }
}