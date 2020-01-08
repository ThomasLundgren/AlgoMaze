package se.hig.dvg329.algomaze.storage;

import java.util.UUID;

import se.hig.dvg329.algomaze.model.Maze;

/**
 * Is used to store {@link Maze} objects to text files.
 * @author Thomas Lundgren
 * @version 1.0.0
 * @since 1.0.0
 */
public class FileMazeDAO implements MazeDAO {

	FileStorageManager storageManager;
	
	public FileMazeDAO() {
		storageManager = FileStorageManager.getInstance();
	}
	
	@Override
	public void store(Maze maze) {	
		storageManager.storeData(maze.toString());
	}

	@Override
	public Maze load(UUID id) {
		return null;
	}

}
