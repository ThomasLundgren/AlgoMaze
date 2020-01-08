package se.hig.dvg329.algomaze.control;

import se.hig.dvg329.algomaze.model.Maze;
import se.hig.dvg329.algomaze.storage.FileMazeDAO;
import se.hig.dvg329.algomaze.storage.MazeDAO;
import se.hig.dvg329.algomaze.view.GUI;
import se.hig.dvg329.algomaze.storage.FileStorageManager;
/**
 * Launches the AlgoMaze program. AlgoMaze generates a randomly generated {@link Maze} of a width and
 * height provided by the user and prints the {@link Maze} to a textfile. 
 * The program must take in command line arguments.
 * The first argument must be the width of the {@link Maze}.
 * The second argument must be the height of the {@link Maze}.
 * The third argument must be the file path that the program should print the {@link Maze} to.
 * @author Thomas Lundgren
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class Main {
	
	private final static MazeDAO MAZE_DAO = new FileMazeDAO();
	private final static FileStorageManager STORAGE_MANAGER = FileStorageManager.getInstance();
	
	/**
	 * Initiates the AlgoMaze program. The program must take in three arguments.
	 * @param args The command line arguments for setting the width and height of
	 * the generated {@link Maze} as well as the file path of the text file that
	 * the {@link Maze} should be written to.
	 * The first argument must be the width of the {@link Maze}. Must be greater than three.
	 * The second argument must be the height of the {@link Maze}. Must be greater than three.
	 * The third argument must be the file path that the program should print the {@link Maze} to.
	 */
	public static void main(String[] args) {
		if (args.length == 3 && args[0] != null && args[1] != null && args[2] != null) {
			int width = 0;
			int height = 0;
			
			try {
				width = Integer.parseInt(args[0]);
			}
			catch (NumberFormatException e) {
				System.err.println("Invalid width provided. Closing program.");
				System.exit(0);
			}
			try {
				height = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException e) {
				System.err.println("Invalid height provided. Closing program.");
				System.exit(0);
			}
			try {
				STORAGE_MANAGER.setSavePath(args[2]);
				MazeController mazeController = new MazeController();
				mazeController.createMaze(width, height);
				mazeController.generateMaze("Prim");
				mazeController.solveMaze("Dijkstra");
				STORAGE_MANAGER.storeData("Maze generated with: " + mazeController.getGeneratorName() + "\n"
						+ "Maze generation time: " + mazeController.getGenerationTime() + " ms\n"
								+ "Maze solved with: " + mazeController.getSolverName() + "\n"
										+ "Maze solving time: " + mazeController.getSolvingTime() + " ms\n\n");
				MAZE_DAO.store(mazeController.getMaze());
				System.out.println("Succesfully generated maze and printed it to: " + STORAGE_MANAGER.getSavePath());
			}
			catch (IllegalArgumentException e) {
				System.err.println("Invalid width or height. Width and height must be larger than three. Closing program.");
				System.exit(0);
			}
		}
		else {
			MazeController mazeController = new MazeController();
			GUI gui = new GUI(mazeController);
			gui.initGUI();
		}
	}
}
