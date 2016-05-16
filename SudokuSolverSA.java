import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SudokuSolverSA
{
	boolean[] squaresUnchangable;
	int x1, y1, x2, y2;	
	int xOffset = 0;
	int yOffset = 0;
	int initConflicts;
	static int[][] first=new int[9][9];
	int square;
	int[][] boardCandidate = new int[9][9];
	int newConflicts;
	int row;
	int col;
	double nextTemperature;
	double probability;
	double random;
	static int[][] board = new int[9][9];
	static int iteration=0;
	int[][] temp;
	int count=0,tab;
	int referenceT;
	static int resets=0;
	static Long time ;
	
	
	public ArrayList<Integer> initNumbers(ArrayList<Integer> numbers)
	{
		numbers.clear();
		for(int i = 1; i <= 9; i++)
			numbers.add(i);
		
		return numbers;
	}
	public void SimulatedAnnealingSolve(int[][] board)
	{
		squaresUnchangable = new boolean[81];
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if(board[i][j] != 0)
					squaresUnchangable[i*9 + j] = true;
			}
		}
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		numbers = initNumbers(numbers);
		
		for(int row = 0; row < 3; row++)
			for(int col = 0; col < 3; col++)
			{
				if(board[row][col] != 0)
					numbers.remove(new Integer(board[row][col]));
			}
		Collections.shuffle(numbers);
		for(int row = 0; row < 3; row++)
			for(int col = 0; col < 3; col++)
			{
				if(board[row][col] == 0)
					board[row][col] = numbers.remove(0);
			}
		
		numbers = initNumbers(numbers);
		
		for(int row = 0; row < 3; row++)
			for(int col = 3; col < 6; col++)
			{
				if(board[row][col] != 0)
					numbers.remove(new Integer(board[row][col]));
			}
		Collections.shuffle(numbers);
		for(int row = 0; row < 3; row++)
			for(int col = 3; col < 6; col++)
			{
				if(board[row][col] == 0)
					board[row][col] = numbers.remove(0);
			}
		
		numbers = initNumbers(numbers);
		
		for(int row = 0; row < 3; row++)
			for(int col = 6; col < 9; col++)
			{
				if(board[row][col] != 0)
					numbers.remove(new Integer(board[row][col]));
			}
		Collections.shuffle(numbers);
		for(int row = 0; row < 3; row++)
			for(int col = 6; col < 9; col++)
			{
				if(board[row][col] == 0)
					board[row][col] = numbers.remove(0);
			}
		
		numbers = initNumbers(numbers);
		
		for(int row = 3; row < 6; row++)
			for(int col = 0; col < 3; col++)
			{
				if(board[row][col] != 0)
					numbers.remove(new Integer(board[row][col]));
			}
		Collections.shuffle(numbers);
		for(int row = 3; row < 6; row++)
			for(int col = 0; col < 3; col++)
			{
				if(board[row][col] == 0)
					board[row][col] = numbers.remove(0);
			}
		
		numbers = initNumbers(numbers);
		
		for(int row = 3; row < 6; row++)
			for(int col = 3; col < 6; col++)
			{
				if(board[row][col] != 0)
					numbers.remove(new Integer(board[row][col]));
			}
		Collections.shuffle(numbers);
		for(int row = 3; row < 6; row++)
			for(int col = 3; col < 6; col++)
			{
				if(board[row][col] == 0)
					board[row][col] = numbers.remove(0);
			}
		
		numbers = initNumbers(numbers);
		
		for(int row = 3; row < 6; row++)
			for(int col = 6; col < 9; col++)
			{
				if(board[row][col] != 0)
					numbers.remove(new Integer(board[row][col]));
			}
		Collections.shuffle(numbers);
		for(int row = 3; row < 6; row++)
			for(int col = 6; col < 9; col++)
			{
				if(board[row][col] == 0)
					board[row][col] = numbers.remove(0);
			}
		
		numbers = initNumbers(numbers);
		
		for(int row = 6; row < 9; row++)
			for(int col = 0; col < 3; col++)
			{
				if(board[row][col] != 0)
					numbers.remove(new Integer(board[row][col]));
			}
		Collections.shuffle(numbers);
		for(int row = 6; row < 9; row++)
			for(int col = 0; col < 3; col++)
			{
				if(board[row][col] == 0)
					board[row][col] = numbers.remove(0);
			}
		
		numbers = initNumbers(numbers);
		
		for(int row = 6; row < 9; row++)
			for(int col = 3; col < 6; col++)
			{
				if(board[row][col] != 0)
					numbers.remove(new Integer(board[row][col]));
			}
		Collections.shuffle(numbers);
		for(int row = 6; row < 9; row++)
			for(int col = 3; col < 6; col++)
			{
				if(board[row][col] == 0)
					board[row][col] = numbers.remove(0);
			}
		
		numbers = initNumbers(numbers);
		
		for(int row = 6; row < 9; row++)
			for(int col = 6; col < 9; col++)
			{
				if(board[row][col] != 0)
					numbers.remove(new Integer(board[row][col]));
			}
		Collections.shuffle(numbers);
		for(int row = 6; row < 9; row++)
			for(int col = 6; col < 9; col++)
			{
				if(board[row][col] == 0)
					board[row][col] = numbers.remove(0);
			}
		
