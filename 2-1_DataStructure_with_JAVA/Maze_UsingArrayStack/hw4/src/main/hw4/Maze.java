package hw4;
/*
 * CSE2010 Homework #4: Maze.java
 *
 * Complete the code below.
 */

import java.util.Arrays;

public class Maze {
	private final int numRows;
	private final int numCols;

	private int next_row;
	private int next_col;
	private static ArrayStack<Location> _path = new ArrayStack<>(4);

	private int[][] maze;
	private boolean[][] visited;

	private final Location entry; // Entry Location
	private final Location exit;  // Exit Location

	private final ArrayStack<Location> _stack = new ArrayStack<>(100);

	public Maze(int[][] maze, Location entry, Location exit) {
		this.maze = maze;
		numRows = maze.length;
		numCols = maze[0].length;
		visited = new boolean[numRows][numCols]; // initialized to false

		this.entry = entry;
		this.exit = exit;
	}

	// For testing purpose
	public void printMaze() {
		System.out.println("Maze[" + numRows + "][" + numCols + "]");
		System.out.println("Entry index = (" + entry.row + ", " + entry.col + ")");
		System.out.println("Exit index = (" + exit.row + ", " + exit.col + ")" + "\n");

		for (int i = 0; i < numRows; i++) {
			System.out.println(Arrays.toString(maze[i]));
		}
		System.out.println();
	}

	public boolean findPath() {
		return moveTo(entry.row, entry.col);
	}

	private boolean moveTo(int row, int col) {
		if(_stack.isEmpty()) { // If '_stack' is empty or current Location is a start Location,
			_stack.push(new Location(row, col)); // push current Location(or start Location).
			visited[row][col] = true; // Modify the visit history.
		}
		// base case
		if(row == exit.row && col == exit.col) return true;

		// Search the path ordering north, east, south, and west.
		while(!_path.isEmpty()) _path.pop(); // initialize _path
		_path.push(new Location(0, -1)); // west
		_path.push(new Location(1, 0)); // south
		_path.push(new Location(0, 1)); // east
		_path.push(new Location(-1, 0)); // north -> top

		while(!_path.isEmpty()) {
			next_row = row + _path.top().row;
			next_col = col + _path.top().col;
			_path.pop();
			// First, we have to prevent the "Array index out of bounds" exception.
			// And then, Check that the next Location is possible.
			// If it is possible, push the Location to _stack and modify the visit history.
			// Find the next Location by using the recursion.
			if(((0 <= next_row && next_row < numRows) && (0 <= next_col && next_col < numCols)) &&
					maze[next_row][next_col] == 0 && !visited[next_row][next_col]) {
				_stack.push(new Location(next_row, next_col));
				visited[next_row][next_col] = true;
				return moveTo(next_row, next_col);
			}
		}
		// If it escapes the while statement, we can't find the next Location from the current.
		// => ① Pop the current Location from _stack.
		//	  ② Check the next Location from the previous again by using recursion.
		try {
			_stack.pop();
			return moveTo(_stack.top().row, _stack.top().col);
		} catch(EmptyStackException e) { // If _stack is empty, there is no path to reach the exit.
			return false;
		}
	}

	public String getPath() {
		StringBuilder builder = new StringBuilder();
		while (!_stack.isEmpty()) {
			builder.append(_stack.pop() + " <-- ");
		}
		builder.append("Start");
		return builder.toString();
	}

}
