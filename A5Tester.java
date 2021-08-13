import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;

public class A5Tester {

	private static int testPassCount = 0;
	private static int testCount = 0;
	
	public static void main(String[] args) {
		testStackOperations();
		testStackIsGeneric();
		testExceptions();
		testMazeRunner(args);
		
		System.out.println("Passed " + testPassCount + " / " + testCount + " tests");
	}
	
	public static void testStackOperations() {
		System.out.println("Stack Operations Tests:");
		System.out.println("Testing with an Integer implementation, check empty, push 2, check empty, test top");
		A5Stack<Integer> testStack = new A5Stack<Integer>();
		int result = 0;
		
		displayResults(testStack.isEmpty(), "stack initially empty");
				
		testStack.push(2);
		result = testStack.top();
		displayResults(!testStack.isEmpty(), "stack no longer empty");
		displayResults(result==2, "top works after initial push \n");
		
		System.out.println("Testing with a Character implementation, check empty, push 'A', push 'B', test top, pop, test top, pop, check empty");
		Character b;
		A5Stack<Character> testStack2 = new A5Stack<Character>();
		displayResults(testStack2.isEmpty(), "stack initially empty");
		testStack2.push('A');
		testStack2.push('B');
		b = testStack2.top();
		displayResults(b == 'B', "top works after initial two pushes");
		b = testStack2.pop();
		displayResults(b == 'B', "pop works after first pop");
		b = testStack2.top();
		displayResults(b == 'A', "top works after second run through");
		b = testStack2.pop();
		displayResults(b == 'A', "pop works after second run through");
		displayResults(testStack2.isEmpty(), "stack is now empty at the end");
		
		
		
		//TODO: add more tests here
				
		System.out.println();
	}
	
	public static void testStackIsGeneric() {
		System.out.println("Stack Generics Tests:");
		A5Stack<Integer> s1 = new A5Stack<Integer>();
		A5Stack<String> s2 = new A5Stack<String>();
		A5Stack<Double> s3 = new A5Stack<Double>();
		
		int result1;
		String result2;
		double result3;
		
		s1.push(3);
		s1.push(8);
		s2.push("CSC");
		s2.push("ENGR");
		s3.push(5.5);
		s3.push(9.1);
		
		result1 = s1.pop();
		result2 = s2.pop();
		result3 = s3.pop();
		
		displayResults(result1==8, "Integer Stack");
		displayResults(result2.equals("ENGR"), "String Stack");
		displayResults(result3==9.1, "Double Stack");		
		
		result1 = s1.top();
		result2 = s2.top();
		result3 = s3.top();
		
		displayResults(result1==3, "Integer Stack");
		displayResults(result2.equals("CSC"), "String Stack");
		displayResults(result3==5.5, "Double Stack");
		
		//TODO: add more tests here
		
		System.out.println();
	}
	
	public static void testExceptions() {
		System.out.println("Stack Exception Tests:");
		A5Stack<Integer> s1 = new A5Stack<Integer>();
		
		try {
			s1.pop();
			displayResults(false, "popping from empty stack should throw exception");
		} catch (EmptyStackException e) {
			displayResults(true, "popping from empty stack should throw StackEmptyException");
		} catch (Exception e) {
			displayResults(false, "popping from empty stack should throw exception");
		}
		
	
		try {
			s1.top();
			displayResults(false, "trying to get top from empty stack should throw exception");
		} catch (EmptyStackException e) {
			displayResults(true, "trying to get top from empty stack should throw StackEmptyException");
		} catch (Exception e) {
			displayResults(false, "trying to get top from empty stack should throw exception");
		}
		
		//TODO: add more tests here
		
		System.out.println();
	}
	
	public static void testMazeRunner(String[] args) {
		if (args.length < 1) {
			displayResults(false, "testing MazeRunner");
            System.err.println("You must specify a maze file");
			System.err.println("Usage: java A5Tester <mazefile>\n");
			return;
        }
		
		System.out.println("testing MazeRunner with "+args[0]);
		System.out.println("Results are in A5-output.txt");
		Maze maze = initialize(args[0]);
		MazeRunner mr = new MazeRunner(maze);
		boolean solved = mr.solve(maze.getStart(), maze.getFinish());
		mr.printResults(solved);
	}
	
	/*
	 * Purpose: Creates a maze by reading the contents of an input file
	 * Parameters: String - name of the input file containing maze data
	 * Returns: Maze - the maze created from input file data
	 */
	public static Maze initialize(String data) {
        try {
            Scanner infileScanner = new Scanner(new File(data));
			
			int rows = infileScanner.nextInt();
			int columns = infileScanner.nextInt();
			int startRow = infileScanner.nextInt();
			int startColumn = infileScanner.nextInt();
			int finishRow = infileScanner.nextInt();
			int finishColumn = infileScanner.nextInt();
			infileScanner.nextLine();
		
			char[][] mazeData = new char[rows][columns];

			for (int row = 0; row < rows; row++) {
				String line = infileScanner.nextLine();
				for (int col = 0; col < columns; col++) {
					mazeData[row][col] = line.charAt(col);
				}
			}
			return new Maze(mazeData, new MazeLocation(startRow, startColumn), new MazeLocation(finishRow, finishColumn));
		} catch (FileNotFoundException ex) {
			System.out.println("Error scanning file "+data);
			System.exit(2);
		} catch(NoSuchElementException ex) {
			System.out.println("Maze data file is not formatted correctly, should be:");
			System.out.println("<num rows> <num columns>");
			System.out.println("<start row> <start column>");
			System.out.println("<finish row> <finish column>");
			System.out.println("<Maze data>");
			System.out.println("Example:");
			System.out.println("7 8");
			System.out.println("0 1");
			System.out.println("6 6");
			System.out.println("H HHHHHH");
			System.out.println("H    H H");
			System.out.println("HHHH H H");
			System.out.println("H      H");
			System.out.println("H HHHHHH");
			System.out.println("H      H");
			System.out.println("HHHHHH H");
			System.exit(3);
		}
		return null;
	}

	public static void displayResults (boolean passed, String testName) {
       /* There is some magic going on here getting the line number
        * Borrowed from:
        * http://blog.taragana.com/index.php/archive/core-java-how-to-get-java-source-code-line-number-file-name-in-code/
        */
        
        testCount++;
        if (passed)
        {
            System.out.println ("Passed test: " + testName);
            testPassCount++;
        }
        else
        {
            System.out.println ("Failed test: " + testName + " at line "
                                + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
    }
	
}