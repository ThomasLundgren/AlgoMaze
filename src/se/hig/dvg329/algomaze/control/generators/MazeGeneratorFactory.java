package se.hig.dvg329.algomaze.control.generators;

/**
 * The {@code MazeGeneratorFactory} class generates {@link MazeGenerator} objects.
 * @author Thomas Lundgren
 * @version 1.0.0
 * @since 1.0.0
 */
public class MazeGeneratorFactory {
	
	/**
	 * Creates a {@link MazeGenerator} object.
	 * @param algorithm The algorithm to use.
	 * @return the created {@link MazeGenerator}.
	 */
	public MazeGenerator createGenerator(String algorithm) {
		if (algorithm.equals("Prim")) {
			return new PrimMazeGenerator();
		}
		else {
			return null;
		}
	}
}
