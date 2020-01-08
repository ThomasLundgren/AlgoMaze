package se.hig.dvg329.algomaze.model;

import java.util.UUID;

import se.hig.dvg329.algomaze.model.Cell.CellValue;

/**
 * The {@code Maze} class is used to represent a maze. It holds {@link Cell}s that describe each cell (square)
 * of the maze. An important method of this class is the {@link Maze#toString()} method. 
 * @author Thomas Lundgren
 * @version 1.0.0
 * @since 1.0.0
 */
public class Maze {
	
	private Cell[][] grid;
	private Cell start, end;
	private UUID id;
	private final int width, height;
	private boolean empty = true;
	
	/**
	 * Constructs a {@code Maze} with the provided width and height.
	 * @param width the width of the {@code Maze} i.e. the number of {@link Cell}s in each
	 * row of the {@code Maze}. Must be greater than three.
	 * @param height the height of the {@code Maze}, i.e. the number of {@link Cell}s in each
	 * column of the {@code Maze}. Must be greater than three.
	 * @throws IllegalArgumentException if an application tries to provide a width or height smaller
	 * than three.
	 */
	public Maze(int width, int height) throws IllegalArgumentException {
		if (width < 3 || height < 3) {
			throw new IllegalArgumentException("A maze must have width and height greater than 3.");
		}
		id = UUID.randomUUID();
		this.width = width;
		this.height = height;
		
		grid = new Cell[height][width];
		clear();
	}

	public void setCell(int x, int y, CellValue value) {
		grid[y][x].setValue(value);
	}
	
	public Cell getCell(int x, int y) {
		return grid[y][x];
	}
	
	public void setStart(Cell start) {
		this.start = start;
	}
	
	public void setEnd(Cell end) {
		this.end = end;
	}
	
	public Cell getStart() {
		if (start == null) {
			throw new MazeEmptyException("Cannot return the start of an empty maze.");
		}
		return start;
	}
	
	public Cell getEnd() {
		if (end == null) {
			throw new MazeEmptyException("Cannot return the end of an empty maze.");
		}
		return end;
	}
	
	public void clear() {
		for (int y = 0; y < height; y ++) {
			for (int x = 0; x < width; x++) {
				grid[y][x] = new Cell(x, y, CellValue.NONE);
			}	
		}
		empty = true;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Cell getNorthNeighborOf(Cell cell) {
		if (cell.getY() > 0) {
			return grid[cell.getY() - 1][cell.getX()];
		}
		else {
			return null;
		}
	}
	
	public Cell getWestNeighborOf(Cell cell) {
		if (cell.getX() > 0) {
			return grid[cell.getY()][cell.getX() - 1];
		}
		else {
			return null;
		}
	}
	
	public Cell getSouthNeighborOf(Cell cell) {
		if (cell.getY() + 1 < height) {
			return grid[cell.getY() + 1][cell.getX()];
		}
		else {
			return null;
		}
	}
	
	public Cell getEastNeighborOf(Cell cell) {
		if (cell.getX() + 1 < width) {
			return grid[cell.getY()][cell.getX() + 1];
		}
		else {
			return null;
		}
	}
	
	/**
	 * Returns a {@link String} representation of a {@code Maze}.
	 * @return a {@link String} representation of a {@code Maze}.
	 */
	public String toString() {
		String str = "";
		/*
		 * We will be writing two lines of the maze in each iteration of the inner for-loop
		 * below. This is because a Cell two rows high, example of a cell with west and south wall:
		 * |    
		 * |---
		 * Example of a cell with east and south walls:
		 *    |
		 * ---|
		 */
		String firstLine = "";
		String secondLine = "";
		
		// Write top row, "ceiling"
		for (int x = 0; x < width; x++) {
			if (x == 0) {
				str += ",";
			}
			if (x == start.getX()) {
				str += " #S ";
			}
			else if (x == width - 1) {
				str += "---.";
			}
			else {
				str += "----";
			}
		}
		
		str += "\n";
		
		for (int y = 0; y < height; y++) {
			firstLine = "";
			secondLine = "";
			for (int x = 0; x < width; x++) {
				// West wall of the Maze
				if (x == 0) {
					if (y == height - 1) {
						firstLine += "|";
						secondLine += "'";
					}
					else {
						firstLine += "|";
						secondLine += "|";
					}
					
				}
				/* Scan the maze from top left to bottom right
				 * and only look for and write east and south walls/openings. */
				
				Cell curr = grid[y][x];
				if (curr.getValue() == CellValue.SOLUTION) {
					firstLine += " # ";
				}
				else {
					firstLine += "   ";
				}
				// Write east wall/opening
				if (curr.hasEast()) {
					firstLine += " ";
				}
				else {
					firstLine += "|";
				}
				
				// Write
				if (curr == end) {
					secondLine += " #E ";
				}
				else if (y == height - 1) {
					if (x < width - 1) {
						secondLine += "----";
					}
					else {
						secondLine += "---'";
					}
				}
				else {
					if (curr.hasSouth()) {
						if (!curr.hasEast()) {
							secondLine += "   |";
						}
						else {
							secondLine += "    ";
						}
					}
					else {
						if (!curr.hasEast()) {
							secondLine += "---|";
						}
						else {
							secondLine += "----";
						}
					}
				}
			}
			firstLine += "\n";
			secondLine += "\n";
			str += firstLine + secondLine;
		}
		return str;
	}

	public boolean isEmpty() {
		return empty;
	}
	
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	
	public UUID getId() {
		return id;
	}
}

