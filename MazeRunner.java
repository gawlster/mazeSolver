// Connor Gawley
// V00955266

public class MazeRunner {
	Maze mazeToSolve;
	A5Stack<MazeLocation> path;
	FilePrinter fileWriter;
	
	public MazeRunner(Maze aMaze) {
		mazeToSolve = aMaze;
		path = new A5Stack<MazeLocation>();
		fileWriter = new FilePrinter();
	}
	
	/*
	 * Purpose: Determines whether there is a path from start to finish in this maze
	 * Parameters: MazeLocation start - starting coordinates of this maze
	 *			   MazeLocation finish - finish coordinates of this maze
	 * Returns: true if there is a path from start to finish
	 */
	public boolean solve(MazeLocation start, MazeLocation finish) {
		fileWriter.println("Searching maze from start: "+start+" to finish: "+finish);
		path.push(start);
		return findPath(start, finish);
	}
	
	/*
	 * Purpose: Recursively determines if there is a path from cur to finish
	 * Parameters: MazeLocation cur - current cordinates in this maze
	 *			   MazeLocation finish - goal coordinates of this maze
	 * Returns: true if there is a path from cur to finish
	 *
	 * NOTE: This method updates the Maze's mazeData array when locations
	 *       are visited to an 'o', and further updates locations to an 'x'
	 *       if it is determined they lead to dead ends. If the finish 
	 *       location is found, the Stack named path should contain all of 
	 *       loations visited from the start location to the finish. 
	 */
	private boolean findPath(MazeLocation cur, MazeLocation finish) {
		// these lines get the current row and column number for the array
		int row = cur.getRow();
		int col = cur.getCol();

		// this line sets the current position to have an 'o' character, representing visited
		mazeToSolve.setChar(row, col, 'o');
		// prints to the file
		fileWriter.println("\n"+mazeToSolve.toString());
		
		// checks if the current position is equal to the end... if yes, returns true
		if (row == finish.getRow() && col == finish.getCol()) {
			System.out.println("finishes");
			return true;
		}
		
		
		// the following blocks decide whether there is an open neighboring space
		// if yes, it creates a new MazeLocation for the new space, pushes it onto
		//the path stack and recursivley calls findPath with the new location
		if (mazeToSolve.getChar(row + 1, col) != 'H'
				&& mazeToSolve.getChar(row + 1, col) != 'o'
				&& mazeToSolve.getChar(row + 1, col) != 'x') {
					// goes down
			MazeLocation next = new MazeLocation(row + 1, col);
			path.push(next);
			return findPath(next, finish);
		}
		
		else if(mazeToSolve.getChar(row, col + 1) != 'H'
				&& mazeToSolve.getChar(row, col + 1) != 'o'
				&& mazeToSolve.getChar(row, col + 1) != 'x') {
					// goes right
			MazeLocation next = new MazeLocation(row, col + 1);
			path.push(next);
			return findPath(next, finish);
		}
		
		else if(mazeToSolve.getChar(row - 1, col) != 'H'
				&& mazeToSolve.getChar(row - 1, col) != 'o'
				&& mazeToSolve.getChar(row - 1, col) != 'x') {
					// goes up
			MazeLocation next = new MazeLocation(row - 1, col);
			path.push(next);
			return findPath(next, finish);
		}
		
		else if(mazeToSolve.getChar(row, col - 1) != 'H'
				&& mazeToSolve.getChar(row, col - 1) != 'o'
				&& mazeToSolve.getChar(row, col - 1) != 'x') {
					// goes left
			MazeLocation next = new MazeLocation(row, col - 1);
			path.push(next);
			return findPath(next, finish);
		}
		
		
		// now we need to handle when there is not an open space for the maze to traverse to
		if (!path.isEmpty()) {
			// set the space to an x
			mazeToSolve.setChar(row, col, 'x');
			// pop the top element off the stack
			path.pop();
			// call findPath with the previous location and the finish
			// this way if there is an open space from there we will keep going, but if not
			// the previous space will also become an x and we will go to the space
			// before that one
			return findPath(path.top(), finish);
		}
		
		
		// this line is only reached if there are no elements left in the stack: 
		// if the maze is unsolveable, therefore we must return false
		return false;
		
	}
	

	/*
	 * Purpose: Creates a string of maze locations found in the stack 
	 * Parameters: None
	 * Returns: A String representation of maze locations
	 */
	public String getPathToSolution() {
		String details = "";
		while(!path.isEmpty()) {
			details = path.pop() + "\n" + details;
		}	
		return details;
	}
	
	/*
	 * Purpose: Print the results of the maze run. Outputs the locations 
	 *          visited on the path from start to finish if the maze is 
	 *          solvable, or that no path was found if it is not.
	 * Parameters: boolean - whether or not the maze was solved
	 * Returns void - nothing
	 */
	public void printResults(boolean solved) {
		if (solved) {
			fileWriter.println("\n*** Maze Solved ***");
			fileWriter.println(getPathToSolution());
		} else {
			fileWriter.println("\n--- No path to solution found ---");
		}
		fileWriter.close();
	}
}