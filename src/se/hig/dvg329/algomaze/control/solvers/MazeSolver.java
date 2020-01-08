package se.hig.dvg329.algomaze.control.solvers;

import java.util.Stack;

import se.hig.dvg329.algomaze.model.Cell;
import se.hig.dvg329.algomaze.model.Maze;
import se.hig.dvg329.algomaze.model.Cell.CellValue;

/**
 * A {@code MazeSolver} takes a {@link Maze} as a parameter and returns a {@link Stack} containing
 * {@link Cell}s that comprise the solution to the {@link Maze}. A {@code MazeSolver} should mark all
 * {@link Cell}s that are part of the solution with the value {@link CellValue#SOLUTION} using the
 * {@link Cell#setValue(se.hig.dvg329.algomaze.model.Cell.CellValue)} method.
 */
public interface MazeSolver {

	/**
	 * Solves a given {@link Maze} and return a {@link Stack} containing the solution.
	 * @param maze the {@link Maze} to be solved.
	 */
	void solve(Maze maze);
	
	/**
	 * Sets the value of a {@link Cell} to {@link CellValue#SOLUTION} to indicate that the
	 * {@link Cell} is a part of the solution.
	 * @param cell the {@link Cell} to mark as being part of the solution.
	 */
	default void markAsSolution(Cell cell) {
		cell.setValue(CellValue.SOLUTION);
	};
	
}
