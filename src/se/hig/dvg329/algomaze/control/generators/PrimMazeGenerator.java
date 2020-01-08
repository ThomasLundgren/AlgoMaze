package se.hig.dvg329.algomaze.control.generators;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import se.hig.dvg329.algomaze.model.Cell;
import se.hig.dvg329.algomaze.model.Maze;
import se.hig.dvg329.algomaze.model.Cell.CellValue;

/**
 * The {@code PrimMazeGenerator} class implements the {@link MazeGenerator}
 * interface. It generates a perfect random maze using Prim's algorithm. Credit
 * for all of the code goes to Jamis Buck. His implementation can be found here:
 * http://weblog.jamisbuck.org/2011/1/10/maze-generation-prim-s-algorithm. Some
 * modifications have been made to port the code from Ruby to Java.
 * 
 * @author Jamis Buck, Thomas Lundgren
 * @version 1.0.0
 * @since 1.0.0
 */
class PrimMazeGenerator implements MazeGenerator {

	private Maze maze;
	private ArrayList<Cell> candidateCells = new ArrayList<>();

	/**
	 * Turns an empty {@link Maze} object into a perfect random maze using Prim's
	 * algorithm. Marks {@link Cell}s with the values {@link CellValue#MARKED} and
	 * {@link CellValue#CANDIDATE}.
	 * 
	 * @param maze An empty {@link Maze} object.
	 */
	@Override
	public void generate(Maze maze) {
		this.maze = maze;

		markCell(ThreadLocalRandom.current().nextInt(0, maze.getWidth()),
				ThreadLocalRandom.current().nextInt(0, maze.getHeight()), CellValue.MARKED);

		while (!candidateCells.isEmpty()) {
			Cell markedCell = candidateCells.remove(ThreadLocalRandom.current().nextInt(0, candidateCells.size()));
			ArrayList<Cell> markedNeighbors = getMarkedNeighbors(markedCell);
			Cell neighbor = markedNeighbors.remove(ThreadLocalRandom.current().nextInt(0, markedNeighbors.size()));

			setDirections(markedCell, neighbor);

			markCell(markedCell.getX(), markedCell.getY(), CellValue.MARKED);
		}
	}

	private void markCell(int x, int y, CellValue value) {
		Cell markedCell = new Cell(x, y, CellValue.MARKED);
		maze.setCell(x, y, CellValue.MARKED);
		if (maze.getNorthNeighborOf(markedCell) != null) {
			addCandidateCell(maze.getNorthNeighborOf(markedCell));
		}
		if (maze.getEastNeighborOf(markedCell) != null) {
			addCandidateCell(maze.getEastNeighborOf(markedCell));
		}
		if (maze.getSouthNeighborOf(markedCell) != null) {
			addCandidateCell(maze.getSouthNeighborOf(markedCell));
		}
		if (maze.getWestNeighborOf(markedCell) != null) {
			addCandidateCell(maze.getWestNeighborOf(markedCell));
		}
	}

	private ArrayList<Cell> getMarkedNeighbors(Cell cell) {
		ArrayList<Cell> markedNeighbors = new ArrayList<>(4);

		if (maze.getNorthNeighborOf(cell) != null && maze.getNorthNeighborOf(cell).getValue() == CellValue.MARKED) {
			markedNeighbors.add(maze.getNorthNeighborOf(cell));
		}
		if (maze.getWestNeighborOf(cell) != null && maze.getWestNeighborOf(cell).getValue() == CellValue.MARKED) {
			markedNeighbors.add(maze.getWestNeighborOf(cell));
		}
		if (maze.getSouthNeighborOf(cell) != null && maze.getSouthNeighborOf(cell).getValue() == CellValue.MARKED) {
			markedNeighbors.add(maze.getSouthNeighborOf(cell));
		}
		if (maze.getEastNeighborOf(cell) != null && maze.getEastNeighborOf(cell).getValue() == CellValue.MARKED) {
			markedNeighbors.add(maze.getEastNeighborOf(cell));
		}
		return markedNeighbors;
	}

	private void addCandidateCell(Cell cell) {
		if (cell.getValue() != CellValue.CANDIDATE && cell.getValue() != CellValue.MARKED) {
			cell.setValue(CellValue.CANDIDATE);
			candidateCells.add(cell);
		}
	}

	private void setDirections(Cell fromCell, Cell toCell) {
		if (fromCell.getY() > toCell.getY()) {
			fromCell.setNorth(true);
			toCell.setSouth(true);
		} else if (fromCell.getX() > toCell.getX()) {
			fromCell.setWest(true);
			toCell.setEast(true);
		} else if (fromCell.getY() < toCell.getY()) {
			fromCell.setSouth(true);
			toCell.setNorth(true);
		} else if (fromCell.getX() < toCell.getX()) {
			fromCell.setEast(true);
			toCell.setWest(true);
		}
	}

	/**
	 * Returns the name of the algorithm used by this MazeGenerator.
	 * 
	 * @return String the name of the algorithm used by this MazeGenerator.
	 */
	public String toString() {
		return "Prim's algorithm";
	}


}
