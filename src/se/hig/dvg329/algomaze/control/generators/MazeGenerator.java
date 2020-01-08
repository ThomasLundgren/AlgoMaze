package se.hig.dvg329.algomaze.control.generators;

import se.hig.dvg329.algomaze.model.Maze;

/**
 * The {@code MazeGenerator} class provides one method: {@link MazeGenerator#generate(Maze)} that generates
 * a perfect random maze. A perfect maze has only one entrance and only one exit and contains no loops.
 * @author Thomas Lundgren
 * @version 1.0.0
 * @since 1.0.0
 */
public interface MazeGenerator {
	
	/**
	 * Turns an empty {@link Maze} object into a perfect random maze.  
	 * @param maze an empty {@link Maze} object.
	 */
	void generate(Maze maze);
}
