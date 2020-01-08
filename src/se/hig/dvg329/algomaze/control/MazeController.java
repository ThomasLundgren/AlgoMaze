package se.hig.dvg329.algomaze.control;

import java.util.Observer;
import java.util.concurrent.ThreadLocalRandom;

import se.hig.dvg329.algomaze.control.generators.MazeGenerator;
import se.hig.dvg329.algomaze.control.generators.MazeGeneratorFactory;
import se.hig.dvg329.algomaze.control.solvers.MazeSolver;
import se.hig.dvg329.algomaze.control.solvers.MazeSolverFactory;
import se.hig.dvg329.algomaze.model.Maze;

/**
 * The {@code MazeController} class is responsible for handling the currently used {@link Maze}.
 * The {@link MazeController#generateMaze(String)} and {@link MazeController#solveMaze(String)} methods
 * are used to generate and solve {@link Maze}s, respectively. The {@code MazeController} class
 * @author Thomas Lundgren
 *
 */
public class MazeController {

	private Maze maze;
	private MazeGenerator generator;
	private MazeSolver solver;
	private final MazeGeneratorFactory generatorFactory = new MazeGeneratorFactory();
	private final MazeSolverFactory solverFactory = new MazeSolverFactory();
	private Timer timer = new Timer();
	private double generationTime;
	private double solvingTime;
	
	/**
	 * Constructs a {@code MazeController}.
	 */
	public MazeController() {}
	
	/**
	 * Creates a {@link Maze} object.
	 * @param width the width of the {@link Maze}.
	 * @param height the height of the {@link Maze}.
	 * @return the created {@link Maze}.
	 */
	public Maze createMaze(int width, int height) {
		maze = new Maze(width, height);
		return maze;
	}
	
	/**
	 * Generates a random {@link Maze} using the algorithm provided as the method's argument.
	 * This method will turn an empty {@link Maze} into a randomized perfect {@link Maze}.
	 * The {@link Maze} used is the {@link Maze} lastly created by calling the {@link MazeController#createMaze(int, int)}
	 * method.
	 * @param algorithm the algorithm to be used to generate the {@link Maze}. The only implemented
	 * method currently is "Prim".
	 * @throws IllegalArgumentException if an application tries to call this method with a String
	 * representing a not yet implemented algorithm as an argument.
	 */
	public void generateMaze(String algorithm) throws IllegalArgumentException {
		if (!maze.isEmpty()) {
			maze.clear();
		}
		generator = generatorFactory.createGenerator(algorithm);
		if (generator == null) {
			throw new IllegalArgumentException("The chosen algorithm has not been implemented.");
		}
		maze.setStart(maze.getCell(ThreadLocalRandom.current().nextInt(0, maze.getWidth()), 0));
		maze.setEnd(maze.getCell(ThreadLocalRandom.current().nextInt(0, maze.getWidth()), maze.getHeight() - 1));
		
		timer.startTimer();
		generator.generate(maze);
		generationTime = timer.endTimer();
		
		maze.setEmpty(false);
	}
	
	/**
	 * Solves a previously generated {@link Maze}.
	 * @param algorithm the algorithm to be used to solve the {@link Maze}. The only implemented
	 * algorithm so far is "Dijkstra".
	 * @throws IllegalArgumentException if an application tries to call this method with a String
	 * representing a not yet implemented algorithm as an argument.
	 */
	public void solveMaze(String algorithm) throws IllegalArgumentException {
		solver = solverFactory.createMazeSolver(algorithm);
		if (solver == null) {
			throw new IllegalArgumentException("The chosen algorithm has not been implemented.");
		}
		timer.startTimer();
		solver.solve(maze);
		solvingTime = timer.endTimer();
	}
	
	/**
	 * Returns the lastly created {@link Maze}.
	 * @return the lastly created {@link Maze}.
	 */
	public Maze getMaze() {
		return maze;
	}
	
	/**
	 * Returns the name of the {@link MazeSolver} last used to solve the lastly created
	 * {@link Maze}.
	 * @return the name of the {@link MazeSolver} last used to solve the lastly created
	 * {@link Maze}.
	 */
	public String getSolverName() {
		return solver.toString();
	}
	
	/**
	 * Returns the name of the {@link MazeGenerator} last used to solve the lastly created
	 * {@link Maze}.
	 * @return the name of the {@link MazeGenerator} last used to solve the lastly created
	 * {@link Maze}.
	 */
	public String getGeneratorName() {
		return generator.toString();
	}
	
	/**
	 * Returns the time it took to generate the lastly generated {@link Maze}.
	 * DISCLAIMER: Note that all overhead operations are included in the generation time, it
	 * does not tell you how fast a particular algorithm is, but rather how fast
	 * it is in the context of this application. Note also that it should never
	 * be compared to maze solving times, since the overhead of these operations
	 * are different.
	 * @return the generation time of the lastly generated maze.
	 */
	public double getGenerationTime() {
		return generationTime;
	}
	
	/**
	 * Returns the time it took to generate the lastly solved {@link Maze}.
	 * DISCLAIMER: Note that all overhead operations are included in the solving time, it
	 * does not tell you how fast a particular algorithm is, but rather how fast
	 * it is in the context of this application. Note also that it should never
	 * be compared to maze generation times, since the overhead of these operations
	 * are different.
	 * @return the solving time of the lastly solved maze.
	 */
	public double getSolvingTime() {
		return solvingTime;
	}
	
	public void registerObserver(Observer observer) {
		for (int y = 0; y < maze.getHeight(); y++) {
			for (int x = 0; x < maze.getWidth(); x++) {
				maze.getCell(x, y).addObserver(observer);
			}	
		}	
	}
	
	public void deleteObserver(Observer observer) {
		for (int y = 0; y < maze.getHeight(); y++) {
			for (int x = 0; x < maze.getWidth(); x++) {
				maze.getCell(x, y).deleteObserver(observer);
			}	
		}
	}
	
	public static void main(String[] args) {
		boolean[][] maze = new boolean[39][37];
		
		for (int y = 0; y < maze[0].length; y++) {
			
			for (int x = 0; x < maze.length; x++) {
				
			}
		}
	}
	
}
