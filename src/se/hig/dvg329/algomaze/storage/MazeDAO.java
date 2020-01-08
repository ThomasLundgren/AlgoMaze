package se.hig.dvg329.algomaze.storage;

import java.util.UUID;

import se.hig.dvg329.algomaze.model.Maze;

public interface MazeDAO {
	
	void store(Maze maze);
	Maze load(UUID id);
	
}