//		for( int row = 0; row < 9; row++ ){
//	    	  System.out.println();
//	    	  if(row==0){System.out.println("\n -----------------------");}
//	         for( int col = 0; col < 9; col++ )
//	            if( board[row][col] != 0 ){
//	            	if(col ==0) {System.out.print("| ");}
//	            	System.out.print(board[row][col] + " ");
//	            	if(col==2 | col == 5 | col ==8){System.out.print("| ");}
//	            }
//	            else{
//	            	if(col ==0) {System.out.print("| ");}
//	            	System.out.print("-" + " ");
//	            	if(col==2 | col == 5 | col ==8){System.out.print("| ");}
//	            }
//	         if(row==2 | row == 5 | row ==8){System.out.print("\n -----------------------");}
//	      }
	      System.out.println();
	      multiArrayCopy(board, first);
		
	      try {
	    	  recurseSolve(board, .8);
	    	  } catch(StackOverflowError t) {
	    	  // more general: catch(Error t)
	    	  // anything: catch(Throwable t)
	    	  System.out.println("Caught at"+iteration+t);
	    	  t.printStackTrace();
	    	  }
		
	}
	
	public int numConflicts(int[][] board)
	{
		int num = 0;
		HashMap<Integer, Integer> numbers = new HashMap<Integer, Integer>();
		
	
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if(numbers.get(board[i][j]) == null)
					numbers.put(board[i][j], 1);
				else
					numbers.put(board[i][j], numbers.get(board[i][j]) + 1);
			}

			for(int j = 1; j <= 9; j++)
			{
				if(numbers.get(j) != null && numbers.get(j) > 1)
				{
					num += numbers.get(j) - 1;
				}
				numbers.put(j, null);
			}
		}
		for(int col = 0; col < 9; col++)
		{
			for(int row = 0; row < 9; row++)
			{
				if(numbers.get(board[row][col]) == null)
					numbers.put(board[row][col], 1);
				else
					numbers.put(board[row][col], numbers.get(board[row][col]) + 1);
			}
			
			for(int j = 1; j <= 9; j++)
			{
				if(numbers.get(j) != null && numbers.get(j) > 1)
				{
					num += numbers.get(j) - 1;
				}
				numbers.put(j, null);
			}
		}
		return num;
	}
	
	public int[][] recurseSolve(int[][] board, double temperature)
	{
		do{
		initConflicts = numConflicts(board);
		square = (int)(Math.random()*9);
		
		
		if(initConflicts == 0)
		{	
			return board;
		}			
		
		switch(square)
		{
			case 0:
				xOffset = 0;
				yOffset = 0;
				break;
			case 1:
				xOffset = 0;
				yOffset = 3;
				break;
			case 2:
				xOffset = 0;
				yOffset = 6;
				break;
			case 3:
				xOffset = 3;
				yOffset = 0;
				break;
			case 4:
				xOffset = 3;
				yOffset = 3;
				break;
			case 5:
				xOffset = 3;
				yOffset = 6;
				break;
			case 6:
				xOffset = 6;
				yOffset = 0;
				break;
			case 7:
				xOffset = 6;
				yOffset = 3;
				break;
			case 8:
				xOffset = 6;
				yOffset = 6;
				break;
		}
		
			
		do {
			x1 = (int)(Math.random()*3);
			y1 = (int)(Math.random()*3);
			x2 = (int)(Math.random()*3);
			y2 = (int)(Math.random()*3);
		} while(squaresUnchangable[(xOffset+x1)*9+(yOffset+y1)] || squaresUnchangable[(xOffset+x2)*9+(yOffset+y2)]);
		
		//System.out.println("x2: "+(xOffset+x2)+" y2: "+(yOffset+y2));
		iteration++;
		
		
		multiArrayCopy(board, boardCandidate);
		boardCandidate[xOffset+x1][yOffset+y1] = board[xOffset+x2][yOffset+y2];
		boardCandidate[xOffset+x2][yOffset+y2] = board[xOffset+x1][yOffset+y1];
		
		newConflicts = numConflicts(boardCandidate);
		
		if(newConflicts < initConflicts){
			multiArrayCopy(boardCandidate, board);
			referenceT=newConflicts;
		}
		else
		{
			probability = Math.exp((initConflicts - newConflicts)/temperature);
			random = Math.random();
			referenceT=initConflicts;
			if(random <= probability){
				multiArrayCopy(boardCandidate, board);
				referenceT=newConflicts;
			}
		}
		
		if(tab==referenceT){
			count++;
		}
		else{
			count=0;
			tab=referenceT;
		}
		if(count>15000){
			iteration-=iteration;
			resets++;
			time = System.currentTimeMillis();
			multiArrayCopy(first,board);
			
		}
		
	      //System.out.println("\nscore is "+referenceT+"\niteration number: "+ iteration+"\nresets is "+resets);
	    
		if(iteration > 100000) 
		{	//System.out.println("\nat the escape");
	
		return null;
		}
		nextTemperature = updateTemp(temperature);
		temperature=nextTemperature;
	}while(true);
	}
	
	public void multiArrayCopy(int[][] source, int[][] destination)
	{
		for (int a=0;a<source.length;a++)
		{
			System.arraycopy(source[a],0,destination[a],0,source[a].length);
		}
	}
	
	public double updateTemp(double temperature)
	{
		temperature *= .8;
		return temperature;
	}
	 static void displaySudoku(int[][] test) {
		    for (int row = 0; row < 9; row++) {
		        for (int col = 0; col < 9; col++){
		            if((col)%3 == 0)
		                System.out.print(" | ");
		            System.out.print(test[row][col]);
		        }
		        System.out.print(" | ");
		        if((row+1)%3 == 0){
		            System.out.println();
		            System.out.print(" -------------------");
		        }
		        System.out.println();
		    }
		 }
	
	
	public static void main(String[] args)
	{		
		board = new int[9][9];
				
			Scanner scanner;
            try {
            scanner = new Scanner(new File(args[0]));
            while(scanner.hasNextInt()){
             for(int i= 0;i<9;i++){
                 for(int j = 0;j<9;j++){
                     board[i][j] = scanner.nextInt();
                 }
             }
        }
         
     } catch (FileNotFoundException ex) {
         System.out.println(ex.getMessage());
     }	
			displaySudoku(board);
		SudokuSolverSA s = new SudokuSolverSA();
		 time = System.currentTimeMillis();
		s.SimulatedAnnealingSolve(board);
		displaySudoku(board);
		time = System.currentTimeMillis()-time;
			System.out.println("SOLUTION");
		 System.out.println("Time taken (ms) : "+time);
		
	}
}
