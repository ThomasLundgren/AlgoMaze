package se.hig.dvg329.algomaze.control.solvers;

import java.util.ArrayList;
import java.util.Arrays;

import se.hig.dvg329.algomaze.model.Cell;
import se.hig.dvg329.algomaze.model.Maze;
import se.hig.dvg329.algomaze.model.Cell.CellValue;

/**
 * Solves a given {@link Maze} using Dijkstra's shortest path algorithm.
 * All credit for this implementation goes to Mike Pound. You can find his
 * implementation here: https://github.com/mikepound/mazesolving/blob/master/dijkstra.py
 * Modifications have been made by Thomas Lundgren.
 * @author Mike Pound, Thomas Lundgren
 * @version 1.0.0
 * @since 1.0.0
 */
class DijkstraMazeSolver implements MazeSolver {
	
	/**
	 * Constructs a {@code DijkstraMazeSolver}.
	 */
	public DijkstraMazeSolver() {}
	
	/**
	 * Solves a given {@link Maze} using Dijkstra's shortest path algorithm.
	 * Sets the value of each {@link Cell} it visits to {@link CellValue#VISITED} and set the value
	 * of each {@link Cell} in the solution set to {@link CellValue#SOLUTION}.
	 * @param maze The {@link Maze} to solve.
	 * through the {@link Maze}.
	 */
	@Override
	public void solve(Maze maze) {
		int width = maze.getWidth();
		int height = maze.getHeight();
		int total = width * height;
		Cell start = maze.getStart();
		Cell end = maze.getEnd();
		
		boolean[] visited = new boolean[total];
		
		Cell[] previous = new Cell[total];
		
		int[] distances = new int[total];
		Arrays.fill(distances, Integer.MAX_VALUE);
		
		FibonacciHeap<Cell> unvisited = new FibonacciHeap<>();
		
		ArrayList<FibonacciHeap.Node<Cell>> nodeIndex = new ArrayList<>(total);
		
		for (int i = 0; i <= total; i++) {
			nodeIndex.add(null);
		}
		
		distances[start.getX() * width + start.getY()] = 0;
		FibonacciHeap.Node<Cell> startNode = unvisited.enqueue(start, 0);
		nodeIndex.set(start.getX() * width + start.getY(), startNode);
		
		// Dijkstras algoritm
		while(unvisited.size() > 0) {
			FibonacciHeap.Node<Cell> n = unvisited.dequeueMin();
			
			Cell u = n.getValue();
			int uPosIndex = u.getX() * width + u.getY();
			
			if (distances[uPosIndex] == Integer.MAX_VALUE) {
				break;
			}
			if (u.getX() == end.getX() && u.getY() == end.getY()) {
				break;
			}
			for (int i = 0; i < 4; i++) {
				int vPosIndex = 0;
				Cell v = new Cell(0, 0, CellValue.NONE);
				if (u.hasNorth() && i == 0) {
					vPosIndex = uPosIndex - 1;
					v = maze.getNorthNeighborOf(u);
				}
				else if (u.hasWest() && i == 1) {
					vPosIndex = uPosIndex - width;
					v = maze.getWestNeighborOf(u);
				}
				else if (u.hasSouth() && i == 2) {
					vPosIndex = uPosIndex + 1;
					v = maze.getSouthNeighborOf(u);
				}
				else if (u.hasEast() && i == 3) {
					vPosIndex = uPosIndex + width;
					v = maze.getEastNeighborOf(u);
				}
				
				if (visited[vPosIndex] == false) {
					int d = Math.abs(v.getX() - u.getX()) + Math.abs(v.getY() - u.getY());
					
					int newDistance = distances[uPosIndex] + d;
					
					if (newDistance < distances[vPosIndex]) {
						FibonacciHeap.Node<Cell> vNode = nodeIndex.get(vPosIndex);
						if (vNode == null) {
							vNode = unvisited.enqueue(v, newDistance);
							nodeIndex.set(vPosIndex, vNode);
							distances[vPosIndex] = newDistance;
							previous[vPosIndex] = u;
						}
						else {
							unvisited.decreaseKey(vNode, newDistance);
							distances[vPosIndex] = newDistance;
							previous[vPosIndex] = u;
						}
					}
				}
			}
			visited[uPosIndex] = true;
			u.setValue(CellValue.VISITED);
		}
		Cell current = end;
		while (current != null) {
			markAsSolution(current);
			current = previous[current.getX() * width + current.getY()];
		}
 	}

	/**
	 * Returns the name of the algorithm used in this {@link MazeSolver}.
	 * @return the name of the algorithm used in this {@link MazeSolver}. 
	 */
	public String toString() {
		return "Dijkstra's shortest path algorithm";
	}
}
