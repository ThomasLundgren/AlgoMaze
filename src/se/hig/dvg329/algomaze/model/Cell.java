package se.hig.dvg329.algomaze.model;

import java.util.Observable;

/**
 * The {@code Cell} class represent cells of a {@link Maze}. The may also be thought of the nodes
 * or squares of a {@link Maze}. Each {@code Cell} holds x and y coordinates as well as information
 * about which of its wall are tore down and which are still erect. Each {@code Cell} can be marked
 * with a value that can be used by other classes to assign specific values to represent some state
 * of the {@code Cell}.
 * @author Thomas Lundgren
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class Cell extends Observable {
	
	private int x, y;
	private CellValue value;
	private boolean north, west, south, east;
	
	public enum CellValue {
		NONE, SOLUTION, MARKED, CANDIDATE, VISITED;
	}
	
	public Cell(int x, int y, CellValue value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	public void setValue(CellValue value) {
		this.value = value;
		setChanged();
		notifyObservers();
	}
	
	@Override
	public void notifyObservers() {
		super.notifyObservers();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public CellValue getValue() {
		return value;
	}

	public void setNorth(boolean north) {
		if (north) {
			this.north = true;
		}
		else {
			this.north = false;
		}
		setChanged();
		notifyObservers();
	}
	
	public void setWest(boolean west) {
		if (west) {
			this.west = true;
		}
		else {
			this.west = false;
		}
		setChanged();
		notifyObservers();
	}
	
	public void setSouth(boolean south) {
		if (south) {
			this.south = true;
		}
		else {
			this.south = false;
		}
		setChanged();
		notifyObservers();
	}
	
	public void setEast(boolean east) {
		if (east) {
			this.east = true;
		}
		else {
			this.east = false;
		}
		setChanged();
		notifyObservers();
	}
	
	public boolean hasNorth() {
		return north;
	}
	
	public boolean hasWest() {
		return west;
	}
	
	public boolean hasSouth() {
		return south;
	}
	
	public boolean hasEast() {
		return east;
	}
}
