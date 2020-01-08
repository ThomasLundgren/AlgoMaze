package se.hig.dvg329.algomaze.control.solvers;

/**
 * The {@code MazeSolverFactory} class generates {@link MazeSolver} objects.
 * @author Thomas Lundgren
 * @version 1.0.0
 * @since 1.0.0
 */
public class MazeSolverFactory {

	/**
	 * Creates and returns a {@link MazeSolver} by taking in a {@link String} that should
	 * hold the name of a {@link MazeSolver} that has been implemented. If the name does
	 * not correspond to an implemented algorithm, this method will return {@code null}.
	 * @param algorithm the name of the {@link MazeSolver} to be returned.
	 * @return the {@link MazeSolver} that corresponds to the given {@link String}. If
	 * an invalid name has been passed in, this method returns null.
	 */
	public MazeSolver createMazeSolver(String algorithm) {
		if (algorithm.equals("Dijkstra")) {
			return new DijkstraMazeSolver();
		}
		else {
			return null;
		}
	}
	
}
